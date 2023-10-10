package org.homework.domain;

/**
 * ����� Transaction ������������ ����� ������ ���������� ����������, ������� ��� ���������� � ����� ��������.
 */
public class Transaction {
    /**
     * ��� ���������� ���������� (����� ��� ������).
     */
    private TypeTransaction typeTransaction;
    /**
     * ����� �������� � ���������� ����������.
     */
    private double sum;

    /**
     * ����������� ��� �������� ������� Transaction � ��������� ���� ���������� � ����� ��������.
     *
     * @param typeTransaction ��� ���������� ���������� (����� ��� ������).
     * @param sum             ����� �������� � ���������� ����������.
     */
    public Transaction(TypeTransaction typeTransaction, double sum) {

        this.typeTransaction = typeTransaction;
        this.sum = sum;
    }

    /**
     * ��������������� ������ toString ��� ������������� ������� Transaction � ���� ������.
     *
     * @return ��������� ������������� ������� Transaction, ������� ��� � ����� ����������.
     */
    @Override
    public String toString() {
        return "��� ���������� = " + typeTransaction +
                ", ����� �������� " + sum;
    }
}
