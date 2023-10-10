package org.homework.dataacess;

import org.homework.domain.Client;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс ClientsDataBase представляет базу данных клиентов.
 * Он хранит информацию о клиентах, предоставляет методы для доступа и управления данными клиентов.
 */
public class ClientsDataBase {
    /**
     * Хранилище клиентов, где ключ - это имя пользователя, а значение - соответствующий объект клиента.
     */
    Map<String, Client> clients = new HashMap<>();

    /**
     * Получает текущее хранилище клиентов.
     *
     * @return Хранилище клиентов в виде отображения, где ключ - имя пользователя, значение - объект клиента.
     */
    public Map<String, Client> getClients() {
        return clients;
    }

    /**
     * Добавляет нового клиента в хранилище.
     *
     * @param username Имя пользователя нового клиента.
     * @param password Пароль нового клиента.
     */
    public void setClients(String username, String password) {
        Client client = new Client();
        client.setUsername(username);
        client.setPassword(password);

        clients.put(username, client);
    }
}
