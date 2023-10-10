package org.homework.exñeption;

/**
 * The `UniqueIdException` class is a custom exception that may occur if the transaction identifier is not unique.
 * It inherits from the standard exception {@link Exception}.
 */
public class UniqueIdException extends Exception {
    /**
     * Constructs a new exception object with the specified error message.
     *
     * @param message The error message.
     */
    public UniqueIdException(String message) {
        super(message);
    }
}
