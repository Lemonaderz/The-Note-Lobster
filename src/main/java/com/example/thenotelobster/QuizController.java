package com.example.thenotelobster;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class QuizController extends NavigationUI {

    @FXML private VBox quizBox;

    @FXML
    public void initialize() {
        // Go through all direct children of quizBox
        for (Node node : quizBox.getChildren()) {
            if (node instanceof VBox questionVBox) {
                setupToggleGroup(questionVBox);
            }
        }
    }

    private void setupToggleGroup(VBox questionBox) {
        ToggleGroup group = new ToggleGroup();
        for (Node node : questionBox.getChildren()) {
            if (node instanceof RadioButton radioButton) {
                radioButton.setToggleGroup(group);
            }
        }
    }

}
