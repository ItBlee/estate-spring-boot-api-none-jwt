package com.itblee.repository.impl;

import com.itblee.entity.BaseEntity;
import com.itblee.exception.RepositoryException;
import com.itblee.converter.RowConverter;
import com.itblee.repository.GenericRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public abstract class AbstractRepository<T extends BaseEntity> implements GenericRepository<T> {

	final ResourceBundle bundle = ResourceBundle.getBundle("Application");

	public void driverTest() throws ClassNotFoundException {
		try {
			Class.forName(bundle.getString("db.driver"));
		} catch (ClassNotFoundException e) {
			throw new ClassNotFoundException("Driver not found " + e.getMessage());
		}
	}

	public Connection getConnection() throws SQLException, ClassNotFoundException {
		driverTest();
		String url = bundle.getString("db.url");
		String username = bundle.getString("db.username");
		String password = bundle.getString("db.password");
		return DriverManager.getConnection(url, username, password);
	}

	private void setParameter(PreparedStatement statement, Object... parameters) throws SQLException {
		for (int i = 0; i < parameters.length; i++) {
			Object parameter = parameters[i];
			int index = i + 1;
			if (parameter instanceof Long) {
				statement.setLong(index, (Long) parameter);
			} else if (parameter instanceof String) {
				statement.setString(index, (String) parameter);
			} else if (parameter instanceof Integer) {
				statement.setInt(index, (Integer) parameter);
			} else if (parameter instanceof Timestamp) {
				statement.setTimestamp(index, (Timestamp) parameter);
			} else if (parameter instanceof Boolean) {
				statement.setBoolean(index, (Boolean) parameter);
			}
		}
	}

	@Override
	public List<T> query(String sql, RowConverter<T> rowMapper, Object... parameters) {
		List<T> results = new ArrayList<>();
		ResultSet resultSet = null;
		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement(sql)) {
			setParameter(statement, parameters);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				results.add(rowMapper.convertToEntity(resultSet));
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new RepositoryException(e);
		} finally {
			close(resultSet);
		}
		return results;
	}

	@Override
	public Long insert(String sql, Object... parameters) {
		Long id = null;
		ResultSet resultSet = null;
		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			try {
				connection.setAutoCommit(false);
				setParameter(statement, parameters);
				statement.executeUpdate();
				resultSet = statement.getGeneratedKeys();
				if (resultSet.next()) {
					id = resultSet.getLong(1);
				}
				connection.commit();
			} catch (SQLException e) {
				connection.rollback();
				e.printStackTrace();
				throw new RepositoryException(e);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new RepositoryException(e);
		} finally {
			close(resultSet);
		}
		return id;
	}

	@Override
	public void update(String sql, Object... parameters) {
		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement(sql)) {
			try {
				connection.setAutoCommit(false);
				setParameter(statement, parameters);
				statement.executeUpdate();
				connection.commit();
			} catch (SQLException e) {
				connection.rollback();
				e.printStackTrace();
				throw new RepositoryException(e);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new RepositoryException(e);
		}
	}

	private void close(AutoCloseable... obs) {
		try {
			for (AutoCloseable o : obs) {
				if (o != null)
					o.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
