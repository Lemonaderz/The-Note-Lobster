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
                            + "userName VARCHAR PRIMARY KEY NOT NULL, "
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
                    "INSERT INTO userAccounts (userName, password) VALUES (?, ?)"
            );
            insertAccount.setString(1, userAccount.getUserName());
            insertAccount.setString(2, userAccount.getPassword());
            insertAccount.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public void update(UserAccount userAccount) {
        // Todo Later: Create a PreparedStatement to run the UPDATE query
    }

    public void delete(String userName) {
        // Todo Later: Create a PreparedStatement to run the DELETE query
    }

    public UserAccount getByUsername(String userName) {
        try {
            PreparedStatement getAccount = connection.prepareStatement("SELECT * FROM userAccounts WHERE userName = ?");
            getAccount.setString(1, userName);
            ResultSet rs = getAccount.executeQuery();
            if (rs.next()) {
                return new UserAccount(
                        rs.getString("userName"),
                        rs.getString("password")
                );
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return null;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }
}
