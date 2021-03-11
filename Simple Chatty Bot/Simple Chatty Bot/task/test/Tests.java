import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import java.util.List;


class Clue {
    int age;
    String name;
    int count;

    Clue(String name, int age, int count) {
        this.age = age;
        this.name = name;
        this.count = count;
    }
}


public class Tests extends StageTest<Clue> {

    @Override
    public List<TestCase<Clue>> generate() {
        String input = "Marry\n1\n0\n5\n10";

        for (int i = 1; i < 9; i++) {
            input += "\n" + i;
        }

        return List.of(
            new TestCase<Clue>()
                .setInput(input)
                .setAttach(new Clue("Marry", 40, 10))
        );
    }

    @Override
    public CheckResult check(String reply, Clue clue) {

        String[] lines = reply.trim().split("\n");

        int length = 9 + clue.count + 1;

        if (lines.length <= length) {
            return CheckResult.wrong(
                "You should output at least " + (length + 1) + " lines " +
                    "(for the count number " + clue.count +").\n" +
                    "Lines found: " + lines.length + "\n" +
                    "Your output:\n" +
                    reply
            );
        }

        String lineWithName = lines[3].toLowerCase();
        String name = clue.name.toLowerCase();

        if (!lineWithName.contains(name)) {
            return CheckResult.wrong(
                "The name was " + clue.name + "\n" +
                    "But the 4-th line was:\n" +
                    "\"" + lines[3] + "\"\n\n" +
                    "4-th line should contain a name of the user"
            );
        }

        String lineWithAge = lines[6].toLowerCase();
        String age = Integer.toString(clue.age);

        if (!lineWithAge.contains(age)) {
            return CheckResult.wrong(
                "Can't find a correct age " +
                    "in the last line of output! " +
                    "Maybe you calculated the age wrong?\n\n" +
                    "Your last line: \n" + "\"" + lines[6] + "\""
            );
        }

        for (int i = 0; i < clue.count + 1; i++) {
            String numLine = lines[i + 8];
            String actualNum = i + "!";

            if (!numLine.equals(actualNum)) {
                return CheckResult.wrong(
                    "Expected " + (i+8) + "-th line: \n" +
                        "\"" + actualNum + "\"\n" +
                        "Your "+ (i+8) + "-th line: \n" +
                        "\"" + numLine + "\""
                );
            }
        }

        String lastLine = lines[lines.length - 1];

        if (!lastLine.equals("Congratulations, have a nice day!")) {
            return CheckResult.wrong(
                "Your last line should be:\n" +
                    "\"Congratulations, have a nice day!\"\n" +
                    "Found:\n" +
                    "\"" + lastLine + "\""
            );
        }

        return CheckResult.correct();
    }

}
