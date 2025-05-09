package com.example.thenotelobster.controller;

import com.example.thenotelobster.model.UserClasses.UserAccount;
import com.example.thenotelobster.model.UserClasses.UserAccountDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Controller class responsible for managing the user's account view.
 * Inherits navigation functionality from {@link NavigationUI}.
 */
public class AccountController extends NavigationUI {

    /** Text field displaying the current username. */
    @FXML private TextField CurrentNameText;

    /** Text field displaying the current email address. */
    @FXML private TextField CurrentEmailText;

    /** Button to save any changes made to the account. */
    @FXML private Button SaveButton;

    /** Label to indicate status of changes. */
    @FXML private Label ChangeLabel;

    /** Text field to input a new username. */
    @FXML private TextField NewNameText;

    /** Password field to input a new password. */
    @FXML private PasswordField NewPasswordText;

    /** Password field to confirm the new password. */
    @FXML private PasswordField NewConfirmPasswordText;

    /**
     * Initializes the controller after the FXML elements have been loaded.
     * Populates the form fields with the current user's data.
     */
    @FXML
    protected void initialize() {
        UserAccount currentAccount = UserAccount.getInstance();
        CurrentEmailText.setText(currentAccount.getEmail());
        CurrentNameText.setText(currentAccount.getUserName());
        NewNameText.setText(currentAccount.getUserName());
        NewPasswordText.setText(currentAccount.getPassword());
        NewConfirmPasswordText.setText(currentAccount.getPassword());
    }

    /**
     * Called when the save button is clicked.
     * Updates the username and password of the current user and reflects changes in the database.
     *
     * @throws IOException if an I/O error occurs during the save operation
     */
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
