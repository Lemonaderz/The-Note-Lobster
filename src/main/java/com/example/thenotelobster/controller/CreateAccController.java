package com.example.thenotelobster.controller;

import com.example.thenotelobster.HelloApplication;
import com.example.thenotelobster.model.UserClasses.UserAccount;
import com.example.thenotelobster.model.UserClasses.UserAccountDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * the JavaFX controller for creating a new account
 */
public class CreateAccController extends NavigationUI {
    /** Continue Button */
    @FXML
    private Button continueButton;
    /** Cancel Button */
    @FXML
    private Button CancelButton;
    /** text field for username */
    @FXML
    private TextField nameText;
    /** text field for email */
    @FXML
    private TextField emailText;
    /** password (hidden) field for password */
    @FXML
    private PasswordField passwordText;

    /**
     * When the continue button is clicked, create a new user and insert into database, before moving on to main view.
     * @throws IOException If this process is interrupted throw an IOException
     */
    @FXML
    protected void onContinueButtonClick() throws IOException {
        UserAccountDAO userAccountDAO = new UserAccountDAO();
        if (emailText.getText() != "") {
            UserAccount currentUser = UserAccount.getInstance();
            currentUser.setUser(emailText.getText(), nameText.getText(), passwordText.getText());
            userAccountDAO.insert(currentUser);

            Stage stage = (Stage) continueButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
            scene.getStylesheets().add(checkCurrentMode());
            stage.setScene(scene);
        }
    }

    /**
     * When CancelButton is clicked, return back to login-view
     * @throws IOException
     */
    @FXML
    protected void onCancelClick() throws IOException {
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        scene.getStylesheets().add(checkCurrentMode());
        stage.setScene(scene);

    }
}
