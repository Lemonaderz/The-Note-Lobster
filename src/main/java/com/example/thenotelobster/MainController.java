package com.example.thenotelobster;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

public class MainController {

    @FXML private BorderPane parent;

    @FXML private VBox header;

    @FXML private VBox secondary;

    @FXML private TextArea AddNotes;

    @FXML private TextArea SourceLinks;

    @FXML private Button modeButton;

    @FXML private Button SummarizeButton;

    @FXML private Button NotesButton;

    @FXML private Button SignOutButton;

    //@FXML private Button NotesButton;

    //@FXML private Button SignOutButton;

    @FXML private TextField NotesSubject;

    @FXML private RadioButton ShortOption;

    @FXML private RadioButton MediumOption;

    @FXML private RadioButton LongOption;

    @FXML private ToggleGroup LengthOption;

    private double mode = 0;

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
    protected void onNotesClick() throws IOException {
        Stage stage = (Stage) NotesButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("NotePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        String stylesheet = HelloApplication.class.getResource("stylesheet.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);
        stage.setScene(scene);
    }

    @FXML
    protected void onChangeModeClick() {
//        modeButton.setOnMouseClicked(event -> {
//            if (mode == 0) {
//                parent.getStylesheets().add("stylesheet.css");
//                parent.getStylesheets().remove("dark_mode.css");
//                mode = 1;
//            } else if (mode == 1) {
//                parent.getStylesheets().add("dark_mode.css");
//                parent.getStylesheets().remove("stylesheet.css");
//                mode = 0;
//            }
//        });
    }

    @FXML
    protected void onSignOut() {
        // Implement sign out
    }
}
