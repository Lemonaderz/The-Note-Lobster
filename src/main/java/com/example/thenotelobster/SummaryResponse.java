package com.example.thenotelobster;

public class SummaryResponse {
    public String response;
    public String subject;
    public String length;
    public double complexity;
    public String title;
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

    public void SetResponse(String response)
    {
        this.response = response;

    }

    public void SetSubject(String subject)
    {
        this.subject = subject;
    }

    public void SetLength(String length)
    {
        this.length = length;

    }

    public void SetComplexity(double complexity)
    {
        this.complexity = complexity;

    }

    public void SetTitle(String title)
    {
        this.title = title;
    }



}
