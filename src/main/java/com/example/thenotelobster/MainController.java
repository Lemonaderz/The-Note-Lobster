package com.example.thenotelobster;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

    @FXML private Button SignOutButton;

    //@FXML private Button NotesButton;

    //@FXML private Button SignOutButton;

    @FXML private TextField NotesSubject;

    @FXML private RadioButton ShortOption;

    @FXML private RadioButton MediumOption;

    @FXML private RadioButton LongOption;

    @FXML private ToggleGroup LengthOption;

    @FXML private Slider ComplexitySlider;

    @FXML private ProgressIndicator LoadingIndicator;

    @FXML
    protected void onSummarizeClick() throws IOException {
// Modularize this at  some point with SummaryController
        String summary = AddNotes.getText();
        AIManager aiManager = AIManager.getInstance();
        double complexity = ComplexitySlider.getValue();
        String length = LengthOption.getSelectedToggle().getUserData().toString();

//        aiManager.fetchAsynchronousChatResponse(summary,length, complexity, new MainResponseListener());

        Task<Void> fetchAsynchronousChatResponse = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                System.out.println("Currently working");
                aiManager.clearChat();
                aiManager.fetchChatResponse(summary, length, complexity);
                System.out.println("Obtained Response");
                aiManager.singleSummary.SetSubject(NotesSubject.getText());
                return null;
            }
        };

        fetchAsynchronousChatResponse.setOnSucceeded(e -> {
            System.out.println("Going to Summary Page");

            try {
                goToSummaryPage();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }


        });
        LoadingIndicator.setVisible(true);
        SummarizeButton.setDisable(true);

        new Thread(fetchAsynchronousChatResponse).start();
    }

    @FXML
    protected void goToSummaryPage() throws IOException {

        System.out.println("Summary made");

        Stage stage = (Stage) SummarizeButton.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("summary-view.fxml"));

        Scene scene = null;
        scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);

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
    protected void onSignOut() {
        // Implement sign out
    }


    static class MainResponseListener implements ResponseListener {
        String response;
        @Override
        public void onResponseReceived(String response) {
            this.response = response;

        }
    }


}
