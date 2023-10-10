package org.homework.domain;

import org.homework.dataacess.IdTransaction;
import org.homework.exeption.BigDebetException;
import org.homework.exeption.UniquIdExeption;

import java.util.*;

/**
 * Класс Client представляет собой модель клиента счета и управление его финансовыми транзакциями.
 * Каждый клиент имеет уникальное имя пользователя (username) и пароль (password), а также может выполнять
 * дебетовые и кредитные транзакции для изменения своего баланса.
 */
public class Client {
    /**
     * Имя пользователя клиента.
     */
    private String username;

    /**
     * Пароль пользователя клиента.
     */
    private String password;
    /**
     * Уникальный идентификатор транзакции клиента.
     */
    private IdTransaction idTransaction = new IdTransaction();
    /**
     * Текущий баланс клиента.
     */
    private double balance;
    /**
     * Список транзакций клиента.
     */

    private Map<Integer, Transaction> transactions = new HashMap<>();

    /**
     * Получает текущий баланс клиента.
     *
     * @return Текущий баланс клиента.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Устанавливает новый баланс клиента.
     *
     * @param balance Новый баланс клиента.
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Получает имя пользователя клиента.
     *
     * @return Имя пользователя клиента.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Устанавливает имя пользователя клиента.
     *
     * @param username Имя пользователя клиента.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Получает пароль пользователя клиента.
     *
     * @return Пароль пользователя клиента.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Устанавливает пароль пользователя клиента.
     *
     * @param password Пароль пользователя клиента.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Выполняет дебетовую транзакцию, снимая средства с баланса клиента.
     *
     * @param withdrow Сумма снятия с баланса.
     * @throws BigDebetException Если сумма снятия превышает текущий баланс клиента.
     * @throws UniquIdExeption   Если идентификатор транзакции не уникален.
     */
    public void debit(double withdrow) throws BigDebetException, UniquIdExeption {

        if (balance - withdrow < 0) {
            throw new BigDebetException("Не хватает средств для снятия");
        }
        balance = balance - withdrow;
        int uId = idTransaction.getId();

        for (Map.Entry<Integer, Transaction> entry : transactions.entrySet()) {
            if (uId == entry.getKey()) {
                throw new UniquIdExeption("Переданный id не уникальный");
            }
        }
        transactions.put(uId, new Transaction(TypeTransaction.DEBET, withdrow));
        idTransaction.setId(idTransaction.getId() + 1);
        System.out.println(transactions.get(uId));


    }

    /**
     * Выполняет кредитную транзакцию, добавляя средства на баланс клиента.
     *
     * @param credit Сумма кредита для добавления на баланс.
     * @throws UniquIdExeption Если идентификатор транзакции не уникален.
     */
    public void credit(double credit) throws UniquIdExeption {


        balance = balance + credit;
        int uId = idTransaction.getId();

        for (Map.Entry<Integer, Transaction> entry : transactions.entrySet()) {
            if (uId == entry.getKey()) {
                throw new UniquIdExeption("Переданный id не уникальный");
            }
        }
        transactions.put(uId, new Transaction(TypeTransaction.CREDIT, credit));
        idTransaction.setId(idTransaction.getId() + 1);
        System.out.println(transactions.get(uId));


    }

    /**
     * Выводит историю транзакций клиента.
     */
    public void history() {
        for (Map.Entry<Integer, Transaction> entry : transactions.entrySet()) {
            System.out.println(entry.getValue());
        }
    }


}
