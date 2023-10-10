package org.homework.exñeption;

/**
 * The `BigDebitException` class is a custom exception that may occur when a debit transaction results in
 * a negative balance in the client's account.
 * It inherits from the standard exception {@link Exception}.
 */
public class BigDebitException extends Exception {
    /**
     * Constructs a new exception object with the specified error message.
     *
     * @param message The error message.
     */
    public BigDebitException(String message) {
        super(message);
    }
}
