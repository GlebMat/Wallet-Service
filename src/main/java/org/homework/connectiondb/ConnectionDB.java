package org.homework.connectiondb;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectionDB {
    private final DatabaseConfig config;
    public ConnectionDB(DatabaseConfig config) {
        this.config = config;
    }
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(config.getUrl(), config.getUsername(), config.getPassword());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
