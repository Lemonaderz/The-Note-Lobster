package com.example.thenotelobster.model.QuizClasses;

import com.example.thenotelobster.controller.DatabaseConnection;
import com.example.thenotelobster.model.UserClasses.UserAccount;
import com.google.gson.Gson;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Data Access Object (DAO) class for managing quiz data including creation,
 * retrieval, and deletion of quizzes and their questions from the database.
 */
public class QuizDAO {
    private final Connection conn;
    private final Gson gson = new Gson();

    /**
     * Constructs a new QuizDAO and establishes a database connection.
     */
    public QuizDAO() {
        this.conn = DatabaseConnection.getInstance();
    }

    /**
     * Inserts a new quiz into the database.
     *
     * @param quiz              The quiz object to be inserted
     * @param noteId            The note ID associated with the quiz
     * @return                  The generated quiz ID, or -1 if the insertion fails
     * @throws SQLException     If a database access error occurs
     */
    public int insertQuiz(QuizResponse quiz, int noteId) throws SQLException {
        String sql = "INSERT INTO Quiz (noteId, name, description, email) VALUES (?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, noteId);
            ps.setString(2, quiz.title);
            ps.setString(3, quiz.description);
            ps.setString(4, UserAccount.getInstance().getEmail());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            return keys.next() ? keys.getInt(1) : -1;
        }
    }

    /**
     * Inserts a list of multiple-choice questions for a specific quiz.
     *
     * @param quizId            The ID of the quiz to associate the questions with
     * @param questions         The list of questions to insert
     * @throws SQLException     If a database access error occurs
     */
    public void insertQuestions(int quizId, List<QuizMultipleChoiceQuestion> questions) throws SQLException {
        String sql = "INSERT INTO Question (quizId, question, answer, choices) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (QuizMultipleChoiceQuestion q : questions) {
                ps.setInt(1, quizId);
                ps.setString(2, q.question);
                ps.setString(3, q.answer);
                ps.setString(4, gson.toJson(q.choices));
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    /**
     * Saves a quiz and its associated questions as a transaction.
     *
     * @param quiz              The quiz object to save
     * @param noteId            The note ID associated with the quiz
     * @return                  The generated quiz ID if successful
     * @throws SQLException     If a database access error occurs or rollback is triggered
     */
    public int save(QuizResponse quiz, int noteId) throws SQLException {
        conn.setAutoCommit(false);
        try {
            int quizId = insertQuiz(quiz, noteId);
            insertQuestions(quizId, quiz.multipleChoiceQuestions);
            conn.commit();
            return quizId;
        } catch (SQLException ex) {
            conn.rollback();
            throw ex;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    /**
     * Loads a quiz and its associated questions by quiz ID.
     *
     * @param quizId            The ID of the quiz to load
     * @return                  A populated QuizResponse object
     * @throws SQLException     If a database access error occurs
     */
    public QuizResponse loadQuiz(int quizId) throws SQLException {
        QuizResponse quiz = new QuizResponse();
        // Load quiz metadata
        String q1 = "SELECT name, description FROM Quiz WHERE quizId = ?";
        try (PreparedStatement ps = conn.prepareStatement(q1)) {
            ps.setInt(1, quizId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                quiz.title       = rs.getString("name");
                quiz.description = rs.getString("description");
            }
        }
        // Load questions
        String q2 = "SELECT question, answer, choices FROM Question WHERE quizId = ?";
        try (PreparedStatement ps = conn.prepareStatement(q2)) {
            ps.setInt(1, quizId);
            ResultSet rs = ps.executeQuery();
            List<QuizMultipleChoiceQuestion> list = new ArrayList<>();
            while (rs.next()) {
                String text    = rs.getString("question");
                String answer  = rs.getString("answer");
                String jsonArr = rs.getString("choices");
                List<String> choices = Arrays.asList(gson.fromJson(jsonArr, String[].class));
                list.add(new QuizMultipleChoiceQuestion(text, answer, choices));
            }
            quiz.multipleChoiceQuestions = list;
        }
        return quiz;
    }

    /**
     * Retrieves all quizzes created by the currently logged-in user.
     *
     * @return                  A list of QuizResponse objects
     * @throws SQLException     If a database access error occurs
     */
    public List<QuizResponse> getAllQuizzesForCurrentUser() throws SQLException {
        List<QuizResponse> quizzes = new ArrayList<>();

        // load all quiz metadata
        String sql = "SELECT quizId, name, description FROM Quiz WHERE email = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, UserAccount.getInstance().getEmail());
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            QuizResponse qr = new QuizResponse();
            qr.title = rs.getString("name");
            qr.description = rs.getString("description");
            int quizId = rs.getInt("quizId");

            // load questions for this quiz
            String q2 = "SELECT question, answer, choices FROM Question WHERE quizId = ?";
            try (PreparedStatement ps2 = conn.prepareStatement(q2)) {
                ps2.setInt(1, quizId);
                ResultSet rs2 = ps2.executeQuery();
                List<QuizMultipleChoiceQuestion> list = new ArrayList<>();
                while (rs2.next()) {
                    String text = rs2.getString("question");
                    String answer = rs2.getString("answer");
                    String jsonArr = rs2.getString("choices");
                    List<String> choices =
                            Arrays.asList(gson.fromJson(jsonArr, String[].class));
                    list.add(new QuizMultipleChoiceQuestion(text, answer, choices));
                }
                qr.multipleChoiceQuestions = list;
            }

            quizzes.add(qr);
        }


        return quizzes;
    }

    /**
     * Retrieves a quiz by its title for the currently logged-in user.
     *
     * @param title             The title of the quiz
     * @return                  A populated QuizResponse object, or null if not found
     * @throws SQLException     If a database access error occurs
     */
    public QuizResponse getQuizByTitle(String title) throws SQLException {
        String q1 = "SELECT quizId, description FROM Quiz WHERE name = ? AND email = ?";
        try (PreparedStatement ps = conn.prepareStatement(q1);) {
            ps.setString(1, title);
            ps.setString(2, UserAccount.getInstance().getEmail());
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) return null;

            int quizId = rs.getInt("quizId");
            QuizResponse qr = new QuizResponse();
            qr.title = title;
            qr.description = rs.getString("description");

            String q2 = "SELECT question, answer, choices FROM Question WHERE quizId = ?";
            try (PreparedStatement ps2 = conn.prepareStatement(q2)) {
                ps2.setInt(1, quizId);
                ResultSet rs2 = ps2.executeQuery();
                List<QuizMultipleChoiceQuestion> list = new ArrayList<>();
                while (rs2.next()) {
                    String text    = rs2.getString("question");
                    String answer     = rs2.getString("answer");
                    String jsonArr = rs2.getString("choices");
                    List<String> choices =
                            Arrays.asList(gson.fromJson(jsonArr, String[].class));
                    list.add(new QuizMultipleChoiceQuestion(text, answer, choices));
                }
                qr.multipleChoiceQuestions = list;
            }

            return qr;
        }
    }

    /**
     * Retrieves the quiz ID by title and user email.
     *
     * @param title             The quiz title
     * @param userEmail         The email of the quiz owner
     * @return                  The quiz ID, or -1 if not found
     * @throws SQLException     If a database access error occurs
     */
    public int getQuizIdByTitle(String title, String userEmail) throws SQLException {
        String sql = "SELECT quizId FROM Quiz WHERE name = ? AND email = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, userEmail);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt("quizId") : -1;
        }
    }

    /**
     * Deletes a quiz and its associated questions from the database.
     *
     * @param quizId            The ID of the quiz to delete
     * @throws SQLException     If a database access error occurs
     */
    public void deleteQuiz(int quizId) throws SQLException {
        // delete questions
        try (PreparedStatement ps1 = conn.prepareStatement(
                "DELETE FROM Question WHERE quizId = ?")) {
            ps1.setInt(1, quizId);
            ps1.executeUpdate();
        }
        // delete quiz record
        try (PreparedStatement ps2 = conn.prepareStatement(
                "DELETE FROM Quiz WHERE quizId = ?")) {
            ps2.setInt(1, quizId);
            ps2.executeUpdate();
        }
    }
}
