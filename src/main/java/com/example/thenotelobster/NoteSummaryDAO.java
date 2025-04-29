package com.example.thenotelobster;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class NoteSummaryDAO {
    private Connection connection;

    String userEmail = UserAccount.getInstance().getEmail();

    public NoteSummaryDAO() {
        connection = DatabaseConnection.getInstance();
    }

    public void createTable() throws SQLException {
        try {
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Notes (" +
                    "noteId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "folderID INTEGER," +
                    "text TEXT, " +
                    "subject TEXT, " +
                    "userEmail VARCHAR NOT NULL, " +
                    "FOREIGN KEY (folderId) REFERENCES Folder (folderId), " +
                    "FOREIGN KEY (userEmail) REFERENCES userAccounts )";
            statement.executeUpdate(sql); } catch (SQLException exception) {
            System.err.println(exception);
        }

    }

    public void insertSummary(String subject, String summary, String email){
        try {
            PreparedStatement insert = connection.prepareStatement(
                    "INSERT INTO Notes (name, folderId, text, subject, userEmail) " +
                            "VALUES (?, ?, ?, ?, ?)"
            );
//            insert.setInt(1, 2);
            insert.setString(1, "DEFAULT");
            insert.setInt(2, 1);
            insert.setString(3, summary);
            insert.setString(4, subject);
            insert.setString(5, email);

            insert.execute();
            System.out.println("Note Summary saved successfully");
        } catch (SQLException exception) {
            System.err.println(exception);
        }
    }

    public void deleteSummary(int noteID) {
        try {
            PreparedStatement delete = connection.prepareStatement(
                    "DELETE FROM Notes WHERE noteId = ?"
            );
            delete.setInt(1, noteID);
            delete.execute();
        } catch (SQLException exception) {
            System.err.println(exception);
        }
    }

    public void getSummary() {

    }

}
