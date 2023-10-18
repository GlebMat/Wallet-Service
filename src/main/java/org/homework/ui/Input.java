package org.homework.ui;

import org.homework.connectiondb.ConnectionDB;
import org.homework.connectiondb.DatabaseConfig;
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

import static org.homework.dao.ClientDAO.printClients;
import static org.homework.dao.ClientDAO.retrieveClients;

public class Input {
    public static void main(String[] args) {
        DatabaseConfig config = new DatabaseConfig();
        ConnectionDB connectionDB = new ConnectionDB(config);
        LiquibaseMigration.runMigrations();
        Scanner scanner = new Scanner(System.in);
        Client client = new Client();

        boolean shouldExit = true;
        boolean checkLogin = true;
        verifyClient(shouldExit, scanner, client, checkLogin, connectionDB);
    }

    public static void register(Scanner scanner, ConnectionDB connectionDB) {
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
            ClientDAO.insertRecord(connectionDB.getConnection(), clientName, clientPass);
            ResultSet resultSet = retrieveClients(connectionDB.getConnection());
            printClients(resultSet);
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

    public static Client login(Scanner scanner, ConnectionDB connectionDB) throws SQLException {
        String clientName = scanner.nextLine();
        String clientPass = scanner.nextLine();
        Client activeClient = ClientDAO.searchClient(clientName, clientPass, connectionDB);
        if (activeClient != null && clientPass.equals(activeClient.getPassword())) {
            System.out.println("Authorization was successful!");
            return activeClient;
        } else {
            System.out.println("Неправильно введен username или пароль");
            return null;
        }

    }

    public static void sessions(Client client, boolean shouldLogout, Scanner scanner, ConnectionDB connectionDB) {
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
                        TransactionService.debit(s2, client, connectionDB);
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
                        TransactionService.credit(s3, client, connectionDB);
                    } catch (UniqueIdException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println(client.getBalance());
                    break;

                case 4:
                    TransactionService.history(client, connectionDB);
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

    public static void verifyClient(boolean shouldExit, Scanner scanner, Client client, boolean checkLogin, ConnectionDB connectionDB) {
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

                    register(scanner, connectionDB);
                    break;
                case 2:
                    System.out.println("To log in to your account, enter username and password");

                    try {
                        client = login(scanner, connectionDB);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    if (client != null) {
                        sessions(client, checkLogin, scanner, connectionDB);
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
