package com.itblee.converter.impl;

import com.itblee.converter.ResultSetExtractor;
import com.itblee.converter.RowMapper;
import com.itblee.entity.BaseEntity;
import com.itblee.util.MapUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.*;

public abstract class AbstractConverter<T extends BaseEntity> implements ResultSetExtractor<List<T>>, RowMapper<T> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<T> extractData(ResultSet resultSet) throws SQLException {
        Map<Long, T> groupById = new HashMap<>();
        for (Map<String, Object> row : extractRows(resultSet)) {
            Long id = MapUtils.get(row, "id", Long.class);
            if (id == null)
                throw new SQLSyntaxErrorException("Query without ID.");
            T instance = groupById.getOrDefault(id, mapRow(row).orElse(null));
            groupRow(row, instance)
                    .ifPresent(inst -> groupById.put(inst.getId(), inst));
        }
        return new ArrayList<>(groupById.values());
    }

    @Override
    public List<Map<String, Object>> extractRows(ResultSet resultSet) throws SQLException {
        List<Map<String, Object>> row = new ArrayList<>();
        ResultSetMetaData md = resultSet.getMetaData();
        while (resultSet.next()) {
            Map<String, Object> col = new HashMap<>();
            for (int i = 1; i <= md.getColumnCount(); i++) {
                col.put(md.getColumnLabel(i), resultSet.getObject(i));
            }
            row.add(col);
        }
        return row;
    }

    protected <E> E convert(Object object, Class<E> convertTo) {
    	if (object == null)
    	    return null;
    	if (object.getClass() == convertTo)
    	    return convertTo.cast(object);
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
