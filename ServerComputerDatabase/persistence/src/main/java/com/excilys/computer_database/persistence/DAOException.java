package com.excilys.computer_database.persistence;

/**
 * Exception thrown by the DAO, it could be linked to a SQLException.
 */
public class DAOException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor for personalized message.
     * @param message The exception message
     */
    public DAOException(String message) {
        super(message);
    }

    /**
     * Wrapper for SQLException.
     * @param e The SQLException to wrap
     */
    public DAOException(Exception e) {
        super(e.getMessage(), e.getCause());
    }
}