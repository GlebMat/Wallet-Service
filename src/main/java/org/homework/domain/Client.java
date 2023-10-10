package org.homework.domain;

import org.homework.dataacess.IdTransaction;

import java.util.*;

public class Client {

    private String username;
    private String password;
    private IdTransaction idTransaction = new IdTransaction();
    private double balance;

    private Map<Integer, String> transactions = new HashMap<>();

    public double getBalance() {
        return balance;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void debit(double withdrow) {
        try {
            if (balance - withdrow < 0) {
                throw new IllegalArgumentException();
            }
            balance = balance - withdrow;
            int uId = idTransaction.getId();

            for (Map.Entry<Integer, String> entry : transactions.entrySet()) {
                if (uId == entry.getKey()) {
                    throw new RuntimeException();
                }
            }
            transactions.put(uId, "Списание средств " + withdrow);
            idTransaction.setId(idTransaction.getId() + 1);
            System.out.println(transactions.get(uId));

        } catch (IllegalArgumentException e) {
            System.out.println("insufficient funds");
        } catch (RuntimeException e) {
            System.out.println("error occurred");
        }

    }

    public void credit(double credit) {
        try {

            balance = balance + credit;
            int uId = idTransaction.getId();

            for (Map.Entry<Integer, String> entry : transactions.entrySet()) {
                if (uId == entry.getKey()) {
                    throw new RuntimeException();
                }
            }
            transactions.put(uId, "Взят кредит " + credit);
            idTransaction.setId(idTransaction.getId() + 1);
            System.out.println(transactions.get(uId));

        } catch (RuntimeException e) {
            System.out.println("error occurred");
        }
    }
    public void history(){
        for (Map.Entry<Integer, String> entry : transactions.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    @Override
    public String toString() {
        return "Client{" +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", idTransaction=" + idTransaction +
                ", balance=" + balance +
                ", transactions=" + transactions +
                '}';
    }
}
