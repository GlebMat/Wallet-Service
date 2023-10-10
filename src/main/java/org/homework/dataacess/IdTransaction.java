package org.homework.dataacess;

/**
 * ����� IdTransaction ������������ ���������� ������������� ����������.
 * ���� ����� ��������� ��������� � �������� ���������� �������������� ��� ����������.
 */
public class IdTransaction {
    /**
     * ���������� ������������� ����������.
     */
    private int id = 0;

    /**
     * �������� ������� �������� ����������� �������������� ����������.
     *
     * @return ���������� ������������� ����������.
     */
    public int getId() {
        return id;
    }

    /**
     * ������������� ����� �������� ����������� �������������� ����������.
     *
     * @param id ����� �������� ����������� �������������� ����������.
     */
    public void setId(int id) {
        this.id = id;
    }
}
