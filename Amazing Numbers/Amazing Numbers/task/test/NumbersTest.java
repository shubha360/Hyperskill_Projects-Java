import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import util.*;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.LongStream;

public final class NumbersTest extends StageTest {
    private static final Random random = new Random();

    private static final int NEGATIVE_NUMBERS_TESTS = 5;
    private static final int FIRST_NUMBERS = 15;
    private static final int RANDOM_TESTS = 10;
    private static final int MAX_COUNT = 20;
    private static final int MIN_START = 2;

    private static final Checker WELCOME = new TextChecker("Welcome to Amazing Numbers!");

    private static final String EXPLAIN = "The program should explain this in the help.";
    private static final Function<UserProgram, UserProgram> HELP =
            new TextChecker("Supported requests")
                    .andThen(new RegexChecker(
                            "(one|a) natural number",
                            "In this stage, a user can enter one number to print a card. " + EXPLAIN))
                    .andThen(new TextChecker(
                            "two natural numbers",
                            "In this stage, a user can enter two numbers to print a list. " + EXPLAIN))
                    .andThen(new TextChecker(
                            "property to search for",
                            "In this stage, a user can enter two numbers and a property to search for. "
                                    + EXPLAIN))
                    .andThen(new TextChecker(
                            "enter 0 to exit",
                            "Display the instructions on how to exit"));

    private static final Checker ASK_REQUEST = new TextChecker(
            "enter a request",
            "The program should ask a user to enter a request."
    );
    private static final Checker ERROR_FIRST = new RegexChecker(
            "The first (parameter|number) should be a natural number or zero",
            "The first parameter \"{0}\" is wrong. The program should print an error message."
    );
    private static final Checker ERROR_SECOND = new RegexChecker(
            "The second (parameter|number) should be a natural number",
            "The second parameter \"{0}\" is wrong. The program should print an error message."
    );
    private static final Checker ERROR_PROPERTY = new RegexChecker(
            "The property .+ is wrong",
            "The request: \"{0}\" has one wrong property. "
                    + "Expected message: \"property ... is wrong\"."
    );
    private static final Checker HELP_PROPERTIES = new TextChecker(
            "Available properties"
    );
    private static final Checker LIST_PROPERTIES = new Checker(
            program -> Arrays.stream(NumberProperty.values())
                    .map(Enum::name)
                    .map("(?i)\\b"::concat)
                    .map(Pattern::compile)
                    .map(p -> p.matcher(program.getOutput()))
                    .allMatch(Matcher::find),
            "If incorrect property has been specified, show the list of the available properties."
    );
    private static final Checker PROPERTIES_OF = new RegexChecker(
            "properties of \\d",
            "The first line of number''s properties should contain \"Properties of {0}\"."
    );
    private static final Checker RUNNING = new Checker(Predicate.not(UserProgram::isFinished),
            "The program should continue to work till the user enter \"0\"."
    );
    private static final Checker FINISHED = new Checker(UserProgram::isFinished,
            "The program should finish when the user entered \"0\"."
    );
    private final UserProgram program = new UserProgram();

    private final String[] wrongProperty = new String[]{
            "1 10 gay", "40 2 bay", "37 4 8", "67 2 +y-", "2 54 Prime", "6 8 ...", "5 9 ,"
    };

    // Stage #3

    @DynamicTest(order = 5)
    CheckResult welcomeTest() {
        return program
                .start()
                .check(WELCOME)
                .check(HELP)
                .check(RUNNING)
                .check(ASK_REQUEST)
                .execute(0)
                .check(FINISHED)
                .result();
    }

    @DynamicTest(repeat = NEGATIVE_NUMBERS_TESTS, order = 10)
    CheckResult notNaturalNumbersTest() {
        long negativeNumber = -random.nextInt(Byte.MAX_VALUE) - 1L;
        return program
                .start()
                .check(WELCOME)
                .check(HELP)
                .check(ASK_REQUEST)
                .execute(negativeNumber)
                .check(ERROR_FIRST)
                .check(RUNNING)
                .check(ASK_REQUEST)
                .execute(0)
                .check(FINISHED)
                .result();
    }

