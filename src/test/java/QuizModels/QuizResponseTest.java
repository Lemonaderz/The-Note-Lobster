package QuizModels;

import com.example.thenotelobster.model.QuizClasses.QuizMultipleChoiceQuestion;
import com.example.thenotelobster.model.QuizClasses.QuizResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuizResponseTest {


    private static final String QUESTION_ONE = "What colour is the sky?";
    private static final String QUESTION_TWO = "What is 3 + 5";
    private static final List<String> CHOICES_ONE =  Arrays.asList("Blue", "Green", "Black", "White");
    private static final List<String> CHOICES_TWO = Arrays.asList("0", "3", "8", "12");
    private static final String ANSWER_ONE = "A";
    private static final String ANSWER_TWO = "C";

    QuizResponse response;

    @BeforeEach
    public void setUp() {

        QuizMultipleChoiceQuestion questionOne= new QuizMultipleChoiceQuestion(QUESTION_ONE, ANSWER_ONE, CHOICES_ONE);
        QuizMultipleChoiceQuestion  questionTwo = new QuizMultipleChoiceQuestion(QUESTION_TWO, ANSWER_TWO, CHOICES_TWO);
        response = new QuizResponse("Basic Knowledge", "This Quiz Tests your basic knowledge", Arrays.asList(questionOne, questionTwo));
    }

    @Test
    public void testDisplayQuiz() {
        String QuizText = response.displayQuiz();
        assertEquals("Basic Knowledge\n" +
                "\n" +
                "This Quiz Tests your basic knowledge\n" +
                "\n" +
                "\n" +
                "What colour is the sky?\n" +
                "Blue\n" +
                "Green\n" +
                "Black\n" +
                "White\n" +
                "Answer:  A\n" +
                "\n" +
                "What is 3 + 5\n" +
                "0\n" +
                "3\n" +
                "8\n" +
                "12\n" +
                "Answer:  C\n", QuizText);

    }
}
