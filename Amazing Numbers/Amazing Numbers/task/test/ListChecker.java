import util.Checker;
import util.UserProgram;

import java.util.Arrays;
import java.util.Set;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class ListChecker extends Checker {
    private static final Pattern LINE_PATTERN = Pattern.compile(
            "\\s*(?<number>[\\d,. ]*\\d)\\s*(is|:|-)\\s*(?<properties>.+)",
            Pattern.CASE_INSENSITIVE);
    private static final Pattern NON_DIGIT_SYMBOL = Pattern.compile("\\D");
    private static final Pattern PROPERTIES_SEPARATOR = Pattern.compile("[, ;]+");

    private final long expectedCount;
    private final long[] expectedList;

    public ListChecker(Request request) {
        this(request.getStart(), request.getCount(), request.getProperties());
    }

    public ListChecker(long start, long count) {
        this(start, count, new String[0]);
    }

    public ListChecker(long start, long count, String properties) {
        this(start, count, properties.split("[, ]+"));
    }

    public ListChecker(long start, long count, String[] queries) {
        super("The list is incorrect");
        this.validator = this::test;
        this.expectedList = getExpectedList(start, count, queries);
        this.expectedCount = count;
    }

    private static long[] getExpectedList(long start, long count, String[] queries) {
        final var condition = Arrays.stream(queries).map(query -> {
            final var isNegative = query.startsWith("-");
            final var name = isNegative ? query.substring(1) : query;
            final var property = NumberProperty.valueOf(name.toUpperCase());
            return isNegative ? property.negate() : property;
        }).reduce(number -> true, LongPredicate::and);

        return LongStream
                .iterate(start, n -> n > 0, n -> n + 1)
                .filter(condition).limit(count).toArray();
    }

    public boolean test(UserProgram program) {
        final var lines = program.getOutput()
                .lines()
                .filter(Predicate.not(String::isBlank))
                .limit(expectedCount)
                .collect(Collectors.toUnmodifiableList());

        if (lines.size() != expectedCount) {
            return false;
        }

        final var iterator = lines.iterator();
        for (final long expectedNumber : expectedList) {
            final var actualLine = iterator.next();
            final var matcher = LINE_PATTERN.matcher(actualLine);
            if (!matcher.matches()) {
                feedback = "Can''t parse line: \"{0}\". Expected: {1} is ...";
                parameters = new Object[]{actualLine, expectedNumber};
                return false;
            }

            final var rawNumber = matcher.group("number").strip();
            final var actualNumber = NON_DIGIT_SYMBOL.matcher(rawNumber).replaceAll("");

            if (!String.valueOf(expectedNumber).equals(actualNumber)) {
                feedback = "Expected number is {0} but actual number is {1}.";
                parameters = new Object[]{expectedNumber, rawNumber};
                return false;
            }

            final var actualProperties = PROPERTIES_SEPARATOR
                    .splitAsStream(matcher.group("properties").toLowerCase())
                    .collect(Collectors.toUnmodifiableList());

            final var expectedProperties = Arrays
                    .stream(NumberProperty.values())
                    .filter(property -> property.test(expectedNumber))
                    .map(Enum::name)
                    .map(String::toLowerCase)
                    .collect(Collectors.toUnmodifiableSet());

            if (actualProperties.size() != expectedProperties.size()) {
                feedback = "For the number {0} the expected number of properties is {1} but the actual number of properties is {2}. " +
                        "Expected properties are {3}. Actual properties are {4}";
                parameters = new Object[]{expectedNumber, expectedProperties.size(),
                        actualProperties.size(), expectedProperties, actualProperties};
                return false;
            }

            if (!Set.copyOf(actualProperties).equals(expectedProperties)) {
                feedback = "For the number {0} the expected properties are {1}. The actual properties are {2}.";
                parameters = new Object[]{expectedNumber, expectedProperties, actualProperties};
            }
        }
        return true;
    }
}
