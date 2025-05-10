package com.example.thenotelobster.controller;

import com.example.thenotelobster.*;
import com.example.thenotelobster.model.NoteClasses.NoteSummaryDAO;
import com.example.thenotelobster.model.SummaryResponse;
import com.example.thenotelobster.model.UserClasses.UserAccount;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

/**
 * Controller Class responsible for the summary view
 */
public class SummaryController extends NavigationUI {

    /**
     * TextArea containing the AI summary output
     */
    @FXML
    private TextArea SummaryText;
    /**
     * TextArea used to enter note contents for re-summarization
     */
    @FXML
    private TextArea ResummarizeNotes;
    /**
     * Button attributed to saving note summary
     */
    @FXML
    private Button SaveButton;
    /**
     * Button attributed to re-summarizing note summary
     */
    @FXML
    private Button ResummarizeButton;
    /**
     * Button attributed to going back to the main view
     */
    @FXML
    private Button BackButton;
    /**
     * ProgressIndicator used to show that the AI is currently working at generating a response
     */
    @FXML
    private ProgressIndicator LoadingIndicator;
    /**
     * Text-field used to display the subject of the summary
     */
    @FXML
    private TextField SubjectText;
    /**
     * Text-field used to display the title of the summary
     */
    @FXML private TextField TitleText;

    SummaryAlert summaryAlert = new SummaryAlert();

    /**
     * Saves the AI Summary response when SaveButton is clicked
     * @throws IOException
     */
    @FXML protected void onSaveClick() throws IOException {
        AIManager aiManager = AIManager.getInstance();
        aiManager.clearChat();
        String subject = SubjectText.getText();
        String summary = SummaryText.getText();
        String title = TitleText.getText();
        String email = UserAccount.getInstance().getEmail();

        NoteSummaryDAO noteSummaryDAO = new NoteSummaryDAO();
        noteSummaryDAO.insertSummary(subject,title, summary, email);
        onNotesClick();
    }

    @FXML protected void onResummarise()
    {
        //Modularize this when I can
        AIManager aiManager = AIManager.getInstance();

//        aiManager.fetchAsynchronousChatResponse(summary,length, complexity, new MainResponseListener());

        Task<Void> fetchAsynchronousChatResponse = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                SummaryResponse summaryResponse = aiManager.singleSummary;
                System.out.println("Currently working");
                aiManager.fetchChatResponse(ResummarizeNotes.getText(), summaryResponse.getLength(), summaryResponse.getComplexity());
                System.out.println("Obtained Response");
                return null;
            }
        };

        fetchAsynchronousChatResponse.setOnSucceeded(e -> {
            System.out.println("Refreshing Summary");

            ResummarizeButton.setDisable(false);
            LoadingIndicator.setVisible(false);
            setSummaryDetails();


        });
        LoadingIndicator.setVisible(true);
        ResummarizeButton.setDisable(true);

        new Thread(fetchAsynchronousChatResponse).start();
    }

    public void setSummaryDetails()
    {
        AIManager aiManager = AIManager.getInstance();
        SubjectText.setText(aiManager.singleSummary.getSubject());
        TitleText.setText(aiManager.singleSummary.getTitle());
        SummaryText.setText(aiManager.singleSummary.getResponse());
    }

    /**
     * Shows alert and sends user back to Main View when Continue is clicked
     * @throws IOException
     */
    @FXML protected void onBackClick() throws IOException {
        Optional<ButtonType> result = summaryAlert.alert.showAndWait();
        if (result.orElse(summaryAlert.Cancel) == summaryAlert.Continue) {
            // If Continue button is clicked then go to page
            super.onMainClick(); // Send back to main
        } else if (result.orElse(summaryAlert.Continue) == summaryAlert.Cancel) {
            // Else if Cancel Button is clicked close alert
            summaryAlert.alert.close();
        }
    }

    @Override
    protected void onMainClick() throws IOException {
        Optional<ButtonType> result = summaryAlert.alert.showAndWait();
        if (result.orElse(summaryAlert.Cancel) == summaryAlert.Continue) {
            // If Continue button is clicked then go to page
            super.onMainClick();
        } else if (result.orElse(summaryAlert.Continue) == summaryAlert.Cancel) {
            // Else if Cancel Button is clicked close alert
            summaryAlert.alert.close();
        }
    }

    @Override
    protected void onNotesClick() throws IOException {
        Optional<ButtonType> result = summaryAlert.alert.showAndWait();
        if (result.orElse(summaryAlert.Cancel) == summaryAlert.Continue) {
            // If Continue button is clicked then go to page
            super.onNotesClick();
        } else if (result.orElse(summaryAlert.Continue) == summaryAlert.Cancel) {
            // Else if Cancel Button is clicked close alert
            summaryAlert.alert.close();
        }
    }

    @Override
    protected void onAccountClick() throws IOException {
        Optional<ButtonType> result = summaryAlert.alert.showAndWait();
        if (result.orElse(summaryAlert.Cancel) == summaryAlert.Continue) {
            // If Continue button is clicked then go to page
            super.onAccountClick();
        } else if (result.orElse(summaryAlert.Continue) == summaryAlert.Cancel) {
            // Else if Cancel Button is clicked close alert
            summaryAlert.alert.close();
        }
    }

    @Override
    protected void onSignOut() throws IOException {
        Optional<ButtonType> result = summaryAlert.alert.showAndWait();
        if (result.orElse(summaryAlert.Cancel) == summaryAlert.Continue) {
            // If Continue button is clicked then go to page
            super.onSignOut();
        } else if (result.orElse(summaryAlert.Continue) == summaryAlert.Cancel) {
            // Else if Cancel Button is clicked close alert
            summaryAlert.alert.close();
        }
    }

    @Override
    protected void onQuizClick() throws IOException {
        Optional<ButtonType> result = summaryAlert.alert.showAndWait();
        if (result.orElse(summaryAlert.Cancel) == summaryAlert.Continue) {
            // If Continue button is clicked then go to page
            super.onQuizClick();
        } else if (result.orElse(summaryAlert.Continue) == summaryAlert.Cancel) {
            // Else if Cancel Button is clicked close alert
            summaryAlert.alert.close();
        }
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
