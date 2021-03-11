import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.abs;


class TestClue {

    String feedback;
    ArrayList<Double> answers;

    TestClue(String feedback, Double[] answers) {
        this.feedback = feedback;
        this.answers = new ArrayList<>(Arrays.asList(answers));
    }
}

public class NumericMatrixProcessorTest extends StageTest<TestClue> {

    @Override
    public List<TestCase<TestClue>> generate() {
        return List.of(
            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "Checking if matrices adding algorithm is correct",
                    new Double[]{
                        7d, 13d, 132d,
                        17d, 23d, 45d,
                        65d, 57d, 78d
                    }))
                .setInput(
                    "1\n"+
                        "3 3\n" +
                        "3 4 55\n" +
                        "4 1 1\n" +
                        "9 0 0\n" +
                        "3 3\n" +
                        "4 9 77\n" +
                        "13 22 44\n" +
                        "56 57 78\n" +
                        "0"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "Checking if matrices adding algorithm is correct",
                    new Double[]{
                        43.13d, 911.62d, 1311.8d, 136.53d,
                        -4.2d, 141.1d, -1.09d, 1421.6d,
                        230.33d, 114.2d, 100.7d, 990.4d,
                        956.72d, 168.94d, 112.16d, 27.0d
                    }))
                .setInput(
                    "1\n"+
                        "4 4\n" +
                        "-0.3 677.4 435.2 123.33\n" +
                        "1.3 141.4 0.11 1411.4\n" +
                        "231.33 113.4 99.9 999.9\n" +
                        "1002.22 123.44 55.66 13.3\n" +
                        "4 4\n" +
                        "43.43 234.22 876.6 13.2\n" +
                        "-5.5 -0.3 -1.2 10.2\n" +
                        "-1.0 0.8 0.8 -9.5\n" +
                        "-45.5 45.5 56.5 13.7\n" +
                        "0"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "Checking if multiplication matrix on constant algorithm is correct",
                    new Double[]{
                        187d, 3978d, 7548d,
                        7752d, 5831d, 3774d,
                        16949d, 7752d, 7752d
                    }))
                .setInput(
                    "2\n" +
                        "3 3\n" +
                        "11 234 444\n" +
                        "456 343 222\n" +
                        "997 456 456\n" +
                        "17\n" +
                        "0"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "Checking if multiplication matrix on constant algorithm is correct",
                    new Double[]{
                        1123653d, 5933505d, 110927295d, 2365632d,
                        1505049d, 8619594d, 25665531d, 3833274d,
                        1366632d, 2598732d ,37999962d, 58303305d,
                        11068143d, 9781653d ,13666653d ,13443321d
                    }))
                .setInput(
                    "2\n" +
                        "4 4\n" +
                        "10123 53455 999345 21312\n" +
                        "13559 77654 231221 34534\n" +
                        "12312 23412 342342 525255\n" +
                        "99713 88123 123123 121111\n" +
                        "111\n" +
                        "0"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "Checking if matrices multiplication algorithm is correct",
                    new Double[]{
                        45d, 113d, 11d, 266d,
                        84d, 139d, 29d, 229d,
                        45d, 49d, 35d, 100d,
                        15d, 86d, 0d, 281d ,
                    }))
                .setInput(
                    "3\n" +
                        "4 4\n" +
                        "1 2 2 7\n" +
                        "3 3 4 5\n" +
                        "5 0 0 1\n" +
                        "0 1 0 8\n" +
                        "4 4\n" +
                        "9 8 7 13\n" +
                        "15 14 0 1\n" +
                        "3 7 2 3\n" +
                        "0 9 0 35\n" +
                        "0"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "Checking if matrices multiplication algorithm is correct",
                    new Double[]{
                        243d, 295d, 78d, 60d,
                        724d, 798d, 2063d, 175d
                    }))
                .setInput(
                    "3\n" +
                        "2 3\n" +
                        "1 0 17\n" +
                        "15 19 7\n" +
                        "3 4\n" +
                        "5 6 78 9\n" +
                        "29 31 47 1\n" +
                        "14 17 0 3\n" +
                        "0"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "Checking if matrices multiplication algorithm is correct",
                    new Double[]{
                        100d, 105d,
                        130d, 43d,
                        48d, 53d
                    }))
                .setInput(
                    "3\n" +
                        "3 5\n" +
                        "1 4 5 6 6\n" +
                        "7 8 9 0 0\n" +
                        "4 1 2 2 2\n" +
                        "5 2\n" +
                        "4 5\n" +
                        "6 1\n" +
                        "6 0\n" +
                        "0 9\n" +
                        "7 7\n" +
                        "0"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "Checking if matrices multiplication algorithm is correct",
                    new Double[]{
                        -3728.685d, 3752.621d, 4367.396d, 1074.785d,
                        246.11d, -228.39d, -396.2d, 29.71d,
                        73.32d, -67.679d, -87.43, 25.04d,
                        -39.22d, 42.02d, 55.6d, -119.42d
                    }))
                .setInput(
                    "3\n" +
                        "4 4\n" +
                        "0.65 0.67 76.4 23.2\n" +
                        "-0.7 -13.1 -7.2 9.2\n" +
                        "-0.7 -5.5 -1.5 0.4\n" +
                        "-1.0 12.6 0.8 -0.4\n" +
                        "4 4\n" +
                        "-5.5 -0.3 -1.2 10.2\n" +
                        "-1.0 0.8 0.8 -9.5\n" +
                        "-45.5 45.5 56.5 13.7\n" +
                        "-10.7 11.9 2.2 1.2\n" +
                        "0"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "Checking if you can do more than one operation in a loop",
                    new Double[]{
                        -3728.685d, 3752.621d, 4367.396d, 1074.785d,
                        246.11d, -228.39d, -396.2d, 29.71d,
                        73.32d, -67.679d, -87.43, 25.04d,
                        -39.22d, 42.02d, 55.6d, -119.42d,
                        100d, 105d,
                        130d, 43d,
                        48d, 53d
                    }))
                .setInput(
                    "3\n" +
                        "4 4\n" +
                        "0.65 0.67 76.4 23.2\n" +
                        "-0.7 -13.1 -7.2 9.2\n" +
                        "-0.7 -5.5 -1.5 0.4\n" +
                        "-1.0 12.6 0.8 -0.4\n" +
                        "4 4\n" +
                        "-5.5 -0.3 -1.2 10.2\n" +
                        "-1.0 0.8 0.8 -9.5\n" +
                        "-45.5 45.5 56.5 13.7\n" +
                        "-10.7 11.9 2.2 1.2\n" +
                        "3\n" +
                        "3 5\n" +
                        "1 4 5 6 6\n" +
                        "7 8 9 0 0\n" +
                        "4 1 2 2 2\n" +
                        "5 2\n" +
                        "4 5\n" +
                        "6 1\n" +
                        "6 0\n" +
                        "0 9\n" +
                        "7 7\n" +
                        "0"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "Checking if diagonal transposition algorithm is correct",
                    new Double[]{
                        1d, 6d, 4d,
                        7d, 6d, 2d,
                        7d, 4d, 1d,
                    }))
                .setInput(
                    "4\n" +
                        "1\n" +
                        "3 3\n" +
                        "1 7 7\n" +
                        "6 6 4\n" +
                        "4 2 1\n" +
                        "0"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "Checking if diagonal transposition algorithm is correct",
                    new Double[]{
                        1d, 6d, 4d,
                        7d, 6d, 5d,
                        7d, 4d, 1d,
                    }))
                .setInput(
                    "4\n" +
                        "1\n" +
                        "3 3\n" +
                        "1 7 7\n" +
                        "6 6 4\n" +
                        "4 5 1\n" +
                        "0"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "Checking if diagonal transposition algorithm is correct",
                    new Double[]{
                        1d, 6d, 4d,
                        7d, 6d, 2d,
                        7d, 4d, 1d,
                    }))
                .setInput(
                    "4\n" +
                        "2\n" +
                        "3 3\n" +
                        "1 2 4\n" +
                        "4 6 6\n" +
                        "7 7 1\n" +
                        "0"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "Checking if diagonal transposition algorithm is correct",
                    new Double[]{
                        1d, 6d, 4d,
                        7d, 6d, 5d,
                        7d, 4d, 1d,
                    }))
                .setInput(
                    "4\n" +
                        "2\n" +
                        "3 3\n" +
                        "1 5 4\n" +
                        "4 6 6\n" +
                        "7 7 1.0\n" +
                        "0"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "Checking if transposition algorithm is correct",
                    new Double[]{
                        2d, 4d, 5d, 6d,
                        6d, 6d, 7d, 8d,
                        5d, 0d, 0d, 1d,
                        8d, 8d, 2d, 9d
                    }))
                .setInput(
                    "4\n" +
                        "3\n" +
                        "4 4\n" +
                        "6 5 4 2\n" +
                        "8 7 6 6\n" +
                        "1 0 0 5.0\n" +
                        "9 2 8 8\n" +
                        "0"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "Checking if transposition algorithm is correct",
                    new Double[]{
                        2d, 4d, 5d, 6d,
                        6d, 6d, 7d, 8d,
                        5d, 0d, 0d, 1d,
                        8d, 8d, 2d, 9d
                    }))
                .setInput(
                    "4\n" +
                        "4\n" +
                        "4 4\n" +
                        "8 8 2 9\n" +
                        "5 0 0 1\n" +
                        "6 6 7 8.0\n" +
                        "2 4 5 6\n" +
                        "0"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "Checking if determinant algorithm is correct",
                    new Double[]{
                        31d
                    }))
                .setInput(
                    "5\n" +
                        "3 3\n" +
                        "1 2 3\n" +
                        "4 5 7\n" +
                        "10 22 23\n" +
                        "0"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "Checking if determinant algorithm is correct",
                    new Double[]{
                        45.2197d
                    }))
                .setInput(
                    "5\n" +
                        "4 4\n" +
                        "2.65 3.54 3.88 8.99\n" +
                        "3.12 5.45 7.77 5.56\n" +
                        "5.31 2.23 2.33 9.81\n" +
                        "1.67 1.67 1.01 9.99\n" +
                        "0"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "Checking if inversion algorithm is correct",
                    new Double[]{
                        1.14717, 2.03717, 2.9711,
                        2.19055, 4.52055, 7.20788,
                        3.67009, 0.590087, 1.33819
                    }))
                .setInput(
                    "6\n" +
                        "3 3\n" +
                        "0.396796 -0.214938 0.276735\n" +
                        "5.19655 -2.06983 -0.388886\n" +
                        "-3.3797 1.50219 0.159794\n" +
                        "0"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "Checking if inversion algorithm is correct",
                    new Double[]{
                        0.396796, -0.214938, 0.276735, -0.5092,
                        5.19655, -2.06983, -0.388886, -3.14252,
                        -3.3797, 1.50219, 0.159794, 2.04842,
                        -0.593332, 0.230065, 0.00259267, 0.50345
                    }))
                .setInput(
                    "6\n" +
                        "4 4\n" +
                        "2.65 3.54 3.88 8.99\n" +
                        "3.12 5.45 7.77 5.56\n" +
                        "5.31 2.23 2.33 9.81\n" +
                        "1.67 1.67 1.01 9.99\n" +
                        "0")
        );
    }


    @Override
    public CheckResult check(String reply, TestClue clue) {

        try {

            String[] strNumbers = reply.lines()
                .filter(line -> {
                    line = line.strip();
                    if (line.length() == 0) {
                        return false;
                    }
                    for (char c : line.toCharArray()) {
                        if (!(c == ' ' ||
                            c >= '0' && c <= '9' ||
                            c == '.' ||
                            c == '-' || c == '+' ||
                            c == 'e' || c == 'E')) {
                            return false;
                        }
                    }
                    return true;
                }).reduce("", (a, b) -> a + " " + b)
                .strip().split("\\s+");

            double[] actual = Arrays.stream(strNumbers).mapToDouble(Double::parseDouble).toArray();

            double[] expected =
                clue.answers.stream()
                    .mapToDouble(e -> e)
                    .toArray();
            if (actual.length != expected.length) {
                return new CheckResult(false, clue.feedback);
            }

            for (int i = 0; i < actual.length; i++) {
                if (abs(actual[i] - expected[i]) > 0.01) {
                    return new CheckResult(false, clue.feedback);
                }
            }
        }
        catch (Exception ex) {
            return new CheckResult(false, "Can't check the answer");
        }

        return CheckResult.correct();

    }

}
