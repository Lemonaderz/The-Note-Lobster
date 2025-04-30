package com.example.thenotelobster.QuizClasses;

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

    public String getQuestion() {
        return question;
    }

    public int getAnswer() {
        return answer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}

