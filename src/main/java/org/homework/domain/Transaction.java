package org.homework.domain;

/**
 * The `Transaction` class represents a financial transaction model, including the transaction type and the transfer amount.
 */
public class Transaction {
    /**
     * The type of financial transaction (debit or credit).
     */
    private TypeTransaction typeTransaction;
    /**
     * The transfer amount in the financial transaction.
     */
    private double sum;

    /**
     * Constructor for creating a `Transaction` object with the specified transaction type and transfer amount.
     *
     * @param typeTransaction The type of financial transaction (debit or credit).
     * @param sum             The transfer amount in the financial transaction.
     */
    public Transaction(TypeTransaction typeTransaction, double sum) {

        this.typeTransaction = typeTransaction;
        this.sum = sum;
    }

    /**
     * Overrides the `toString` method to represent a `Transaction` object as a string.
     *
     * @return A string representation of the `Transaction` object, including the type and amount of the transaction.
     */
    @Override
    public String toString() {
        return "Transaction type - " + typeTransaction +
                ", transfer amount " + sum;
    }
}
