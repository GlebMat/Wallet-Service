package org.homework.domain;

import org.homework.dataacess.IdTransaction;
import org.homework.exeption.BigDebetException;
import org.homework.exeption.UniquIdExeption;

import java.util.*;

/**
 * ����� Client ������������ ����� ������ ������� ����� � ���������� ��� ����������� ������������.
 * ������ ������ ����� ���������� ��� ������������ (username) � ������ (password), � ����� ����� ���������
 * ��������� � ��������� ���������� ��� ��������� ������ �������.
 */
public class Client {
    /**
     * ��� ������������ �������.
     */
    private String username;

    /**
     * ������ ������������ �������.
     */
    private String password;
    /**
     * ���������� ������������� ���������� �������.
     */
    private IdTransaction idTransaction = new IdTransaction();
    /**
     * ������� ������ �������.
     */
    private double balance;
    /**
     * ������ ���������� �������.
     */

    private Map<Integer, Transaction> transactions = new HashMap<>();

    /**
     * �������� ������� ������ �������.
     *
     * @return ������� ������ �������.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * ������������� ����� ������ �������.
     *
     * @param balance ����� ������ �������.
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * �������� ��� ������������ �������.
     *
     * @return ��� ������������ �������.
     */
    public String getUsername() {
        return username;
    }

    /**
     * ������������� ��� ������������ �������.
     *
     * @param username ��� ������������ �������.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * �������� ������ ������������ �������.
     *
     * @return ������ ������������ �������.
     */
    public String getPassword() {
        return password;
    }

    /**
     * ������������� ������ ������������ �������.
     *
     * @param password ������ ������������ �������.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * ��������� ��������� ����������, ������ �������� � ������� �������.
     *
     * @param withdrow ����� ������ � �������.
     * @throws BigDebetException ���� ����� ������ ��������� ������� ������ �������.
     * @throws UniquIdExeption   ���� ������������� ���������� �� ��������.
     */
    public void debit(double withdrow) throws BigDebetException, UniquIdExeption {

        if (balance - withdrow < 0) {
            throw new BigDebetException("�� ������� ������� ��� ������");
        }
        balance = balance - withdrow;
        int uId = idTransaction.getId();

        for (Map.Entry<Integer, Transaction> entry : transactions.entrySet()) {
            if (uId == entry.getKey()) {
                throw new UniquIdExeption("���������� id �� ����������");
            }
        }
        transactions.put(uId, new Transaction(TypeTransaction.DEBET, withdrow));
        idTransaction.setId(idTransaction.getId() + 1);
        System.out.println(transactions.get(uId));


    }

    /**
     * ��������� ��������� ����������, �������� �������� �� ������ �������.
     *
     * @param credit ����� ������� ��� ���������� �� ������.
     * @throws UniquIdExeption ���� ������������� ���������� �� ��������.
     */
    public void credit(double credit) throws UniquIdExeption {


        balance = balance + credit;
        int uId = idTransaction.getId();

        for (Map.Entry<Integer, Transaction> entry : transactions.entrySet()) {
            if (uId == entry.getKey()) {
                throw new UniquIdExeption("���������� id �� ����������");
            }
        }
        transactions.put(uId, new Transaction(TypeTransaction.CREDIT, credit));
        idTransaction.setId(idTransaction.getId() + 1);
        System.out.println(transactions.get(uId));


    }

    /**
     * ������� ������� ���������� �������.
     */
    public void history() {
        for (Map.Entry<Integer, Transaction> entry : transactions.entrySet()) {
            System.out.println(entry.getValue());
        }
    }


}
