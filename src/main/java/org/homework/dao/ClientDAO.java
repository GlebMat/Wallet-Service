package org.homework.dao;

import org.homework.connectiondb.ConnectionDB;
import org.homework.domain.Client;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;

public class ClientDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/wallet_service";
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "C2DnNimJTzte4XlCkwqM";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            // createTable(connection);
            LiquibaseMigration.runMigrations();
            //  insertRecord(connection);
            ResultSet resultSet = retrieveStudents(connection);
            printStudents(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void printStudents(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            BigDecimal balance = resultSet.getBigDecimal("balance");
            System.out.println("ID: " + id + ", Username: " + username + ", Password: " + password + ", Balance: " + balance);
        }
    }

    public static ResultSet retrieveStudents(Connection connection) throws SQLException {
        String retrieveDataSQL = "SELECT * FROM private_scheme.clients";
        Statement retrieveDataStatement = connection.createStatement();
        ResultSet resultSet = retrieveDataStatement.executeQuery(retrieveDataSQL);
        return resultSet;
    }

    public static void insertRecord(Connection connection, String username, String pass) throws SQLException {
        String insertDataSQL = "INSERT INTO private_scheme.clients (username, password, balance) VALUES (?, ?, ?)";
        PreparedStatement insertDataStatement = connection.prepareStatement(insertDataSQL);
        insertDataStatement.setString(1, username);
        insertDataStatement.setString(2, pass);
        insertDataStatement.setBigDecimal(3, new BigDecimal("100.00"));
        int affectedRows = insertDataStatement.executeUpdate();

        if (affectedRows > 0) {
            ResultSet generatedKeys = insertDataStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                System.out.println("Inserted with ID: " + id);
            }
        }
    }

    public static void createTable(Connection connection) throws SQLException {
        // Создать схему, если она ещё не существует
        String createSchemaSQL = "CREATE SCHEMA IF NOT EXISTS private_scheme";
        Statement createSchemaStatement = connection.createStatement();
        createSchemaStatement.execute(createSchemaSQL);

        // Создать таблицу в схеме private_scheme
        String createTableSQL = "CREATE TABLE IF NOT EXISTS private_scheme.clients (" +
                "id SERIAL PRIMARY KEY," +
                "username VARCHAR(255)," +
                "password VARCHAR(255)," +
                "balance NUMERIC(10,2))";
        Statement createTableStatement = connection.createStatement();
        createTableStatement.execute(createTableSQL);
    }

    public static Client serchClient(String username, String password) throws SQLException {
        String serchClientSQL = "SELECT * FROM private_scheme.clients WHERE username = ?";

        Connection connection = ConnectionDB.getConnection();
        PreparedStatement serchStatement = connection.prepareStatement(serchClientSQL);
        serchStatement.setString(1, username); // Устанавливаем значение параметра вместо "?"

        ResultSet resultSet = serchStatement.executeQuery();

        if (resultSet.next()) {
            String storedPassword = resultSet.getString("password");
            if (password.equals(storedPassword)) {
                Client client = new Client();
                client.setUsername(username);
                client.setPassword(storedPassword);
                client.setBalance(resultSet.getBigDecimal("balance"));
                return client;
            }
        }

        return null; // Если пользователь не найден или пароль не совпадает, возвращаем null.
    }

}
