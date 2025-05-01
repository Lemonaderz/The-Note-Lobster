package com.example.thenotelobster.model.NoteClasses;

public class Note {
    private int noteId;
    private String name;
    private String text;

    public Note(int noteID, String name, String text){
        this.noteId = noteID;
        this.name = name;
        this.text= text;
    }

    public int getNoteId() {
        return noteId;
    }

    public String getName() {
        return name;
    }
    public String getText(){
        return text;
    }
}