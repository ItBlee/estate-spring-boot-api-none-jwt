package com.itblee.converter;

import java.sql.ResultSet;

public interface RowConverter<T> {
	T convertRow(ResultSet resultSet);
}
