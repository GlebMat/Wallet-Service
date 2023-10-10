package org.homework.domain;

/**
 * Класс Transaction представляет собой модель финансовой транзакции, включая тип транзакции и сумму перевода.
 */
public class Transaction {
    /**
     * Тип финансовой транзакции (дебет или кредит).
     */
    private TypeTransaction typeTransaction;
    /**
     * Сумма перевода в финансовой транзакции.
     */
    private double sum;

    /**
     * Конструктор для создания объекта Transaction с указанием типа транзакции и суммы перевода.
     *
     * @param typeTransaction Тип финансовой транзакции (дебет или кредит).
     * @param sum             Сумма перевода в финансовой транзакции.
     */
    public Transaction(TypeTransaction typeTransaction, double sum) {

        this.typeTransaction = typeTransaction;
        this.sum = sum;
    }

    /**
     * Переопределение метода toString для представления объекта Transaction в виде строки.
     *
     * @return Строковое представление объекта Transaction, включая тип и сумму транзакции.
     */
    @Override
    public String toString() {
        return "Тип транзакции = " + typeTransaction +
                ", сумма перевода " + sum;
    }
}
