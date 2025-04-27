package com.example.thenotelobster;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

public class MainController extends NavigationUI {

    @FXML private TextArea AddNotes;
    @FXML private TextArea SourceLinks;
    @FXML private TextField NotesSubject;
    @FXML private Button SummarizeButton;
    @FXML private RadioButton ShortOption;
    @FXML private RadioButton MediumOption;
    @FXML private RadioButton LongOption;
    @FXML private ToggleGroup LengthOption;

    @FXML
    protected void onSummarizeClick() throws IOException {
        Stage stage = (Stage) SummarizeButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("summary-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        scene.getStylesheets().add(checkCurrentMode());
        stage.setScene(scene);
    }
}
