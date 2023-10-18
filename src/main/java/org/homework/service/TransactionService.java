package org.homework.service;

import org.homework.connectiondb.ConnectionDB;
import org.homework.domain.Client;
import org.homework.domain.TypeTransaction;
import org.homework.exception.BigDebitException;
import org.homework.exception.UniqueIdException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionService {



    public static void debit(BigDecimal withdraw, Client client, ConnectionDB connectionDB) throws BigDebitException, UniqueIdException {

        if ((client.getBalance().subtract(withdraw).compareTo(BigDecimal.ZERO)) < 0) {
            throw new BigDebitException("Not enough funds for withdrawal");
        }
        client.setBalance(client.getBalance().subtract(withdraw));
        System.out.println(client.getBalance() + " ///////////");
        String changeBalanceSQL = "UPDATE private_scheme.clients SET balance = ? WHERE username = ?";
        try {
            PreparedStatement updateStatement = connectionDB.getConnection().prepareStatement(changeBalanceSQL);
            updateStatement.setBigDecimal(1, client.getBalance());
            updateStatement.setString(2, client.getUsername());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Проверки и исключения оставляем без изменений.

        // Добавляем транзакцию в базу данных.
        String insertTransactionSQL = "INSERT INTO private_scheme.transactions (type, amount, username) VALUES (?, ?, ?)";
        try (Connection connection = connectionDB.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(insertTransactionSQL)) {
            insertStatement.setString(1, TypeTransaction.DEBIT.name());
            insertStatement.setBigDecimal(2, withdraw);
            insertStatement.setString(3, client.getUsername());
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void credit(BigDecimal credit, Client client, ConnectionDB connectionDB) throws UniqueIdException {
        // Обновляем баланс клиента
        BigDecimal newBalance = client.getBalance().add(credit);
        client.setBalance(newBalance);

        // Обновляем баланс клиента в базе данных
        String changeBalanceSQL = "UPDATE private_scheme.clients SET balance = ? WHERE username = ?";
        try (Connection connection = connectionDB.getConnection();
             PreparedStatement updateStatement = connection.prepareStatement(changeBalanceSQL)) {
            updateStatement.setBigDecimal(1, newBalance);
            updateStatement.setString(2, client.getUsername());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Создаем и сохраняем транзакцию в базе данных
        String insertTransactionSQL = "INSERT INTO private_scheme.transactions (type, amount, username) VALUES (?, ?, ?)";
        try (Connection connection = connectionDB.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(insertTransactionSQL)) {
            insertStatement.setString(1, TypeTransaction.CREDIT.name());
            insertStatement.setBigDecimal(2, credit);
            insertStatement.setString(3, client.getUsername());
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Обновляем идентификатор транзакции

    }

    public static void history(Client client, ConnectionDB connectionDB) {
        String selectTransactionSQL = "SELECT * FROM private_scheme.transactions WHERE username = ? ORDER BY username, id";
        try (Connection connection = connectionDB.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(selectTransactionSQL)) {
            selectStatement.setString(1, client.getUsername());
            ResultSet resultSet = selectStatement.executeQuery();

            System.out.println("Transaction History for " + client.getUsername() + ":");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                TypeTransaction type = TypeTransaction.valueOf(resultSet.getString("type"));
                BigDecimal amount = resultSet.getBigDecimal("amount");
                System.out.println("Transaction ID: " + id + ", Type: " + type + ", Amount: " + amount);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
