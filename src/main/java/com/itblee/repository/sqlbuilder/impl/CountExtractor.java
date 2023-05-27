package com.itblee.repository.sqlbuilder.impl;

import com.itblee.repository.sqlbuilder.SqlExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CountExtractor implements SqlExtractor {

    @Override
    public <T> List<T> extractData(ResultSet resultSet, Class<T> tClass) throws SQLException {
        int count = resultSet.getInt("count");
        return new ArrayList<>(count);
    }

    @Override
    public List<Map<String, Object>> extractRows(ResultSet resultSet) throws SQLException {
        throw new UnsupportedOperationException();
    }

}
