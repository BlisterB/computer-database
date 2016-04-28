package com.excilys.computer_database.database;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionDB {
    private static HikariConfig config;
    private static HikariDataSource datasource;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // TODO : Passer à des fichiers de propreties
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";

    /**
     * Return a Connection to the DB.
     * @return A Connection to the DB
     */
    public static Connection getConnection() {
        // First use : initialize properties, just one time
        if (config == null) {
            synchronized (ConnectionDB.class) {
                if (config == null) {
                    config = new HikariConfig("hikari.properties");
                    config.setJdbcUrl(URL);
                    // Should we close the datasource ?
                    datasource = new HikariDataSource(config);
                }
            }
        }

        // Obtain the connection via HikariCP (for blazing fast performances)
        try {
            return datasource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}