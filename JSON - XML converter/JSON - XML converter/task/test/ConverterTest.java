import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.util.regex.Pattern;

public class ConverterTest extends ExtendedTest {
    private static final Pattern SPACES = Pattern.compile("\\s+");

    final String[][] clues = new String[][]{
            {"<host>127.0.0.1</host>", "{\"host\":\"127.0.0.1\"}"},
            {"{\"host\":\"127.0.0.1\"}", "<host>127.0.0.1</host>"},
            {"<pizza>slice</pizza>", "{\"pizza\":\"slice\"}"},
            {"{\"pizza\":\"slice\"}", "<pizza>slice</pizza>"},
            {"<success/>", "{\"success\":null}"},
            {"{\"success\":null}", "<success/>"},
            {"{\"jdk\":\"1.8.9\"}", "<jdk>1.8.9</jdk>"},
            {"<jdk>1.8.9</jdk>", "{\"jdk\":\"1.8.9\"}"},
            {"<qwerty/>", "{\"qwerty\":null}"},
            {"{\"qwerty\":null}", "<qwerty/>"}
    };

    @DynamicTest(data = "clues")
    CheckResult simpleTest(final String input, final String expected) {
        final var program = new TestedProgram();
        program.start();

        final var actual = SPACES.matcher(program.execute(input)).replaceAll("");

        assertEquals(expected, actual, "feedback", input, expected, actual);

        return CheckResult.correct();
    }
}