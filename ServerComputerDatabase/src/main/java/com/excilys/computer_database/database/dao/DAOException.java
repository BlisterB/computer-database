package com.excilys.computer_database.database.dao;

import java.sql.SQLException;

/**
 * Exception thrown by the DAO, it could be linked to a SQLException.
 */
public class DAOException extends Exception {
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
    public DAOException(SQLException e) {
        super(e.getMessage(), e.getCause());
    }
}