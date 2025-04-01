package com.example.thenotelobster;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.IOException;

public class SummaryController {

    @FXML private TextArea SummaryText;

    @FXML private Button SaveButton;

    @FXML private Button BackButton;

    @FXML protected void onSaveClick() {
        //Implement saving summary notes
    }

    @FXML protected void onBackClick() {
        //Implement going back to main page
    }

}
