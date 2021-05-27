package util;

import java.util.regex.Pattern;

public class RegexChecker extends Checker {
    private final Pattern expected;

    public RegexChecker(final String regexp, final String feedback) {
        this(regexp, Pattern.CASE_INSENSITIVE, feedback);
    }

    public RegexChecker(final String regexp, final int flags, final String feedback) {
        super(feedback);
        this.expected = Pattern.compile(regexp, flags);
        validator = program -> expected.matcher(program.getOutput()).find();
    }

}
