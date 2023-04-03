package com.itblee.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<T> {
	T mapRow(ResultSet resultSet) throws SQLException;
	T mergeRow(ResultSet resultSet, T mergeTo) throws SQLException;
}
