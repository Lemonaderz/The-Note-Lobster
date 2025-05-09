package com.example.thenotelobster.controller;

import com.example.thenotelobster.HelloApplication;
import com.example.thenotelobster.model.QuizClasses.QuizMultipleChoiceQuestion;
import com.example.thenotelobster.model.QuizClasses.QuizResponse;
import com.example.thenotelobster.model.QuizClasses.QuizDAO;
import com.example.thenotelobster.model.UserClasses.UserAccount;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

/**
 * Controller class for managing the quiz view interface.
 *
 * Inherits navigation functionality from {@link NavigationUI}.
 */
public class QuizController extends NavigationUI {

    /** The container VBox that displays quiz questions and options dynamically. */
    @FXML private VBox quizBox;

    /** A scrollable container box inside the quiz view (not directly used in logic). */
    @FXML private VBox scrollBox;

    /** Button that navigates the user to the new quiz creation screen. */
    @FXML private Button newQuizButton;

    /** Label that displays the description of the currently selected quiz. */
    @FXML private Label descriptionLabel;

    /** Label that displays the title of the currently selected quiz. */
    @FXML private Label titleLabel;

    /** ListView that displays all available quiz titles for the logged-in user. */
    @FXML private ListView<String> QuizMenu;

    /** Button that deletes the selected quiz from the database and UI. */
    @FXML private Button deleteQuizButton;

    /** Button that submits the user's quiz responses for grading. */
    @FXML private Button SubmitButton;

    /** Button that allows the user to retry the selected quiz. */
    @FXML private Button RetryButton;

    /** Label that displays the user's grade after submitting a quiz. */
    @FXML private Label gradeLabel;

    /** ScrollPane that holds the main content and enables vertical scrolling of the quiz. */
    @FXML private ScrollPane ScrollBar;

    /** Stores the currently selected quiz object being taken or viewed. */
    private QuizResponse selectedQuiz;

    /** The email address of the currently logged-in user, retrieved from the singleton UserAccount. */
    private final String userEmail = UserAccount.getInstance().getEmail();

