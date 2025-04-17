package com.example.thenotelobster.NotePage;

import com.example.thenotelobster.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.stage.Stage;

import java.io.IOException;


public class TheNotePageController {
    @FXML private ImageView icon;
    @FXML private TreeView<String> chatHistory;
    @FXML private VBox leftVBox;
    @FXML private HBox buttonBox;
    @FXML private VBox centerVBox;

    private TreeItem<String> rootItem;

    @FXML
    public void initialize() {
        // Initialize chat history tree
        rootItem = new TreeItem<>("Subjects");
        rootItem.setExpanded(true);
        chatHistory.setRoot(rootItem);
        chatHistory.setCellFactory(tv -> new TextFieldTreeCellImpl());

        // Load icon using the correct path
        /*
        icon.setImage(new Image(getClass().getResource("/com/example/refactorsummary/test.png").toExternalForm()));
         */

        // Set padding programmatically
        leftVBox.setPadding(new Insets(10));
        buttonBox.setPadding(new Insets(10));
        centerVBox.setPadding(new Insets(10));
    }



    @FXML
    private void createNewChat() {
        TreeItem<String> selectedFolder = chatHistory.getSelectionModel().getSelectedItem();
        if (selectedFolder != null && selectedFolder.getParent() == rootItem) {
            selectedFolder.getChildren().add(new TreeItem<>("New Chat"));
        }
    }

    @FXML
    private void createNewFolder() {
        TreeItem<String> newFolder = new TreeItem<>("New Subject");
        newFolder.setExpanded(true);
        rootItem.getChildren().add(newFolder);
    }

    @FXML
    private void saveNotes() {

    }

    @FXML
    private void deleteSelectedItem() {
        TreeItem<String> selectedItem = chatHistory.getSelectionModel().getSelectedItem();
        if (selectedItem != null && selectedItem.getParent() != null) {
            selectedItem.getParent().getChildren().remove(selectedItem);
        }
    }

    @FXML
    private void backbutton()  throws IOException  {
        Stage stage = (Stage) buttonBox.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/thenotelobster/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        String stylesheet = HelloApplication.class.getResource("stylesheet.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);
        stage.setScene(scene);

    }

    private class TextFieldTreeCellImpl extends TreeCell<String> {
        private TextField textField;

        public TextFieldTreeCellImpl() {
            setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !isEmpty()) {
                    startEdit();
                }
            });
        }



    }
}
