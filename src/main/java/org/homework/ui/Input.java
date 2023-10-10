package org.homework.ui;

import org.homework.dataacess.ClientsDataBase;
import org.homework.domain.Client;
import org.homework.exception.BigDebitException;
import org.homework.exception.UniqueIdException;


import java.util.Scanner;

/**
 * The `Input` class represents a console application for managing player credit/debit transactions.
 * Users can register, log in, view their balance, perform debit and credit transactions, and view transaction history.
 */
public class Input {
    /**
     * The entry point of the application. It creates Scanner, ClientsDataBase, and Client objects,
     * and then initiates the login and transaction management process.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ClientsDataBase clientsDataBase = new ClientsDataBase();
        Client client = new Client();

        boolean shouldExit = true;
        boolean checkLogin = true;
        verifyClient(shouldExit, scanner, clientsDataBase, client, checkLogin);
    }

    /**
     * Registers a new player by requesting a username and password through console input.
     * Displays a success message after successful registration.
     *
     * @param scanner         A Scanner object for reading user input.
     * @param clientsDataBase A ClientsDataBase object for storing registered players.
     */
    public static void register(Scanner scanner, ClientsDataBase clientsDataBase) {
        System.out.println("Enter username:");
        String clientName = scanner.nextLine();
        System.out.println("Enter password:");
        String clientPass = scanner.nextLine();
        clientsDataBase.setClients(clientName, clientPass);
        System.out.println("Registration was successful!");
    }

    /**
     * Allows a player to log in by requesting a username and password through console input.
     * Returns a Client object representing the logged-in player upon successful authentication,
     * or displays an error message otherwise.
     *
     * @param scanner         A Scanner object for reading user input.
     * @param clientsDataBase A ClientsDataBase object for checking player credentials.
     * @return A Client object representing the authenticated player, or null in case of an error.
     */
    public static Client login(Scanner scanner, ClientsDataBase clientsDataBase) {
        String clientName = scanner.nextLine();
        String clientPass = scanner.nextLine();
        Client activeClient = clientsDataBase.getClients().get(clientName);

        if (clientPass.equals(activeClient.getPassword())) {
            System.out.println("Authorization was successful!");
            return activeClient;
        }
        if (activeClient == null || activeClient.getPassword().equals(clientPass)) {
            System.out.println("The user with such username or password does not exist");
        }
        return activeClient;
    }

    /**
     * Allows an authenticated player to perform various actions such as debit and credit transactions,
     * view their balance, and transaction history. Exits this session when the player chooses "Exit."
     *
     * @param client       The authenticated player.
     * @param shouldLogout A flag indicating whether to continue the session.
     * @param scanner      A Scanner object for reading user input.
     */
    public static void sessions(Client client, boolean shouldLogout, Scanner scanner) {
        while (shouldLogout) {
            System.out.println("Select an action:");
            System.out.println("1 - Debit");
            System.out.println("2 - Credit");
            System.out.println("3 - Balance");
            System.out.println("4 - Transaction History");
            System.out.println("0 - Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {

                case 1:
                    System.out.println("Enter the amount of the debit");
                    int s2 = scanner.nextInt();
                    try {
                        client.debit(s2);
                    } catch (BigDebitException e) {
                        System.out.println(e.getMessage());
                    } catch (UniqueIdException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("Enter the amount of credit");
                    int s3 = scanner.nextInt();
                    try {
                        client.credit(s3);
                    } catch (UniqueIdException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println(client.getBalance());
                    break;

                case 4:
                    client.history();
                    break;
                case 0:
                    shouldLogout = false;
                    break;
                default:
                    System.out.println("Select an action:");
                    System.out.println("1 - Debit");
                    System.out.println("2 - Credit");
                    System.out.println("3 - Balance");
                    System.out.println("4 - Transaction History");
                    System.out.println("0 - Exit");
            }
        }
    }

    /**
     * Allows users to register or log in and manage their transactions.
     * Exits the application when the user chooses "Exit."
     *
     * @param shouldExit      A flag indicating whether to terminate the application.
     * @param scanner         A Scanner object for reading user input.
     * @param clientsDataBase A ClientsDataBase object for managing users.
     * @param client          A Client object for storing the current user.
     * @param checkLogin      A flag indicating whether the current user is logged in.
     */
    public static void verifyClient(boolean shouldExit, Scanner scanner, ClientsDataBase clientsDataBase, Client client, boolean checkLogin) {
        while (shouldExit) {
            System.out.println("Good afternoon! Choose an action:");
            System.out.println("1 - Registration");
            System.out.println("2 - Authorization");
            System.out.println("0 - Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("To register, enter username and password");
                    register(scanner, clientsDataBase);
                    break;
                case 2:
                    System.out.println("To log in to your account, enter username and password");
                    client = login(scanner, clientsDataBase);
                    if (client != null) {
                        sessions(client, checkLogin, scanner);
                    }
                    break;
                case 0:
                    shouldExit = false;
                    break;
                default:
                    System.out.println("Incorrect input");
            }
        }
    }
}
