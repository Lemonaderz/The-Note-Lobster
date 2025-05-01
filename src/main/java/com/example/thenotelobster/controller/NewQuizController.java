package com.example.thenotelobster.controller;

import com.example.thenotelobster.HelloApplication;
import com.example.thenotelobster.model.QuizClasses.QuizResponse;
import com.example.thenotelobster.model.QuizClasses.QuizDAO;
import com.example.thenotelobster.model.UserClasses.UserAccount;
import com.example.thenotelobster.model.NoteClasses.Folder;
import com.example.thenotelobster.model.NoteClasses.Note;
import com.example.thenotelobster.model.NoteClasses.NotePageDAO;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class NewQuizController extends NavigationUI {

    @FXML Button backButton;

    @FXML Button createQuizButton;

    @FXML private TreeView<String> notesTreeView;
    @FXML private ProgressIndicator LoadingIndicator;

    private final NotePageDAO notePageDAO = new NotePageDAO();
    private final String userEmail = UserAccount.getInstance().getEmail();

    @FXML
    protected void onBackButtonClick() throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("quiz-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        scene.getStylesheets().add(checkCurrentMode());
        stage.setScene(scene);

    }

    @FXML
    public void initialize() {
        TreeItem<String> root = new TreeItem<>("My Notes");
        root.setExpanded(true);

        // Load each folder for this user
        for (Folder f : notePageDAO.getAllFolders(userEmail)) {
            TreeItem<String> folderItem = new TreeItem<>(f.getName());
            folderItem.setExpanded(true);

            // Load notes in that folder
            for (Note n : notePageDAO.getNotesByFolder(f.getFolderId())) {
                TreeItem<String> noteItem = new TreeItem<>(n.getName());
                folderItem.getChildren().add(noteItem);
            }
            root.getChildren().add(folderItem);
        }

        notesTreeView.setRoot(root);
        notesTreeView.setShowRoot(false);
    }


    @FXML
    public void onCreateQuizButtonClick() {
        // 1) figure out what TreeItem is selected
        TreeItem<String> sel = notesTreeView.getSelectionModel().getSelectedItem();
        if (sel == null || sel.getParent() == null || sel.getParent().getValue().equals("My Notes")) {
            // nothing selected or a folder (not a note) selected
            System.out.println("Please select a note to generate a quiz from.");
            return;
        }

        // 2) determine folderName and noteName
        String noteName   = sel.getValue();
        String folderName = sel.getParent().getValue();

        // 3) look up folderId (scoped to current user)
        int folderId = notePageDAO.getFolderId(folderName, userEmail);
        if (folderId == -1) {
            System.err.println("Unknown folder: " + folderName);
            return;
        }

        // 4) fetch the actual Note object so we can get its text
        Note chosenNote = notePageDAO
                .getNotesByFolder(folderId)
                .stream()
                .filter(n -> n.getName().equals(noteName))
                .findFirst()
                .orElse(null);

        if (chosenNote == null) {
            System.err.println("Could not find note text for: " + noteName);
            return;
        }

        String finalText = chosenNote.getText();    // the note’s full text

        // 5) run AI call + save quiz in background
        Task<Void> fetchAsynchronousChatResponse = new Task<>() {
            @Override
            protected Void call() throws Exception {
                System.out.println("Generating quiz…");
                QuizResponse quizResponse =
                        AIManager.getInstance().fetchQuizResponse(finalText);

                // now save into your database (quizResponse + folderId)
                QuizDAO dao = new QuizDAO();
                dao.save(quizResponse, folderId);
                System.out.println("Quiz generated and saved.");
                return null;
            }
        };

        fetchAsynchronousChatResponse.setOnSucceeded(e -> {
            // restore UI & navigate back to quiz view
            createQuizButton.setDisable(false);
            LoadingIndicator.setVisible(false);
            try {
                Stage stage = (Stage) createQuizButton.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(
                        HelloApplication.class.getResource("quiz-view.fxml"));
                Scene scene = new Scene(loader.load(),
                        HelloApplication.WIDTH,
                        HelloApplication.HEIGHT);
                scene.getStylesheets().add(checkCurrentMode());
                stage.setScene(scene);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        // disable button & show spinner while working
        createQuizButton.setDisable(true);
        LoadingIndicator.setVisible(true);
        new Thread(fetchAsynchronousChatResponse).start();
    }


}
