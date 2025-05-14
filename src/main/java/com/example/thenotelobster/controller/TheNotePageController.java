package com.example.thenotelobster.controller;

import com.example.thenotelobster.model.NoteClasses.NotePageDAO;
import com.example.thenotelobster.model.SummaryResponse;
import com.example.thenotelobster.model.UserClasses.UserAccount;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;

/**
 * TheNotePageController controller manages the multiple functions such as the creating, deleting, and saving of notes.
 * It is also responsible in prompting the AI based notes expansion
 */
public class TheNotePageController extends NavigationUI {
    @FXML private TreeView<String> chatHistory;
    @FXML private TextArea notesContent;
    @FXML private TextField searchField;
    @FXML private Label titleLabel;
    @FXML private Button ExpandNotesButton;
    private TreeItem<String> rootItem;
    private final NotePageDAO notePageDAO = new NotePageDAO();
    private final String userEmail = UserAccount.getInstance().getEmail();

    /**
     * The initialize method activities the user-interface components by setting up the listeners and
     * loading up data from the database.
     */
    @FXML
    public void initialize() {
        rootItem= new TreeItem<>("Subjects");
        rootItem.setExpanded(true);
        chatHistory.setRoot(rootItem);
        chatHistory.setShowRoot(false);

        //enable renaming of folders and chats on double-clicking.
        chatHistory.setCellFactory(tv -> new editcell());

        // Listen for search queries in the search bar
        searchField.textProperty().addListener((observableValue, oldVal, newVal) -> findMatchingNote(newVal));

        // load the chat history with folders and notes
        loadFoldersAndNotes();

        // Update the folder title and content.
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

        // This automatically saves the code everytime text is inserted into the textbox
        // instead of having to click the save button everytime
        notesContent.textProperty().addListener((observableValue, oldText, newText) -> {
            saveNotes();
        });

    }

    /**
     * The findMatchingNote is used to filter notes in the chat history by the selected keyword while updating the
     * tree view. The notes filtered is also case-insensitive.
     * @param keyword the term used to search for the note's title and content.
     */
    @FXML
    private void findMatchingNote(String keyword){
        // clear current items.
        rootItem.getChildren().clear();

        // Reload all chats if no keyword is in search bar.
        if(keyword == null || keyword.isEmpty()){
            loadFoldersAndNotes();
            return;
        }
        //make keyword lowercase for case-insensitive search
        keyword=keyword.toLowerCase();

        for (var folder : notePageDAO.getAllFolders(userEmail)){
            TreeItem<String> folderItem = new TreeItem<>(folder.getName());
            folderItem.setExpanded(true);

            for(var note: notePageDAO.getNotesByFolder(folder.getFolderId())){
                String title = note.getName().toLowerCase();
                String content = note.getText().toLowerCase();

                if (title.contains(keyword) || content.contains(keyword)) {
                    TreeItem<String> noteItem = new TreeItem<>(note.getName());
                    TextArea noteArea = new TextArea(note.getText());
                    noteArea.setWrapText(true);
                    noteItem.setGraphic(noteArea);
                    folderItem.getChildren().add(noteItem);
                }
            }
            // Only show folders which match the keyword
            if (!folderItem.getChildren().isEmpty()) {
                rootItem.getChildren().add(folderItem);
            }
        }

    }


    /**
     * The createNewChat method prompts for a chat name and creates a new chat under the chosen folder.
     * It will also save the new chat into the SQL database.
     */
    @FXML
    private void createNewChat() {
        TreeItem<String> selected = chatHistory.getSelectionModel().getSelectedItem();
        if(selected !=null && rootItem.getChildren().contains(selected)){
            TextInputDialog dialog = new TextInputDialog("New Chat");
            dialog.setTitle("Create new Chat");
            dialog.setHeaderText("Enter a name for the new chat:");
            dialog.showAndWait().ifPresent(chatName ->{
                int folderId = notePageDAO.getFolderId(selected.getValue(), userEmail);
                if(folderId != -1){
                    // Database insert
                    notePageDAO.insertNote(chatName,folderId,"", selected.getValue());
                    TreeItem<String> chat = new TreeItem<>(chatName);

                    //This will attach empty TextArea for new chats
                    TextArea noteArea = new TextArea();
                    noteArea.setWrapText((true));
                    chat.setGraphic(noteArea);
                    selected.getChildren().add(chat);
                    selected.setExpanded(true);

                    //This will auto select and edit the brand new chat
                    chatHistory.getSelectionModel().select(chat);
                    chatHistory.edit(chat);

                }
            });
        }
    }

