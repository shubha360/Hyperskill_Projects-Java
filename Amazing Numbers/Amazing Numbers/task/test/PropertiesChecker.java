import util.Checker;
import util.UserProgram;

public class PropertiesChecker extends Checker {
    private final long number;

    public PropertiesChecker(long number) {
        this.number = number;
        validator = this::test;
    }

    public boolean test(UserProgram program) {
        for (var property : NumberProperty.values()) {
            final var name = property.name();

            if (!program.getOutput().toLowerCase().contains(name.toLowerCase())) {
                feedback = "The property \"{0}\" was not found in the output.";
                parameters = new Object[]{name};
                return false;
            }

            final var expected = property.test(number);
            final var actualValue = property.extractValue(program.getOutput());

            if (actualValue.isEmpty()) {
                feedback = "The value for property {0} was not found. Expected: {1}";
                parameters = new Object[]{name, expected};
                return false;
            }
            final var actual = actualValue.get();

            if (expected != actual) {
                feedback = "For the property {0}, the expected value is {1} but was found {2}.";
                parameters = new Object[]{name, expected, actual};
                return false;
            }
        }
        return true;
    }
}
