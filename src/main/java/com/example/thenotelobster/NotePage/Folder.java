package com.example.thenotelobster.NotePage;

public class Folder {
    private int folderId;
    private String name;
    public Folder(int folderId, String name) {
        this.folderId = folderId;
        this.name = name;

    }

    public int getFolderId() {
        return folderId;
    }

    public String getName() {
        return name;
    }
}
