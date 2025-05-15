package com.example.thenotelobster.controller;
import java.net.URI;
import java.net.http.*;

import com.example.thenotelobster.model.QuizClasses.QuizResponse;

import com.example.thenotelobster.model.SummaryResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * A singleton object used as a manager for all AI requests.
 * <p>This class ensures that only one instance of a user is active in memory at a time,
 *  using the Singleton design pattern. This is to allow data transfer between scenes.</p>
 */
public final class AIManager {

    // This is the url we are using, its local.
    /** A client object, which is used to send and recieve requests*/
    private HttpClient client = HttpClient.newHttpClient();
    /** The main url for using Ollama restAPI calls*/
    public String Url = "http://localhost:11434/api/generate";
    /** A string containing a formatted history of messages, for use in chats */
    public String messageHistory = "\"messages\": [";
    /** A boolean for if a chat is currently active or a new one is to be started*/
    public boolean chatActive = false;
    /** A summary response which is the main data transferred between scenes*/
    public SummaryResponse singleSummary = new SummaryResponse();
    /** The static AI Manager instance which is used for the singleton design pattern */
    private final static AIManager INSTANCE = new AIManager();
    /** The quiz last created, the other data transferred between scenes*/
    public QuizResponse currentQuiz;

    /**
     * A private constructor for the singleton design pattern
     */
    private AIManager()
    {
    }

    /**
     * A instance getter to allow for a single object
     * @return The instance of the AIManager
     */
    public static AIManager getInstance() {
        return INSTANCE;
    }

    /**
     * Fetches a single prompt response to the ai with the given message
     * @param message The prompt to give to the AI
     * @return A string of the AI response
     */
    public String fetchPromptResponse(String message)
    {

        return fetchPromptResponse(message, Url);
    }

    /**
     * Fetches a prompt response from a specified url instead
     * @param prompt Prompt to get response from
     * @param url Url to use
     * @return A string with the response
     */
    public String fetchPromptResponse(String prompt, String url)
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

    /**
     * Fetch a prompt response, using the history to get the response, and appending the response to the history afterwards
     * @return A string with the AI response.
     */
    public String fetchPromptResponseWithHistory()
    {
        String formattedMessage = messageHistory.substring(0,messageHistory.length()-1) +"]";
        //formats out message into json

        String prompt = "{\"model\": \"gemma3:12b\","
                + formattedMessage
                + ",\"stream\": false }";
        String response = fetchPromptResponse(prompt,"http://localhost:11434/api/chat");
        System.out.println(response);
        //Turn the response into raw text
        JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
        String stringResponse = (jsonResponse.get("message").getAsJsonObject()).get("content").getAsString();  // Get a field named "answer"
        //Put that raw text into the message history
        //replace the new lines with spaces so it doesn't cause json issues
        messageHistory += " { \"role\": \"assistant\", \"content\": \""+ (stringResponse.replace("\n", " ")).replace("\"", "'") + "\" },";

        singleSummary.setResponse(stringResponse);

        return stringResponse;

    }

    /**
     * Fetch a chat response, from a message, length and complexity, integrating response into the message history for further chats
     * @param message the prompt message to ask the AI
     * @param length the preferred summary response length
     * @param complexity the preferred summary response complexity
     * @return Returns a string with the response
     */
    public String fetchChatResponse(String message, String length, double complexity)
    {

        if (!chatActive) {
            // on the first time through, summarize your
            messageHistory += " { \"role\": \"user\", \"content\": \"" + "Please summarize the following text, with a maximum of "
                    + length + " words. You MUST Adhere to this word limit and if  and with a complexity of "
                    + complexity + " out of 10:"
                    + message.replace("\n"," ").replace("\"", "'") + "\" },";

            chatActive = true;
        }

        else {
            messageHistory += "{\"role\": \"user\", \"content\": \"" +message.replace("\n"," ").replace("\"", "'") + "\" },";
        }
//        System.out.println(messageHistory);

        fetchPromptResponseWithHistory();
        singleSummary.setLength(length);
        singleSummary.setComplexity(complexity);
//        System.out.println(messageHistory);

        return singleSummary.getResponse();

    }


    /**
     * Clears the message history and sets chatActive to false, to allow a new chat to start
     */
    public void clearChat()
    {
        chatActive = false;
        messageHistory = "\"messages\": [";
    }

    /**
     * Fetch a QuizResponse object from a given summary
     * @param summary a summary or notes to make a quiz out of
     * @return A filled in QuizResponse object with the quiz questions and answers
     */
    public QuizResponse fetchQuizResponse(String summary)
    {

        QuizResponse quizResponse;
        String jsonFormat = "{" +
                "  \"type\": \"object\"," +
                "  \"properties\": {" +
                "\"title\": { \"type\": \"string\" }," +
                "\"description\": { \"type\": \"string\" }," +
                "    \"multipleChoiceQuestions\": {" +
                "      \"type\": \"array\"," +
                "      \"items\": {" +
                "        \"type\": \"object\"," +
                "        \"properties\": {" +
                "          \"question\": { \"type\": \"string\" }," +
                "          \"choices\": { \"type\": \"array\", \"items\": { \"type\": \"string\" } }," +
                "          \"answer\": { \"type\": \"string\", \"pattern\" : \"^[A-D]$\" }" +
                "        }," +
                "        \"required\": [\"question\", \"choices\", \"answer\"]" +
                "      }" +
                "    }" +
                "  }," +
                "  \"required\": [\"multipleChoiceQuestions\", \"title\", \"description\" ]" +
                "}";


        String prompt = ("{\"model\": \"gemma3:12b\", \"prompt\": \"" +
                "Please make a Quiz with the following notes/summary. Make the quiz be multiple choice questions" +
                "Indicate the answer with a Letter (A,B,C,D) for the position. Add a relevant title and a brief description of what the quiz is about. Begin the description with 'This quiz is about'. Use the Json Format provided as your response. Here is the Notes/Summary: " +
                summary.replace("\"", "'").replace("\n", " ")
                + "\",\"stream\": false, \"format\" :" +
                  jsonFormat +
                " }");
        String response = fetchPromptResponse(prompt);
        System.out.println(response);
//        System.out.println(response);
        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();

        String responseString = jsonObject.get("response").getAsString().replaceAll("(?m)^[A-D]\\.\\s?", "");

        JsonObject quizJson = JsonParser.parseString(responseString).getAsJsonObject();

        Gson gson = new Gson();
        quizResponse = gson.fromJson(quizJson, QuizResponse.class);
        quizResponse.randomizeAnswers();
        quizResponse.displayQuiz();

        currentQuiz = quizResponse;
        return quizResponse;



//        System.out.println(stringResponse);
    }

    /**
     * A method for setting the AI to resummarize mode, for use when expanding on notes from notes page.
     */
    public void setResummaryMode()
    {
        messageHistory += " { \"role\": \"assistant\", \"content\": \"" + singleSummary.getResponse().replace("\n"," ").replace("\"", "'") + "\" },";
        chatActive = true;
    }









}
