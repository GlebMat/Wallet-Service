package org.homework.ui;

import org.homework.dataacess.ClientsDataBase;
import org.homework.domain.Client;
import org.homework.exeption.BigDebetException;
import org.homework.exeption.UniquIdExeption;


import java.util.Scanner;

/**
 * Класс Input представляет консольное приложение для управления кредитными/дебетовыми транзакциями игроков.
 * Пользователи могут зарегистрироваться, авторизоваться, просматривать баланс, выполнять дебетовые и кредитные транзакции,
 * а также просматривать историю транзакций.
 */
public class Input {
    /**
     * Точка входа в приложение. Создает объекты Scanner, ClientsDataBase и Client,
     * а затем запускает процесс авторизации и управления транзакциями.
     *
     * @param args аргументы командной строки (не используются).
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
     * Регистрирует нового игрока путем запроса имени пользователя и пароля через консольный ввод.
     * После успешной регистрации выводит сообщение об успешной регистрации.
     *
     * @param scanner         объект Scanner для чтения ввода пользователя.
     * @param clientsDataBase объект ClientsDataBase для хранения зарегистрированных игроков.
     */
    public static void register(Scanner scanner, ClientsDataBase clientsDataBase) {
        System.out.println("Введите имя пользователя:");
        String clientName = scanner.nextLine();
        System.out.println("Введите пароль:");
        String clientPass = scanner.nextLine();
        clientsDataBase.setClients(clientName, clientPass);
        System.out.println("Регистрация прошла успешно!");
    }

    /**
     * Позволяет игроку авторизоваться, запрашивая имя пользователя и пароль через консольный ввод.
     * После успешной авторизации возвращает объект клиента, иначе выводит сообщение об ошибке.
     *
     * @param scanner         объект Scanner для чтения ввода пользователя.
     * @param clientsDataBase объект ClientsDataBase для проверки учетных данных игрока.
     * @return объект Client, представляющий авторизованного игрока, или null в случае ошибки.
     */
    public static Client login(Scanner scanner, ClientsDataBase clientsDataBase) {
        String clientName = scanner.nextLine();
        String clientPass = scanner.nextLine();
        Client activeClient = clientsDataBase.getClients().get(clientName);

        if (clientPass.equals(activeClient.getPassword())) {
            System.out.println("Авторизация прошла успешно!");

            return activeClient;
        }
        if (activeClient == null || activeClient.getPassword().equals(clientPass)) {
            System.out.println("Пользователя с таким username или password не существует");
        }
        return activeClient;
    }

    /**
     * Позволяет авторизованному игроку выполнять различные действия, такие как дебетовые и кредитные транзакции,
     * просмотр баланса и истории транзакций. Выходит из этой сессии, когда игрок выбирает опцию "Выйти".
     *
     * @param client       авторизованный игрок.
     * @param shouldLogout флаг, указывающий, продолжать ли сессию.
     * @param scanner      объект Scanner для чтения ввода пользователя.
     */
    public static void sesions(Client client, boolean shouldLogout, Scanner scanner) {

        while (shouldLogout) {
            System.out.println("Выберите действие:");
            System.out.println("1 - Дебет");
            System.out.println("2 - Кредит");
            System.out.println("3 - Баланс");
            System.out.println("4 - История транзакций");
            System.out.println("0 - Выйти");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {

                case 1:
                    System.out.println("Введите сумму дебита");
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
                    System.out.println("Введите сумму кредита");
                    int s3 = scanner.nextInt();
                    try {
                        client.credit(s3);
                    } catch (UniquIdExeption e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println(client.getBalance());
                    System.out.println("Введите одну из комманд: debit, credit, balance, history, logout");

                    break;

                case 4:
                    client.history();
                    System.out.println("Введите одну из комманд: debit, credit, balance, history, logout");

                    break;
                case 0:
                    shouldLogout = false;
                    break;
                default:
                    System.out.println("Введите одну из комманд: debit, credit, balance, history, logout");

            }
        }
    }

    /**
     * Позволяет пользователям регистрироваться или авторизовываться и управлять своими транзакциями.
     * Выходит из приложения, когда пользователь выбирает опцию "Выход".
     *
     * @param shouldExit      флаг, указывающий, завершить ли приложение.
     * @param scanner         объект Scanner для чтения ввода пользователя.
     * @param clientsDataBase объект ClientsDataBase для управления пользователями.
     * @param client          объект Client для хранения текущего пользователя.
     * @param checkLogin      флаг, указывающий, авторизован ли текущий пользователь.
     */
    public static void verifyClient(boolean shouldExit, Scanner scanner, ClientsDataBase clientsDataBase, Client client, boolean checkLogin) {
        while (shouldExit) {
            System.out.println("Добрый день! Выберите действие:");
            System.out.println("1 - Регистрация");
            System.out.println("2 - Авторизация");
            System.out.println("0 - Выход");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Чтобы зарегистрироваться введите username и password");
                    register(scanner, clientsDataBase);
                    break;
                case 2:
                    System.out.println("Чтобы войти в свой аккаунт введите username и password");
                    client = login(scanner, clientsDataBase);
                    if (client != null) {
                        sesions(client, checkLogin, scanner);
                    }
                    break;
                case 0:
                    shouldExit = false;
                    break;
                default:
                    System.out.println("Неккоректный ввод");
            }

        }
    }
}
