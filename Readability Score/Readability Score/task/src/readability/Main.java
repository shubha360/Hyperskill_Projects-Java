package readability;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String input = "";

        try {
            input = new String(Files.readAllBytes(Paths.get(args[0])));
        } catch (IOException e) {
            e.printStackTrace();
        }

        double sentenceCount = 0;
        double characterCount = 0;
        double syllableCount = 0;
        double polySyllableCount = 0;

        if (String.valueOf(input.charAt(input.length() - 1)).matches("[^.?!]")) {
            input += ".";
            characterCount--;
        }

        input = input.replaceAll("[AEIOUYaeiouy]{2}", "()");
        input = input.replaceAll("[eE]\\b", "^");

        String[] arr = input.split(" ");

        double wordCount = arr.length;

        for (String s : arr) {

            if (s.substring(s.length() - 1).matches("[.?!]")) {
                sentenceCount++;
            }

            double syllableFound = 0;
            for (char c : s.toCharArray()) {
                characterCount++;

                if (String.valueOf(c).matches("[aeiouyAEIOUY(]")) {
                    syllableFound++;
                }
            }
            syllableCount += syllableFound == 0 ? 1 : syllableFound;
            polySyllableCount += syllableFound > 2 ? 1 : 0;
        }

        System.out.println("Words: " + (int) wordCount);
        System.out.println("Sentences: " + (int) sentenceCount);
        System.out.println("Characters: " + (int) characterCount);
        System.out.println("Syllables: " + (int) syllableCount);
        System.out.println("Polysyllables: " + (int) polySyllableCount);

        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
        Scanner scanner = new Scanner(System.in);
        String selection = scanner.nextLine();

        switch (selection) {

            case "ARI" :
                System.out.println();
                automatedReadabilityIndex(characterCount, wordCount, sentenceCount);
                break;

            case "FK" :
                System.out.println();
                fkReadabilityTest(wordCount, sentenceCount, syllableCount);
                break;

            case "SMOG" :
                System.out.println();
                smogIndex(sentenceCount, polySyllableCount);
                break;

            case "CL" :
                System.out.println();
                clIndex(wordCount, characterCount, sentenceCount);
                break;

            case "all" :

                System.out.println();
                double ariScore = automatedReadabilityIndex(characterCount, wordCount, sentenceCount);
                double fkRTest = fkReadabilityTest(wordCount, sentenceCount, syllableCount);
                double smog = smogIndex(sentenceCount, polySyllableCount);
                double cl = clIndex(wordCount, characterCount, sentenceCount);

                double average = (ariScore + fkRTest + smog + cl) / 4.00;

                System.out.printf("\nThis text should be understood in average by %.2f-year-olds.", average);
        }
    }

    static double getAge(double score) {

        double age = -1;

        switch ((int) Math.ceil(score)) {

            case 1:
                age = 6;
                break;

            case 2:
                age = 7;
                break;

            case 3:
                age = 9;
                break;

            case 4:
                age = 10;
                break;

            case 5:
                age = 11;
                break;

            case 6:
                age = 12;
                break;

            case 7:
                age = 13;
                break;

            case 8:
                age = 14;
                break;

            case 9:
                age = 15;
                break;

            case 10:
                age = 16;
                break;

            case 11:
                age = 17;
                break;

            case 12:
                age = 18;
                break;

            case 13:
                age = 24;
                break;

            case 14:
                age = 25;
                break;
        }
        return age;
    }

    static double automatedReadabilityIndex(double totalCharacters, double totalWords, double totalSentences) {

        double score = 4.71 * (totalCharacters / totalWords) + 0.5 * (totalWords / totalSentences) - 21.43;

        double age = getAge(score);

        System.out.printf("Automated Readability Index: %.2f (about %d-year-olds).\n", score, (int) age);

        return age;
    }

    static double fkReadabilityTest(double totalWords, double totalSentences, double totalSyllables) {

        double score = 0.39 * (totalWords / totalSentences) + 11.8 * (totalSyllables / totalWords) - 15.59;

        double age = getAge(score);

        System.out.printf("Flesch–Kincaid readability tests: %.2f (about %s-year-olds).\n", score, (int) age);

        return age;
    }

    static double smogIndex(double totalSentences, double totalPolysyllables) {

        double score = 1.043 * Math.sqrt(totalPolysyllables * 30 / totalSentences) + 3.1291;

        double age = getAge(score);

        System.out.printf("Simple Measure of Gobbledygook: %.2f (about %s-year-olds).\n", score, (int) age);

        return age;
    }

    static double clIndex(double totalWords, double totalCharacters, double totalSentences) {

        double L = totalCharacters / totalWords * 100;
        double S = totalSentences / totalWords * 100;

        double score = 0.0588 * L - 0.296 * S - 15.8;

        double age = getAge(score);

        System.out.printf("Coleman–Liau index: %.2f (about %s-year-olds).\n", score, (int) age);

        return age;
    }
}