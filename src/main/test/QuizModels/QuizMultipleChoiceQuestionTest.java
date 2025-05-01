package QuizModels;

import com.example.thenotelobster.model.QuizClasses.QuizMultipleChoiceQuestion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuizMultipleChoiceQuestionTest {

    private static final String QUESTION_ONE = "What colour is the sky?";
    private static final String QUESTION_TWO = "What is 3 + 5";
    private static final List<String> CHOICES_ONE =  Arrays.asList("Blue", "Green", "Black", "White");
    private static final List<String> CHOICES_TWO = Arrays.asList("0", "3", "8", "12");
    private static final int ANSWER_ONE = 1;
    private static final int ANSWER_TWO = 3;


    private QuizMultipleChoiceQuestion questionOne;
    private QuizMultipleChoiceQuestion questionTwo;

    @BeforeEach
    public void setUp() {
        questionOne = new QuizMultipleChoiceQuestion(QUESTION_ONE, ANSWER_ONE, CHOICES_ONE);
        questionTwo = new QuizMultipleChoiceQuestion(QUESTION_TWO, ANSWER_TWO, CHOICES_TWO);
    }

    @Test
    public void testCheckResponse() {
        assertTrue(questionOne.checkResponse(1));
        assertFalse(questionTwo.checkResponse(4));
    }




}