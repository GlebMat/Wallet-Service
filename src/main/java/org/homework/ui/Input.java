package org.homework.ui;

import org.homework.dataacess.ClientsDataBase;
import org.homework.domain.Client;
import org.homework.exeption.BigDebetException;
import org.homework.exeption.UniquIdExeption;


import java.util.Scanner;

/**
 * ����� Input ������������ ���������� ���������� ��� ���������� ����������/���������� ������������ �������.
 * ������������ ����� ������������������, ��������������, ������������� ������, ��������� ��������� � ��������� ����������,
 * � ����� ������������� ������� ����������.
 */
public class Input {
    /**
     * ����� ����� � ����������. ������� ������� Scanner, ClientsDataBase � Client,
     * � ����� ��������� ������� ����������� � ���������� ������������.
     *
     * @param args ��������� ��������� ������ (�� ������������).
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
     * ������������ ������ ������ ����� ������� ����� ������������ � ������ ����� ���������� ����.
     * ����� �������� ����������� ������� ��������� �� �������� �����������.
     *
     * @param scanner         ������ Scanner ��� ������ ����� ������������.
     * @param clientsDataBase ������ ClientsDataBase ��� �������� ������������������ �������.
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
     * ��������� ������ ��������������, ���������� ��� ������������ � ������ ����� ���������� ����.
     * ����� �������� ����������� ���������� ������ �������, ����� ������� ��������� �� ������.
     *
     * @param scanner         ������ Scanner ��� ������ ����� ������������.
     * @param clientsDataBase ������ ClientsDataBase ��� �������� ������� ������ ������.
     * @return ������ Client, �������������� ��������������� ������, ��� null � ������ ������.
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
     * ��������� ��������������� ������ ��������� ��������� ��������, ����� ��� ��������� � ��������� ����������,
     * �������� ������� � ������� ����������. ������� �� ���� ������, ����� ����� �������� ����� "�����".
     *
     * @param client       �������������� �����.
     * @param shouldLogout ����, �����������, ���������� �� ������.
     * @param scanner      ������ Scanner ��� ������ ����� ������������.
     */
    public static void sesions(Client client, boolean shouldLogout, Scanner scanner) {

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
                    } catch (BigDebetException e) {
                        System.out.println(e.getMessage());
                    } catch (UniquIdExeption e) {
                        System.out.println(e.getMessage());
                        ;
                    }
                    break;

                case 2:
                    System.out.println("Enter the amount of credit");
                    int s3 = scanner.nextInt();
                    try {
                        client.credit(s3);
                    } catch (UniquIdExeption e) {
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
     * ��������� ������������� ���������������� ��� ���������������� � ��������� ������ ������������.
     * ������� �� ����������, ����� ������������ �������� ����� "�����".
     *
     * @param shouldExit      ����, �����������, ��������� �� ����������.
     * @param scanner         ������ Scanner ��� ������ ����� ������������.
     * @param clientsDataBase ������ ClientsDataBase ��� ���������� ��������������.
     * @param client          ������ Client ��� �������� �������� ������������.
     * @param checkLogin      ����, �����������, ����������� �� ������� ������������.
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
                        sesions(client, checkLogin, scanner);
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
