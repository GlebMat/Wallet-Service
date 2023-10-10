package org.homework.exeption;
/**
 * Класс AutorizationException является пользовательским исключением,
 * которое может возникнуть в случае проблем с авторизацией пользователя.
 * Наследует стандартное исключение {@link Exception}.
 */
public class AutorizationException extends Exception {
    /**
     * Создает новый объект исключения с заданным сообщением об ошибке.
     *
     * @param message сообщение об ошибке
     */
    public AutorizationException(String message) {
        super(message);
    }
}
