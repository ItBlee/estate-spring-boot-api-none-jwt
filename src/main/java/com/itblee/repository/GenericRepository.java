package com.itblee.repository;

import com.itblee.mapper.ResultSetExtractor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface GenericRepository<T> {
	Connection createConnection() throws SQLException, ClassNotFoundException;
	void setParameter(PreparedStatement statement, Object... parameters) throws SQLException;
	void close(AutoCloseable... obs);

	List<T> query(String sql, ResultSetExtractor<List<T>> rowMapper, Object... parameters);
	Long insert(String sql, Object... parameters);
	void update(String sql, Object... parameters);
}
