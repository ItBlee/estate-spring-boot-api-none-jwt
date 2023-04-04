package com.itblee.repository.impl;

import com.itblee.exception.ErrorRepositoryException;
import com.itblee.mapper.ResultSetExtractor;
import com.itblee.repository.GenericRepository;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.sql.*;
import java.util.List;

public abstract class AbstractRepository<T> implements GenericRepository<T> {

	@Autowired
	private Environment env;

	private MysqlDataSource source;

	private void testDriver() throws ClassNotFoundException {
		try {
			Class.forName(env.getProperty("db.driver"));
		} catch (ClassNotFoundException e) {
			throw new ClassNotFoundException("Driver not found " + e.getMessage());
		}
	}

	private MysqlDataSource getDataSource() {
		if (source == null)
			source = new MysqlDataSource();
		source.setServerName(env.getProperty("db.server"));
		source.setPortNumber(Integer.parseInt(env.getProperty("db.port")));
		source.setDatabaseName(env.getProperty("db.name"));
		source.setUser(env.getProperty("db.username"));
		source.setPassword(env.getProperty("db.password"));
		return source;
	}

	@Override
	public Connection createConnection() throws SQLException, ClassNotFoundException {
		testDriver();
		return getDataSource().getConnection();
	}

	@Override
	public void setParameter(PreparedStatement statement, Object... parameters) throws SQLException {
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
			}
		}
	}

	@Override
	public void close(AutoCloseable... obs) {
		try {
			for (AutoCloseable o : obs) {
				if (o != null)
					o.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<T> query(String sql, ResultSetExtractor<List<T>> extractor, Object... parameters) {
		List<T> results;
		ResultSet resultSet = null;
		try (Connection connection = createConnection();
			 PreparedStatement statement = connection.prepareStatement(sql)) {
			setParameter(statement, parameters);
			resultSet = statement.executeQuery();
			results = extractor.extractData(resultSet);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new ErrorRepositoryException(e);
		} finally {
			close(resultSet);
		}
		return results;
	}

	@Override
	public Long insert(String sql, Object... parameters) {
		Long id = null;
		ResultSet resultSet = null;
		try (Connection connection = createConnection();
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
				throw new ErrorRepositoryException(e);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new ErrorRepositoryException(e);
		} finally {
			close(resultSet);
		}
		return id;
	}

	@Override
	public void update(String sql, Object... parameters) {
		try (Connection connection = createConnection();
			 PreparedStatement statement = connection.prepareStatement(sql)) {
			try {
				connection.setAutoCommit(false);
				setParameter(statement, parameters);
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
