package com.itblee.repository;

import java.util.List;

import com.itblee.converter.RowConverter;
import com.itblee.entity.BaseEntity;

public interface GenericRepository<T extends BaseEntity> {
	List<T> query(String sql, RowConverter<T> rowMapper, Object... parameters);
	Long insert(String sql, Object... parameters);
	void update(String sql, Object... parameters);
}
