import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.stage.StageTest;

import java.util.ResourceBundle;
import static java.text.MessageFormat.format;

public abstract class ExtendedTest extends StageTest {
    private static final ResourceBundle messages = ResourceBundle.getBundle("ErrorMessages");

    public static void assertEquals(
            final String expected,
            final String actual,
            final String error,
            final Object... args) {

        if (!expected.equals(actual)) {
            final var feedback = format(messages.getString(error), args);
            throw new WrongAnswer(feedback);
        }
    }
}