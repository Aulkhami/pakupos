package com.aulkhami.mavenproject1.database;

import com.aulkhami.mavenproject1.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {

    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String url = DatabaseConfig.getMySqlUrl();
            String user = DatabaseConfig.getMySqlUsername();
            String pass = DatabaseConfig.getMySqlPassword();
            try {
                Class.forName(DatabaseConfig.getMySqlDriver());
            } catch (ClassNotFoundException ignored) {
            }
            connection = DriverManager.getConnection(url, user, pass);
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ignored) {
            } finally {
                connection = null;
            }
        }
    }
}
