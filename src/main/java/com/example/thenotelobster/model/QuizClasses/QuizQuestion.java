package com.example.thenotelobster.model.QuizClasses;

/**
 * A class to hold a variable response QuizQuestion
 */
public class QuizQuestion {
    /** The question string */
    public String question;
    /** The answer string **/
    public String answer;

    /**
     * An empty constructor for Gson
     */
    public QuizQuestion() { }

    /**
     * A constructor to create a complete QuizQuestion manually
     * @param question The question as a string
     * @param answer The correct answer.
     */
    public QuizQuestion(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    /**
     *  Returns a bool on whether the given response is the same as the question answer.
     * @param response the user response given to the question
     * @return A boolean, true being the response is correct (same as answer) or false otherwise.
     */
    public boolean checkResponse(String response) {
        return answer.equals(response);
    }

}

