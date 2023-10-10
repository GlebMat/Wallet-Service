package org.homework.exeption;

/**
 * ����� BigDebetException �������� ���������������� �����������,
 * ������� ����� ���������� � ������, ���� ��������� ���������� �������� �
 * �������������� ������� �� ����� �������.
 * ��������� ����������� ���������� {@link Exception}.
 */
public class BigDebetException extends Exception {
    /**
     * ������� ����� ������ ���������� � �������� ���������� �� ������.
     *
     * @param message ��������� �� ������
     */
    public BigDebetException(String message) {
        super(message);
    }
}
