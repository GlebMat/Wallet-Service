package org.homework.dataacess;

/**
 * The `IdTransaction` class represents a unique transaction identifier.
 * This class allows you to create and retrieve unique identifiers for transactions.
 */
public class IdTransaction {
    /**
     * The unique identifier for the transaction.
     */
    private int id = 0;

    /**
     * Get the current value of the unique transaction identifier.
     *
     * @return The unique transaction identifier.
     */
    public int getId() {
        return id;
    }

    /**
     * Set a new value for the unique transaction identifier.
     *
     * @param id The new value of the unique transaction identifier.
     */
    public void setId(int id) {
        this.id = id;
    }
}
