package com.example.thenotelobster;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML private TextArea AddNotes;

    @FXML private TextArea SourceLinks;

    @FXML private Button SummarizeButton;

    @FXML private Button NotesButton;

    @FXML private TextField NotesSubject;

    @FXML
    protected void onSummarizeClick() throws IOException {
        Stage stage = (Stage) SummarizeButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("summary-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        String stylesheet = HelloApplication.class.getResource("stylesheet.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);
        stage.setScene(scene);
    }

    @FXML
    protected void onNotesClick() {
        // Implement connection to notes page/ view
    }
}
