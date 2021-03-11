package bullscows;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Input the length of the secret code:");

        String strLength;
        int length = 0;

        strLength = sc.nextLine();

        try {
            length = Integer.parseInt(strLength);
        } catch (NumberFormatException e) {
            System.out.println("Error: \"" + strLength + "\" isn't a valid number.");
        }
        if (length < 1 || length > 36) {
            System.out.println("Error: " + strLength + " isn't a valid length." + " Length should be between 1 - 36");
            return;
        }

        System.out.println("Input the number of possible symbols in the code:");

        String strSize;
        int size = 0;

        strSize = sc.nextLine();

        try {
            size = Integer.parseInt(strSize);
        } catch (NumberFormatException e) {
            System.out.println("Error: \"" + strSize + "\" isn't a valid number.");
            return;
        }

        if (size < length) {
            System.out.println("Error: it's not possible to generate a code with a length of " +
                    length + " with " + size + " unique symbols.");
            return;
        } else if (size > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            return;
        }

        String secretCode = generateRandomNumber(length,size);

        System.out.print("The secret is prepared: ");

        for (int i = 0; i < length; i++) {
            System.out.print("*");
        }

        if (size < 11) {
            System.out.print(" (0-" + (size - 1) + ").");
        } else {
            System.out.print(" (0-9, a-" + (char) ('a' + (size - 11)) + ").");
        }

        System.out.println();

        System.out.println("Okay, let's start a game!");

        int turn = 1;

        while (true) {

            int bulls = 0;
            int cows = 0;

            System.out.println("Turn " + turn++ + ":");

            String userCode = sc.nextLine();

            if (userCode.equals(secretCode)) {
                System.out.println("Grade: " + length + " bulls");
                break;
            }

            for (int i = 0; i < secretCode.length(); i++) {

                if (secretCode.charAt(i) == userCode.charAt(i)) {
                    bulls++;
                } else if (secretCode.contains(String.valueOf(userCode.charAt(i)))) {
                    cows++;
                }
            }

            String bullsStr = bulls > 1 ? "bulls" : "bull";
            String cowsStr = cows > 1 ? "cows" : "cow";

            if (bulls == 0 && cows == 0) {
                System.out.println("Grade: None.");
            } else if (bulls > 0 && cows == 0) {
                System.out.println("Grade: " + bulls + " " + bullsStr);
            } else if (cows > 0 && bulls == 0) {
                System.out.println("Grade: " + cows + " " + cowsStr);
            } else if (cows > 0 && bulls > 0) {
                System.out.println("Grade: " + bulls + " " + bullsStr + " and " + cows + " " + cowsStr);
            }
        }
        System.out.println("Congratulations! You guessed the secret code.");
    }

    private static String generateRandomNumber(int length, int size) {

        String randomNumber = "";
        Random r = new Random();

        while (randomNumber.length() < length) {

            int temp = r.nextInt((size - 1) + 1);
            char c = ' ';

            if (temp <= 9) {
                c = (char) ('0' + temp);
            } else {
                c = (char) ('a' + (temp - 10));
            }

            if (randomNumber.length() < length && !randomNumber.contains(String.valueOf(c))) {

                randomNumber += String.valueOf(c);
            }
        }
        return randomNumber;
    }
}