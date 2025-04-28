package com.example.thenotelobster.QuizClasses;

import com.example.thenotelobster.Question;

import java.util.List;

public class QuizResponse {
    public String title;
    //public List<QuizQuestion> shortResponseQuestions;
    public List<QuizMultipleChoiceQuestion> multipleChoiceQuestions;
    public String description;
    public QuizResponse()
    {

    }

    public QuizResponse(String title, List<QuizMultipleChoiceQuestion> questions) {
        this.title = title;
        this.multipleChoiceQuestions = questions;
    }

    public void consoleDisplay()
    {
        System.out.println(title);
        System.out.println("");
        System.out.println(description);
        System.out.println("");
        for(QuizMultipleChoiceQuestion question : multipleChoiceQuestions)
        {
            System.out.println("");
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
