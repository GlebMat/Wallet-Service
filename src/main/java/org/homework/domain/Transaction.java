package org.homework.domain;
public class Transaction {
    private TypeTransaction typeTransaction;
    private double amount;
    public Transaction(TypeTransaction typeTransaction, double amount) {
        this.typeTransaction = typeTransaction;
        this.amount = amount;
    }
    @Override
    public String toString() {
        return "Transaction type - " + typeTransaction +
                ", transfer amount " + amount;
    }
}
