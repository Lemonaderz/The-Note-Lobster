package com.example.thenotelobster;

import java.util.List;

public class Question {
    private String questionText;
    private List<String> options; // A, B, C, D
    private String correctAnswer; // optional if you want checking later

    // Constructor
    public Question(String questionText, List<String> options) {
        this.questionText = questionText;
        this.options = options;
    }

    // Getters
    public String getQuestionText() { return questionText; }
    public List<String> getOptions() { return options; }
}
