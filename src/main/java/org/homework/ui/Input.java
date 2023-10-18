package org.homework.ui;

import org.homework.connectiondb.ConnectionDB;
import org.homework.dao.ClientDAO;
import org.homework.dao.LiquibaseMigration;
import org.homework.service.TransactionService;
import org.homework.domain.Client;
import org.homework.exception.BigDebitException;
import org.homework.exception.UniqueIdException;


import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static org.homework.dao.ClientDAO.printStudents;
import static org.homework.dao.ClientDAO.retrieveStudents;

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
        LiquibaseMigration.runMigrations();
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
        boolean flag1 = true;
        boolean flag2 = true;
        String clientName = "";
        String clientPass = "";
        while (flag1) {
            System.out.println("Enter username:");
            clientName = scanner.nextLine();
            if (!clientName.equals("")) {
                flag1 = false;
            }
        }
        while (flag2) {
            System.out.println("Enter password:");
            clientPass = scanner.nextLine();
            if (!clientPass.equals("")) {
                flag2 = false;
            }
        }

        try {
            ClientDAO.insertRecord(ConnectionDB.getConnection(), clientName, clientPass);
            ResultSet resultSet = retrieveStudents(ConnectionDB.getConnection());
            printStudents(resultSet);
            System.out.println("Registration was successful!");
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                // Ошибка 23505 означает, что нарушена уникальность поля в БД (например, username).
                System.out.println("Username is not unique. Please enter a unique username.");
            } else {
                throw new RuntimeException(e);
            }
        }
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
    public static Client login(Scanner scanner) throws SQLException {
        String clientName = scanner.nextLine();
        String clientPass = scanner.nextLine();
        Client activeClient = ClientDAO.serchClient(clientName, clientPass);
        if (activeClient != null && clientPass.equals(activeClient.getPassword())) {
            System.out.println("Authorization was successful!");
            return activeClient;
        } else {
            System.out.println("Неправильно введен username или пароль");
            return null;
        }

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
                    BigDecimal s2 = scanner.nextBigDecimal();

                    try {
                        TransactionService.debit(s2, client);
                    } catch (BigDebitException e) {
                        System.out.println(e.getMessage());
                    } catch (UniqueIdException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("Enter the amount of credit");
                    BigDecimal s3 = scanner.nextBigDecimal();
                    try {
                        TransactionService.credit(s3, client);
                    } catch (UniqueIdException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println(client.getBalance());
                    break;

                case 4:
                    TransactionService.history(client);
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

                    try {
                        client = login(scanner);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    if (client != null){
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
