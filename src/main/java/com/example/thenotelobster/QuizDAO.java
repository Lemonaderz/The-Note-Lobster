package com.example.thenotelobster;

import com.example.thenotelobster.QuizClasses.QuizMultipleChoiceQuestion;
import com.example.thenotelobster.QuizClasses.QuizResponse;
import com.google.gson.Gson;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuizDAO {
    private final Connection conn;
    private final Gson gson = new Gson();

    public QuizDAO() {
        this.conn = DatabaseConnection.getInstance();
    }

    /** Insert a new Quiz, return its generated ID */
    public int insertQuiz(QuizResponse quiz, int noteId) throws SQLException {
        String sql = "INSERT INTO Quiz (noteId, name, description) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, noteId);
            ps.setString(2, quiz.title);
            ps.setString(3, quiz.description);
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            return keys.next() ? keys.getInt(1) : -1;
        }
    }

    /** Insert all questions for a given quizId */
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

    /** Full save: quiz + all its questions */
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

    /** Load a quiz + its questions by quizId */
    public QuizResponse loadQuiz(int quizId) throws SQLException {
        QuizResponse quiz = new QuizResponse();
        // 1) Load quiz metadata
        String q1 = "SELECT name, description FROM Quiz WHERE quizId = ?";
        try (PreparedStatement ps = conn.prepareStatement(q1)) {
            ps.setInt(1, quizId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                quiz.title       = rs.getString("name");
                quiz.description = rs.getString("description");
            }
        }
        // 2) Load questions
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
}
