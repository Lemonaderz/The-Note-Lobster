package com.example.thenotelobster.controller;

import com.example.thenotelobster.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * An abstract class which handles the Navigation Bar
 * functions for the Controller Classes which extend it
 */
public abstract class NavigationUI {
    /**
     * BorderPane which is styled by light mode & dark mode
     */
    @FXML
    private BorderPane SceneTheme;
    /**
     * fx:id given to the VBox containing the Navigation Bar Header
     */
    @FXML
    private VBox header;
    /**
     * fx:id given to VBox for styling purposes to separate different sections of the application
     */
    @FXML
    private VBox secondary;
    /**
     * Button attributed to the mode button on the Navigation Bar
     */
    @FXML
    private Button modeButton;
    /**
     * Button attributed to the notes button on the Navigation Bar
     */
    @FXML
    private Button NotesButton;
    /**
     * Button attributed to the sign-out button on the Navigation Bar
     */
    @FXML
    private Button SignOutButton;
    /**
     * Button attributed to the quiz button on the Navigation Bar
     */
    @FXML
    private Button QuizButton;
    /**
     * Button attributed to the main button on the Navigation Bar
     */
    @FXML
    private Button MainButton;
    /**
     * Button attributed to the account button on the Navigation Bar
     */
    @FXML
    private Button AccountButton;

    @FXML
    private ImageView ModeIcon;

    /**
     * String which gets the CSS file for light mode
     */
    String lightmode = HelloApplication.class.getResource("style/light_mode.css").toExternalForm();

    /**
     * String which gets the CSS file for dark mode
     */
    String darkmode = HelloApplication.class.getResource("style/dark_mode.css").toExternalForm();
//    String darkmodeIcon = HelloApplication.class.getResource("images/darkmode_icon.png").toExternalForm();
//    String lightmodeIcon = HelloApplication.class.getResource("images/lightmode_icon.png").toExternalForm();

    /**
     * Static double value used to determine which mode the application is in, e.g. 0 = light mode and 1 = dark mode
     */
    private static double mode;

    /**
     * String which returns current application theme, used to translate theme across from different views
     */
    private String CurrentTheme;

    /**
     * Function which sends the user to the NotePage view when they click the NotesButton
      * @throws IOException Throws exception when input output processes interrupted or fail
     */
    @FXML
    protected void onNotesClick() throws IOException {
        Stage stage = (Stage) NotesButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("NotePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        scene.getStylesheets().add(checkCurrentMode());
        stage.setScene(scene);
    }

    /**
     * Function which changes the views current theme when modeButton is clicked
     */
    @FXML
    protected void onChangeModeClick() {
        modeButton.setOnMouseClicked(mouseEvent -> {
            if (mode == 0) {
                SceneTheme.getStylesheets().add(darkmode);
                SceneTheme.getStylesheets().remove(lightmode);
                mode = 1;
            } else if (mode == 1) {
                SceneTheme.getStylesheets().add(lightmode);
                SceneTheme.getStylesheets().remove(darkmode);
                mode = 0;
            }
        });
    }

    /**
     * Function which sends the user to the login view when they click the SignOutButton
     * @throws IOException Throws exception when input output processes interrupted or fail
     */
    @FXML
    protected void onSignOut() throws IOException {
        Stage stage = (Stage) SignOutButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        scene.getStylesheets().add(checkCurrentMode());
        stage.setScene(scene);
    }

    /**
     * Function which sends the user to the main view when they click the MainButton
     * @throws IOException Throws exception when input output processes interrupted or fail
     */
    @FXML
    protected void onMainClick() throws IOException {
        Stage stage = (Stage) MainButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        scene.getStylesheets().add(checkCurrentMode());
        stage.setScene(scene);
    }

    /**
     * Function which sends the user to the account view when they click the AccountButton
     * @throws IOException Throws exception when input output processes interrupted or fail
     */
    @FXML
    protected void onAccountClick() throws IOException {
        Stage stage = (Stage) AccountButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("account-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        scene.getStylesheets().add(checkCurrentMode());
        stage.setScene(scene);
    }

    /**
     * Function which sends the user to the summary view once their summary has been generated
     * @param button The button clicked to go to the summary page.
     * @throws IOException Throws exception when input output processes interrupted or fail
     */
    @FXML
    protected void goToSummaryPage(Button button) throws IOException {

        System.out.println("Summary made");
        Stage stage = (Stage) button.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("summary-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        scene.getStylesheets().add(checkCurrentMode());
        stage.setScene(scene);
    }

    /**
     * Function which sends the user to the quiz view when they click the QuizButton
     * @throws IOException Throws exception when input output processes interrupted or fail
     */
    @FXML
    protected void onQuizClick() throws IOException {
        Stage stage = (Stage) QuizButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("quiz-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        scene.getStylesheets().add(checkCurrentMode());
        stage.setScene(scene);
    }

    /**
     * Function used to check which theme the application is currently in, e.g Dark Mode or Light Mode
     * @return The Current Theme
     */
    protected String checkCurrentMode() {
        if (mode == 0) { // IF LIGHT MODE:
            return HelloApplication.class.getResource("style/light_mode.css").toExternalForm();
        } else if (mode == 1) { // IF DARK MODE:
            return HelloApplication.class.getResource("style/dark_mode.css").toExternalForm();
        }
        return CurrentTheme;
    }
}
