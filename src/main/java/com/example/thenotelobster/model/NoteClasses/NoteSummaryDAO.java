package com.example.thenotelobster.model.NoteClasses;

import com.example.thenotelobster.controller.DatabaseConnection;
import com.example.thenotelobster.model.UserClasses.UserAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class NoteSummaryDAO {
    private Connection connection;

    public NoteSummaryDAO() {
        connection = DatabaseConnection.getInstance();
    }

    public void insertSummary(String subject, String title, String summary, String email) {
        try {

            NotePageDAO notePageDAO = new NotePageDAO();

            // We can consider making this block here in the notePageDAO class and making it either its own function or part of getFolderInt by default.
            int folderId = notePageDAO.getFolderId(subject, email);
            if (folderId == -1) {
                notePageDAO.insertFolder(subject, email);
                folderId = notePageDAO.getFolderId(subject, email);
            }


            PreparedStatement insert = connection.prepareStatement(
                    "INSERT INTO Notes (name, folderId, text) " +
                            "VALUES (?, ?, ?)"
            );
//            insert.setInt(1, 2);
            insert.setString(1, title);
            insert.setInt(2, folderId);
            insert.setString(3, summary);

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
}