package com.itblee.converter;

import java.util.Map;
import java.util.Optional;

public interface RowMapper<T> {
	Optional<T> mapRow(Map<String, ?> row);
	Optional<T> groupRow(Map<String, ?> row, T mergeTo);
}
