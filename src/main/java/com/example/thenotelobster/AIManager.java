package com.example.thenotelobster;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.*;
import com.google.gson.Gson;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

public class AIManager {

    // This is the url we are using, its local.
    private HttpClient client = HttpClient.newHttpClient();
    public String Url = "http://localhost:11434/api/generate";
    public String fetchPromptResponse(String message)
    {
        // This is the request here, pretty much building the template
        String prompt = "{\"model\": \"gemma3\", \"prompt\": \"" + "Please summarize the following lecture in as short as possible:" + message + "\",\"stream\": false }";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(prompt))
                .build();
        try {
            String totalResponse = "";
            //Send a "question" with our prompt
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Response Status Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());
            return response.body();



        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }







}
