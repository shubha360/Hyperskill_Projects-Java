import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.TestCase;

import java.io.*;
import java.util.List;

class TestClue {
    String input;

    TestClue(String input) {
        this.input = input;
    }
}

public class CorrecterTest extends StageTest<TestClue> {

    public static File received = null;

    @Override
    public List<TestCase<TestClue>> generate() {
        TestClue firstTestClue = new TestClue("Eat more of these french buns!");
        TestClue secondTestClue = new TestClue("$ome rand0m messAge");
        TestClue thirdTestClue = new TestClue("better call Saul 555-00-73!");
        TestClue sixthTestClue = new TestClue("5548172 6548 225147 23656595 5155");

        return List.of(
            new TestCase<TestClue>()
                .setAttach(firstTestClue)
                .addFile("send.txt", firstTestClue.input),

            new TestCase<TestClue>()
                .setAttach(secondTestClue)
                .addFile("send.txt", secondTestClue.input),

            new TestCase<TestClue>()
                .setAttach(thirdTestClue)
                .addFile("send.txt", thirdTestClue.input),

            new TestCase<TestClue>()
                .setAttach(sixthTestClue)
                .addFile("send.txt", sixthTestClue.input)
        );
    }

    @Override
    public CheckResult check(String reply, TestClue clue) {
        String path = System.getProperty("user.dir");
        searchFile("received.txt");

        if (received == null) {
            return new CheckResult(false,
                "Can't find received.txt file. " +
                    "Make sure your program writes it down or " +
                    "make sure the name of the file is correct.");
        }

        byte[] receivedContent;

        FileInputStream stream;
        try {
            stream = new FileInputStream(received);
        } catch (FileNotFoundException e) {
            return new CheckResult(false,
                "Can't find received.txt file. " +
                    "Make sure your program writes it down " +
                    "or make sure the name of the file is correct.");
        }

        try {
            receivedContent = stream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException("Can't read the file");
        }

        String correctBinary = toBinary(clue.input.getBytes());
        String outputBinary = toBinary(receivedContent);

        return checkMatches(outputBinary, correctBinary);
    }

    private static String toBinary(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * Byte.SIZE);
        for (int i = 0; i < Byte.SIZE * bytes.length; i++) {
            sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
        }
        return sb.toString();
    }

    private static byte[] fromBinary(String s) {
        int sLen = s.length();
        byte[] toReturn = new byte[(sLen + Byte.SIZE - 1) / Byte.SIZE];
        char c;
        for (int i = 0; i < sLen; i++)
            if ((c = s.charAt(i)) == '1')
                toReturn[i / Byte.SIZE] = (byte) (toReturn[i / Byte.SIZE] | (0x80 >>> (i % Byte.SIZE)));
            else if (c != '0')
                throw new IllegalArgumentException();
        return toReturn;
    }

    private CheckResult checkMatches(String output, String correct) {
        if (output.isEmpty() && correct.isEmpty()) return CheckResult.correct();

        if (output.length() != correct.length()) {
            return new CheckResult(false,
                "The program was expected to output " +
                    correct.length() / 8 +
                    " bytes, but output " +
                    output.length() / 8);
        }

        for (int i = 0; i < output.length(); i += 8) {
            String currOutputByte = output.substring(i, i+8);
            String currCorrectByte = correct.substring(i, i+8);

            int difference = 0;
            for (int j = 0; j < currCorrectByte.length(); j++) {
                char currOutputBit = currOutputByte.charAt(j);
                char currCorrectBit = currCorrectByte.charAt(j);

                if (currCorrectBit != currOutputBit) {
                    difference++;
                }
            }

            if (difference == 0) {
                return new CheckResult(false,
                    "One of bytes from the input stayed the same but should be changed");
            }

            if (difference != 1) {
                return new CheckResult(false,
                    "One of bytes from the input was changes in more than one bit");
            }
        }

        return CheckResult.correct();
    }

    public static void searchFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            received = file;
        }
    }
}
