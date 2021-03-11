import bullscows.Main;
import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BullsCowsTest extends StageTest<String> {

    // base test with 1 digit number
    @DynamicTestingMethod
    CheckResult test1() {
        TestedProgram main = new TestedProgram();
        main.start();

        String output;
        int gotAnswer = 0;
        main.execute("1");
        main.execute("10");

        for (int i = 0; i <= 9; i++) {
            if (main.isFinished()) {
                break; // if game has stopped, stop cycle and start check of results;
            }
            output = main.execute(Integer.toString(i));
            int[] result = getNumOfBullsAndCows(output);
            if (result[0] == 1) {
                gotAnswer++; // if got a bull, count for an amount of answers
            }
        }

        // if we got less or more than 1 answer, the program work is incorrect
        if (gotAnswer != 1) {
            return CheckResult.wrong("The game has no answer or more than one. ");
        }

        return CheckResult.correct();
    }

    // standard bulls and cows game
    @DynamicTestingMethod
    CheckResult test2() {
        TestedProgram main = new TestedProgram();
        main.start();
        main.execute("4");
        String output = main.execute("10");
        secretCheck(output, 4, 10);

        Character[] usedSymbols = getUsedSymbols(main, 4);
        boolean check = getPermutations(main, 4, usedSymbols);

        if (!check && main.isFinished()) {
            return CheckResult.wrong("The program has finished before the answer was found");
        }

        if (!check) {
            return CheckResult.wrong("The program has finished before the answer was found. " +
                    "It means that your game was broken (we used length 4 and 10 symbols).");
        }

        if (!main.isFinished()) {
            return CheckResult.wrong("The program didn't finish after " +
                    "the answer was found");
        }

        return CheckResult.correct();
    }

    // max length we can check
    @DynamicTestingMethod
    CheckResult test3() {
        TestedProgram main = new TestedProgram();
        main.start();
        main.execute("6");
        String output = main.execute("10");
        secretCheck(output, 6, 10);

        Character[] usedSymbols = getUsedSymbols(main, 6);
        boolean check = getPermutations(main, 6, usedSymbols);

        if (!check && main.isFinished()) {
            return CheckResult.wrong("The program has finished before the answer was found");
        }

        if (!check) {
            return CheckResult.wrong("The program has finished before the answer was found. " +
                    "It means that your game was broken (we used length 6 and 10 symbols).");
        }

        if (!main.isFinished()) {
            return CheckResult.wrong("The program didn't finish after " +
                    "the answer was found");
        }

        return CheckResult.correct();
    }

    // length limit check
    @DynamicTestingMethod
    CheckResult test4() {
        TestedProgram main = new TestedProgram();
        main.start();
        String output = main.execute("11");
        output = main.execute("10");

        if (output.toLowerCase().contains("error")) {
            return CheckResult.correct();
        } else {
            return CheckResult.wrong("The testing system waited for a error message " +
                    "(the message should contain a word \"error\").");
        }
    }

    // this stage test
    @DynamicTestingMethod
    CheckResult test5() {
        TestedProgram main = new TestedProgram();
        main.start();
        main.execute("4");
        String output = main.execute("16");
        secretCheck(output, 4, 16);

        Character[] usedSymbols = getUsedSymbols(main, 4);
        boolean check = getPermutations(main, 4, usedSymbols);

        if (!check && main.isFinished()) {
            return CheckResult.wrong("The program has finished before the answer was found. " +
                    "It means that your game was broken (we used length 4 and 16 symbols).");
        }

        if (!main.isFinished()) {
            return CheckResult.wrong("The program didn't finish after " +
                    "the answer was found");
        }

        return CheckResult.correct();
    }

    // test of usage of full dictionary
    @DynamicTestingMethod
    CheckResult test6() {
        TestedProgram main = new TestedProgram();
        main.start();

        main.execute("6");
        String output = main.execute("36");
        secretCheck(output, 6, 36);

        Character[] usedSymbols = getUsedSymbols(main, 6);
        boolean check = getPermutations(main, 6, usedSymbols);

        if (!check && main.isFinished()) {
            return CheckResult.wrong("The program has finished before the answer was found. " +
                    "It means that your game was broken (we used length 6 and 36 symbols).");
        }

        if (!main.isFinished()) {
            return CheckResult.wrong("The program didn't finish after " +
                    "the answer was found");
        }

        return CheckResult.correct();
    }

    // tested incorrect word's length
    @DynamicTestingMethod
    CheckResult test7() {
        TestedProgram main = new TestedProgram();
        main.start();
        String output = main.execute("0");

        if (!main.isFinished()) {
            output = main.execute("1");
        }

        if (!main.isFinished()) {
            return CheckResult.wrong("The program continued work after an incorrect input.");
        }

        if (!output.toLowerCase().contains("error")) {
            return CheckResult.wrong("The testing system waited for a error message " +
                    "(the message should contain a word \"error\").");
        }

        return CheckResult.correct();
    }

    // the dictionary is less than word's length
    @DynamicTestingMethod
    CheckResult test8() {
        TestedProgram main = new TestedProgram();
        main.start();
        main.execute("10");
        main.execute("9");

        if (!main.isFinished()) {
            return CheckResult.wrong("The program continued work after an incorrect input.");
        }

        return CheckResult.correct();
    }

    // test of dictionary's limit
    @DynamicTestingMethod
    CheckResult test9() {
        TestedProgram main = new TestedProgram();
        main.start();
        String output;
        main.execute("9");
        output = main.execute("37");

        if (!main.isFinished()) {
            return CheckResult.wrong("The program continued work after an incorrect input.");
        }

        if (!output.toLowerCase().contains("error")) {
            return CheckResult.wrong("The testing system waited for a error message " +
                    "(the message should contain a word \"error\").");
        }

        return CheckResult.correct();
    }

    // test of words input
    @DynamicTestingMethod
    CheckResult test10() {
        TestedProgram main = new TestedProgram();
        main.start();
        String output;
        output = main.execute("abcdefg 1 -6");

        if (!main.isFinished()) {
            return CheckResult.wrong("The program continued work after an incorrect input.");
        }

        if (!output.toLowerCase().contains("error")) {
            return CheckResult.wrong("The testing system waited for a error message " +
                    "(the message should contain a word \"error\").");
        }

        return CheckResult.correct();
    }


    void secretCheck(String output, int length, int dictLen) {
        String secret = new String(new char[length]).replace('\0', '*');
        output = output.toLowerCase();

        if (!output.contains(secret)) {
            throw new WrongAnswer("The length of secret code is incorrect.");
        }

        String firstChar = "0";
        String lastChar;
        if (dictLen <= 10) {
            lastChar = "" + ((char) (47 + dictLen));
        } else {
            lastChar = "" + ((char) (86 + dictLen));
        }

        if (!(output.contains(firstChar) && output.contains(lastChar))) {
            throw new WrongAnswer("The range of possible symbols " +
                    "in the secret code is incorrect. " +
                    "For the " + dictLen + " possible symbols " +
                    "the last symbol should be '" + lastChar + "'.");
        }
    }


    Character[] getUsedSymbols(TestedProgram main, int length) {
        Character[] symbols = new Character[length];
        char[] dictionary = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                'u', 'v', 'w', 'x', 'y', 'z'};
        int[] result;

        int index = 0;
        String input;
        String output;

        for (char c : dictionary) {
            input = new String(new char[length]).replace('\0', c);
            output = main.execute(input);
            result = getNumOfBullsAndCows(output);

            if (result[0] > 1) {
                throw new WrongAnswer("Seems like " +
                        "the calculation of bulls isn't right. " +
                        "For the guess \"" + input + "\" there can be 1 bull at max.");
            }

            if (result[0] == 1) {
                symbols[index++] = c;
            }

            if (index == length) {
                break;
            }
        }

        if (index != length) {
            throw new WrongAnswer(
                    "Output should contain " + length + " bulls " +
                    "summarized as every option was tried. Found: " + index
            );
        }

        return symbols;
    }


    // permutations one by one
    public boolean getPermutations(TestedProgram main, int length, Character[] elements) {
        int[] indexes = new int[length];
        for (int i = 0; i < length; i++) {
            indexes[i] = 0;
        }

        String output = main.execute(Arrays.toString(elements).replaceAll("\\[|\\]|, ", ""));
        int[] result = getNumOfBullsAndCows(output);
        if (result[0] == length) {
            return true;
        }

        int i = 0;
        while (i < length) {
            if (indexes[i] < i) {
                swap(elements, i % 2 == 0 ? 0 : indexes[i], i);
                output = main.execute(Arrays.toString(elements).replaceAll("\\[|\\]|, ", ""));
                result = getNumOfBullsAndCows(output);
                if (result[0] == length) {
                    return true;
                }
                indexes[i]++;
                i = 0;
            } else {
                indexes[i] = 0;
                i++;
            }
        }
        return false;
    }

    // get number of bulls and cows from user program's output
    int[] getNumOfBullsAndCows(String userString) {
        Matcher nonePattern = Pattern.compile("\\b[nN]one\\b").matcher(userString);
        Matcher cowsPattern = Pattern.compile("\\b\\d [cC]ow").matcher(userString);
        Matcher bullsPattern = Pattern.compile("\\b\\d [bB]ull").matcher(userString);
        Pattern oneNumPattern = Pattern.compile("\\d");

        if (nonePattern.find()) {
            return new int[]{0, 0};
        }

        int[] ans = {0, 0};
        boolean found = false;

        if (bullsPattern.find()) {
            String temp = bullsPattern.group();
            Matcher oneNumBulls = oneNumPattern.matcher(temp);
            oneNumBulls.find();
            ans[0] = Integer.parseInt(oneNumBulls.group());
            found = true;
        }

        if (cowsPattern.find()) {
            String temp = cowsPattern.group();
            Matcher oneNumCows = oneNumPattern.matcher(temp);
            oneNumCows.find();
            ans[1] = Integer.parseInt(oneNumCows.group());
            found = true;
        }

        if (!found) {
            throw new WrongAnswer(
                    "Cannot find number of bulls or number of cows or None after the input."
            );
        }

        return ans;
    }

    private static void swap(Character[] input, int a, int b) {
        char tmp = input[a];
        input[a] = input[b];
        input[b] = tmp;
    }
}