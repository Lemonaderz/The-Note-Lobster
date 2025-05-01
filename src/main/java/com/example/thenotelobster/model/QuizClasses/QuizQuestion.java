package com.example.thenotelobster.model.QuizClasses;

public class QuizQuestion {
    public String question;
    public int answer;

    public QuizQuestion() { }
    public QuizQuestion(String question, int answer) {
        this.question = question;
        this.answer = answer;
    }

    // Getters and Setters

    public boolean checkResponse(int response) {
        return answer == response;
    }

}

