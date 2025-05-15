package com.example.thenotelobster.model.QuizClasses;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * A class to store and give ability to randomize multiple choice quiz questions.
 */
public class QuizMultipleChoiceQuestion extends QuizQuestion{
    /** A list of answer choices as a string */
    public List<String> choices;
    /** A list of all the letter options for a question to assist in answer randomizing */
    public static List<String> letterOptions =   Arrays.asList("A", "B", "C", "D");

    /**
     * An empty constructor for Gson to use
     */
    public QuizMultipleChoiceQuestion()
    {

    }

    /**
     * A constructor for QuizMultipleChoiceQuestion with all neccessary variables for a complete object
     * @param question The question that is being asked
     * @param answer The answer (A single letter denoting the answer from letterOptions)
     * @param choices A list of answer choices
     */
    public QuizMultipleChoiceQuestion(String question, String answer, List<String> choices) {
        super(question, answer);
        this.choices = choices;
    }

    /**
     * A method to randomize the position of the answer, as the AI commonly chose B.
     */
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
