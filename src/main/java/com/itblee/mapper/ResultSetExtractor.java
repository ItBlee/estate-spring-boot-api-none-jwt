package com.itblee.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetExtractor<T> {
    T extractData(ResultSet resultSet) throws SQLException;
}
