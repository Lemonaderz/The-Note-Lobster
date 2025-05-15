package com.example.thenotelobster.controller;

import javafx.scene.control.TextArea;

import java.io.*;

/**
 * Class used to Manage uploading files for the summarizing
 */
public class FileManager {


    public void loadFile(File file, TextArea AddNotes) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

        } catch (IOException e) {

        }

    }
}
