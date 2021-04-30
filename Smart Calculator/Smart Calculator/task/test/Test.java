import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestedProgram;

import java.util.Arrays;
import java.util.List;


public class Test extends StageTest<String> {

    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
                new TestCase<String>().setDynamicTesting(() -> {

                    TestedProgram main = new TestedProgram();

                    // The base test suit that checks if the program correctly responses to the commands and can stop
                    main.start();


                    // test of /help
                    String output = main.execute("/help").trim();
                    if (output.length() < 4) {
                        return CheckResult.wrong("It seems like there was no any \"help\" message.");
                    }

                    // input empty string
                    output = main.execute("");
                    if (output.length() != 0) {
                        return CheckResult.wrong("Incorrect response to an empty string. " +
                                "The program should not print anything.");
                    }

                    // test of /exit
                    output = main.execute("/exit").trim().toLowerCase();
                    if (!output.startsWith("bye")) {
                        return CheckResult.wrong("Your program didn't print \"bye\" after entering \"/exit\".");
                    }

                    return new CheckResult(main.isFinished(), "Your program should exit after entering \"/exit\".");
                }),

                new TestCase<String>().setDynamicTesting(() -> {
                    TestedProgram main = new TestedProgram();

                    // The test suit that checks basic functionality of this step
                    main.start();

                    // sum of positive numbers
                    String output = main.execute("7 + 1 + 4").trim();
                    if (!output.equals("12")) {
                        return CheckResult.wrong("The program cannot sum more than two numbers.");
                    }

                    // sum mixed numbers & positive answer
                    output = main.execute("23 - 17 - 4").trim();
                    if (!output.equals("2")) {
                        return CheckResult.wrong("Incorrect sum of positive and negative numbers.");
                    }

                    // sum mixed numbers & negative answer
                    output = main.execute("23 - 17 - 13").trim();
                    if (!output.equals("-7")) {
                        return CheckResult.wrong("Incorrect sum of positive and negative numbers.");
                    }

                    // sum of negative numbers
                    output = main.execute("-9 - 4 - 21").trim();
                    if (!output.equals("-34")) {
                        return CheckResult.wrong("Incorrect sum of three negative numbers.");
                    }

                    // testing a big amount of numbers
                    output = main.execute("33 + 21 + 11 + 49 - 32 - 9 + 1 - 80 + 4").trim();
                    if (!output.equals("-2")) {
                        return CheckResult.wrong("The program cannot process a large number of numbers.");
                    }

                    // input one number
                    output = main.execute("101").trim();
                    if (!output.equals("101")) {
                        return CheckResult.wrong("The program printed not the same number that was entered.");
                    }

                    // input one negative number
                    output = main.execute("-302").trim();
                    if (!output.equals("-302")) {
                        return CheckResult.wrong("The program printed not the same number that was entered.");
                    }

                    // input empty string
                    output = main.execute("");
                    if (output.length() != 0) {
                        return CheckResult.wrong("Incorrect response to an empty string. " +
                                "The program should not print anything.");
                    }

                    // the sum of the numbers is zero
                    output = main.execute("10 - 7 - 3").trim();
                    if (!output.equals("0")) {
                        return CheckResult.wrong("The problem when sum is equal to 0 has occurred.");
                    }

                    // test of /exit
                    output = main.execute("/exit").trim().toLowerCase();
                    if (!output.startsWith("bye")) {
                        return CheckResult.wrong("Your program didn't print \"bye\" after entering \"/exit\".");
                    }

                    return new CheckResult(main.isFinished(), "Your program should exit after entering \"/exit\".");
                }),
                new TestCase<String>().setDynamicTesting(() -> {
                    TestedProgram main = new TestedProgram();

                    // The test suit that checks the usage of several operators
                    main.start();

                    // test of odd number of minus signs
                    String output = main.execute("8 --- 3").trim();
                    if (!output.equals("5")) {
                        return CheckResult.wrong("The program cannot process several minus signs.");
                    }

                    // test of even number of minus signs
                    output = main.execute("8 -- 3").trim();
                    if (!output.equals("11")) {
                        return CheckResult.wrong("The program not correctly processes even number of minus signs.");
                    }

                    // test of several plus signs
                    output = main.execute("32 ++++++++++++++ 4").trim();
                    if (!output.equals("36")) {
                        return CheckResult.wrong("The program cannot process several plus signs.");
                    }

                    // test of multiple operations
                    output = main.execute("5 --- 2 ++++++ 4 -- 2 ---- 1").trim();
                    if (!output.equals("10")) {
                        return CheckResult.wrong("The program cannot process multiple operations with several operators.");
                    }

                    // test of /exit
                    output = main.execute("/exit").trim().toLowerCase();
                    if (!output.startsWith("bye")) {
                        return CheckResult.wrong("Your program didn't print \"bye\" after entering \"/exit\".");
                    }

                    return new CheckResult(main.isFinished(), "Your program should exit after entering \"/exit\".");
                })
        );
    }
}
