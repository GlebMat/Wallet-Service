package org.homework.dao;

import org.homework.connectiondb.ConnectionDB;
import org.homework.domain.Client;

import java.math.BigDecimal;
import java.sql.*;
public class ClientDAO {
    public static void printClients(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            BigDecimal balance = resultSet.getBigDecimal("balance");
            System.out.println("ID: " + id + ", Username: " + username + ", Password: " + password + ", Balance: " + balance);
        }
    }
    public static ResultSet retrieveClients(Connection connection) throws SQLException {
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
    public static Client searchClient(String username, String password, ConnectionDB connectionDB) throws SQLException {
        String searchClientSQL = "SELECT * FROM private_scheme.clients WHERE username = ?";
        Connection connection = connectionDB.getConnection();
        PreparedStatement serchStatement = connection.prepareStatement(searchClientSQL);
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
