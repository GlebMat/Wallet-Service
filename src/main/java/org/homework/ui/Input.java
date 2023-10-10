package org.homework.ui;

import org.homework.dataacess.ClientsDataBase;
import org.homework.domain.Client;
import org.homework.exeption.BigDebetException;
import org.homework.exeption.UniquIdExeption;


import java.util.Scanner;

public class Input {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ClientsDataBase clientsDataBase = new ClientsDataBase();
        Client client = new Client();

        boolean shouldExit = true;
        boolean checkLogin = true;
        verifyClient(shouldExit, scanner, clientsDataBase, client, checkLogin);


    }

    public static void register(Scanner scanner, ClientsDataBase clientsDataBase) {
        System.out.println("������� ��� ������������:");
        String clientName = scanner.nextLine();
        System.out.println("������� ������:");
        String clientPass = scanner.nextLine();
        clientsDataBase.setClients(clientName, clientPass);
        System.out.println("����������� ������ �������!");
    }

    public static Client login(Scanner scanner, ClientsDataBase clientsDataBase) {
        String clientName = scanner.nextLine();
        String clientPass = scanner.nextLine();
        Client activeClient = clientsDataBase.getClients().get(clientName);

        if (clientPass.equals(activeClient.getPassword())) {
            System.out.println("����������� ������ �������!");

            return activeClient;
        }
        if (activeClient == null || activeClient.getPassword().equals(clientPass)) {
            System.out.println("������������ � ����� username ��� password �� ����������");
        }
        return activeClient;
    }

    public static void sesions(Client client, boolean flag, Scanner scanner) {
        System.out.println("�������� ��������:");
        System.out.println("1 - �����");
        System.out.println("2 - ������");
        System.out.println("3 - ������");
        System.out.println("4 - ������� ����������");
        System.out.println("0 - �����");
        while (flag) {
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {

                case 1:
                    System.out.println("������� ����� ������");
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
                    System.out.println("������� ����� �������");
                    int s3 = scanner.nextInt();
                    try {
                        client.credit(s3);
                    } catch (UniquIdExeption e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println(client.getBalance());
                    System.out.println("������� ���� �� �������: debit, credit, balance, history, logout");

                    break;

                case 4:
                    client.history();
                    System.out.println("������� ���� �� �������: debit, credit, balance, history, logout");

                    break;
                case 0:
                    flag = false;
                    break;
                default:
                    System.out.println("������� ���� �� �������: debit, credit, balance, history, logout");

            }
        }
    }

    public static void verifyClient(boolean shouldExit, Scanner scanner, ClientsDataBase clientsDataBase, Client client, boolean checkLogin) {
        while (shouldExit) {
            System.out.println("������ ����! �������� ��������:");
            System.out.println("1 - �����������");
            System.out.println("2 - �����������");
            System.out.println("0 - �����");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("����� ������������������ ������� username � password");
                    register(scanner, clientsDataBase);
                    break;
                case 2:
                    System.out.println("����� ����� � ���� ������� ������� username � password");
                    client = login(scanner, clientsDataBase);
                    if (client != null) {
                        sesions(client, checkLogin, scanner);
                    }
                    break;
                case 0:
                    shouldExit = false;
                    break;
                default:
                    System.out.println("������������ ����");
            }

        }
    }
}
