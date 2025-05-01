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

public class CreateAccController extends NavigationUI {

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
            UserAccount currentUser = UserAccount.getInstance();
            currentUser.setUser(emailText.getText(), nameText.getText(), passwordText.getText());
            userAccountDAO.insert(currentUser);


            Stage stage = (Stage) contuineButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
            scene.getStylesheets().add(checkCurrentMode());
            stage.setScene(scene);
        }
    }
}
