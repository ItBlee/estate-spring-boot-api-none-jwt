package com.itblee.mapper.impl;

import com.itblee.mapper.ModelMapper;
import com.itblee.mapper.ResultSetExtractor;
import com.itblee.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractMapper<T> implements ResultSetExtractor<List<T>>, RowMapper<T> {

    @Override
    public List<T> extractData(ResultSet resultSet) throws SQLException {
        List<T> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(mapRow(resultSet));
        }
        return list;
    }

    public <E> E convert(Object object, Class<E> convertTo) {
        return ModelMapper.getInstance().mapModel(object, convertTo);
    }

    public <K, V> List<K> convert(Collection<V> collection, Class<K> convertTo) {
        return ModelMapper.getInstance().mapModel(collection, convertTo);
    }

}
