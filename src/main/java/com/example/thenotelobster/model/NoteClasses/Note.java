package com.example.thenotelobster.model.NoteClasses;

/**
 * A note object for database integration
 */
public class Note {
    /** The notes ID*/
    private int noteId;
    /** The notes name*/
    private String name;
    /** The notes text*/
    private String text;

    /**
     * A constructor for the ntoe
     * @param noteID The ID of the note
     * @param name the Name of the note
     * @param text The text inside the note
     */
    public Note(int noteID, String name, String text){
        this.noteId = noteID;
        this.name = name;
        this.text= text;
    }

    /**
     * Gets the noteID
     * @return the noteID
     */
    public int getNoteId() {
        return noteId;
    }

    /**
     * Gets the Note name
     * @return the notes Name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the notes text
     * @return the notes text
     */
    public String getText(){
        return text;
    }
}