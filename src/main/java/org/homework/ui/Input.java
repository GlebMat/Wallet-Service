package org.homework.ui;

import org.homework.dataacess.ClientsDataBase;
import org.homework.domain.Client;


import java.util.Scanner;

public class Input {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ClientsDataBase clientsDataBase = new ClientsDataBase();
        Client client = new Client();

        boolean checkAutorisation = true;
        boolean checkLogin = true;
        verifyClient(checkAutorisation, scanner, clientsDataBase, client, checkLogin);


    }

    public static void register(Scanner scanner, ClientsDataBase clientsDataBase) {
        String clientName = scanner.nextLine();
        String clientPass = scanner.nextLine();
        clientsDataBase.setClients(clientName, clientPass);
        System.out.println("����������� ������ �������!");
    }

    public static Client login(Scanner scanner, ClientsDataBase clientsDataBase) {
        String clientName = scanner.nextLine();
        String clientPass = scanner.nextLine();
        Client activeClient;
        for (Client client : clientsDataBase.getClients()) {
            if ((clientName.equals(client.getUsername()))) {
                if (clientPass.equals(client.getPassword())) {
                    System.out.println("����������� ������ �������!");
                    activeClient = client;
                    return activeClient;
                } else {
                    System.out.println("������ ������ �������");
                }
            } else {
                System.out.println("������������ � ����� username �� ����������");
            }
        }
        return null;
    }

    public static void sesions(Client client, boolean flag, Scanner scanner) {
        System.out.println("������� ���� �� �������: debit, credit, balance, history, logout");
        while (flag) {
            switch (scanner.nextLine()) {

                case ("debit"):
                    System.out.println("������� ����� ������");
                    int s2 = scanner.nextInt();
                    client.debit(s2);
                    break;

                case ("credit"):
                    System.out.println("������� ����� �������");
                    int s3 = scanner.nextInt();
                    client.credit(s3);
                    break;
                case ("balance"):
                    System.out.println(client.getBalance());
                    System.out.println("������� ���� �� �������: debit, credit, balance, history, logout");

                    break;

                case ("history"):
                    client.history();
                    System.out.println("������� ���� �� �������: debit, credit, balance, history, logout");

                    break;
                case ("logout"):
                    flag = false;
                    break;
                default:
                    System.out.println("������� ���� �� �������: debit, credit, balance, history, logout");

            }
        }
    }

    public static void verifyClient(boolean checkAutorisation, Scanner scanner, ClientsDataBase clientsDataBase, Client client, boolean checkLogin) {
        while (checkAutorisation) {
            System.out.println("������ ����! ���� �� ��� ������������������ ������������ ������� 1, ���� �� ������ ������������������ ������� 0, ����� ����� �������� exit");
            switch (scanner.nextLine()) {
                case ("0"):
                    System.out.println("����� ������������������ ������� username � password");
                    register(scanner, clientsDataBase);
                    break;
                case ("1"):
                    System.out.println("����� ����� � ���� ������� ������� username � password");
                    client = login(scanner, clientsDataBase);
                    if (client != null) {
                        sesions(client, checkLogin, scanner);
                    }
                    break;
                case ("exit"):
                    checkAutorisation = false;
                    break;
                default:
                    System.out.println("������������ ����");
            }

        }
    }
}
