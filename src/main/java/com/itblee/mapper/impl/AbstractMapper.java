package com.itblee.mapper.impl;

import com.itblee.entity.BaseEntity;
import com.itblee.mapper.ModelMapper;
import com.itblee.mapper.ResultSetExtractor;
import com.itblee.mapper.RowMapper;
import com.itblee.repository.ConditionKey;
import com.itblee.utils.StringUtils;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

import static com.itblee.utils.MapUtils.get;

public abstract class AbstractMapper<T extends BaseEntity> implements ResultSetExtractor<List<T>>, RowMapper<T> {

    @Override
    public List<T> extractData(ResultSet resultSet) throws SQLException {
        Map<Long, T> map = new HashMap<>();
        for (Map<String, Object> row : extractRows(resultSet)) {
            Long id = get(row, "id", Long.class);
            if (id == null)
                continue;
            T obj = map.getOrDefault(id, null);
            if (obj == null)
                obj = mapRow(row);
            try {
                /*merge duplicate building row if any
                merge fail -> Illegal exception*/
                obj = mergeRow(row, obj);
                map.put(obj.getId(), obj);
            } catch (IllegalArgumentException ignored) {}
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

    protected Optional<T> quickMap(Map<String, Object> row, Class<T> tClass, Class<? extends ConditionKey> keyClass) {
        try {
            final String SET_KEYWORD = "set";
            T instance = tClass.newInstance();
            Set<Method> methods = new HashSet<>();
            for (Method method : tClass.getMethods()) {
                if (method.getName().contains(SET_KEYWORD))
                    methods.add(method);
            }
            Map<String, ConditionKey> keyMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
            for (ConditionKey key : keyClass.getEnumConstants())
                if (key.getParam() != null)
                    keyMap.put(key.getParam(), key);
            for (Method method : methods) {
                String paramName = StringUtils.formatAlphaOnly(method.getName())
                        .replaceFirst(SET_KEYWORD, "")
                        .toLowerCase();
                ConditionKey key = keyMap.get(paramName);
                if (key != null)
                    method.invoke(instance, get(row, key.getParam(), key.props().getType()));
            }
            return Optional.of(instance);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    protected <E> E convert(Object object, Class<E> convertTo) {
        return ModelMapper.getInstance().mapModel(object, convertTo);
    }

    protected <K, V> List<K> convert(Collection<V> collection, Class<K> convertTo) {
        return ModelMapper.getInstance().mapModel(collection, convertTo);
    }

}
