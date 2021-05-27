package util;

import static java.util.function.Predicate.not;

public class LinesChecker extends Checker {

    public LinesChecker(final long expected) {
        super("Expected {0} non blank lines but actual output contains {1} lines.");

        validator = program -> {
            final var actual = program.getOutput().lines().filter(not(String::isBlank)).count();
            parameters = new Object[]{expected, actual};
            return actual == expected;
        };
    }


}
