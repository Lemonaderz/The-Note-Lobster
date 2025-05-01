package com.example.thenotelobster.controller;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class MainController extends NavigationUI {

    @FXML
    private TextArea AddNotes;
    @FXML
    private TextArea SourceLinks;
    @FXML
    private TextField SubjectText;
    @FXML
    private TextField TitleText;
    @FXML
    private Button SummarizeButton;
    @FXML
    private RadioButton ShortOption;
    @FXML
    private RadioButton MediumOption;
    @FXML
    private RadioButton LongOption;
    @FXML
    private ToggleGroup LengthOption;

    @FXML
    private Slider ComplexitySlider;

    @FXML private Button QuizButton;

    @FXML
    private ProgressIndicator LoadingIndicator;

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
                aiManager.singleSummary.setSubject(SubjectText.getText());
                aiManager.singleSummary.setTitle(TitleText.getText());
                return null;
            }
        };

        fetchAsynchronousChatResponse.setOnSucceeded(e -> {
            System.out.println("Going to Summary Page");

            try {
                goToSummaryPage(SummarizeButton);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }


        });
        LoadingIndicator.setVisible(true);
        SummarizeButton.setDisable(true);

        new Thread(fetchAsynchronousChatResponse).start();
    }


}
