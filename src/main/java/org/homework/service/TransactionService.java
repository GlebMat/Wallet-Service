package org.homework.service;

import org.homework.connectiondb.ConnectionDB;
import org.homework.dataacess.IdTransaction;
import org.homework.domain.Client;
import org.homework.domain.Transaction;
import org.homework.domain.TypeTransaction;
import org.homework.exception.BigDebitException;
import org.homework.exception.UniqueIdException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class TransactionService {
    private static IdTransaction idTransaction = new IdTransaction();
    private static Map<Integer, Transaction> transactions = new HashMap<>();


    public static void debit(double withdraw, Client client) throws BigDebitException, UniqueIdException {

        if ((client.getBalance().subtract(BigDecimal.valueOf(withdraw)).compareTo(BigDecimal.ZERO)) < 0) {
            throw new BigDebitException("Not enough funds for withdrawal");
        }
        client.setBalance(client.getBalance().subtract(BigDecimal.valueOf(withdraw)));
        System.out.println(client.getBalance() + " ///////////");
        String changeBalanceSQL = "UPDATE private_scheme.clients SET balance = ? WHERE username = ?";
        try {
            PreparedStatement updateStatement = ConnectionDB.getConnection().prepareStatement(changeBalanceSQL);
            updateStatement.setBigDecimal(1, client.getBalance());
            updateStatement.setString(2, client.getUsername());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Проверки и исключения оставляем без изменений.

        // Добавляем транзакцию в базу данных.
        String insertTransactionSQL = "INSERT INTO private_scheme.transactions (type, amount, username) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionDB.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(insertTransactionSQL)) {
            insertStatement.setString(1, TypeTransaction.DEBIT.name());
            insertStatement.setDouble(2, withdraw);
            insertStatement.setString(3, client.getUsername());
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Perform a credit transaction, adding funds to the client's balance.
     *
     * @param credit The amount of credit to add to the balance.
     * @throws UniqueIdException If the transaction ID is not unique.
     */
    public static void credit(double credit, Client client) throws UniqueIdException {
        int uId = idTransaction.getId();
        if (transactions.get(uId) != null) {
            throw new UniqueIdException("The passed ID is not unique");
        }
        client.setBalance(client.getBalance().add(BigDecimal.valueOf(credit)));
        Transaction transaction = new Transaction(TypeTransaction.CREDIT, credit);
        transactions.put(uId, transaction);
        client.getTransactions().put(uId, transaction);
        idTransaction.setId(idTransaction.getId() + 1);
        System.out.println(transactions.get(uId));
    }

    /**
     * Display the transaction history of the client.
     */
    public static void history(Client client) {
        for (Map.Entry<Integer, Transaction> entry : client.getTransactions().entrySet()) {
            System.out.println(entry.getValue());
        }
    }


}