    @DynamicTest(repeat = RANDOM_TESTS, order = 15)
    CheckResult notNaturalSecondNumberTest() {
        int first = 1 + random.nextInt(Short.MAX_VALUE);
        int negativeSecond = -random.nextInt(Short.MAX_VALUE);
        return program
                .start()
                .check(WELCOME)
                .check(HELP)
                .check(ASK_REQUEST)
                .execute(first + " " + negativeSecond)
                .check(ERROR_SECOND)
                .check(RUNNING)
                .check(ASK_REQUEST)
                .execute(0)
                .check(FINISHED)
                .result();
    }

    // Stage #4

    @DynamicTest(order = 20)
    CheckResult naturalNumbersTest() {
        final var numbers = LongStream.concat(
                LongStream.range(1, FIRST_NUMBERS),
                random.longs(RANDOM_TESTS, 1, Long.MAX_VALUE)
        );

        program.start().check(WELCOME).check(HELP);

        numbers.forEach(number -> program
                .check(ASK_REQUEST)
                .execute(number)
                .check(PROPERTIES_OF)
                .check(new PropertiesChecker(number))
                .check(RUNNING));

        return program
                .check(RUNNING)
                .check(ASK_REQUEST)
                .execute(0)
                .check(FINISHED)
                .result();
    }

    @DynamicTest(order = 40)
    CheckResult firstNumbersListTest() {
        return program
                .start()
                .check(WELCOME)
                .check(HELP)
                .check(ASK_REQUEST)
                .execute("1 " + FIRST_NUMBERS)
                .check(new LinesChecker(FIRST_NUMBERS + 1))
                .check(new ListChecker(1, FIRST_NUMBERS))
                .execute(0)
                .check(FINISHED)
                .result();
    }

    private Object[][] getRandomTwo() {
        return random
                .longs(RANDOM_TESTS, MIN_START, Long.MAX_VALUE - MAX_COUNT)
                .mapToObj(start -> new Long[]{start, (long) 1 + random.nextInt(MAX_COUNT)})
                .toArray(Long[][]::new);
    }

    // Stage #5

    @DynamicTest(data = "getRandomTwo", order = 44)
    CheckResult twoRandomNumbersTest(long start, long count) {
        return program
                .start()
                .check(WELCOME)
                .check(HELP)
                .check(ASK_REQUEST)
                .execute(start + " " + count)
                .check(new LinesChecker(count + 1))
                .check(new ListChecker(start, count))
                .check(RUNNING)
                .execute(0)
                .check(FINISHED)
                .result();
    }

    @DynamicTest(data = "wrongProperty", order = 50)
    CheckResult wrongPropertyRequestTest(String wrongProperty) {
        return program
                .start()
                .check(WELCOME)
                .check(HELP)
                .check(ASK_REQUEST)
                .execute(wrongProperty)
                .check(ERROR_PROPERTY)
                .check(HELP_PROPERTIES)
                .check(LIST_PROPERTIES)
                .check(RUNNING)
                .check(ASK_REQUEST)
                .execute(0)
                .check(FINISHED)
                .result();
    }

    // The test generates and checks request "1 10 <property>" for each property

    @DynamicTest(order = 53)
    CheckResult allPropertiesTest() {
        program.start().check(WELCOME).check(HELP);

        Arrays.stream(NumberProperty.values())
                .map(Enum::name)
                .map("1 10 "::concat)
                .map(Request::new)
                .peek(program.check(ASK_REQUEST)::execute)
                .forEach(request -> program
                        .check(request.getLinesChecker())
                        .check(new ListChecker(request))
                        .check(RUNNING)
                );

        return program.execute(0).check(FINISHED).result();
    }

    @DynamicTest(repeat = RANDOM_TESTS, order = 55)
    CheckResult randomTwoNumbersAndPropertyTest() {
        final var request = Request.random(Request.Parameter.THREE);
        return program
                .start()
                .check(WELCOME)
                .check(HELP)
                .check(ASK_REQUEST)
                .execute(request)
                .check(request.getLinesChecker())
                .check(new ListChecker(request))
                .check(RUNNING)
                .check(ASK_REQUEST)
                .execute(0)
                .check(FINISHED)
                .result();
    }

}
