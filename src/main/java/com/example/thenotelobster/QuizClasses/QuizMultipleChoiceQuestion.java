package com.example.thenotelobster.QuizClasses;

import java.util.List;

public class QuizMultipleChoiceQuestion extends QuizQuestion{
    List<String> choices;

    public QuizMultipleChoiceQuestion(String question, String answer, List<String> choices) {
        super(question, answer);
        this.choices = choices;
    }
}
