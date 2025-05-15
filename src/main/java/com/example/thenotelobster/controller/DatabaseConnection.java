package com.example.thenotelobster.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A class to return an instance of an sqlite database connection.
 */
public class DatabaseConnection {
    /** A connection to the database, only one per running application */
    private static Connection instance = null;
    /** private constructor, to create a connection on start */
    private DatabaseConnection() {
        String url = "jdbc:sqlite:database.db";
        try {
            instance = DriverManager.getConnection(url);
        } catch (SQLException sqlEx) {
            System.err.println(sqlEx);
        }
    }

    /**
     * Get an instance of the current database connection
     * @return an SQLite connection instance of the current database connection
     */
    public static Connection getInstance() {
        if (instance == null) {
            new DatabaseConnection();
        }
        return instance;
    }
}
