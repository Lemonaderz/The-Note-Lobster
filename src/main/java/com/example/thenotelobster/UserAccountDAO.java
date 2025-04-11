package com.example.thenotelobster;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        // Todo Later: Create a PreparedStatement to run the UPDATE query
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
                System.out.println(userAccount.getEmail());
                System.out.println(userAccount.getPassword());
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
