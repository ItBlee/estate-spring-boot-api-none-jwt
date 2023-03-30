package com.itblee.converter;

import com.itblee.entity.BaseEntity;

import java.sql.ResultSet;

public interface RowConverter<T extends BaseEntity> {
	T convertToEntity(ResultSet resultSet);
}
