package com.example.thenotelobster.controller;

import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.converter.IntegerStringConverter;
import java.io.File;
import java.io.IOException;
import java.util.function.UnaryOperator;

/**
 * Controller Class responsible for the main view
 */
public class MainController extends NavigationUI {

    /**
     * TextArea used to add notes from summarization
     */
    @FXML
    private TextArea AddNotes;
    /**
     * TextArea used to add additional source links related to summary notes
     */
    @FXML
    private TextArea SourceLinks;
    /**
     * TextField used to enter the subject title
     */
    @FXML
    private TextField SubjectText;
    /**
     * TextField used to enter the title
     */
    @FXML
    private TextField TitleText;
    /**
     * Button attributed to summarizing note summary
     */
    @FXML
    private Button SummarizeButton;
    /**
     * Button attributed to uploading a file
     */
    @FXML
    private Button FileButton;
    /**
     * Button attributed to uploading handwritten notes
     */
    @FXML
    private Button ImageNotesButton;
    /**
     * RadioButton attributed to short response length option
     */
    @FXML
    private RadioButton ShortOption;
    /**
     * RadioButton attributed to medium response length option
     */
    @FXML
    private RadioButton MediumOption;
    /**
     * RadioButton attributed to long response length option
     */
    @FXML
    private RadioButton LongOption;
    /**
     * ToggleGroup attributed to response length options
     */
    @FXML
    private ToggleGroup LengthOption;
    /**
     * Label used to show complexity value
     */
    @FXML
    private Label ComplexityValue;
    /**
     * Slider used to select complexity value of response
     */
    @FXML
    private Slider ComplexitySlider;
    /**
     * ProgressIndicator responsible for showing that the AI Summary is loading
     */
    @FXML
    private ProgressIndicator LoadingIndicator;
    /**
     * TextField attributed to setting custom response length
     */
    @FXML
    private TextField CustomLength;

    private Window stage;

    /**
     *
     * Handles when the summary button is clicked
     * Gets the title, subject and notes inside the Main screen and
     * <ul>
     *   <li>Begins generating a summary from the AIManager</li>
     *   <li>Displays a loading indicator while processing</li>
     *   <li>Moves to summary scene on success</li>
     * </ul>
     * This method executes asynchronously to prevent program freezing
     * @throws IOException if an I/O error occurs during the summarizing
     */
    @FXML
    protected void onSummarizeClick() throws IOException {
// Modularize this at  some point with SummaryController
        String summary = AddNotes.getText();
        AIManager aiManager = AIManager.getInstance();
        double complexity = ComplexitySlider.getValue();

        String length = checkLength();

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

    /**
     * On initialize, binds value of complexity sliders to complexity value label
     * and ensures that custom length is an integer only
     */
    @FXML
    private void initialize() {
        ComplexityValue.textProperty().bind(Bindings.createStringBinding(
                () -> String.format("%.2f", ComplexitySlider.getValue()),
                ComplexitySlider.valueProperty()
        ));

        // Ensure that CustomLength input is integer only
        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String input = change.getControlNewText();
            if (input.matches("-?([1-9][0-9]*)?")) {
                return change;
            }
            return null;
        };
        CustomLength.setTextFormatter(
                new TextFormatter<Integer>(new IntegerStringConverter(), null, integerFilter));
    }

    /**
     * Checks if user has set custom length or not
     * @return the length option selected by user
     */
    private String checkLength() {
        // If CustomLength is not null use CustomLength
        if (CustomLength != null && !CustomLength.getText().trim().isEmpty()) {
            return CustomLength.getText();
        } else { // Otherwise return length options from radio buttons
            return LengthOption.getSelectedToggle().getUserData().toString();
        }
    }

    /**
     * On Action for FilesButton
     */
    @FXML
    protected void onFilesClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Resource File");
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            System.out.println("Opened File: " + selectedFile.getPath());
        }

    }
}
