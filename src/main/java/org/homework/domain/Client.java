package org.homework.domain;

import org.homework.dataacess.IdTransaction;
import org.homework.exñeption.BigDebitException;
import org.homework.exñeption.UniqueIdException;

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
    private double balance;
    /**
     * The list of client transactions.
     */

    private Map<Integer, Transaction> transactions = new HashMap<>();

    /**
     * Get the current balance of the client.
     *
     * @return The current balance of the client.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Set a new balance for the client.
     *
     * @param balance The new balance of the client.
     */
    public void setBalance(double balance) {
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

    /**
     * Perform a debit transaction, withdrawing funds from the client's balance.
     *
     * @param withdrow The amount to withdraw from the balance.
     * @throws BigDebitException If the withdrawal amount exceeds the current balance of the client.
     * @throws UniqueIdException If the transaction ID is not unique.
     */
    public void debit(double withdrow) throws BigDebitException, UniqueIdException {

        if (balance - withdrow < 0) {
            throw new BigDebitException("Not enough funds for withdrawal");
        }
        balance = balance - withdrow;
        int uId = idTransaction.getId();

        for (Map.Entry<Integer, Transaction> entry : transactions.entrySet()) {
            if (uId == entry.getKey()) {
                throw new UniqueIdException("The passed ID is not unique");
            }
        }
        transactions.put(uId, new Transaction(TypeTransaction.DEBIT, withdrow));
        idTransaction.setId(idTransaction.getId() + 1);
        System.out.println(transactions.get(uId));
    }

    /**
     * Perform a credit transaction, adding funds to the client's balance.
     *
     * @param credit The amount of credit to add to the balance.
     * @throws UniqueIdException If the transaction ID is not unique.
     */
    public void credit(double credit) throws UniqueIdException {
        balance = balance + credit;
        int uId = idTransaction.getId();

        for (Map.Entry<Integer, Transaction> entry : transactions.entrySet()) {
            if (uId == entry.getKey()) {
                throw new UniqueIdException("The passed ID is not unique");
            }
        }
        transactions.put(uId, new Transaction(TypeTransaction.CREDIT, credit));
        idTransaction.setId(idTransaction.getId() + 1);
        System.out.println(transactions.get(uId));
    }

    /**
     * Display the transaction history of the client.
     */
    public void history() {
        for (Map.Entry<Integer, Transaction> entry : transactions.entrySet()) {
            System.out.println(entry.getValue());
        }
    }
}

