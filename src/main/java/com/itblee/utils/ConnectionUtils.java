package com.itblee.utils;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ResourceBundle;

public final class ConnectionUtils {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("Application");

    private static MysqlDataSource dbSource;

    public static MysqlDataSource getDataSource() {
        if (dbSource == null)
            dbSource = new MysqlDataSource();
        dbSource.setServerName(bundle.getString("db.server"));
        dbSource.setPortNumber(Integer.parseInt(bundle.getString("db.port")));
        dbSource.setDatabaseName(bundle.getString("db.name"));
        dbSource.setUser(bundle.getString("db.username"));
        dbSource.setPassword(bundle.getString("db.password"));
        return dbSource;
    }

    public static Connection createConnection() throws SQLException, ClassNotFoundException {
        Class.forName(bundle.getString("db.driver"));
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

    public static void close(AutoCloseable... obs) {
        try {
            for (AutoCloseable o : obs)
                if (o != null)
                    o.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ConnectionUtils() {
        throw new AssertionError();
    }

}