    /**
     * Handles navigation to the "Create New Quiz" view.
     * @throws IOException if the FXML file fails to load.
     */
    @FXML
    protected void onCreateNewQuizClick() throws IOException {
        Stage stage = (Stage) newQuizButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("new-quiz-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        scene.getStylesheets().add(checkCurrentMode());
        stage.setScene(scene);

    }

    /**
     * Initializes the quiz controller by loading a sample or saved quiz, populating the quiz menu,
     * and setting up listeners for quiz selection.
     */
    public void initialize() {
        QuizResponse quiz;
        try {
            QuizDAO dao = new QuizDAO();
            quiz = dao.loadQuiz(1);
        } catch (SQLException e) {
            e.printStackTrace();
            // fallback to AI‐sample if DB fails
            AIManager aiManager = AIManager.getInstance();
            quiz = (aiManager.currentQuiz != null)
                    ? aiManager.currentQuiz
                    : new QuizResponse("Sample Quiz", "Sample", Arrays.asList(
                    new QuizMultipleChoiceQuestion("What is 2 + 2?", "B", List.of("3", "4", "5", "6")),
                    new QuizMultipleChoiceQuestion("What color is the sky?", "A", List.of("Blue", "Green", "Red", "Yellow"))
            ));
        }

        loadQuiz(quiz);

        QuizDAO quizDAO = new QuizDAO();

        ObservableList<String> quizTitles = FXCollections.observableArrayList();
        try {
            List<QuizResponse> saved = quizDAO.getAllQuizzesForCurrentUser();
            for (QuizResponse quizResponse : saved) {
                quizTitles.add(quizResponse.title);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        QuizMenu.setItems(quizTitles);

        QuizMenu.getSelectionModel().selectedItemProperty().addListener((obs, oldTitle, newTitle) -> {
            if (newTitle != null) {
                try {
                    QuizResponse q = quizDAO.getQuizByTitle(newTitle);
                    if (q != null)
                    {
                        q.displayQuiz();
                        loadQuiz(q);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        // clears the quiz on load up
        quizBox.getChildren().clear();
        titleLabel.setText("");
        descriptionLabel.setText("");
    }

    /**
     * Loads the specified quiz into the UI for display.
     * @param quiz The quiz to load.
     */
    private void loadQuiz(QuizResponse quiz) {
        // Clear old content

        quizBox.getChildren().clear();
        gradeLabel.setVisible(false);
        titleLabel.setText(quiz.title);
        descriptionLabel.setText(quiz.description);

        for (int i = 0; i < quiz.multipleChoiceQuestions.size(); i++) {
            QuizMultipleChoiceQuestion question = quiz.multipleChoiceQuestions.get(i);

            VBox questionBox = new VBox(5);
            questionBox.setSpacing(10);
            questionBox.setStyle("-fx-border-color: d8d8d8; -fx-padding: 10;");

            Label questionLabel = new Label("Question " + (i+1));
            Label questionText = new Label(question.question);

            questionBox.getChildren().add(0, questionLabel); // Add at top
            questionBox.getChildren().add(1, questionText);  // Add under label
            ToggleGroup group = new ToggleGroup();
            for (int questionNumber = 0; questionNumber < 4; questionNumber++) {
                RadioButton radioButton = new RadioButton(question.choices.get(questionNumber));
                radioButton.setUserData(QuizMultipleChoiceQuestion.letterOptions.get(questionNumber));
                radioButton.setToggleGroup(group);
                questionBox.getChildren().add(radioButton);
            }

            quizBox.getChildren().add(questionBox);
        }
        SubmitButton.setVisible(true);
        RetryButton.setVisible(false);
        selectedQuiz = quiz;
    }

    /**
     * Deletes the currently selected quiz from the database and updates the UI.
     */
    @FXML
    protected void onDeleteQuizButton() {
        String title = QuizMenu.getSelectionModel().getSelectedItem();
        if (title == null) return;

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Delete quiz \""+ title +"\"?",
                ButtonType.OK, ButtonType.CANCEL);
        confirm.setHeaderText(null);
        confirm.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.OK) {
                try {
                    QuizDAO dao = new QuizDAO();
                    int quizId = dao.getQuizIdByTitle(title, userEmail);
                    if (quizId != -1) {
                        dao.deleteQuiz(quizId);
                        // remove from ListView
                        QuizMenu.getItems().remove(title);
                        // clear display if it was showing
                        quizBox.getChildren().clear();
                        titleLabel.setText("");
                        descriptionLabel.setText("");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Could not delete quiz").showAndWait();
                }
            }
        });
    }

    /**
     * Handles submission of quiz answers, calculates the score, displays feedback, and disables inputs.
     */
    @FXML
    protected void onSubmitButtonClick()
    {
        int questionNumber = 0;
        int grade = 0;
        //search through each quizbox's  question box and get the toggle group for that box.
        for(Node questionBox: quizBox.getChildren())
        {
            if (questionBox instanceof VBox) {
                for (Node child : ((VBox) questionBox).getChildren()) {
                    if (child instanceof RadioButton) {
                        //find the first radio button of the section
                        RadioButton firstChoice = (RadioButton) child;
                        ToggleGroup choices = firstChoice.getToggleGroup();
                        Toggle selectedButton = choices.getSelectedToggle();
                        QuizMultipleChoiceQuestion currentQuestion = selectedQuiz.multipleChoiceQuestions.get(questionNumber);
                        // if they didnt pick an answer instantly wrong
                        if (selectedButton != null) {


                            String answer = (String) choices.getSelectedToggle().getUserData();
                            //Disable touching the buttons again
                            for (Toggle button : choices.getToggles()) {
                                RadioButton radioButton = (RadioButton) button;
                                radioButton.setMouseTransparent(true);
                            }
                            //If their answer is wrong, colour it red
                            if (!currentQuestion.checkResponse(answer)) {
                                RadioButton chosenAnswer = (RadioButton) choices.getSelectedToggle();
                                chosenAnswer.getStyleClass().add("wrong");
                            } else {
                                grade += 1;
                            }
                        }
                        //Set the correct answer to green, overriding if they chose it
                        RadioButton correctAnswer = (RadioButton) choices.getToggles().get(QuizMultipleChoiceQuestion.letterOptions.indexOf(currentQuestion.answer));
                        correctAnswer.getStyleClass().remove("wrong");
                        correctAnswer.getStyleClass().add("correct");
                        //Next question

                        questionNumber += 1;


                        break;
                    }
                }
            }


        }
        //set button visibility
        SubmitButton.setVisible(false);
        RetryButton.setVisible(true);
        gradeLabel.setVisible(true);

        //tell you grade
        gradeLabel.setText("Grade:" + grade + "/" + selectedQuiz.multipleChoiceQuestions.size());

        //reset to top
        Platform.runLater(() -> {
            ScrollBar.setVvalue(0);
        });

    }

    /**
     * Reloads the last selected quiz.
     */
    @FXML
    protected void onRetryClick()
    {
        loadQuiz(selectedQuiz);

    }

}
