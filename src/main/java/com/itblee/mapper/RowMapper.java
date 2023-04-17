package com.itblee.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface RowMapper<T> {
	List<Map<String, Object>> getRow(ResultSet resultSet) throws SQLException;
	T mapRow(Map<String, Object> row) throws SQLException;
	T mergeRow(Map<String, Object> row, T mergeTo) throws SQLException;
}
