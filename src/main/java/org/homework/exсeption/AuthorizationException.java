package org.homework.exñeption;

/**
 * The `AuthorizationException` class is a custom exception that may occur in case of user authorization issues.
 * It inherits from the standard exception {@link Exception}.
 */
public class AuthorizationException extends Exception {
    /**
     * Constructs a new exception object with the specified error message.
     *
     * @param message The error message.
     */
    public AuthorizationException(String message) {
        super(message);
    }
}
