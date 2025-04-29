package com.example.thenotelobster.QuizClasses;

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
        return answer != null && answer.equals(response);
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}

