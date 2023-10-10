package org.homework.exeption;

/**
 * ����� UniquIdExeption �������� ���������������� �����������,
 * ������� ����� ���������� � ������, ���� ������������� ���������� �� ��������.
 * ��������� ����������� ���������� {@link Exception}.
 */
public class UniquIdExeption extends Exception {
    /**
     * ������� ����� ������ ���������� � �������� ���������� �� ������.
     *
     * @param message ��������� �� ������
     */
    public UniquIdExeption(String message) {
        super(message);
    }
}
