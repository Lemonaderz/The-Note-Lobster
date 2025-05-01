package AIClasses;

import com.example.thenotelobster.controller.AIManager;
import com.example.thenotelobster.model.SummaryResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AIManagerTest {

    private AIManager aiManager;
    private SummaryResponse summary = new SummaryResponse("Hello", "Subject",  "100", 4, "Title" );


    @BeforeEach
    public void setUp() {
        aiManager = AIManager.getInstance();
        aiManager.clearChat();
        aiManager.singleSummary = new SummaryResponse();

    }

    @Test
    public void testClearChat() {
        aiManager.messageHistory = "Hello";
        aiManager.chatActive = true;
        aiManager.clearChat();
        assertEquals("\"messages\": [", aiManager.messageHistory);
        assertFalse(aiManager.chatActive);

    }

    @Test
    public void setResummaryMode() {
        aiManager.singleSummary = summary;
        aiManager.setResummaryMode();
        assertEquals("\"messages\": [ { \"role\": \"assistant\", \"content\": \"Hello\" },", aiManager.messageHistory);
        assertTrue(aiManager.chatActive);

    }

}