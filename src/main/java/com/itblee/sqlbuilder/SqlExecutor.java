package com.itblee.sqlbuilder;

import com.itblee.entity.BaseEntity;
import com.itblee.exception.ErrorRepositoryException;
import com.itblee.util.ConnectionUtils;

import java.sql.*;
import java.util.List;

public class SqlExecutor {

    public static <T extends BaseEntity> List<T> query(String sql, Class<T> entityClass, Object... params) {
        List<T> results;
        ResultSet resultSet = null;
        try (Connection connection = ConnectionUtils.createConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ConnectionUtils.setParameter(statement, params);
            resultSet = statement.executeQuery();
            results = SqlExtractor.extractData(resultSet, entityClass);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorRepositoryException(e);
        } finally {
            ConnectionUtils.close(resultSet);
        }
        return results;
    }

    public static Long insert(String sql, Object... params) {
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
                    int firstColumnIndex = 1;
                    id = resultSet.getLong(firstColumnIndex);
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

    public static void update(String sql, Object... params) {
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
