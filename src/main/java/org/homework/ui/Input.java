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
        System.out.println("Регистрация прошла успешно!");
    }

    public static Client login(Scanner scanner, ClientsDataBase clientsDataBase) {
        String clientName = scanner.nextLine();
        String clientPass = scanner.nextLine();
        Client activeClient;
        for (Client client : clientsDataBase.getClients()) {
            if ((clientName.equals(client.getUsername()))) {
                if (clientPass.equals(client.getPassword())) {
                    System.out.println("Авторизация прошла успешно!");
                    activeClient = client;
                    return activeClient;
                } else {
                    System.out.println("Пароль введен неверно");
                }
            } else {
                System.out.println("Пользователя с таким username не существует");
            }
        }
        return null;
    }

    public static void sesions(Client client, boolean flag, Scanner scanner) {
        System.out.println("Введите одну из комманд: debit, credit, balance, history, logout");
        while (flag) {
            switch (scanner.nextLine()) {

                case ("debit"):
                    System.out.println("Введите сумму дебита");
                    int s2 = scanner.nextInt();
                    client.debit(s2);
                    break;

                case ("credit"):
                    System.out.println("Введите сумму кредита");
                    int s3 = scanner.nextInt();
                    client.credit(s3);
                    break;
                case ("balance"):
                    System.out.println(client.getBalance());
                    System.out.println("Введите одну из комманд: debit, credit, balance, history, logout");

                    break;

                case ("history"):
                    client.history();
                    System.out.println("Введите одну из комманд: debit, credit, balance, history, logout");

                    break;
                case ("logout"):
                    flag = false;
                    break;
                default:
                    System.out.println("Введите одну из комманд: debit, credit, balance, history, logout");

            }
        }
    }

    public static void verifyClient(boolean checkAutorisation, Scanner scanner, ClientsDataBase clientsDataBase, Client client, boolean checkLogin) {
        while (checkAutorisation) {
            System.out.println("Добрый день! если вы уже зарегистрированный пользователь нажмите 1, Если вы хотите зарегистрироваться нажмите 0, Чтобы выйти напишите exit");
            switch (scanner.nextLine()) {
                case ("0"):
                    System.out.println("Чтобы зарегистрироваться введите username и password");
                    register(scanner, clientsDataBase);
                    break;
                case ("1"):
                    System.out.println("Чтобы войти в свой аккаунт введите username и password");
                    client = login(scanner, clientsDataBase);
                    if (client != null) {
                        sesions(client, checkLogin, scanner);
                    }
                    break;
                case ("exit"):
                    checkAutorisation = false;
                    break;
                default:
                    System.out.println("Неккоректный ввод");
            }

        }
    }
}
