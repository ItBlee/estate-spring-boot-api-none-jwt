package com.itblee.repository;

import com.itblee.mapper.ResultSetExtractor;

import java.util.List;

public interface GenericRepository<T> {
	List<T> query(String sql, ResultSetExtractor<List<T>> rowMapper, Object... params);
	Long insert(String sql, Object... params);
	void update(String sql, Object... params);
}