    /**
     * createNewFolder will prompt a folder name and creates a new folder in the chat history while
     * saving the folder name into the SQL database.
     */
    @FXML
    private void createNewFolder() {
        TextInputDialog dialog = new TextInputDialog("New Subject");
        dialog.setTitle("Create new Folder");
        dialog.setHeaderText("Enter a name for the new subject");
        dialog.showAndWait().ifPresent(name -> {
            // Database insert
            int folderId = notePageDAO.insertFolder(name, userEmail);
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

    /**
     * Saves the currently edited notepage to the database.
     */
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

            int folderId = notePageDAO.getFolderId(folderName, userEmail);
            if (folderId == -1) {
                System.out.println("Folder ID not found for saving note.");
                return;
            }

            int noteId = notePageDAO.getNoteId(chatName, folderId);
            if (noteId != -1) {
                notePageDAO.updateNoteText(noteId, content); //if note already exists update
            } else {
                notePageDAO.insertNote(chatName, folderId, content, folderName); // if note does not exist insert mew
            }

            TextArea updatedTextArea = new TextArea(content);
            updatedTextArea.setWrapText(true);
            selected.setGraphic(updatedTextArea);


        }

    }

    /**
     * deleteSelectedItem deletes the selected folder or chat with user confirmation.
     * It will also delete the selected folder or chat to the database
     */
    @FXML
    private void deleteSelectedItem(){
        TreeItem<String> selected = chatHistory.getSelectionModel().getSelectedItem();
        if (selected == null){
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you want to delete?");
        alert.setContentText("This acction will not be undone");

        alert.showAndWait().ifPresent(response ->{
            if (response == ButtonType.OK){
                TreeItem<String> parent= selected.getParent();
                if (parent == null || parent == rootItem){
                    //it's a folder
                    int folderId = notePageDAO.getFolderId(selected.getValue(), userEmail);
                    if(folderId !=-1){
                        notePageDAO.deleteFolder(folderId);
                    }
                } else {
                    // it's a chat
                    int folderId = notePageDAO.getFolderId(parent.getValue(), userEmail);
                    if(folderId !=-1){
                        int noteId = notePageDAO.getNoteId(selected.getValue(),folderId);
                        if (noteId !=-1){
                            notePageDAO.deleteNote(noteId);
                        }
                    }
                }
                // Remove chat from the user-interface
                if(parent !=null) {
                    parent.getChildren().remove(selected);
                } else {
                    rootItem.getChildren().remove(selected);
                }
            }
        });
    }


    /**
     * This allows renaming folders and chats by double-clicking.
     */
    private class editcell extends TreeCell<String>{
        public editcell(){
            // double-click to rename
            setOnMouseClicked(mouseEvent ->{
                if (mouseEvent.getClickCount() == 2 && !isEmpty()){
                    showEditPopup();
                }
            });
        }

        /**
         * When a user double-clicks on a selected folder or chat, it will show a dialog
         * to rename the folder or chat and will update the database afterward.
         */
        private void showEditPopup(){
            TreeItem<String> item = getTreeItem();
            if (item==null) return;
            TextInputDialog dialog = new TextInputDialog(item.getValue());
            dialog.setTitle("Rename");
            dialog.setHeaderText("Enter a new name:");
            dialog.showAndWait().ifPresent(newName ->{
                if (!newName.trim().isEmpty()){
                    String oldName= item.getValue();
                    item.setValue(newName.trim());

                    if (item.getParent() == rootItem) {
                        notePageDAO.renameFolder(oldName, newName.trim(), userEmail);
                    }
                    else{
                        int folderId = notePageDAO.getFolderId((item.getParent().getValue()), userEmail);
                        int noteId = notePageDAO.getNoteId(oldName,folderId);
                        if (noteId !=-1) {
                            notePageDAO.renameNote(noteId,newName.trim());

                        }
                    }
                }
            });
        }

        @Override
        protected void updateItem(String item, boolean empty){
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            }
            else{
                setText(getItem());
                setGraphic(null);
            }
        }

    }

    /**
     * Loads all folders from the database with their associated notes into the chat history.
     */
    private void loadFoldersAndNotes() {
            for (var folder : notePageDAO.getAllFolders(userEmail)) {
                TreeItem<String> folderItem = new TreeItem<>(folder.getName());
                folderItem.setExpanded(true);

                for(var note: notePageDAO.getNotesByFolder(folder.getFolderId())){
                    TreeItem<String> noteItem = new TreeItem<>(note.getName());
                    TextArea noteArea = new TextArea(note.getText());
                    noteArea.setWrapText(true);
                    noteItem.setGraphic(noteArea);
                    folderItem.getChildren().add(noteItem);
                }
                rootItem.getChildren().add(folderItem);
            }
    }

    /**
     * The expandNotes expands the current notes using the Ollama AI and navigates the user to teh summary
     * page automatically.
     * @throws IOException
     */
    @FXML
    private void expandNotes() throws IOException {
        AIManager aiManager = AIManager.getInstance();
        aiManager.singleSummary = new SummaryResponse(notesContent.getText(), chatHistory.getSelectionModel().getSelectedItem().getParent().getValue(), "300", 5, chatHistory.getSelectionModel().getSelectedItem().getValue() + " - Expanded");
        aiManager.setResummaryMode();
        goToSummaryPage(ExpandNotesButton);

    }
}
