package AIClasses;
import com.example.thenotelobster.model.SummaryResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SummaryResponseTest {

    SummaryResponse summary;
    @BeforeEach
    public void setUp() {
        summary = new SummaryResponse("This is AI Response", "Subject goes here", "350", 4, "Title goes here");
    }

    @Test
    public void testGetResponse() {
        assertEquals("This is AI Response", summary.getResponse());
    }

    @Test
    public void testGetSubject() {
        assertEquals("Subject goes here", summary.getSubject());
    }

    @Test
    public void testGetLength() {
        assertEquals("350", summary.getLength());
    }

    @Test
    public void testGetComplexity() {
        assertEquals(4, summary.getComplexity());
    }

    @Test
    public void testGetTitle() {
        assertEquals("Title goes here", summary.getTitle());
    }


    @Test
    public void testSetResponse() {
        summary.setResponse("New response");
        assertEquals("New response", summary.getResponse());
    }

    @Test
    public void testSetSubject() {
        summary.setSubject("New subject");
        assertEquals("New subject", summary.getSubject());
    }

    @Test
    public void testSetLength() {
        summary.setLength("100");
        assertEquals("100", summary.getLength());
    }

    @Test
    public void testSetComplexity() {
        summary.setComplexity(10);
        assertEquals(10, summary.getComplexity());
    }

    @Test
    public void testSetTitle() {
        summary.setTitle("New title");
        assertEquals("New title", summary.getTitle());
    }


}
