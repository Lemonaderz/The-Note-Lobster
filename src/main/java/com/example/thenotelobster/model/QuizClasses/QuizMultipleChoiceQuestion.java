package com.example.thenotelobster.model.QuizClasses;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class QuizMultipleChoiceQuestion extends QuizQuestion{
    public List<String> choices;
    public static List<String> letterOptions =   Arrays.asList("A", "B", "C", "D");

    public QuizMultipleChoiceQuestion()
    {

    }
    public QuizMultipleChoiceQuestion(String question, String answer, List<String> choices) {
        super(question, answer);
        this.choices = choices;
    }

    public void randomizeAnswer()
    {
        Random r = new Random();
        int randomPosition = r.nextInt(4);
        int answerIndex = letterOptions.indexOf(answer);
        String correctAnswer = choices.get(answerIndex);
        choices.remove(correctAnswer);
        choices.add(randomPosition, correctAnswer);
        answer = letterOptions.get(randomPosition);
    }







}
