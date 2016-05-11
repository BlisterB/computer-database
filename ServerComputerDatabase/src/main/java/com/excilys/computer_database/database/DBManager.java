package com.excilys.computer_database.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.excilys.computer_database.database.dao.DAOException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DBManager {
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static HikariConfig config;
    private static HikariDataSource datasource;
    private static ThreadLocalConnection localConnection = new ThreadLocalConnection();

    static class ThreadLocalConnection extends ThreadLocal<Connection> {
        @Override
        protected Connection initialValue() throws DAOException {
            // Obtain the connection via HikariCP
            try {
                return datasource.getConnection();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    };

    // TODO : Passer à des fichiers de propreties

    private static void initConnection() throws DAOException {
        // First use for all : initialize universal properties, just one time
        if (config == null) {
            synchronized (DBManager.class) {
                if (config == null) {
                    // Load the property file
                    String resourceName = "hikari.properties";
                    ClassLoader loader = Thread.currentThread().getContextClassLoader();
                    Properties props = new Properties();
                    try(InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
                        props.load(resourceStream);
                    } catch (IOException e) {
                        new DAOException(e.getMessage());
                    }

                    config = new HikariConfig(props);
                    // Should we close the datasource ?
                    datasource = new HikariDataSource(config);
                }
            }
        }

        // First local use : initiate the local connection
        Connection con = localConnection.get();
        if (con == null) {
            throw new DAOException("Connection has failed");
        }

    }

    /**
     * Initiate (if necessary) and return a Connection to the DB.
     * @return A Connection to the DB
     */
    public static Connection getConnection() throws DAOException {
        initConnection();
        return localConnection.get();
    }

    public static void closeConnection() throws DAOException {
        try {
            localConnection.get().close();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            localConnection.remove();
        }
    }

    public static void initTransaction() throws DAOException {
        try {
            DBManager.getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public static void rollback() throws DAOException {
        try {
            DBManager.getConnection().rollback();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public static void commit() throws DAOException {
        try {
            DBManager.getConnection().commit();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}