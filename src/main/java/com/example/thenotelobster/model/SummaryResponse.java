package com.example.thenotelobster.model;

public class SummaryResponse {
    private String response;
    private String subject;
    private String length;
    private double complexity;
    private String title;
    public SummaryResponse()
    {

    }
    public SummaryResponse(String response, String subject, String length, double complexity, String title)
    {
        this.response = response;
        this.subject = subject;
        this.length = length;
        this.complexity = complexity;
        this.title = title;
    }

    public void setResponse(String response)
    {
        this.response = response;

    }

    public String getResponse()
    {
        return response;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setLength(String length)
    {
        this.length = length;

    }

    public String getLength()
    {
        return length;
    }

    public void setComplexity(double complexity)
    {
        this.complexity = complexity;

    }

    public double getComplexity()
    {
        return complexity;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }



}
