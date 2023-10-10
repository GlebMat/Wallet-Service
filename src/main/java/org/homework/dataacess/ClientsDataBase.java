package org.homework.dataacess;

import org.homework.domain.Client;
import java.util.HashMap;
import java.util.Map;

public class ClientsDataBase {
    Map<String, Client> clients = new HashMap<>();

    public Map<String, Client> getClients() {
        return clients;
    }

    public void setClients(String username, String password) {
        Client client = new Client();
        client.setUsername(username);
        client.setPassword(password);

        clients.put(username, client);
    }

    @Override
    public String toString() {
        return "ClientsDataBase{" +
                "clients=" + clients +
                '}';
    }
}
