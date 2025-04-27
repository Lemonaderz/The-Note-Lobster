package com.example.thenotelobster;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.Text;
import java.io.IOException;

public class SummaryController extends NavigationUI {

    @FXML private TextArea SummaryText;

    @FXML private TextArea ResummarizeNotes;

    @FXML private Button SaveButton;
    @FXML private Button ResummarizeButton;
    @FXML private Button BackButton;

    @FXML private Button SummaryButton;

    @FXML private TextField SubjectText;

    @FXML protected void onSaveClick() {



    }

    @FXML protected void onResummarizeClick()
    {
        //Modularize this when I can
        AIManager aiManager = AIManager.getInstance();

//        aiManager.fetchAsynchronousChatResponse(summary,length, complexity, new MainResponseListener());

        Task<Void> fetchAsynchronousChatResponse = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                String resummarizeText = "";
                SummaryResponse summaryResponse = aiManager.singleSummary;
                System.out.println("Currently working");
                aiManager.fetchChatResponse(resummarizeText, summaryResponse.length, summaryResponse.complexity);
                System.out.println("Obtained Response");
                return null;
            }
        };

        fetchAsynchronousChatResponse.setOnSucceeded(e -> {
            System.out.println("Refreshing Summary");

            //ResummarizeButton.setDisable(false);
            //LoadingIndicator.setVisible(false);
            setSummaryDetails();


        });
        //LoadingIndicator.setVisible(true);
        //ResummarizeButton.setDisable(true);

        new Thread(fetchAsynchronousChatResponse).start();
    }

    public void setSummaryDetails()
    {
        AIManager aiManager = AIManager.getInstance();
        SubjectText.setText(aiManager.singleSummary.subject);
        SummaryText.setText(aiManager.singleSummary.response);
    }

    @FXML protected void onBackClick() throws IOException {
        Stage stage = (Stage) BackButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        scene.getStylesheets().add(checkCurrentMode());
        stage.setScene(scene);
    }

    @FXML protected void onResummarise() {
        //Implement resummarise button functionality

    }

    @FXML private void initialize() {
//        AIManager aiManager = AIManager.getInstance();
//        String summary = aiManager.singleMessage;
//        SummaryText.setText(summary);

        setSummaryDetails();


        Platform.runLater(() -> {

            //do stuff

        });

    }

}
