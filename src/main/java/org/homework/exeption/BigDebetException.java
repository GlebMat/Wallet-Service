package org.homework.exeption;

/**
 * Класс BigDebetException является пользовательским исключением,
 * которое может возникнуть в случае, если дебетовая транзакция приводит к
 * отрицательному балансу на счете клиента.
 * Наследует стандартное исключение {@link Exception}.
 */
public class BigDebetException extends Exception {
    /**
     * Создает новый объект исключения с заданным сообщением об ошибке.
     *
     * @param message сообщение об ошибке
     */
    public BigDebetException(String message) {
        super(message);
    }
}
