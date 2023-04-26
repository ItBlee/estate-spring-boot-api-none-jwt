package com.itblee.converter.impl;

import com.itblee.converter.ResultSetExtractor;
import com.itblee.converter.RowConverter;
import com.itblee.entity.BaseEntity;
import com.itblee.repository.builder.SqlKey;
import com.itblee.utils.MapUtils;
import com.itblee.utils.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.*;

public abstract class AbstractConverter<T extends BaseEntity> implements ResultSetExtractor<List<T>>, RowConverter<T> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<T> extractData(ResultSet resultSet) throws SQLException {
        Map<Long, T> groupId = new HashMap<>();
        for (Map<String, Object> row : extractRows(resultSet)) {
            Long id = MapUtils.get(row, "id", Long.class);
            if (id == null)
                throw new SQLSyntaxErrorException("Query without ID.");
            T instance = groupId.getOrDefault(id, null);
            if (instance == null)
                instance = convertRow(row);
            try {
                /*merge duplicate building row if any
                merge fail -> Illegal exception*/
                instance = mergeRow(row, instance);
                groupId.put(instance.getId(), instance);
            } catch (IllegalStateException ignored) {}
        }
        return new ArrayList<>(groupId.values());
    }

    @Override
    public List<Map<String, Object>> extractRows(ResultSet resultSet) throws SQLException {
        List<Map<String, Object>> row = new ArrayList<>();
        ResultSetMetaData md = resultSet.getMetaData();
        while (resultSet.next()) {
            Map<String, Object> col = new LinkedHashMap<>();
            for (int i = 1; i <= md.getColumnCount(); i++) {
                col.put(md.getColumnLabel(i), resultSet.getObject(i));
            }
            row.add(col);
        }
        return row;
    }

    protected Optional<T> convertByKey(Map<String, ?> row, Class<T> tClass, Class<? extends SqlKey> keyClass) {
        final String SETTER_KEYWORD = "set";
        Set<Method> setters = new HashSet<>();
        for (Method method : tClass.getMethods()) {
            if (method.getName().startsWith(SETTER_KEYWORD))
                setters.add(method);
        }
        Map<String, SqlKey> keyMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for (SqlKey key : keyClass.getEnumConstants()) {
        	keyMap.put(key.getParamName(), key);
        }
        try {
            T instance = tClass.newInstance();
            for (Method setter : setters) {
                String setterName = StringUtils.formatAlphaOnly(setter.getName())
                        .replaceFirst(SETTER_KEYWORD, "")
                        .toLowerCase();
                SqlKey key = keyMap.getOrDefault(setterName, null);
                if (key != null
                        && key.getType() == setter.getParameterTypes()[0])
                    setter.invoke(instance, MapUtils.get(row, key.getParamName(), key.getType()));
            }
            return Optional.of(instance);
        } catch (ReflectiveOperationException  e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    protected <E> E convert(Object object, Class<E> convertTo) {
        return modelMapper.map(object, convertTo);
    }

    protected <K, V> List<K> convert(Collection<V> collection, Class<K> convertTo) {
        List<K> results = new ArrayList<>();
        for (V element : collection)
            results.add(convert(element, convertTo));
        return results;
    }

    /*protected <E> E convert(Object object, Class<E> convertTo) {
        return ModelMapper.getInstance().mapModel(object, convertTo);
    }

    protected <K, V> List<K> convert(Collection<V> collection, Class<K> convertTo) {
        return ModelMapper.getInstance().mapModel(collection, convertTo);
    }*/

}
