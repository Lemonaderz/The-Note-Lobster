package com.example.thenotelobster.controller;

import javafx.scene.control.TextArea;

import java.io.*;

/**
 * Class used to Manage uploading files for the summarizing
 */
public class FileManager {

    /**
     * Loads a given file
     * @param file the file
     * @param AddNotes any additional notes added to it
     */
    public void loadFile(File file, TextArea AddNotes) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

        } catch (IOException e) {

        }

    }
}
