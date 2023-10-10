package org.homework.domain;

import java.util.HashMap;
import java.util.Map;


public class Transaction {

    private TypeTransaction typeTransaction;
    private double sum;

    public Transaction(TypeTransaction typeTransaction, double sum) {

        this.typeTransaction = typeTransaction;
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "Тип транзакции = " + typeTransaction +
                ", сумма перевода " + sum;
    }
}
