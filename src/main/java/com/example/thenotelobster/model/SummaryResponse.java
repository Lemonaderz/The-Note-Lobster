package com.example.thenotelobster.model;
/**
 * A model class for summary responses from the AI fetcher, with aditional user input included
 * */
public class SummaryResponse {
    /** The response that was given by the AI */
    private String response;
    /** The subject the user inputs, as a folder in the notes page */
    private String subject;
    /** The preferred length for the AI summary */
    private String length;
    /** The preferred complexity for the AI, out of 10*/
    private double complexity;
    /** The title the user wants the note saved under */
    private String title;
    /**
     * An empty constructor to allow for an empty response, allowing for some information to be inputted later
     */
    public SummaryResponse()
    {

    }

    /**
     * A full constructor for the Summary Response object, most used with the database
     * @param response A summary response from the AI
     * @param subject The subject given by the user. Used as the folder name.
     * @param length The preferred length of the summary
     * @param complexity The preferred complexity for the summary, generally out of 10
     * @param title The title given by the user, will be given to the note
     */
    public SummaryResponse(String response, String subject, String length, double complexity, String title)
    {
        this.response = response;
        this.subject = subject;
        this.length = length;
        this.complexity = complexity;
        this.title = title;
    }

    /**
     * Sets the response variable to given string
     * @param response A response to be stored.
     */
    public void setResponse(String response)
    {
        this.response = response;

    }

    /**
     * Returns the current response string
     * @return The current response string
     */
    public String getResponse()
    {
        return response;
    }

    /**
     * Sets the current subject, name of the folder
     * @param subject The subject of the note, and name of the folder
     */
    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    /** Gets the current subject as a string
     *
     * @return The current subject (string)
     */
    public String getSubject()
    {
        return subject;
    }

    /**
     * Sets the preferred summary length to given string
     * @param length A string of the current preferred summary length
     */
    public void setLength(String length)
    {
        this.length = length;

    }

    /**
     * gets the current length, returned as a string
     * @return gets the current length
     */
    public String getLength()
    {
        return length;
    }

    /**
     * Sets the complexity out of 10 as a double
     * @param complexity The preferred complexity of the note
     */
    public void setComplexity(double complexity)
    {
        this.complexity = complexity;

    }

    /**
     * Gets the current preferred complexity of the summary
     * @return The currenty complexity
     */
    public double getComplexity()
    {
        return complexity;
    }

    /**
     * Sets the current title
     * @param title the title of the summary
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * Gets the current title
     * @return the title of the summary
     */
    public String getTitle()
    {
        return title;
    }



}
