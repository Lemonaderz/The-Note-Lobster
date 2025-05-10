package com.example.thenotelobster.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

/**
 *
 */
public class SummaryAlert {
    ButtonType Continue = new ButtonType("Continue", ButtonBar.ButtonData.OK_DONE);
    ButtonType Cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);


    public Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
            "Are you sure you want to leave without saving?",
            Continue,
            Cancel);

    public SummaryAlert() {
        alert.setTitle("Unsaved Changes");
        alert.setHeaderText("Warning - Note Summary is not saved");
    }
}
