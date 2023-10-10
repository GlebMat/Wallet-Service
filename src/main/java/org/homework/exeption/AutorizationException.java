package org.homework.exeption;
/**
 * ����� AutorizationException �������� ���������������� �����������,
 * ������� ����� ���������� � ������ ������� � ������������ ������������.
 * ��������� ����������� ���������� {@link Exception}.
 */
public class AutorizationException extends Exception {
    /**
     * ������� ����� ������ ���������� � �������� ���������� �� ������.
     *
     * @param message ��������� �� ������
     */
    public AutorizationException(String message) {
        super(message);
    }
}
