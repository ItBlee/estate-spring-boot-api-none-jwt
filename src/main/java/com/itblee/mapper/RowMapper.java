package com.itblee.mapper;

import java.sql.ResultSet;
import java.util.List;

public interface RowMapper<T> {
	List<T> processResultSet(ResultSet resultSet);
	T mapRow(ResultSet resultSet);
	T mergeRow(ResultSet resultSet, T oldRow);
}
