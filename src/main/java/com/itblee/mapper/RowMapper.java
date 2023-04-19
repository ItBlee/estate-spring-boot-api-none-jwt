package com.itblee.mapper;

import java.util.Map;

public interface RowMapper<T> {
	T mapRow(Map<String, Object> row);
	T mergeRow(Map<String, Object> row, T mergeTo);
}
