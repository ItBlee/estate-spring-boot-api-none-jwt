package com.itblee.mapper.impl;

import com.itblee.mapper.ModelMapper;
import com.itblee.mapper.ResultSetExtractor;
import com.itblee.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

public abstract class AbstractMapper<T> implements ResultSetExtractor<List<T>>, RowMapper<T> {

    @Override
    public List<T> extractData(ResultSet resultSet) throws SQLException {
        List<T> list = new ArrayList<>();
        for (Map<String, Object> row : getRow(resultSet)) {
            list.add(mapRow(row));
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> getRow(ResultSet resultSet) throws SQLException {
        List<Map<String, Object>> rows = new ArrayList<>();
        ResultSetMetaData md = resultSet.getMetaData();
        while (resultSet.next()) {
            Map<String, Object> columns = new LinkedHashMap<String, Object>();
            for (int i = 1; i <= md.getColumnCount(); i++)
                columns.put(md.getColumnLabel(i), resultSet.getObject(i));
            rows.add(columns);
        }
        return rows;
    }

    public <E> E convert(Object object, Class<E> convertTo) {
        return ModelMapper.getInstance().mapModel(object, convertTo);
    }

    public <K, V> List<K> convert(Collection<V> collection, Class<K> convertTo) {
        return ModelMapper.getInstance().mapModel(collection, convertTo);
    }

}
