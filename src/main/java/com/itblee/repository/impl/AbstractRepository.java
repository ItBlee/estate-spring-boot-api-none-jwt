package com.itblee.repository.impl;

import com.itblee.exception.ErrorRepositoryException;
import com.itblee.mapper.ResultSetExtractor;
import com.itblee.repository.GenericRepository;
import com.itblee.utils.ConnectionUtils;

import java.sql.*;
import java.util.List;

public abstract class AbstractRepository<T> implements GenericRepository<T> {

	@Override
	public List<T> query(String sql, ResultSetExtractor<List<T>> extractor, Object... params) {
		List<T> results;
		ResultSet resultSet = null;
		try (Connection connection = ConnectionUtils.createConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
			ConnectionUtils.setParameter(statement, params);
			resultSet = statement.executeQuery();
			results = extractor.extractData(resultSet);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new ErrorRepositoryException(e);
		} finally {
			ConnectionUtils.close(resultSet);
		}
		return results;
	}

	@Override
	public Long insert(String sql, Object... params) {
		Long id = null;
		ResultSet resultSet = null;
		try (Connection connection = ConnectionUtils.createConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			try {
				connection.setAutoCommit(false);
				ConnectionUtils.setParameter(statement, params);
				statement.executeUpdate();
				resultSet = statement.getGeneratedKeys();
				if (resultSet.next()) {
					id = resultSet.getLong(1);
				}
				connection.commit();
				return id;
			} catch (SQLException e) {
				connection.rollback();
				e.printStackTrace();
				throw new ErrorRepositoryException(e);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new ErrorRepositoryException(e);
		} finally {
			ConnectionUtils.close(resultSet);
		}
	}

	@Override
	public void update(String sql, Object... params) {
		try (Connection connection = ConnectionUtils.createConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
			try {
				connection.setAutoCommit(false);
				ConnectionUtils.setParameter(statement, params);
				statement.executeUpdate();
				connection.commit();
			} catch (SQLException e) {
				connection.rollback();
				e.printStackTrace();
				throw new ErrorRepositoryException(e);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new ErrorRepositoryException(e);
		}
	}

}
