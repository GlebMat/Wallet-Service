package org.homework.exeption;

/**
 * Класс UniquIdExeption является пользовательским исключением,
 * которое может возникнуть в случае, если идентификатор транзакции не уникален.
 * Наследует стандартное исключение {@link Exception}.
 */
public class UniquIdExeption extends Exception {
    /**
     * Создает новый объект исключения с заданным сообщением об ошибке.
     *
     * @param message сообщение об ошибке
     */
    public UniquIdExeption(String message) {
        super(message);
    }
}
