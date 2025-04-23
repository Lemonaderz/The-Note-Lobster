package com.example.thenotelobster;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateAccController {

    @FXML
    private Button contuineButton;

    @FXML
    private TextField nameText;

    @FXML
    private TextField emailText;

    @FXML
    private PasswordField passwordText;

    @FXML
    protected void onContuineButtonClick() throws IOException {
        UserAccountDAO userAccountDAO = new UserAccountDAO();
        if (emailText.getText() != "") {
            userAccountDAO.insert(new UserAccount(emailText.getText(), nameText.getText(), passwordText.getText()));


            Stage stage = (Stage) contuineButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
            String stylesheet = HelloApplication.class.getResource("style/stylesheet.css").toExternalForm();
            scene.getStylesheets().add(stylesheet);
            stage.setScene(scene);
        }
    }
}
