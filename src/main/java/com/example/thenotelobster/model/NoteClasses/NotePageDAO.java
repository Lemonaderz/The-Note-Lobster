package com.example.thenotelobster.model.NoteClasses;

import com.example.thenotelobster.controller.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Data Access Object is used for handling the CRUD operations on folders and notes.
 */
public class NotePageDAO {
    private final Connection conn;

    /**
     * Creates a NotePageDAo and creates a database connection
     */
    public NotePageDAO(){
        conn = DatabaseConnection.getInstance();
    }

    /**
     * Inserts a new folder into the database for the user
     * @param folderName name of the newly created folder
     * @param userEmail email identifying the folder's owner
     * @return will generate a folderId on success or -1 if the insertion has failed
     */
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

    /**
     * Retrieves the ID of a folder by its name and owner.
     * @param folderName name of the folder
     * @param userEmail email identifying the owner of the folder
     * @return returns folderId if found or -1 if not found or error
     */
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

    /**
     * Inserts a new note under the specified folder
     * @param name title of the note
     * @param folderId ID fo the folder which contains the note
     * @param text content of the note
     * @param subject Unused
     */
    public void insertNote (String name, int folderId, String text, String subject){
        String sql = "INSERT INTO Notes (name, folderId, text) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setInt(2, folderId);
            statement.setString(3, text);
            statement.executeUpdate();
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();

        }

    }

    /**
     * Collects the ID of note by its name and folder
     * @param noteName title of the note
     * @param folderId ID of the folder which contains the notes
     * @return noteId if found or -1 if noteId is not found or error
     */
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

    /**
     * Renames a note by its ID
     * @param noteId ID of the note to rename
     * @param newName new title for the note
     */
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

    /**
     * Renames the folder by its old name and owner
     * @param oldName the current folder name
     * @param newName the desired folder name
     * @param userEmail the user's email identifying the folder's owner
     */
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

    /**
     * Updates the text content of the existing note.
     * @param noteId ID of the note that is need to be updated
     * @param newText new text content of the note
     */
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

    /**
     * Deletes a note by the ID
     * @param noteId ID of the note that will be deleted
     */
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

    /**
     * Deletes a folder by it's ID
     * @param folderId ID of the folder that will be deleted.
     */
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

    /**
     * Retrieves all the folders belonging to the user
     * @param userEmail the email which identify the owner
     * @return list of folders for the user
     */
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

    /**
     * Retrieves all the notes contained
     * @param folderId ID of the folder
     * @return returns the list of Note objects which belong to the folder
     */
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

