package com.example.thenotelobster;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;

public class SummaryController {

    @FXML private VBox header;
    @FXML private VBox secondary;
    @FXML private TextArea SummaryText;

    @FXML private TextArea ResummarizeNotes;

    @FXML private Button SaveButton;

    @FXML private Button ResummarizeButton;

    @FXML private Button BackButton;

    @FXML protected void onSaveClick() {
        //Implement saving summary notes
    }

    @FXML protected void onBackClick() throws IOException {
        Stage stage = (Stage) BackButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        String stylesheet = HelloApplication.class.getResource("style/stylesheet.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);
        stage.setScene(scene);
    }

    @FXML protected void onResummarise() {
        //Implement resummarise button functionality
    }

}
