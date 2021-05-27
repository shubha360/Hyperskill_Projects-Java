package util;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.util.function.Function;

public class UserProgram {
    private TestedProgram program;

    private Object input;
    private String output;

    public UserProgram start(String... args) {
        program = new TestedProgram();
        output = program.start(args);
        return this;
    }

    public UserProgram check(final Function<UserProgram, UserProgram> checker) {
        return checker.apply(this);
    }

    public CheckResult result() {
        return CheckResult.correct();
    }

    public UserProgram execute(Object userInput) {
        this.input = userInput;
        output = program.execute(userInput.toString());
        return this;
    }

    public String getOutput() {
        return output;
    }

    public Object getInput() {
        return input;
    }

    public boolean isFinished() {
        return program.isFinished();
    }
}