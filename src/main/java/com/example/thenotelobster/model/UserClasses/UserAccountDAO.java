package com.example.thenotelobster.model.UserClasses;

import com.example.thenotelobster.controller.DatabaseConnection;

import java.sql.*;

/**
 * Data Access Object (DAO) class for managing user accounts in the database.
 */
public class UserAccountDAO {
    /** Database connection object. */
    private Connection connection;

    /**
     * Initializes a new UserAccountDAO instance and connects to the database.
     * Retrieves the Singleton database connection instance.
     */
    public UserAccountDAO() {
        connection = DatabaseConnection.getInstance();
    }

    /**
     * Creates the `userAccounts` table if it does not already exist.
     * The table includes columns for email (primary key), username, and password.
     */
    public void createTable() {
        try {
            Statement createTable = connection.createStatement();
            createTable.execute(
                    "CREATE TABLE IF NOT EXISTS userAccounts ("
                            + "email VARCHAR PRIMARY KEY NOT NULL, "
                            + "userName VARCHAR NOT NULL, "
                            + "password VARCHAR NOT NULL "
                            + ")"
            );
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    /**
     * Inserts a new user account into the database.
     *
     * @param userAccount the UserAccount object containing user details to insert.
     */
    public void insert(UserAccount userAccount) {
        try {
            PreparedStatement insertAccount = connection.prepareStatement(
                    "INSERT INTO userAccounts (email, userName, password) VALUES (?, ?, ?)"
            );
            insertAccount.setString(1, userAccount.getEmail());
            insertAccount.setString(2, userAccount.getUserName());
            insertAccount.setString(3, userAccount.getPassword());
            insertAccount.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    /**
     * Updates the username and password of an existing user in the database.
     * Uses the email to identify the record to update.
     *
     * @param userAccount the UserAccount object containing updated user details.
     */
    public void update(UserAccount userAccount) {
        try {
            PreparedStatement updateAccount = connection.prepareStatement(
                    "UPDATE userAccounts SET userName = ?, password = ? WHERE email = ?"
            );
            updateAccount.setString(1, userAccount.getUserName());
            updateAccount.setString(2, userAccount.getPassword());
            updateAccount.setString(3, userAccount.getEmail());
            updateAccount.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    /**
     * Deletes a user account from the database using the provided email.
     *
     * @param email the email of the user to delete.
     */
    public void delete(String email) {
        try {
            PreparedStatement deleteAccount = connection.prepareStatement("DELETE FROM userAccounts WHERE email = ?");
            deleteAccount.setString(1, email);
            deleteAccount.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    /**
     * Retrieves a user by email from the database and populates the singleton UserAccount instance.
     *
     * @param email the email of the user to retrieve.
     */
    public void getByEmail(String email) {
        try {
            UserAccount userAccount = UserAccount.getInstance();
            PreparedStatement getAccount = connection.prepareStatement("SELECT * FROM userAccounts WHERE email = ?");
            getAccount.setString(1, email);
            ResultSet rs = getAccount.executeQuery();
            if (rs.next()) {
                userAccount.setUser(
                        rs.getString("email"),
                        rs.getString("userName"),
                        rs.getString("password")

                );
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }

    }

    /**
     * Closes the database connection.
     * Should be called when DAO operations are complete to free resources.
     */
    public void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }
}
