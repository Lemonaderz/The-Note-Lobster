package com.example.thenotelobster;
import java.net.URI;
import java.net.http.*;

import com.example.thenotelobster.QuizClasses.QuizResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public final class AIManager {

    // This is the url we are using, its local.
    private HttpClient client = HttpClient.newHttpClient();
    public String Url = "http://localhost:11434/api/generate";
    public String messageHistory = "\"messages\": [";
    public boolean chatActive = false;
    public SummaryResponse singleSummary = new SummaryResponse("", "");
    private final static AIManager INSTANCE = new AIManager();

    private AIManager()
    {
    }

    public static AIManager getInstance() {
        return INSTANCE;
    }
    public String fetchPromptResponse(String message, String length, double complexity)
    {

        return fetchPromptResponse(message, length, complexity, Url);
    }
    public String fetchPromptResponse(String prompt, String length, double complexity, String url)
    {
        // This is the request here, pretty much building the template


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(prompt))
                .build();
        try {
            String totalResponse = "";
            //Send a "question" with our prompt
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();





        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }
    public SummaryResponse fetchSingleResponse(String message, String length, double complexity)
    {
        String prompt = "{\"model\": \"gemma3\", \"prompt\": \"" +
                "Please summarize the following text, with a maximum of"
                + length + "words. You MUST Adhere to this word limit and if  and with a complexity of"
                + complexity + "out of 10:"
                + message
                + "\",\"stream\": false }";
        String response = fetchChatResponse(prompt, length, complexity);
        JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();

        String stringResponse = jsonResponse.get("response").getAsString();  // Get a field named "answer"

        System.out.println(stringResponse);
        singleSummary.response = stringResponse;
        return singleSummary;
    }
    public String fetchChatResponse(String message, String length, double complexity)
    {

        if (!chatActive) {
            // on the first time through, summarize your
            messageHistory += " { \"role\": \"user\", \"content\": \"" + "Please summarize the following text, with a maximum of "
                    + length + " words. You MUST Adhere to this word limit and if  and with a complexity of "
                    + complexity + " out of 10:"
                    + message.replace("\n"," ") + "\" },";

            chatActive = true;
        }
        else {
            messageHistory += "{\"role\": \"user\", \"content\": \"" +message + "\" },";
        }

        String formattedMessage = messageHistory.substring(0,messageHistory.length()-1) +"]";
        //formats out message into json

        String prompt = "{\"model\": \"gemma3\","
                + formattedMessage
                + ",\"stream\": false }";
        String response = fetchPromptResponse(prompt, length,complexity,"http://localhost:11434/api/chat");
        System.out.println(response);
        //Turn the response into raw text
        JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
        String stringResponse = (jsonResponse.get("message").getAsJsonObject()).get("content").getAsString();  // Get a field named "answer"
        //Put that raw text into the message history
        //replace the new lines with spaces so it doesn't cause json issues
        messageHistory += " { \"role\": \"assistant\", \"content\": \""+ (stringResponse.replace("\n", " ")).replace("\"", "'") + "\" },";

        singleSummary.SetResponse(stringResponse);
        singleSummary.SetLength(length);
        singleSummary.SetComplexity(complexity);
        return stringResponse;
    }

    public void clearChat()
    {
        chatActive = false;
        messageHistory = "\"messages\": [";
    }


    public QuizResponse









}
