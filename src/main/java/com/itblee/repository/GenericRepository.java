package com.itblee.repository;

import com.itblee.converter.RowConverter;

import java.util.List;

public interface GenericRepository<T> {
	List<T> query(String sql, RowConverter<T> rowMapper, Object... parameters);
	Long insert(String sql, Object... parameters);
	void update(String sql, Object... parameters);
}
