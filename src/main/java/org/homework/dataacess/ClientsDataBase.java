package org.homework.dataacess;

import org.homework.domain.Client;

import java.util.HashMap;
import java.util.Map;

/**
 * The `ClientsDataBase` class represents a database of clients.
 * It stores information about clients and provides methods for accessing and managing client data.
 */
public class ClientsDataBase {
    /**
     * The storage of clients, where the key is the username, and the value is the corresponding client object.
     */
    Map<String, Client> clients = new HashMap<>();

    /**
     * Get the current storage of clients.
     *
     * @return The storage of clients as a map, where the key is the username, and the value is the client object.
     */
    public Map<String, Client> getClients() {
        return clients;
    }

    /**
     * Add a new client to the storage.
     *
     * @param username The username of the new client.
     * @param password The password of the new client.
     */
    public void setClients(String username, String password) {
        Client client = new Client();
        client.setUsername(username);
        client.setPassword(password);

        clients.put(username, client);
    }
}
