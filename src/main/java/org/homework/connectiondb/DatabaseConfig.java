package org.homework.connectiondb;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
public class DatabaseConfig {
    private Properties properties = new Properties();
    public DatabaseConfig() {
        try {
            properties.load(new FileInputStream("database.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load database.properties", e);
        }
    }
    public String getUrl() {
        return properties.getProperty("db.url");
    }
    public String getUsername() {
        return properties.getProperty("db.username");
    }
    public String getPassword() {
        return properties.getProperty("db.password");
    }
}
