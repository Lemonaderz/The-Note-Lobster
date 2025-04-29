package com.example.thenotelobster;

import com.example.thenotelobster.QuizClasses.QuizMultipleChoiceQuestion;
import com.example.thenotelobster.QuizClasses.QuizResponse;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class QuizController extends NavigationUI {

    @FXML private VBox quizBox;
    @FXML private VBox scrollBox;
    @FXML private Button newQuizButton;
    @FXML private Label descriptionLabel;
    @FXML private Label titleLabel;

    @FXML
    protected void onCreateNewQuizClick() throws IOException {
        Stage stage = (Stage) newQuizButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("new-quiz-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        scene.getStylesheets().add(checkCurrentMode());
        stage.setScene(scene);

    }

    public void initialize() {
        QuizResponse quiz;
        try {
            // load the quiz you just saved (for now we use ID=1; later bind this to selection)
            QuizDAO dao = new QuizDAO();
            quiz = dao.loadQuiz(1);
        } catch (SQLException e) {
            e.printStackTrace();
            // fallback to AI‐sample if DB fails
            AIManager aiManager = AIManager.getInstance();
            quiz = (aiManager.currentQuiz != null)
                    ? aiManager.currentQuiz
                    : new QuizResponse("Sample Quiz", Arrays.asList(
                    new QuizMultipleChoiceQuestion("What is 2 + 2?", "4", List.of("3", "4", "5", "6")),
                    new QuizMultipleChoiceQuestion("What color is the sky?", "A", List.of("Blue", "Green", "Red", "Yellow"))
            ));
        }
        loadQuiz(quiz);
    }

    private void loadQuiz(QuizResponse quiz) {
        // Clear old content
        quizBox.getChildren().clear();
        titleLabel.setText(quiz.title);
        descriptionLabel.setText(quiz.description);
        for (int i = 0; i < quiz.multipleChoiceQuestions.size(); i++) {
            QuizMultipleChoiceQuestion question = quiz.multipleChoiceQuestions.get(i);

            VBox questionBox = new VBox(5);
            questionBox.setSpacing(10);
            questionBox.setStyle("-fx-border-color: d8d8d8; -fx-padding: 10;");

            Label questionLabel = new Label("Question " + (i + 1));
            Label questionText = new Label(question.question);

            questionBox.getChildren().add(0, questionLabel); // Add at top
            questionBox.getChildren().add(1, questionText);  // Add under label

            ToggleGroup group = new ToggleGroup();
            for (String option : question.choices) {
                RadioButton radioButton = new RadioButton(option);
                radioButton.setToggleGroup(group);
                questionBox.getChildren().add(radioButton);
            }

            quizBox.getChildren().add(questionBox);
        }
    }

}
