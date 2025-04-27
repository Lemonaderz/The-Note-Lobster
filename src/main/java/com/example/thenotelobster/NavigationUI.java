package com.example.thenotelobster;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

abstract class NavigationUI {

    @FXML
    private BorderPane SceneTheme;
    @FXML
    private VBox header;
    @FXML
    private VBox secondary;
    @FXML
    private Button modeButton;
    @FXML
    private Button NotesButton;

    String lightmode = HelloApplication.class.getResource("style/light_mode.css").toExternalForm();
    String darkmode = HelloApplication.class.getResource("style/dark_mode.css").toExternalForm();

    // Double value used to determine which mode the application is in, e.g. 0 = light mode and 1 = dark mode.
    private double mode = 0;
    private String CurrentTheme;

    @FXML
    protected void onNotesClick() throws IOException {
        Stage stage = (Stage) NotesButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("NotePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        scene.getStylesheets().add(checkCurrentMode());
        stage.setScene(scene);
    }

    @FXML
    protected void onChangeModeClick() {
        modeButton.setOnMouseClicked(mouseEvent -> {
            // Mode is equal to 0, therefore the application is currently in light mode.
            if (mode == 0) {
                SceneTheme.getStylesheets().add(darkmode);
                SceneTheme.getStylesheets().remove(lightmode);
                mode = 1;
            } else if (mode == 1) { // Mode is equal to 1, therefore the application is currently in dark mode.
                SceneTheme.getStylesheets().add(lightmode);
                SceneTheme.getStylesheets().remove(darkmode);
                mode = 0;
            }
        });
    }

    @FXML
    protected void onSignOut() {
        // Implement sign out
    }

    // Method used to check which theme the application is currently in, e.g Dark Mode or Light Mode.
    protected String checkCurrentMode() {
        if (mode == 0) { // IF LIGHT MODE:
            String CurrentTheme = HelloApplication.class.getResource("style/light_mode.css").toExternalForm();
            mode = 0;
            return CurrentTheme;
        } else if (mode == 1) { // IF DARK MODE:
            String CurrentTheme = HelloApplication.class.getResource("style/dark_mode.css").toExternalForm();
            mode = 1;
            return CurrentTheme;
        }
        return CurrentTheme;
    }
}
