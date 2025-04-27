package com.example.thenotelobster.QuizClasses;

import java.util.List;

public class QuizResponse {
    public String title;
    //public List<QuizQuestion> shortResponseQuestions;
    public List<QuizMultipleChoiceQuestion> multipleChoiceQuestions;

    public QuizResponse()
    {

    }

    public void consoleDisplay()
    {
        for(QuizMultipleChoiceQuestion question : multipleChoiceQuestions)
        {
            System.out.println(question.question);
            for(String choice : question.choices)
            {
                System.out.println(choice);
            }
            System.out.println("Answer:  " + question.answer);




        }
    }
    // Getters and Setters

}
