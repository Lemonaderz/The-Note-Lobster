package com.example.thenotelobster.controller;

import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

import static java.awt.SystemColor.window;

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
    private Label ComplexityValue;
    @FXML
    private Slider ComplexitySlider;
    @FXML
    private ProgressIndicator LoadingIndicator;
    private Window file;

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

    @FXML
    private void initialize() {
        ComplexityValue.textProperty().bind(Bindings.createStringBinding(
                () -> String.format("%.2f", ComplexitySlider.getValue()),
                ComplexitySlider.valueProperty()
        ));
    }

    @FXML
    protected void onFilesClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Resource File");
        fileChooser.showOpenDialog(file);
    }
}
