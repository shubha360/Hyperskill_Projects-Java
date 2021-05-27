package util;

import org.hyperskill.hstest.exception.outcomes.WrongAnswer;

import java.text.MessageFormat;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import static java.util.Objects.isNull;

public class Checker implements UnaryOperator<UserProgram> {
    protected Predicate<UserProgram> validator;
    protected String feedback;
    protected Object[] parameters;

    protected Checker() {
        this("Incorrect output for user input: {0}.");
    }

    protected Checker(String feedback) {
        this($ -> true, feedback);
    }

    public Checker(Predicate<UserProgram> validator, String feedback) {
        this.feedback = feedback;
        this.validator = validator;
    }

    @Override
    public UserProgram apply(UserProgram program) {
        if (validator.test(program)) {
            return program;
        }
        throw new WrongAnswer(MessageFormat.format(feedback, isNull(parameters)
                ? new Object[]{program.getInput(), program.getOutput()} : parameters));

    }

}
