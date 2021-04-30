package calculator;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {

            String input = scanner.nextLine();

            switch (input) {

                case "/help":
                    System.out.println("The program calculates subtraction or summation of the numbers");
                    break;

                case "":
                    continue;

                case "/exit":
                    System.out.println("Bye!");
                    return;

                default:

                    String[] inputArr = input.split(" ");
                    int sum = 0;

                    if (inputArr.length > 0) {

                        for (String s : inputArr) {
                            sum += Integer.parseInt(s);
                        }
                    }
                    System.out.println();
            }
        }
    }
}
