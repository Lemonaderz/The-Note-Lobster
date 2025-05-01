package com.example.thenotelobster.model.QuizClasses;

import java.util.List;
import java.util.Random;

public class QuizResponse {
    public String title;
    //public List<QuizQuestion> shortResponseQuestions;
    public List<QuizMultipleChoiceQuestion> multipleChoiceQuestions;
    public String description;
    public QuizResponse()
    {

    }

    public QuizResponse(String title, String description, List<QuizMultipleChoiceQuestion> questions) {
        this.title = title;
        this.description = description;
        this.multipleChoiceQuestions = questions;
    }

    public String displayQuiz()
    {
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
    public void randomizeAnswers()
    {
         for(QuizMultipleChoiceQuestion question : multipleChoiceQuestions)
         {
             question.randomizeAnswer();
         }

    }

 


}
