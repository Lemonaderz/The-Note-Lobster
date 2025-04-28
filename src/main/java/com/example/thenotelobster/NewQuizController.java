package com.example.thenotelobster;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;

public class NewQuizController extends NavigationUI {

    @FXML Button backButton;

    @FXML private TreeView<String> notesTreeView;

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
        TreeItem<String> rootItem = new TreeItem<>("My Notes");
        rootItem.setExpanded(true);

        TreeItem<String> note1 = new TreeItem<>("Biology Notes");
        TreeItem<String> note2 = new TreeItem<>("History Notes");
        TreeItem<String> note3 = new TreeItem<>("Math Notes");

        rootItem.getChildren().addAll(note1, note2, note3);

        notesTreeView.setRoot(rootItem);
    }
}
