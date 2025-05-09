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
 * Controller class for handling user login and account creation navigation.
 * Inherits navigation functionality from {@link NavigationUI}.
 */
public class LoginController extends NavigationUI {

    /** Button that navigates to the account creation view. */
    @FXML
    private Button createAccButton;

    /** Button that initiates the login process. */
    @FXML
    private Button loginButton;

    /** Text field for inputting user's email. */
    @FXML
    private TextField emailText;

    /** Password field for inputting user's password. */
    @FXML
    private PasswordField passwordText;

    /** Label to display login error messages. */
    @FXML
    private Label errorLabel;

    /**
     * Handles the event when the "Create Account" button is clicked.
     * Loads and displays the account creation screen.
     *
     * @throws IOException if the FXML file for the create account view fails to load
     */
    @FXML
    protected void onCreateAccButtonClick() throws IOException {
        Stage stage = (Stage) createAccButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("create-acc-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        scene.getStylesheets().add(checkCurrentMode());
        stage.setScene(scene);
    }

    /**
     * Handles the event when the "Login" button is clicked.
     * Verifies the user's credentials, and if valid, loads the main application view.
     * If invalid, displays an error message to the user.
     *
     * @throws IOException if the main view fails to load
     */
    @FXML
    protected void onLoginButtonClick() throws IOException {
        UserAccountDAO userAccountDAO = new UserAccountDAO();
        UserAccount currentAccount = UserAccount.getInstance();

        userAccountDAO.delete("");
        try {
            userAccountDAO.getByEmail(emailText.getText());

            if (currentAccount.getPassword().equals(passwordText.getText()))
            {
                currentAccount.setEmail(emailText.getText());
                Stage stage = (Stage) loginButton.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
                scene.getStylesheets().add(checkCurrentMode());
                stage.setScene(scene);
            } else {
                errorLabel.setText("Incorrect username or Password! Please try again.");
            }
        }
        catch(Exception e){

            errorLabel.setText("Invalid Username! Please try again");
        }

    }
}
