package com.example.thenotelobster;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;

public class QuizController extends NavigationUI {

    @FXML private VBox quizBox;
    @FXML private VBox scrollBox;

    public void initialize() {
        // Example quiz creation inside controller
        Quiz quiz = new Quiz("Sample Quiz", Arrays.asList(
                new Question("What is 2 + 2?", Arrays.asList("3", "4", "5", "6")),
                new Question("What color is the sky?", Arrays.asList("Blue", "Green", "Red", "Yellow"))
        ));

        loadQuiz(quiz);
    }

    private void loadQuiz(Quiz quiz) {
        // Clear old content
        quizBox.getChildren().clear();

        for (int i = 0; i < quiz.getQuestions().size(); i++) {
            Question question = quiz.getQuestions().get(i);

            VBox questionBox = new VBox(5);
            questionBox.setSpacing(10);
            questionBox.setStyle("-fx-border-color: d8d8d8; -fx-padding: 10;");

            Label questionLabel = new Label("Question " + (i + 1));
            Label questionText = new Label(question.getQuestionText());

            questionBox.getChildren().add(0, questionLabel); // Add at top
            questionBox.getChildren().add(1, questionText);  // Add under label

            ToggleGroup group = new ToggleGroup();
            for (String option : question.getOptions()) {
                RadioButton radioButton = new RadioButton(option);
                radioButton.setToggleGroup(group);

                radioButton.setOnAction(e -> {
                    System.out.println("Selected: " + radioButton.getText());
                });

                questionBox.getChildren().add(radioButton);
            }

            quizBox.getChildren().add(questionBox);
        }
        System.out.println("Loaded " + quiz.getQuestions().size() + " questions into quizBox");
    }

}
