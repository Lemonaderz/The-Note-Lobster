package com.example.thenotelobster.NotePage;

import com.example.thenotelobster.HelloApplication;
import com.example.thenotelobster.NavigationUI;
import com.example.thenotelobster.UserAccount;
import com.sun.source.tree.Tree;
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
    @FXML private TextField searchField;
    @FXML private Label titleLabel;
    private TreeItem<String> rootItem;
    private final NotePageDAO notePageDAO = new NotePageDAO();
    private final String userEmail = UserAccount.getInstance().getEmail();


    @FXML
    public void initialize() {
        rootItem= new TreeItem<>("Subjects");
        rootItem.setExpanded(true);
        chatHistory.setRoot(rootItem);
        chatHistory.setShowRoot(false);
        chatHistory.setCellFactory(tv -> new editcell());
        searchField.textProperty().addListener((observableValue, oldVal, newVal) -> findMatchingNote(newVal));

        chatHistory.getSelectionModel().selectedItemProperty().addListener((observableValue, oldVal, newVal) -> {
            if (newVal !=null){
                TreeItem<String> selected = newVal;
                TreeItem<String> parent = selected.getParent();
                if(parent !=null && parent != rootItem){
                    titleLabel.setText(parent.getValue() +" - " + selected.getValue());
                    if (selected.getGraphic() instanceof TextArea ta){
                        notesContent.setText(ta.getText());
                    }
                    else
                    {
                        notesContent.clear();

                    }
                } else if (rootItem.getChildren().contains(selected)){
                    titleLabel.setText(selected.getValue());
                    notesContent.clear();

                }else {
                    titleLabel.setText("");
                    notesContent.clear();

                }
            }
        });

        leftVBox.setPadding(new Insets(10));
        buttonBox.setPadding(new Insets(10));
        centerVBox.setPadding(new Insets(10));

    }



    private void findMatchingNote(String keyword){
        if(keyword == null || keyword.isEmpty()){
            notesContent.clear();
            titleLabel.setText("");
            return;
        }
        for (TreeItem<String> folder: rootItem.getChildren()){
            for (TreeItem<String> chat : folder.getChildren()){
                String title = chat.getValue().toLowerCase();
                String content = "";
                if(chat.getGraphic()instanceof TextArea textArea) {
                    content =textArea.getText().toLowerCase();

                }
                if (title.contains(keyword) || content.contains(keyword)){
                    chatHistory.getSelectionModel().select(chat);
                    chatHistory.scrollTo(chatHistory.getRow(chat));
                    return;
                }

            }
        }

    }

    @FXML
    private void createNewChat() {
        TreeItem<String> selected = chatHistory.getSelectionModel().getSelectedItem();
        if(selected !=null && rootItem.getChildren().contains(selected)){
            TextInputDialog dialog = new TextInputDialog("New Chat");
            dialog.setTitle("Create new Chat");
            dialog.setHeaderText("Enter a name for the new chat:");
            dialog.showAndWait().ifPresent(chatName ->{
                int folderId = notePageDAO.getFolderId(selected.getValue());
                if(folderId != -1){
                    notePageDAO.insertNote(chatName,folderId,"", selected.getValue(), userEmail);
                    TreeItem<String> chat = new TreeItem<>(chatName);

                    //This will attach empty TextArea for new chats
                    TextArea noteArea = new TextArea();
                    noteArea.setWrapText((true));
                    chat.setGraphic(noteArea);
                    selected.getChildren().add(chat);
                    selected.setExpanded(true);

                    //This will auto select and edi the brand new chat
                    chatHistory.getSelectionModel().select(chat);
                    chatHistory.edit(chat);

                }
            });
        }
    }

    @FXML
    private void createNewFolder() {
        TextInputDialog dialog = new TextInputDialog("New Subject");
        dialog.setTitle("Create new Folder");
        dialog.setHeaderText("Enter a name for the new subject");
        dialog.showAndWait().ifPresent(name -> {
            int folderId = notePageDAO.insertFolder(name);
            if (folderId !=-1){
                TreeItem<String> folder = new TreeItem<>(name);
                folder.setExpanded(true);
                rootItem.getChildren().add(folder);

                //autoselect folder if user wants to rename
                chatHistory.getSelectionModel().select(folder);
                chatHistory.edit(folder);
            }
        });
    }

    @FXML
    private void saveNotes(){
        TreeItem<String> selected = chatHistory.getSelectionModel().getSelectedItem();
        if(selected == null){
            return;
        }

        TreeItem<String> parent = selected.getParent();

        if (parent !=null && parent !=rootItem) {
            String chatName = selected.getValue();
            String folderName = parent.getValue();
            String content = notesContent.getText();

            int folderId = notePageDAO.getFolderId(folderName);
            if (folderId == -1) {
                System.out.println("Folder ID not found for saving note.");
                return;
            }


            int noteId = notePageDAO.getNoteId(chatName, folderId, userEmail);
            if (noteId != -1) {
                notePageDAO.updateNoteText(noteId, content); //if note already exists update
            } else {
                notePageDAO.insertNote(chatName, folderId, content, folderName, userEmail); // if note does not exist insert mew
            }

            TextArea updatedTextArea = new TextArea(content);
            updatedTextArea.setWrapText(true);
            selected.setGraphic(updatedTextArea);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Save Successfull");
            alert.setHeaderText(null);
            alert.setContentText("your note has been saved sucessfully");
            alert.showAndWait();
        }
        else if (parent == rootItem){
            System.out.println("The Selected item is a folder, not a note.");

        }


    }

    @FXML
    private void deleteSelectedItem(){
        TreeItem<String> selected = chatHistory.getSelectionModel().getSelectedItem();
        if (selected != null && selected.getParent() != null) {
            selected.getParent().getChildren().remove(selected);
        }
    }

    @FXML
    private void backbutton()  throws IOException  {

        Stage stage = (Stage) buttonBox.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        scene.getStylesheets().add(checkCurrentMode());
        stage.setScene(scene);

    }


    private class editcell extends TreeCell<String>{
        private final TextField textField = new TextField();

        public editcell(){
            setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() ==2 && !isEmpty()) startEdit();

            });
            textField.setOnAction(e -> commitOrCancel());
            textField.focusedProperty().addListener(((observableValue, oldVal, newVal) -> {
                if (!newVal) commitOrCancel();
            }
            ));
        }

        private void commitOrCancel(){
            String input = textField.getText().trim();
            if (!input.isEmpty()) {
                getTreeItem().setValue(input);
                commitEdit(input);
            } else {
                cancelEdit();
            }
        }

        @Override
        public void startEdit(){
            super.startEdit();
            textField.setText(getItem());
            setText(null);
            setGraphic(textField);
            textField.selectAll();
            textField.requestFocus();

        }
        @Override
        public void cancelEdit(){
            super.cancelEdit();
            setText(getItem());
            setGraphic(null);

        }

        @Override
        public void commitEdit(String newName){
            super.commitEdit(newName);
            setText(newName);
            setText(newName);
            setGraphic(null);

        }
        @Override
        protected void updateItem(String item, boolean empty){
            super.updateItem(item,empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            }
            else if (isEditing()) {
                textField.setText(getItem());
                setText(null);
                setGraphic(textField);
            }
            else{
                setText(getItem());
                setGraphic(null);

            }
        }

    }


    @FXML
    private void expandNotes(){

    }
}
