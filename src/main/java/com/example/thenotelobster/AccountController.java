package com.example.thenotelobster;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AccountController {

    @FXML private TextField CurrentNameText;

    @FXML private TextField CurrentEmailText;

    @FXML private Button NotesButton;

    @FXML private Button SignOutButton;

    @FXML private Button MainButton;

    @FXML private Button SaveButton;

    @FXML private Label ChangeLabel;


    @FXML private TextField NewNameText;

    @FXML private PasswordField NewPasswordText;

    @FXML private PasswordField NewConfirmPasswordText;

    @FXML
    protected void onNotesClick() throws IOException {
        Stage stage = (Stage) NotesButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("NotePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        String stylesheet = HelloApplication.class.getResource("stylesheet.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);
        stage.setScene(scene);
    }

    @FXML
    protected void onSignOut() throws IOException {
        Stage stage = (Stage) SignOutButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        String stylesheet = HelloApplication.class.getResource("stylesheet.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);
        stage.setScene(scene);
        // Implement sign out
    }

    @FXML
    protected void onMainClick() throws IOException {
        Stage stage = (Stage) MainButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        String stylesheet = HelloApplication.class.getResource("stylesheet.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);
        stage.setScene(scene);
    }

    @FXML
    protected void initialize() {
        UserAccount currentAccount = UserAccount.getInstance();
        CurrentEmailText.setText(currentAccount.getEmail());
        CurrentNameText.setText(currentAccount.getUserName());
        NewNameText.setText(currentAccount.getUserName());
        NewPasswordText.setText(currentAccount.getPassword());
        NewConfirmPasswordText.setText(currentAccount.getPassword());
    }

    @FXML
    protected void onSaveButtonClick() throws IOException {
        UserAccountDAO userAccountDAO = new UserAccountDAO();
        UserAccount currentAccount = UserAccount.getInstance();

        currentAccount.setUserName(NewNameText.getText());
        currentAccount.setPassword(NewPasswordText.getText());
        System.out.println(NewPasswordText.getText());
        System.out.println(currentAccount.getPassword());
        userAccountDAO.update(currentAccount);

        ChangeLabel.setText("Changes were Saved");
    }
}
