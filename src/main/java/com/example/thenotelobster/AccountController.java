package com.example.thenotelobster;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AccountController extends NavigationUI {

    @FXML private TextField CurrentNameText;
    @FXML private TextField CurrentEmailText;
    @FXML private Button SaveButton;
    @FXML private Label ChangeLabel;
    @FXML private TextField NewNameText;
    @FXML private PasswordField NewPasswordText;
    @FXML private PasswordField NewConfirmPasswordText;
    @FXML private Button QuizButton;

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
