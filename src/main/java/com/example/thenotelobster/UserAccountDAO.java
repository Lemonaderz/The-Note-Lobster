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
        // Todo Later: Create a Statement to run the CREATE TABLE query
    }

    public void insert(UserAccount userAccount) {
        // Todo Later: Create a PreparedStatement to run the INSERT query
    }

    public void update(UserAccount userAccount) {
        // Todo Later: Create a PreparedStatement to run the UPDATE query
    }

    public void delete(int id) {
        // Todo Later: Create a PreparedStatement to run the DELETE query
    }

    public List<UserAccount> getAll() {
        List<UserAccount> accounts = new ArrayList<>();
        // Todo Later: Create a Statement to run the SELECT * query
        // and populate the accounts list above
        return accounts;
    }

    public UserAccount getById(int id) {
        // Todo Later: Create a PreparedStatement to run the conditional SELECT query
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
