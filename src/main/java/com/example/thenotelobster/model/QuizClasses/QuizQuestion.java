package com.example.thenotelobster.model.QuizClasses;

public class QuizQuestion {
    public String question;
    public String answer;

    public QuizQuestion() { }
    public QuizQuestion(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    // Getters and Setters

    public boolean checkResponse(String response) {
        return answer.equals(response);
    }

}

