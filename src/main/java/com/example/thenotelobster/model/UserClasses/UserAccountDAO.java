package com.example.thenotelobster.model.UserClasses;

import com.example.thenotelobster.controller.DatabaseConnection;

import java.sql.*;

public class UserAccountDAO {
    private Connection connection;

    public UserAccountDAO() {
        connection = DatabaseConnection.getInstance();
    }

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

    public void delete(String email) {
        try {
            PreparedStatement deleteAccount = connection.prepareStatement("DELETE FROM userAccounts WHERE email = ?");
            deleteAccount.setString(1, email);
            deleteAccount.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

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

    public void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }
}
