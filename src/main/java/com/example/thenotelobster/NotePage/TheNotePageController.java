package com.example.thenotelobster.NotePage;
import com.example.thenotelobster.HelloApplication;
import com.example.thenotelobster.NavigationUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import java.io.IOException;


public class TheNotePageController extends NavigationUI {
    @FXML private TreeView<String> chatHistory;
    @FXML private VBox leftVBox;
    @FXML private HBox buttonBox;
    @FXML private VBox centerVBox;
    @FXML private TextArea notesContent;

    private TreeItem<String> rootItem;

    @FXML
    public void initialize() {
        // Initialize chat history tree
        rootItem = new TreeItem<>("Subjects");
        rootItem.setExpanded(true);
        chatHistory.setRoot(rootItem);
        chatHistory.setCellFactory(tv -> new editcell());

        leftVBox.setPadding(new Insets(10));
        buttonBox.setPadding(new Insets(10));
        centerVBox.setPadding(new Insets(10));
    }

    @FXML
    private void createNewChat() {
        TreeItem<String> selected = chatHistory.getSelectionModel().getSelectedItem();

        if (selected != null && rootItem.getChildren().contains(selected)) {
            TreeItem<String> chat = new TreeItem<>("New Chat");
            selected.getChildren().add(chat);
            selected.setExpanded(true);
            chatHistory.getSelectionModel().select(chat);
            chatHistory.edit(chat);
        }
    }

    @FXML
    private void createNewFolder() {
        TreeItem<String> folder = new TreeItem<>("New Subject");
        folder.setExpanded(true);
        rootItem.getChildren().add(folder);
        chatHistory.getSelectionModel().select(folder);
        chatHistory.edit(folder);
    }

    @FXML
    private void saveNotes() {
        TreeItem<String> selected = chatHistory.getSelectionModel().getSelectedItem();

        if (selected != null && selected.getParent() != null && selected.getParent() != rootItem) {
            TextArea hiddenNote = new TextArea(notesContent.getText());
            hiddenNote.setWrapText(true);
            hiddenNote.setPrefHeight(0);
            selected.setGraphic(hiddenNote);
        }
    }

    @FXML
    private void deleteSelectedItem() {
        TreeItem<String> selected = chatHistory.getSelectionModel().getSelectedItem();
        if (selected != null && selected.getParent() != null) {
            selected.getParent().getChildren().remove(selected);
        }
    }

    @FXML
    private void backbutton()  throws IOException  {
        Stage stage = (Stage) buttonBox.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/thenotelobster/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        scene.getStylesheets().add(checkCurrentMode());
        stage.setScene(scene);

    }

    private class editcell extends TreeCell<String> {
        private TextField textField;

        public editcell() {
            setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !isEmpty()) {
                    startEdit();
                }
            });
        }
    }

    @FXML
    private void expandNotes() {
    }

}
