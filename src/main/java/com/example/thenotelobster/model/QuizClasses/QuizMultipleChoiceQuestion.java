package com.example.thenotelobster.model.QuizClasses;

import java.util.List;
import java.util.Random;

public class QuizMultipleChoiceQuestion extends QuizQuestion{
    public List<String> choices;

    public QuizMultipleChoiceQuestion()
    {

    }
    public QuizMultipleChoiceQuestion(String question, int answer, List<String> choices) {
        super(question, answer);
        this.choices = choices;
    }

    public void randomizeAnswer()
    {
        Random r = new Random();
        int randomPosition = r.nextInt(4);
        String correctAnswer = choices.get(answer - 1);
        choices.remove(correctAnswer);
        choices.add(randomPosition, correctAnswer);
        answer = randomPosition + 1;
    }







}
