package com.example.thenotelobster.QuizClasses;

public class QuizQuestion {
    public String question;
    public String answer;

    public QuizQuestion()
    {

    }
    public QuizQuestion(String question, String answer)
    {

    }





    // Getters and Setters

    public boolean checkResponse(String response)
    {
        return (answer.equals(response));
    }


}
