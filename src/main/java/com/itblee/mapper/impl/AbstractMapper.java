package com.itblee.mapper.impl;

import com.itblee.entity.BaseEntity;
import com.itblee.mapper.ModelMapper;
import com.itblee.mapper.ResultSetExtractor;
import com.itblee.mapper.RowMapper;
import com.itblee.repository.query.ConditionKey;
import com.itblee.utils.MapUtils;
import com.itblee.utils.StringUtils;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.*;

public abstract class AbstractMapper<T extends BaseEntity> implements ResultSetExtractor<List<T>>, RowMapper<T> {

    @Override
    public List<T> extractData(ResultSet resultSet) throws SQLException {
        Map<Long, T> map = new HashMap<>();
        for (Map<String, Object> row : extractRows(resultSet)) {
            Long id = MapUtils.get(row, "id", Long.class);
            if (id == null)
                throw new SQLSyntaxErrorException("Query without ID.");
            T obj = map.getOrDefault(id, null);
            if (obj == null)
                obj = mapRow(row);
            try {
                /*merge duplicate building row if any
                merge fail -> Illegal exception*/
                obj = mergeRow(row, obj);
                map.put(obj.getId(), obj);
            } catch (IllegalStateException ignored) {}
        }
        return new ArrayList<>(map.values());
    }

    @Override
    public List<Map<String, Object>> extractRows(ResultSet resultSet) throws SQLException {
        List<Map<String, Object>> row = new ArrayList<>();
        ResultSetMetaData md = resultSet.getMetaData();
        while (resultSet.next()) {
            Map<String, Object> column = new LinkedHashMap<>();
            for (int i = 1; i <= md.getColumnCount(); i++)
                column.put(md.getColumnLabel(i), resultSet.getObject(i));
            row.add(column);
        }
        return row;
    }

    protected Optional<T> mapByKey(Map<String, Object> row, Class<T> tClass, Class<? extends ConditionKey> keyClass) {
        final String SETTER_KEYWORD = "set";
        Set<Method> setters = new HashSet<>();
        for (Method method : tClass.getMethods()) {
            if (method.getName().contains(SETTER_KEYWORD))
                setters.add(method);
        }
        Map<String, ConditionKey> keyMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for (ConditionKey key : keyClass.getEnumConstants()) {
            keyMap.put(key.getParamName(), key);
        }
        try {
            T instance = tClass.newInstance();
            for (Method setter : setters) {
                String setterName = StringUtils.formatAlphaOnly(setter.getName())
                        .replaceFirst(SETTER_KEYWORD, "")
                        .toLowerCase();
                ConditionKey key = keyMap.getOrDefault(setterName, null);
                if (key != null)
                    setter.invoke(instance, MapUtils.get(row, key.getParamName(), key.getType()));
            }
            return Optional.of(instance);
        } catch (ReflectiveOperationException  e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    protected <E> E convert(Object object, Class<E> convertTo) {
        return ModelMapper.getInstance().mapModel(object, convertTo);
    }

    protected <K, V> List<K> convert(Collection<V> collection, Class<K> convertTo) {
        return ModelMapper.getInstance().mapModel(collection, convertTo);
    }

}
