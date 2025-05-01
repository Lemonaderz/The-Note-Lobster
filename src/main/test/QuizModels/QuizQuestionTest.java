package QuizModels;

import com.example.thenotelobster.model.QuizClasses.QuizQuestion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuizQuestionTest {

    private static final String QUESTION = "What is 8*8?";
    private static final int ANSWER = 64;

    private QuizQuestion question;

    @BeforeEach
    public void setUp() {
        question = new QuizQuestion(QUESTION, ANSWER);
    }

    @Test
    public void testCheckResponseCorrect() {
        assertTrue(question.checkResponse(64));

    }

    @Test
    public void testCheckResponseWrong()
    {
        assertFalse(question.checkResponse(10));
    }
}


