package com.itblee.utils;

import com.itblee.converter.ResultSetExtractor;
import com.itblee.exception.ErrorRepositoryException;
import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;

public final class ConnectionUtils {

    private ConnectionUtils() {
        throw new AssertionError();
    }

    static final ResourceBundle bundle = ResourceBundle.getBundle("Application");
    private static DataSource dbSource;

    public static DataSource getDataSource() {
        if (dbSource == null) {
            MysqlDataSource source = new MysqlDataSource();
            source.setServerName(bundle.getString("db.server"));
            source.setPortNumber(Integer.parseInt(bundle.getString("db.port")));
            source.setDatabaseName(bundle.getString("db.name"));
            source.setUser(bundle.getString("db.username"));
            source.setPassword(bundle.getString("db.password"));
            dbSource = source;
        }
        return dbSource;
    }

    public static Connection createConnection() throws SQLException, ClassNotFoundException {
        return getDataSource().getConnection();
    }

    public static void setParameter(PreparedStatement statement, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            Object parameter = params[i];
            int index = i + 1;
            if (parameter instanceof Long) {
                statement.setLong(index, (Long) parameter);
            } else if (parameter instanceof String) {
                statement.setString(index, (String) parameter);
            } else if (parameter instanceof Integer) {
                statement.setInt(index, (Integer) parameter);
            } else if (parameter instanceof Date) {
                statement.setDate(index, (Date) parameter);
            }
        }
    }

    public static <T> List<T> query(String sql, ResultSetExtractor<List<T>> extractor, Object... params) {
        List<T> results;
        ResultSet resultSet = null;
        try (Connection connection = createConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            setParameter(statement, params);
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

    public static Long insert(String sql, Object... params) {
        Long id = null;
        ResultSet resultSet = null;
        try (Connection connection = createConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            try {
                connection.setAutoCommit(false);
                setParameter(statement, params);
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
            close(resultSet);
        }
    }

    public static void update(String sql, Object... params) {
        try (Connection connection = createConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            try {
                connection.setAutoCommit(false);
                setParameter(statement, params);
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

    public static void close(AutoCloseable... obs) {
        try {
            for (AutoCloseable o : obs)
                if (o != null)
                    o.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
