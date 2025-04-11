package com.example.thenotelobster;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class LoginController {

    @FXML
    private Button createAccButton;

    @FXML
    private Button loginButton;

    @FXML
    private TextField emailText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Label errorLabel;

    @FXML
    protected void onCreateAccButtonClick() throws IOException {
        Stage stage = (Stage) createAccButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("create-acc-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        String stylesheet = HelloApplication.class.getResource("stylesheet.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);
        stage.setScene(scene);
    }

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
                String stylesheet = HelloApplication.class.getResource("stylesheet.css").toExternalForm();
                scene.getStylesheets().add(stylesheet);
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
