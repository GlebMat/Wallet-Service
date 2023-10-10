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
        System.out.println("Enter username:");
        String clientName = scanner.nextLine();
        System.out.println("Enter password:");
        String clientPass = scanner.nextLine();
        clientsDataBase.setClients(clientName, clientPass);
        System.out.println("Registration was successful!");
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
            System.out.println("Authorization was successful!");

            return activeClient;
        }
        if (activeClient == null || activeClient.getPassword().equals(clientPass)) {
            System.out.println("The user with such username or password does not exist");
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
