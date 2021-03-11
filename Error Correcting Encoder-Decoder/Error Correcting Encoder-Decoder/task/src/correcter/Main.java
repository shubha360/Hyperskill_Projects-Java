package correcter;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        StringBuilder input = null;

        try (FileWriter writer = new FileWriter("received.txt")) {
            input = new StringBuilder(new String(Files.readAllBytes(Paths.get("send.txt"))));

            input = convertBits(input);
            writer.write(input.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static StringBuilder convertBits(StringBuilder input) {

        StringBuilder convertedString = new StringBuilder(input.length());
        Random random = new Random();

        for (int i = 0; i < input.length(); i++) {

            StringBuilder binary = new StringBuilder(Integer.toBinaryString(input.charAt(i)));

            int target = random.nextInt(binary.length() - 1) + 1;

            binary = binary.charAt(target) == '1' ? binary.replace(target, target + 1, "0") : binary.replace(target, target + 1, "1");

            convertedString.append((char) ((int) Integer.valueOf(binary.toString(), 2)));
        }
        return convertedString;
    }

    static StringBuilder sendOverPoorConnection(StringBuilder input) {

        Random random = new Random();
        int i = 0;

        while (i < input.length()) {

            int j = random.nextInt(3);

            if (i + j > input.length()) {
                break;
            }

            int characterIndex = random.nextInt(63);

            input.replace(i + j, i + j + 1, String.valueOf(characterGenerator(characterIndex)));
            i += 3;
        }
        return input;
    }

    static StringBuilder encode(StringBuilder input) {

        StringBuilder encodedMessage = new StringBuilder(input.length() * 3);

        for (int i = 0; i < input.length(); i++) {
            for (int j = 0; j < 3; j++) {
                encodedMessage.append(input.charAt(i));
            }
        }
        return encodedMessage;
    }

    static StringBuilder decode(StringBuilder input) {

        StringBuilder decodedMessage = new StringBuilder(input.length() / 3);

        for (int i = 0; i < input.length(); i += 3) {

            if (input.charAt(i) == input.charAt(i + 1) || input.charAt(i) == input.charAt(i + 2)) {
                decodedMessage.append(input.charAt(i));
            } else {
                decodedMessage.append(input.charAt(i + 1));
            }
        }
        return decodedMessage;
    }

    static char characterGenerator(int index) {

        if (index < 26) {
            return (char) ('A' + index);
        } else if (index < 52) {
            return (char) ('a' + (index - 26));
        } else if (index == 52) {
            return ' ';
        } else {
            return (char) ('0' + (index - 53));
        }
    }
}
