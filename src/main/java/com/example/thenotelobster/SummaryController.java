package com.example.thenotelobster;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.IOException;

public class SummaryController {

    @FXML private TextArea SummaryText;


    @FXML private Button SaveButton;

    @FXML private Button BackButton;

    @FXML private Button SummaryButton;

    @FXML protected void onSaveClick() {



    }

    public void setSummaryText()
    {
        AIManager aiManager = AIManager.getInstance();
        SummaryText.setText(aiManager.singleMessage);
    }

    @FXML protected void onBackClick() throws IOException {
        Stage stage = (Stage) BackButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        String stylesheet = HelloApplication.class.getResource("stylesheet.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);
        stage.setScene(scene);
    }

    @FXML private void initialize() {
//        AIManager aiManager = AIManager.getInstance();
//        String summary = aiManager.singleMessage;
//        SummaryText.setText(summary);
        AIManager aiManager = AIManager.getInstance();
        String summary = aiManager.singleMessage;
        SummaryText.setText(summary);

        Platform.runLater(() -> {

            //do stuff

        });

    }

}
