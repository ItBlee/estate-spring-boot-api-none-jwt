package com.itblee.sqlbuilder;

import com.itblee.entity.BaseEntity;
import com.itblee.sqlbuilder.model.MappedResult;
import com.itblee.util.CastUtils;
import com.itblee.util.MapUtils;
import com.itblee.util.ValidateUtils;
import com.itblee.util.function.ThrowableBiConsumer;
import com.itblee.util.function.ThrowableFunction;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.*;

public class SqlExtractor {

    static <T extends BaseEntity> List<T> extractData(ResultSet resultSet, Class<T> entityClass) throws SQLException {
        ValidateUtils.requireNonNull(resultSet);
        ValidateUtils.requireNonNull(entityClass);
        Map<Long, T> groupById = new HashMap<>();
        Map<Long, MappedResult> results = new LinkedHashMap<>();
        for (Map<String, Object> row : extractRows(resultSet)) {
            Long id = MapUtils.get(row, "id", Long.class);
            if (id == null)
                throw new SQLSyntaxErrorException("Query without ID.");
            T instance = groupById.computeIfAbsent(id, (ThrowableFunction<Long, T>) inst -> entityClass.newInstance());
            results.computeIfAbsent(id, result -> new MappedResult())
                    .add(mapRowToEntity(instance, row));
        }
        results.forEach((id, result) -> {
            result.setTitle(entityClass.getSimpleName() + " " + id);
            result.print();
        });
        return new ArrayList<>(groupById.values());
    }

    static List<Map<String, Object>> extractRows(ResultSet resultSet) throws SQLException {
        List<Map<String, Object>> row = new ArrayList<>();
        ResultSetMetaData md = resultSet.getMetaData();
        while (resultSet.next()) {
            Map<String, Object> col = new LinkedHashMap<>();
            for (int i = 1; i <= md.getColumnCount(); i++) {
                if (resultSet.getObject(i) != null)
                    col.put(md.getColumnLabel(i), resultSet.getObject(i));
            }
            row.add(col);
        }
        return row;
    }

    private static MappedResult mapRowToEntity(BaseEntity entity, Map<String, ?> row) {
        ValidateUtils.requireNonNull(row);
        ValidateUtils.requireNonNull(entity);
        MappedResult results = new MappedResult();
        Map<Field, Object> fieldsIsChild = new LinkedHashMap<>();
        row.forEach((ThrowableBiConsumer<String, Object>)
                (colName, colValue) -> results.add(mapFieldIfMatch(entity, fieldsIsChild, colName, colValue, true)));
        setFinalValueForFieldsIsChild(entity, fieldsIsChild);
        return results;
    }

    private static MappedResult mapFieldIfMatch(Object instance, Map<Field, Object> fieldsIsChild, String colName, Object colValue, boolean isRoot) throws IllegalAccessException {
        MappedResult results = new MappedResult();
        StringTokenizer colNameTokens = new StringTokenizer(colName, ".");
        String findFieldName = colNameTokens.nextToken();
        getSetterOfNullFields(instance).forEach((ThrowableBiConsumer<Field, Method>) (field, setter) -> {
            if (findFieldName.equalsIgnoreCase(field.getName())) {
                Class<?> fieldType = field.getType();
                boolean isCollection = Collection.class.isAssignableFrom(fieldType);
                boolean isEntity = BaseEntity.class.isAssignableFrom(fieldType);
                boolean isChild = isCollection || isEntity;
                if (isChild) {
                    if (isCollection)
                        fieldType = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                    Object childInstance = fieldsIsChild.getOrDefault(field, fieldType.newInstance());
                    String childFindName = colName.replaceFirst(findFieldName + ".","");
                    results.add(mapFieldIfMatch(childInstance, fieldsIsChild, childFindName, colValue, false));
                    fieldsIsChild.put(field, childInstance);
                } else if (!colNameTokens.hasMoreTokens()) {
                    try {
                        setter.invoke(instance, CastUtils.cast(colValue, fieldType).orElse(null));
                    } catch (Exception e) {
                        throw new RuntimeException("Value of column "
                                + instance.getClass().getSimpleName() + "." + colName
                                + "=" + colValue
                                + " not match " + instance.getClass().getSimpleName() + "." + field.getName()
                                +  " type " + fieldType.getSimpleName(), e.getCause());
                    }
                } else return;
                if (isRoot) {
                    results.newResult()
                            .setFieldType(fieldType.getSimpleName())
                            .setEntityField(instance.getClass().getSimpleName() + "." + field.getName())
                            .setColumnName(colName)
                            .setColumnValue(colValue)
                            .add();
                }
            }
        });
        return results;
    }

    private static void setFinalValueForFieldsIsChild(Object instance, Map<Field, Object> fieldIsChild) {
        if (fieldIsChild == null)
            return;
        fieldIsChild.forEach((ThrowableBiConsumer<Field, Object>) (field, value) -> {
            boolean accessible = field.isAccessible();
            field.setAccessible(true);
            if (Collection.class.isAssignableFrom(field.getType())) {
                Object rootCollection = field.get(instance);
                Method addElement = rootCollection.getClass().getMethod("add", Object.class);
                addElement.invoke(rootCollection, value);
            } else field.set(instance, value);
            field.setAccessible(accessible);
        });
    }

    private static List<Field> getFields(Object instance) {
        List<Field> fields = new ArrayList<>();
        Class<?> fieldType = instance.getClass().getSuperclass();
        while (fieldType != Object.class) {
            fields.addAll(Arrays.asList(fieldType.getDeclaredFields()));
            fieldType = fieldType.getSuperclass();
        }
        fields.addAll(Arrays.asList(instance.getClass().getDeclaredFields()));
        return fields;
    }

    private static Map<Field, Method> getSetterOfNullFields(Object instance) throws IllegalAccessException {
        List<Field> fields = getFields(instance);
        Map<Field, Method> setters = new LinkedHashMap<>();
        for (Field field : fields) {
            boolean accessible = field.isAccessible();
            field.setAccessible(true);
            if (field.get(instance) == null || field.get(instance) instanceof Collection) {
                Arrays.stream(instance.getClass().getMethods())
                        .filter(m -> m.getName().equalsIgnoreCase("set" + field.getName()))
                        .filter(m -> m.getParameterTypes().length ==  1)
                        .filter(m -> m.getParameterTypes()[0].equals(field.getType()))
                        .findFirst()
                        .ifPresent(method -> setters.put(field, method));
            }
            field.setAccessible(accessible);
        }
        return setters;
    }

}
