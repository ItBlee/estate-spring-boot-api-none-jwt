package com.itblee.converter;

import java.util.Map;

public interface RowConverter<T> {
	T convertRow(Map<String, ?> row);
	T mergeRow(Map<String, ?> row, T mergeTo);
}
