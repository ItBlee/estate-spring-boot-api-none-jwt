package com.itblee.repository;

import java.util.List;

import com.itblee.mapper.RowMapper;

public interface GenericRepository<T> {
	List<T> query(String sql, RowMapper<T> rowMapper, Object... parameters);
	Long insert(String sql, Object... parameters);
	void update(String sql, Object... parameters);
}
