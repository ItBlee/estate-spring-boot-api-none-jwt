package com.itblee.converter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ResultSetExtractor<T> {
    T extractData(ResultSet resultSet) throws SQLException;
    List<Map<String, Object>> extractRows(ResultSet resultSet) throws SQLException;
}
