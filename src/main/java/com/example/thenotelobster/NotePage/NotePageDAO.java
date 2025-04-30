package com.example.thenotelobster.NotePage;
import com.example.thenotelobster.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotePageDAO {
    private final Connection conn;

    public NotePageDAO(){
        conn = DatabaseConnection.getInstance();
    }

    public int insertFolder(String folderName, String userEmail) {
        String sql = "INSERT INTO Folder (name, userEmail) VALUES (?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, folderName);
            statement.setString(2, userEmail);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) return resultSet.getInt(1);
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        } return -1;
    }

    public int getFolderId(String folderName, String userEmail){
        String sql = "SELECT folderId from Folder WHERE name = ? AND userEmail = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, folderName);
            statement.setString(2, userEmail);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) return resultSet.getInt("folderId");
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        } return -1;
    }

    public void insertNote (String name, int folderId, String text, String subject){
        String sql = "INSERT INTO Notes (name, folderId, text, subject) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setInt(2, folderId);
            statement.setString(3, text);
            statement.setString(4, subject);
            statement.executeUpdate();
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();

        }

    }

    public int getNoteId(String noteName, int folderId){
        String sql = "SELECT noteId FROM Notes WHERE name = ? AND folderId = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, noteName);
            statement.setInt(2, folderId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) return resultSet.getInt("noteId");
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        } return -1;
    }

    public void renameNote(int noteId, String newName){
        String sql ="UPDATE Notes SET name = ? WHERE noteId = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)){
            statement.setString(1,newName);
            statement.setInt(2,noteId);
            statement.executeUpdate();
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public void renameFolder(String oldName, String newName, String userEmail){
        String sql = "UPDATE Folder SET name = ? WHERE name = ? AND userEmail = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, newName);
            statement.setString(2, oldName);
            statement.setString(3, userEmail);
            statement.executeUpdate();
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public void updateNoteText(int noteId, String newText){
        String sql = "UPDATE Notes SET text = ? WHERE noteId = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, newText);
            statement.setInt(2, noteId);
            statement.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public void deleteNote(int noteId){
        String sql = "DELETE FROM Notes WHERE noteId = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, noteId);
            statement.executeUpdate();
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public void deleteFolder(int folderId){
        String sql = "DELETE FROM Folder WHERE folderId = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, folderId);
            statement.executeUpdate();
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }


    public List<Folder> getAllFolders(String userEmail){
        List<Folder> folders = new ArrayList<>();
        try(PreparedStatement statement = conn.prepareStatement(
                "SELECT folderId, name FROM Folder WHERE userEmail = ?"))
        {
            statement.setString(1, userEmail);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                folders.add(new Folder(resultSet.getInt("folderId"), resultSet.getString("name")));
            }
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return  folders;
    }

    public List<Note> getNotesByFolder(int folderId) {
        List<Note> notes = new ArrayList<>();
        String sql =  "SELECT * FROM Notes WHERE folderId = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)){
            statement.setInt(1,folderId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                notes.add(new Note(resultSet.getInt("noteId"), resultSet.getString("name"), resultSet.getString("text")));
            }
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return notes;
    }
}

