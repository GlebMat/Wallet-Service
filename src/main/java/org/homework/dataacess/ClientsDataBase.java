package org.homework.dataacess;

import org.homework.domain.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientsDataBase {
    List<Client> clients = new ArrayList<>();

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(String username, String password) {
        Client client = new Client();
        client.setUsername(username);
        client.setPassword(password);

        clients.add(client);
    }

    @Override
    public String toString() {
        return "ClientsDataBase{" +
                "clients=" + clients +
                '}';
    }
}
