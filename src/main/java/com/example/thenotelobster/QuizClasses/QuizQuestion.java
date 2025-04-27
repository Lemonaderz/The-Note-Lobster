package com.example.thenotelobster.QuizClasses;

public class QuizQuestion {
    String question;
    String answer;

    public QuizQuestion(String question, String answer)
    {
        this.question = question;
        this.answer = answer;
    }
    public boolean checkResponse(String response)
    {
        return (answer==response);
    }


}
