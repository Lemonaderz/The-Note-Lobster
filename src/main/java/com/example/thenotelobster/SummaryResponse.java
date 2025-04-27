package com.example.thenotelobster;

public class SummaryResponse {
    public String response;
    public String subject;
    public String length;
    public double complexity;

    public SummaryResponse(String response, String subject)
    {
        this.response = response;
        this.subject = subject;
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
        this.response = response;

    }



}
