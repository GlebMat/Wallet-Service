package org.homework.domain;

import org.homework.exception.BigDebitException;
import org.homework.exception.UniqueIdException;

import java.math.BigDecimal;
import java.util.*;

/**
 * The `Client` class represents a client model with management of their financial transactions.
 * Each client has a unique username and password and can perform debit and credit transactions to change their balance.
 */
public class Client {

    /**
     * The username of the client.
     */
    private String username;

    /**
     * The password of the client.
     */
    private String password;
    /**
     * The unique transaction identifier of the client.
     */
    private IdTransaction idTransaction = new IdTransaction();
    /**
     * The current balance of the client.
     */
    private BigDecimal balance;
    /**
     * The list of client transactions.
     */

    private Map<Integer, Transaction> transactions = new HashMap<>();

    /**
     * Get the current balance of the client.
     *
     * @return The current balance of the client.
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Set a new balance for the client.
     *
     * @param balance The new balance of the client.
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * Get the username of the client.
     *
     * @return The username of the client.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username of the client.
     *
     * @param username The username of the client.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the password of the client.
     *
     * @return The password of the client.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password of the client.
     *
     * @param password The password of the client.
     */
    public void setPassword(String password) {
        this.password = password;
    }
    public Map<Integer, Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Perform a debit transaction, withdrawing funds from the client's balance.
     *
     * @param withdrow The amount to withdraw from the balance.
     * @throws BigDebitException If the withdrawal amount exceeds the current balance of the client.
     * @throws UniqueIdException If the transaction ID is not unique.
     */

}

