package org.homework.dataacess;

import org.homework.domain.Client;

import java.util.HashMap;
import java.util.Map;

/**
 * ����� ClientsDataBase ������������ ���� ������ ��������.
 * �� ������ ���������� � ��������, ������������� ������ ��� ������� � ���������� ������� ��������.
 */
public class ClientsDataBase {
    /**
     * ��������� ��������, ��� ���� - ��� ��� ������������, � �������� - ��������������� ������ �������.
     */
    Map<String, Client> clients = new HashMap<>();

    /**
     * �������� ������� ��������� ��������.
     *
     * @return ��������� �������� � ���� �����������, ��� ���� - ��� ������������, �������� - ������ �������.
     */
    public Map<String, Client> getClients() {
        return clients;
    }

    /**
     * ��������� ������ ������� � ���������.
     *
     * @param username ��� ������������ ������ �������.
     * @param password ������ ������ �������.
     */
    public void setClients(String username, String password) {
        Client client = new Client();
        client.setUsername(username);
        client.setPassword(password);

        clients.put(username, client);
    }
}
