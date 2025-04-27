package com.example.thenotelobster;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private CheckBox agreeCheckBox;
    @FXML
    private Button nextButton;

    @FXML
    private Button CancelButton;

    @FXML
    private TextArea termsAndConditions;


    @FXML
    public void initialize() {
        termsAndConditions.setText("""
Terms and Conditions
Last Updated: 28/03/2025

Welcome to NoteLobster! These Terms and Conditions ("Terms") govern your use of the NoteLobster mobile and web application ("App"). By using NoteLobster, you agree to be bound by these terms. If you do not agree, please do not use the app.

1. Acceptance of Terms

By creating an account and using NoteLobster, you confirm that you are at least 13 years old and have the legal capacity to enter into this agreement.

2. Account Registration

To use NoteLobster, you must create an account using a username and password. You are responsible for maintaining the confidentiality of your account credentials and for all activities under your account. If you suspect unauthorised access, notify us immediately.

3. Use of the App

NoteLobster allows users to input brief notes, which the app will expand using AI.

The app does not support in-app purchases; all features are provided free of charge.

You agree not to use the app for illegal, harmful, or abusive purposes, including but not limited to:

Uploading harmful, offensive, or infringing content.

Attempting to disrupt or hack the app.

Violating any applicable laws.

4. Privacy and Data Usage

Your privacy is important to us. We collect and process personal information, including your username, password, and inputted notes, in accordance with our Privacy Policy. By using NoteLobster, you consent to this data collection and processing.

5. Intellectual Property

NoteLobster and its AI-powered content generation features are owned and operated by TheBlueLobsters.

You retain ownership of your original notes, but you grant NoteLobster a non-exclusive licence to process them for AI expansion purposes.

6. Limitation of Liability

NoteLobster is provided "as is" without warranties of any kind.

We do not guarantee the accuracy, reliability, or usefulness of AI-generated content.

We are not responsible for any data loss, unauthorised access, or damages resulting from your use of the app.

7. Termination

We reserve the right to suspend or terminate your access to NoteLobster at any time if you violate these terms. You may also delete your account at any time.

8. Changes to Terms

We may update these terms from time to time. Continued use of the app after updates constitutes acceptance of the new terms.

9. Contact Us

If you have any questions about these terms, please contact us at "hudson.aberle@connect.qut.edu.au."

By using NoteLobster, you acknowledge that you have read, understood, and agreed to these Terms and Conditions.""");
    }



    @FXML
    protected void onAgreeCheckBoxClick() {
        boolean accepted = agreeCheckBox.isSelected();
        nextButton.setDisable(!accepted);
    }
    @FXML
    protected void onNextButtonClick() throws IOException {
        Stage stage = (Stage) nextButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        String stylesheet = HelloApplication.class.getResource("style/light_mode.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);
        stage.setScene(scene);
    }

    @FXML
    protected void onCancelButtonClick() throws IOException {
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        String stylesheet = HelloApplication.class.getResource("style/light_mode.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);
        stage.setScene(scene);
    }
}