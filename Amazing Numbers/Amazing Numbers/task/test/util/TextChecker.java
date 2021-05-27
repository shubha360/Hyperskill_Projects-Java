package util;

public class TextChecker extends Checker {

    public TextChecker(String expected) {
        this(expected, "Expected that the output contains \"{2}\".");
    }

    public TextChecker(String expected, String feedback) {
        super(feedback);

        validator = program -> {
            parameters = new Object[]{program.getInput(), program.getOutput(), expected};
            return program.getOutput().toLowerCase().contains(expected.toLowerCase());
        };
    }

}
