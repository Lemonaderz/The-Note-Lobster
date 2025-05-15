package com.example.thenotelobster.model.QuizClasses;

import java.util.List;
import java.util.Random;

/**
 * A Class holding an entire quiz
 */
public class QuizResponse {
    /** The title of the quiz */
    public String title;
    //A unused list in case of implementing short response questions
    //public List<QuizQuestion> shortResponseQuestions;
    /** A list of all of the multiple choice questions */
    public List<QuizMultipleChoiceQuestion> multipleChoiceQuestions;
    /** a short description of the quiz */
    public String description;

    /**
     * An empty constructor for Gson
     */
    public QuizResponse()
    {

    }

    /**
     * A full constructor for manual quiz creation;
     * @param title The title of the quiz
     * @param description A short quiz description
     * @param questions A list of multiple choice questions
     */
    public QuizResponse(String title, String description, List<QuizMultipleChoiceQuestion> questions) {
        this.title = title;
        this.description = description;
        this.multipleChoiceQuestions = questions;
    }

    /**
     * Displays the quiz to the console, then returns the display as a string.
     * @return A string of the quiz, formatted for viewing.
     */
    public String displayQuiz()
    {
        System.out.println(multipleChoiceQuestions.size());
        String displayString = "";
        displayString += title + "\n";
        displayString += "\n";
        displayString += description + "\n";
        displayString += "\n";



        for(QuizMultipleChoiceQuestion question : multipleChoiceQuestions)
        {
            displayString += "\n";

            displayString += question.question + "\n";

            for(String choice : question.choices)
            {
                displayString += choice + "\n";
            }
            displayString += "Answer:  " + question.answer + "\n";

        }
        System.out.println(displayString);
        return displayString;
    }
    // This is to randomize the quiz since the AI is prone to picking B/C as its favourite answers.

    /**
     * Randomizes the answer location of all multiple choice questions in the quiz.
     */
    public void randomizeAnswers()
    {
         for(QuizMultipleChoiceQuestion question : multipleChoiceQuestions)
         {
             question.randomizeAnswer();
         }

    }

 


}
