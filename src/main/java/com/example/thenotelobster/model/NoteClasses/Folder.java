package com.example.thenotelobster.model.NoteClasses;

/**
 * A model of a folder object for database integration
 */
public class Folder {
    /** The folder id*/
    private int folderId;
    /** The name of the folder*/
    private String name;

    /**
     * A constructor for a folder
     * @param folderId The id of a folder
     * @param name the folder name
     */
    public Folder(int folderId, String name) {
        this.folderId = folderId;
        this.name = name;

    }

    /**
     * Gets the  folderId
     * @return the folder id as an int
     */
    public int getFolderId() {
        return folderId;
    }

    /**
     * gets the  folderName
     * @return the folderName as string
     */
    public String getName() {
        return name;
    }
}
