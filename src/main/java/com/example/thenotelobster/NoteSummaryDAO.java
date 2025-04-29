package com.example.thenotelobster;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class NoteSummaryDAO {
    private Connection connection;
    public NoteSummaryDAO() {
        connection = DatabaseConnection.getInstance();
    }

    public void createTable() throws SQLException {
        Statement statement = connection.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS Notes (" +
                "noteId INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL, " +
                "folderID INTEGER FOREIGN KEY REFERENCES Folder," +
                "text TEXT, " +
                "subject INTEGER, " +
                "userEmail VARCHAR NOT NULL FOREIGN KEY REFERENCES userAccounts)";
        statement.executeUpdate(sql);
    }



    public void saveSummary(){
//        try {
//            PreparedStatement insertAccount = connection.prepareStatement(
//                    "INSERT INTO Notes (email, userName, password) VALUES (?, ?, ?)"
//            );
//            insertAccount.setString(1, userAccount.getEmail());
//            insertAccount.setString(2, userAccount.getUserName());
//            insertAccount.setString(3, userAccount.getPassword());
//            insertAccount.execute();
//        } catch (SQLException ex) {
//            System.err.println(ex);
//        }
    }

}
