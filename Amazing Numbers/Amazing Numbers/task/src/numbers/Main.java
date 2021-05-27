package numbers;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Welcome to Amazing Numbers!\n");
        showInstructions();

        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("Enter a request:");
            String input = scanner.nextLine();
            System.out.println();

            if (input.equals("0")) {
                System.out.println("Goodbye!");
                break;
            }

            if (input.length() == 0) {
                showInstructions();
                continue;
            }

            long inputNumber;
            int limit;

            if (!input.contains(" ")) {

                try {
                    inputNumber = Long.parseLong(input);
                } catch (NumberFormatException e) {
                    System.out.println("The first parameter should be a natural number or zero\n");
                    continue;
                }

                if (inputNumber < 0) {
                    System.out.println("The first parameter should be a natural number or zero.\n");
                    continue;
                }

                OldStyledNumbers numbers = new OldStyledNumbers(inputNumber);
                numbers.printProperties();
            } else {

                try {
                    inputNumber = Long.parseLong(input.substring(0, input.indexOf(' ')));
                } catch (NumberFormatException e) {
                    System.out.println("The first parameter should be a natural number or zero\n");
                    continue;
                }

                if (inputNumber < 0) {
                    System.out.println("The first parameter should be a natural number or zero.\n");
                    continue;
                }

                try {
                    limit = Integer.parseInt(input.substring(input.indexOf(' ') + 1));
                } catch (NumberFormatException e) {
                    System.out.println("The second parameter should be a natural number.\n");
                    continue;
                }

                if (limit < 1) {
                    System.out.println("The second parameter should be a natural number.\n");
                    continue;
                }

                ModernStyledNumbers numbers = new ModernStyledNumbers(inputNumber, limit);
                numbers.printProperties();
            }
        }
    }

    static void showInstructions() {
        System.out.println(
                "Supported requests:\n" +
                        "- enter a natural number to know its properties;\n" +
                        "- enter two natural numbers to obtain the properties of the list:\n" +
                        "  * the first parameter represents a starting number;\n" +
                        "  * the second parameter shows how many consecutive numbers are to be printed;\n" +
                        "- separate the parameters with one space;\n" +
                        "- enter 0 to exit.\n");
    }
}
