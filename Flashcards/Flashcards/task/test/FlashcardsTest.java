import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestedProgram;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FlashcardsTest extends StageTest<String> {
    
    private static List<String> listOfTerms;
    private static List<String> listOfDefinitions;
    private static List<String> listOfAddedDefinitions;
    private static List<String> listOfCountries;
    private static List<String> listOfCapitals;
    private final String capitalsFile = "capitals.txt";
    private final String capitalsNewFile = "capitalsNew.txt";
    
    @BeforeClass
    public static void generateLists() {
        listOfTerms = getListOfTerms();
        listOfDefinitions = getListOfDefinitions();
        listOfAddedDefinitions = getListOfWrongDefinitions1();
        listOfCountries = getListOfCountries();
        listOfCapitals = getListOfCapitals();
    }
    
    @AfterClass
    public static void deleteFiles() {
        File capitalsFile = new File("capitals.txt");
        //noinspection ResultOfMethodCallIgnored
        capitalsFile.delete();
        
        File capitalsNewFile = new File("capitalsNew.txt");
        //noinspection ResultOfMethodCallIgnored
        capitalsNewFile.delete();
        
        File logFile = new File("todayLog.txt");
        //noinspection ResultOfMethodCallIgnored
        logFile.delete();
    
        File fridayFile = new File("fridayThe13th.txt");
        //noinspection ResultOfMethodCallIgnored
        fridayFile.delete();
    
        File newFridayFile = new File("newFridayThe13th.txt");
        //noinspection ResultOfMethodCallIgnored
        newFridayFile.delete();
        
    }
    
    @DynamicTestingMethod
    CheckResult test1() {
        TestedProgram main = new TestedProgram();
        
        String output = main.start().toLowerCase().trim();
        if (!output.contains("input the action")) {
            return CheckResult.wrong("Your program should prompt the user for an action with the message \"Input the " +
                    "action\"");
        }
    
        output = main.execute("exit").toLowerCase();
        if (!output.contains("bye")) {
            return CheckResult.wrong("Your program should print \"Bye bye!\" and terminate if the user enters " +
                    "\"exit\"");
        }
    
        if (!main.isFinished()) {
            return CheckResult.wrong("Your program should terminate if the user enters \"exit\"");
        }
        
        return CheckResult.correct();
    }
    
    //Test to check the "add" action
    @DynamicTestingMethod
    CheckResult test2() {
        TestedProgram main = new TestedProgram();
        main.start();
        
        String output;
        String lastLine;
        String[] lines;
        
        output = main.execute("add").toLowerCase();
        if (!output.contains("card")) {
            return CheckResult.wrong("Your program should output the message \"The card:\" when the 'add' action is " +
                    "selected");
        }
        
        output = main.execute("France").toLowerCase();
        if (!output.contains("definition of the card")) {
            return CheckResult.wrong("Your program should prompt the user for the definition of the card with the " +
                    "message \"The definition of the card:\"");
        }
        
        output = main.execute("Paris").toLowerCase();
        if (!output.contains("pair (\"france\":\"paris\") has been added")) {
            return CheckResult.wrong("Your program should print the message \"The pair (\"term\":\"definition\") has " +
                    "been added\" after adding a card successfully");
        }
        
        lines = output.split("\n");
        lastLine = lines[lines.length - 1];
        if (!lastLine.toLowerCase().contains("input the action")) {
            return CheckResult.wrong("Your program should continue to request an action from the user until they " +
                    "enter \"exit\"");
        }
        
        main.execute("add");
        output = main.execute("France").toLowerCase();
        if (!output.contains("card \"france\" already exists")) {
            return CheckResult.wrong("Your program should not add a card that already exists and should notify the " +
                    "user with the message \"The card 'term' already exists\".");
        }
        
        lines = output.split("\n");
        lastLine = lines[lines.length - 1];
        if (!lastLine.toLowerCase().contains("input the action")) {
            return CheckResult.wrong("Your program should request an action from the user if a card was declined " +
                    "because it already exists");
        }
        
        main.execute("add");
        main.execute("Great Britain");
        output = main.execute("Paris").toLowerCase();
        if (!output.contains("definition \"paris\" already exists")) {
            return CheckResult.wrong("Your program should not add a definition that already exists and should notify " +
                    "the \"user with the message \"The definition 'definition' already exists\".");
        }
    
        output = main.execute("exit").toLowerCase();
        if (!output.contains("bye")) {
            return CheckResult.wrong("Your program should print \"Bye bye!\" and terminate if the user enters " +
                    "\"exit\"");
        }
    
        if (!main.isFinished()) {
            return CheckResult.wrong("Your program should terminate if the user enters \"exit\"");
        }
        
        return CheckResult.correct();
    }
    
    //Test to check the "remove" action
    @DynamicTestingMethod
    CheckResult test3() {
        TestedProgram main = new TestedProgram();
        main.start();
        
        String output;
        String lastLine;
        String[] lines;
        
        //No need to check user's output because they have been checked in test 2.
        main.execute("add");
        main.execute("France");
        main.execute("Paris");
        
        main.execute("add");
        main.execute("Japan");
        main.execute("Tokyo");
        
        main.execute("add");
        main.execute("Great Britain");
        main.execute("London");
        
        output = main.execute("remove").toLowerCase();
        if (!output.contains("which card")) {
            return CheckResult.wrong("Your program should prompt the user for the name of the card with the message " +
                    "\"Which card?\"");
        }
        
        output = main.execute("Japan").toLowerCase();
        if (!output.contains("card has been removed")) {
            return CheckResult.wrong("Your program should notify the user with the message " +
                    "\"The card has been removed.\" if the card was removed successfully");
        }
        
        lines = output.split("\n");
        lastLine = lines[lines.length - 1];
        if (!lastLine.toLowerCase().contains("input the action")) {
            return CheckResult.wrong("Your program should continue to request an action from the user until they " +
                    "enter \"exit\"");
        }
        
        main.execute("remove");
        output = main.execute("Wakanda").toLowerCase();
        if (!output.contains("can't remove \"wakanda\"")) {
            return CheckResult.wrong("Your program should notify the user with the message" +
                    " \"Can't remove 'card': there is no such card.\" if the card doesn't exist");
        }
        
        if (!output.contains("no such card")) {
            return CheckResult.wrong("Your program should notify the user with the message" +
                    " \"Can't remove 'card': there is no such card.\" if the card doesn't exist");
        }
        
        lines = output.split("\n");
        lastLine = lines[lines.length - 1];
        if (!lastLine.toLowerCase().contains("input the action")) {
            return CheckResult.wrong("Your program should request an action from the user even if a card could not be" +
                    " removed");
        }
        
        main.execute("remove");
        output = main.execute("Tokyo").toLowerCase();
        if (output.contains("card has been removed")) {
            return CheckResult.wrong("You should not be able to remove a card by it's definition");
        }
    
        output = main.execute("exit").toLowerCase();
        if (!output.contains("bye")) {
            return CheckResult.wrong("Your program should print \"Bye bye!\" and terminate if the user enters " +
                    "\"exit\"");
        }
    
        if (!main.isFinished()) {
            return CheckResult.wrong("Your program should terminate if the user enters \"exit\"");
        }
        
        return CheckResult.correct();
    }
    
    //Test to check "export" action
    @DynamicTestingMethod
    CheckResult test4() {
        TestedProgram main = new TestedProgram();
        main.start();
        
        String output;
        String lastLine;
        String[] lines;
        boolean fileExists;
        boolean empty;
        
        main.execute("add");
        main.execute("Japan");
        main.execute("Tokyo");
        
        main.execute("add");
        main.execute("France");
        main.execute("Paris");
        
        output = main.execute("export").toLowerCase();
        if (!output.contains("file name")) {
            return CheckResult.wrong("Your program should prompt the user for the name of the file with the message " +
                    "\"File name:\"");
        }
        
        output = main.execute(capitalsFile).toLowerCase();
        if (!output.contains("2 cards")) {
            return CheckResult.wrong("Wrong number of cards were exported. Expected 2");
        }
        
        if (!output.contains("saved")) {
            return CheckResult.wrong("Your program should notify the user if the cards were exported with the message " +
                    "\"n cards have been saved.\" where \"n\" is the number of cards exported");
        }
        
        fileExists = checkFileExistence(capitalsFile);
        if (!fileExists) {
            return CheckResult.wrong("Your program did not save a file after exporting");
        }
        
        empty = isEmpty(capitalsFile);
        if (empty) {
            return CheckResult.wrong("The file your program saves is empty");
        }
        
        lines = output.split("\n");
        lastLine = lines[lines.length - 1];
        if (!lastLine.toLowerCase().contains("input the action")) {
            return CheckResult.wrong("Your program should continue to request an action from the user until they " +
                    "enter \"exit\"");
        }
    
        output = main.execute("exit").toLowerCase();
        if (!output.contains("bye")) {
            return CheckResult.wrong("Your program should print \"Bye bye!\" and terminate if the user enters " +
                    "\"exit\"");
        }
    
        if (!main.isFinished()) {
            return CheckResult.wrong("Your program should terminate if the user enters \"exit\"");
        }
        
        return CheckResult.correct();
    }
    
    //saves file for the next test
    @DynamicTestingMethod
    CheckResult test5() {
        TestedProgram main = new TestedProgram();
        main.start();
        
        String output;
        boolean fileExists;
        boolean empty;
        
        for (int i = 0; i < listOfCountries.size(); i++) {
            main.execute("add");
            main.execute(listOfCountries.get(i));
            main.execute(listOfCapitals.get(i));
        }
        
        main.execute("export");
        output = main.execute(capitalsNewFile).toLowerCase();
        if (!output.contains("6 cards")) {
            return CheckResult.wrong("Wrong number of cards were exported. Expected 6");
        }
        
        if (!output.contains("saved")) {
            return CheckResult.wrong("Your program should notify the user if the cards were exported with the message" +
                    " \"n cards have been saved.\" where \"n\" is the number of cards exported");
        }
        
        fileExists = checkFileExistence(capitalsNewFile);
        if (!fileExists) {
            return CheckResult.wrong("Your program did not save a file after exporting");
        }
        
        empty = isEmpty(capitalsNewFile);
        if (empty) {
            return CheckResult.wrong("The file your program saves is empty");
        }
    
        output = main.execute("exit").toLowerCase();
        if (!output.contains("bye")) {
            return CheckResult.wrong("Your program should print \"Bye bye!\" and terminate if the user enters " +
                    "\"exit\"");
        }
    
        if (!main.isFinished()) {
            return CheckResult.wrong("Your program should terminate if the user enters \"exit\"");
        }
        
        return CheckResult.correct();
    }
    
    //Test to check "import" action
    @DynamicTestingMethod
    CheckResult test6() {
        TestedProgram main = new TestedProgram();
        main.start();
        
        String output;
        String lastLine;
        String[] lines;
        boolean fileExists;
        boolean empty;
        
        output = main.execute("import").toLowerCase();
        if (!output.contains("file name")) {
            return CheckResult.wrong("Your program should prompt the user for the name of the file with the message " +
                    "\"File name:\"");
        }
        
        String nonExistentFile = "ghost_file.txt";
        output = main.execute(nonExistentFile).toLowerCase();
        if (!output.contains("not found")) {
            return CheckResult.wrong("Your program should notify the user if the file does not exist with the message" +
                    " \"File not found.\"");
        }
        
        lines = output.split("\n");
        lastLine = lines[lines.length - 1];
        if (!lastLine.toLowerCase().contains("input the action")) {
            return CheckResult.wrong("Your program should continue to request an action from the user until they " +
                    "enter \"exit\"");
        }
        
        main.execute("import");
        output = main.execute(capitalsFile).toLowerCase();
        if (output.contains("not found")) {
            return CheckResult.wrong("You should be able to import a file that you previously saved.");
        }
        
        if (!output.contains("2 cards")) {
            return CheckResult.wrong("Wrong number of cards were imported. Expected 2");
        }
        
        if (!output.contains("been loaded")) {
            return CheckResult.wrong("Your program should notify the user if the cards were imported with the message" +
                    " \"n cards have been loaded.\" where \"n\" is the number of cards in the file");
        }
        
        lines = output.split("\n");
        lastLine = lines[lines.length - 1];
        if (!lastLine.toLowerCase().contains("input the action")) {
            return CheckResult.wrong("Your program should continue to request an action from the user until they " +
                    "enter \"exit\"");
        }
        
        main.execute("remove");
        output = main.execute("Japan").toLowerCase();
        if (!output.contains("card has been removed")) {
            return CheckResult.wrong("You should be able to remove a card that you imported from a file");
        }
        
        main.execute("export");
        output = main.execute(capitalsFile).toLowerCase();
        if (!output.contains("1 card")) {
            return CheckResult.wrong("Wrong number of cards were exported. Expected 1");
        }
        
        main.execute("import");
        output = main.execute(capitalsNewFile).toLowerCase();
        if (!output.contains("6 cards")) {
            return CheckResult.wrong("Wrong number of cards were imported. Expected 6");
        }
        
        main.execute("export");
        output = main.execute(capitalsNewFile).toLowerCase();
        if (!output.contains("7 cards")) {
            return CheckResult.wrong("Wrong number of cards were exported. Expected 7");
        }
        
        fileExists = checkFileExistence(capitalsNewFile);
        if (!fileExists) {
            return CheckResult.wrong("Your program did not save a file after exporting");
        }
        
        empty = isEmpty(capitalsNewFile);
        if (empty) {
            return CheckResult.wrong("The file your program saves is empty");
        }
    
        output = main.execute("exit").toLowerCase();
        if (!output.contains("bye")) {
            return CheckResult.wrong("Your program should print \"Bye bye!\" and terminate if the user enters " +
                    "\"exit\"");
        }
    
        if (!main.isFinished()) {
            return CheckResult.wrong("Your program should terminate if the user enters \"exit\"");
        }
        
        return CheckResult.correct();
    }
    
    //Test to check "ask" action
    @DynamicTestingMethod
    CheckResult test7() {
        TestedProgram main = new TestedProgram();
        main.start();
        
        String output;
        String lastLine;
        String[] lines;
        
        main.execute("add");
        main.execute("France");
        main.execute("Eiffel Tower");
        
        main.execute("import");
        output = main.execute(capitalsFile).toLowerCase();
        if (!output.contains("1 card")) {
            return CheckResult.wrong("Wrong number of cards were imported. Expected 1");
        }
        
        if (!output.contains("been loaded")) {
            return CheckResult.wrong("Your program should notify the user if the cards were imported with the message" +
                    " \"n cards have been loaded.\" where \"n\" is the number of cards in the file");
        }
        
        output = main.execute("ask").toLowerCase();
        if (!output.contains("times")) {
            return CheckResult.wrong("Your program should prompt the user for the number of cards they want to be " +
                    "asked about with the message \"How many times to ask?\"");
        }
        
        
        output = main.execute("1").toLowerCase();
        if (!output.contains("\"france\"")) {
            return CheckResult.wrong("Your program should print the definition requested for in quotes");
        }
        
        if (!output.contains("definition")) {
            return CheckResult.wrong("Your program should prompt the user for the definition of a card with the " +
                    "message \" Print the definition of \"term\": where \"term\" is the term of the flashcard to be " +
                    "checked");
        }
        
        output = main.execute("Eiffel Tower").toLowerCase();
        if (output.equals("correct!")) {
            return CheckResult.wrong("If the program's memory already has a card that is also contained in the file " +
                    "imported, then the definition of the card from the file should overwrite the one in the " +
                    "program's memory");
        }
        
        if (!output.contains("wrong. the right answer is \"paris\"")) {
            return CheckResult.wrong("Your program should print \"Wrong\" followed by the correct definition in quotes" +
                    " if the user inputs the wrong definition");
        }
        
        lines = output.split("\n");
        lastLine = lines[lines.length - 1];
        if (!lastLine.toLowerCase().contains("input the action")) {
            return CheckResult.wrong("Your program should request an action from the user if there are no more cards " +
                    "to ask");
        }
        
        main.execute("ask");
        main.execute("1");
        output = main.execute("Paris").toLowerCase();
        lines = output.split("\n");
        
        if (!lines[0].equals("correct!")) {
            return CheckResult.wrong("Your program should print \"Correct!\" if the user inputs the correct " +
                    "definition");
        }
    
        output = main.execute("exit").toLowerCase();
        if (!output.contains("bye")) {
            return CheckResult.wrong("Your program should print \"Bye bye!\" and terminate if the user enters " +
                    "\"exit\"");
        }
    
        if (!main.isFinished()) {
            return CheckResult.wrong("Your program should terminate if the user enters \"exit\"");
        }
        
        return CheckResult.correct();
    }
    
    @DynamicTestingMethod
    CheckResult test8() {
        TestedProgram main = new TestedProgram();
        main.start();
        
        int index;
        String output;
        String term;
        String lastLine;
        String[] lines;
        
        for (int i = 0; i < listOfTerms.size(); i++) {
            main.execute("add");
            main.execute(listOfTerms.get(i));
            main.execute(listOfDefinitions.get(i));
        }
        
        main.execute("ask");
        output = main.execute("6").toLowerCase();
        term = getTerm(output);
        
        for (int i = 0; i < listOfTerms.size(); i++) {
            index = listOfTerms.indexOf(term);

            if (index == -1) {
                return CheckResult.wrong("The card \"" + term + "\" wasn't added, but you ask to enter its definition!");
            }
            
            output = main.execute(listOfDefinitions.get(index)).toLowerCase();
            lines = output.split("\n");
            lastLine = lines[lines.length - 1];
            
            if (!lines[0].equals("correct!")) {
                return CheckResult.wrong("Your program should print \"Correct!\" if the user inputs the correct" +
                        " definition");
            }
            
            if (i == listOfTerms.size() - 1) {
                
                if (!lastLine.toLowerCase().contains("input the action")) {
                    return CheckResult.wrong("Your program should request an action from the user if there are no more cards " +
                            "to ask");
                }
            } else {
                term = getTerm(lastLine);
            }
            
        }
    
        output = main.execute("exit").toLowerCase();
        if (!output.contains("bye")) {
            return CheckResult.wrong("Your program should print \"Bye bye!\" and terminate if the user enters " +
                    "\"exit\"");
        }
    
        if (!main.isFinished()) {
            return CheckResult.wrong("Your program should terminate if the user enters \"exit\"");
        }
        
        return CheckResult.correct();
    }
    
    @DynamicTestingMethod
    CheckResult test9() {
        TestedProgram main = new TestedProgram();
        main.start();
        
        int index;
        String output;
        String term;
        String lastLine;
        String[] lines;
        
        for (int i = 0; i < listOfTerms.size(); i++) {
            main.execute("add");
            main.execute(listOfTerms.get(i));
            main.execute(listOfDefinitions.get(i));
        }
        
        main.execute("ask");
        output = main.execute("6").toLowerCase();
        term = getTerm(output);
        
        for (int i = 0; i < listOfTerms.size(); i++) {
            index = listOfTerms.indexOf(term);

            if (index == -1) {
                return CheckResult.wrong("The card \"" + term + "\" wasn't added, but you ask to enter its definition!");
            }
            
            output = main.execute(listOfAddedDefinitions.get(index)).toLowerCase();
            lines = output.split("\n");
            lastLine = lines[lines.length - 1];
            
            if (!lines[0].contains("wrong. the right answer is \"" + listOfDefinitions.get(index) + "\"")) {
                return CheckResult.wrong("Your program should print \"Wrong\" followed by the correct definition" +
                        " in quotes if the user inputs the wrong definition");
            }
            
            if (i == listOfTerms.size() - 1) {
                
                if (!lastLine.toLowerCase().contains("input the action")) {
                    return CheckResult.wrong("Your program should request an action from the user if there are no more cards " +
                            "to ask");
                }
            } else {
                term = getTerm(lastLine);
            }
            
        }
    
        output = main.execute("exit").toLowerCase();
        if (!output.contains("bye")) {
            return CheckResult.wrong("Your program should print \"Bye bye!\" and terminate if the user enters " +
                    "\"exit\"");
        }
    
        if (!main.isFinished()) {
            return CheckResult.wrong("Your program should terminate if the user enters \"exit\"");
        }
        
        return CheckResult.correct();
    }
    
    @DynamicTestingMethod
    CheckResult test10() {
        TestedProgram main = new TestedProgram();
        main.start();
        
        int wrongIndex;
        int index;
        String output;
        String term;
        String lastLine;
        String[] lines;
        
        for (int i = 0; i < listOfTerms.size(); i++) {
            main.execute("add");
            main.execute(listOfTerms.get(i));
            main.execute(listOfDefinitions.get(i));
        }
        
        main.execute("ask");
        output = main.execute("6").toLowerCase();
        term = getTerm(output);
        
        for (int i = 0; i < listOfTerms.size(); i++) {
            index = listOfTerms.indexOf(term);

            if (index == -1) {
                return CheckResult.wrong("The card \"" + term + "\" wasn't added, but you ask to enter its definition!");
            }

            wrongIndex = index == listOfDefinitions.size() - 1 ? index - 1 : index + 1;
            
            output = main.execute(listOfDefinitions.get(wrongIndex)).toLowerCase();
            lines = output.split("\n");
            lastLine = lines[lines.length - 1];
            
            if (!lines[0].contains("wrong. the right answer is \"" + listOfDefinitions.get(index) + "\"")) {
                return CheckResult.wrong("Your program should print \"Wrong\" followed by the correct definition" +
                        " in quotes if the user inputs the wrong definition");
            }
            
            if (!lines[0].contains("correct for \"" + listOfTerms.get(wrongIndex) + "\"")) {
                return CheckResult.wrong("Your output should also contain \"but your definition is correct for 'term'" +
                        " \"");
            }
            
            if (i == listOfTerms.size() - 1) {
                
                if (!lastLine.toLowerCase().contains("input the action")) {
                    return CheckResult.wrong("Your program should request an action from the user if there are no more cards " +
                            "to ask");
                }
            } else {
                term = getTerm(lastLine);
            }
            
        }
    
        output = main.execute("exit").toLowerCase();
        if (!output.contains("bye")) {
            return CheckResult.wrong("Your program should print \"Bye bye!\" and terminate if the user enters " +
                    "\"exit\"");
        }
    
        if (!main.isFinished()) {
            return CheckResult.wrong("Your program should terminate if the user enters \"exit\"");
        }
        
        return CheckResult.correct();
    }
    
    //Test to check the "hardest card" action
    @DynamicTestingMethod
    CheckResult test11() {
        TestedProgram main = new TestedProgram();
        main.start();
        
        String output;
        String lastLine;
        String[] lines;
        
        output = main.execute("hardest card").toLowerCase();
        lines = output.split("\n");
        lastLine = lines[lines.length - 1];
        
        if (!output.contains("no cards with errors")) {
            return CheckResult.wrong("Your program should print \"There are no cards with errors.\" if there are no " +
                    "cards with errors");
        }
        
        if (!lastLine.toLowerCase().contains("input the action")) {
            return CheckResult.wrong("Your program should continue to request an action from the user until they enter" +
                    " \"exit\"");
        }
        
        main.execute("add");
        main.execute("France");
        main.execute("Paris");
        
        main.execute("ask");
        main.execute("1");
        main.execute("Eiffel Tower");
        
        main.execute("ask");
        main.execute("1");
        main.execute("Lyon");
        
        output = main.execute("hardest card").toLowerCase();
        if (!output.contains("the hardest card is")) {
            return CheckResult.wrong("Your program should show the user the hardest card(s) in the format" +
                    " \"The hardest card is \"card\". You have n errors answering it.\"");
        }
        
        if (!output.contains("errors answering it")) {
            return CheckResult.wrong("Your program should show the user the hardest card(s) in the format" +
                    " \"The hardest card is \"card\". You have n errors answering it.\"");
        }
        
        if (!output.contains("\"france\"")) {
            return CheckResult.wrong("Your program should print the hardest card in quotes");
        }
        
        if (!output.contains("2")) {
            return CheckResult.wrong("Your program shows wrong number of errors. Expected 2");
        }
        
        main.execute("export");
        main.execute(capitalsFile);
    
        output = main.execute("exit").toLowerCase();
        if (!output.contains("bye")) {
            return CheckResult.wrong("Your program should print \"Bye bye!\" and terminate if the user enters " +
                    "\"exit\"");
        }
    
        if (!main.isFinished()) {
            return CheckResult.wrong("Your program should terminate if the user enters \"exit\"");
        }
        
        return CheckResult.correct();
    }
    
    @DynamicTestingMethod
    CheckResult test12() {
        TestedProgram main = new TestedProgram();
        main.start();
        
        String output;
        
        main.execute("add");
        main.execute("Russia");
        main.execute("Moscow");
        
        main.execute("ask");
        main.execute("1");
        main.execute("Saint Petersburg");
        
        main.execute("ask");
        main.execute("1");
        main.execute("Saint Petersburg");
        
        main.execute("ask");
        main.execute("1");
        main.execute("Saint Petersburg");
        
        output = main.execute("hardest card").toLowerCase();
        if (!output.contains("the hardest card is")) {
            return CheckResult.wrong("Your program should show the user the hardest card(s) in the format" +
                    " \"The hardest card is \"card\". You have n errors answering it.\"");
        }
        
        if (!output.contains("errors answering it")) {
            return CheckResult.wrong("Your program should show the user the hardest card(s) in the format" +
                    " \"The hardest card is \"card\". You have n errors answering it.\"");
        }
        
        if (!output.contains("\"russia\"")) {
            return CheckResult.wrong("Your program should print the hardest card in quotes");
        }
        
        if (!output.contains("3")) {
            return CheckResult.wrong("Your program shows wrong number of errors. Expected 3");
        }
        
        main.execute("export");
        main.execute(capitalsNewFile);
    
        output = main.execute("exit").toLowerCase();
        if (!output.contains("bye")) {
            return CheckResult.wrong("Your program should print \"Bye bye!\" and terminate if the user enters " +
                    "\"exit\"");
        }
    
        if (!main.isFinished()) {
            return CheckResult.wrong("Your program should terminate if the user enters \"exit\"");
        }
        
        return CheckResult.correct();
    }
    
    @DynamicTestingMethod
    CheckResult test13() {
        TestedProgram main = new TestedProgram();
        main.start();
        
        String output;
        
        main.execute("import");
        main.execute(capitalsFile);
        
        main.execute("import");
        main.execute(capitalsNewFile);
        
        output = main.execute("hardest card").toLowerCase();
        if (output.contains("no cards with errors")) {
            return CheckResult.wrong("Your program should print the hardest card(s) if such card(s) exists");
        }
        
        if (!output.contains("the hardest card is")) {
            return CheckResult.wrong("Your program should show the user the hardest card(s) in the format" +
                    " \"The hardest card is \"card\". You have n errors answering it.\"");
        }
        
        if (!output.contains("errors answering it")) {
            return CheckResult.wrong("Your program should show the user the hardest card(s) in the format" +
                    " \"The hardest card is \"card\". You have n errors answering it.\"");
        }
        
        if (output.contains("france")) {
            return CheckResult.wrong("Your program printed the wrong card");
        }
        
        if (!output.contains("\"russia\"")) {
            return CheckResult.wrong("Your program should print the hardest card in quotes");
        }
        
        if (!output.contains("3")) {
            return CheckResult.wrong("Your program shows wrong number of errors. Expected 3");
        }
    
        output = main.execute("exit").toLowerCase();
        if (!output.contains("bye")) {
            return CheckResult.wrong("Your program should print \"Bye bye!\" and terminate if the user enters " +
                    "\"exit\"");
        }
    
        if (!main.isFinished()) {
            return CheckResult.wrong("Your program should terminate if the user enters \"exit\"");
        }
        
        return CheckResult.correct();
    }
    
    @DynamicTestingMethod
    CheckResult test14() {
        TestedProgram main = new TestedProgram();
        main.start();
        
        String output;
        
        main.execute("import");
        main.execute(capitalsFile);
        
        main.execute("ask");
        main.execute("1");
        main.execute("Lyon");
        
        main.execute("import");
        main.execute(capitalsNewFile);
        
        output = main.execute("hardest card").toLowerCase();
        if (output.contains("no cards with errors")) {
            return CheckResult.wrong("Your program should print the hardest card(s) if such card(s) exists");
        }
        
        if (!output.contains("hardest cards are")) {
            return CheckResult.wrong("Your program should show the user the hardest card(s) in the format" +
                    " \"The hardest cards are \"card#1\", \"card#2\"...\"card#n\". You have n errors answering them" +
                    ".\"");
        }
        
        if (!output.contains("errors answering them")) {
            return CheckResult.wrong("Your program should show the user the hardest card(s) in the format \"The " +
                    "hardest cards are \"card#1\", \"card#2\"...\"card#n\". You have n errors answering them\".");
        }
        
        if (!output.contains("france")) {
            return CheckResult.wrong("Your program should print all the cards with the highest number of errors");
        }
        
        if (!output.contains("russia")) {
            return CheckResult.wrong("Your program should print all the cards with the highest number of errors");
        }
        
        if (!output.contains("\"france\"")) {
            return CheckResult.wrong("Your program should print the hardest card(s) in quotes");
        }
        
        if (!output.contains("\"russia\"")) {
            return CheckResult.wrong("Your program should print the hardest card(s) in quotes");
        }
    
        output = main.execute("exit").toLowerCase();
        if (!output.contains("bye")) {
            return CheckResult.wrong("Your program should print \"Bye bye!\" and terminate if the user enters " +
                    "\"exit\"");
        }
    
        if (!main.isFinished()) {
            return CheckResult.wrong("Your program should terminate if the user enters \"exit\"");
        }
        
        return CheckResult.correct();
    }
    
    //Test to check the "reset stats" action
    @DynamicTestingMethod
    CheckResult test15() {
        TestedProgram main = new TestedProgram();
        main.start();
        
        String output;
        
        main.execute("hardest card");
        
        main.execute("import");
        main.execute(capitalsFile);
        
        main.execute("hardest card");
        
        main.execute("ask");
        main.execute("1");
        main.execute("Tokyo");
        
        main.execute("hardest card");
        
        main.execute("import");
        main.execute(capitalsNewFile);
        
        output = main.execute("reset stats").toLowerCase();
        if (!output.contains("statistics have been reset")) {
            return CheckResult.wrong("Your program should notify the user if the stats have been reset with the " +
                    "message \"Card statistics have been reset.\"");
        }
        
        output = main.execute("hardest card").toLowerCase();
        if (!output.contains("no cards with errors")) {
            return CheckResult.wrong("Your program should reset the errors of all the cards back to zero");
        }
    
        output = main.execute("exit").toLowerCase();
        if (!output.contains("bye")) {
            return CheckResult.wrong("Your program should print \"Bye bye!\" and terminate if the user enters " +
                    "\"exit\"");
        }
    
        if (!main.isFinished()) {
            return CheckResult.wrong("Your program should terminate if the user enters \"exit\"");
        }
        
        return CheckResult.correct();
    }
    
    //Test to check the "log" action
    @DynamicTestingMethod
    CheckResult test16() {
        TestedProgram main = new TestedProgram();
        main.start();
        
        String output;
        
        main.execute("hardest card");
        
        main.execute("import");
        main.execute(capitalsFile);
        
        main.execute("hardest card");
        
        main.execute("ask");
        main.execute("1");
        main.execute("Tokyo");
        
        main.execute("hardest card");
        
        output = main.execute("reset stats").toLowerCase();
        if (!output.contains("statistics have been reset")) {
            return CheckResult.wrong("Your program should notify the user if the stats have been reset with the " +
                    "message \"Card statistics have been reset.\"");
        }
        
        output = main.execute("hardest card").toLowerCase();
        if (!output.contains("no cards with errors")) {
            return CheckResult.wrong("Your program should reset the errors of all the cards back to zero");
        }
        
        output = main.execute("log").toLowerCase();
        if (!output.contains("file name")) {
            return CheckResult.wrong("Your program should prompt the user for the name of the file to be saved with " +
                    "the message \"File name:\"");
        }
        
        output = main.execute("todayLog.txt").toLowerCase();
        if (!output.contains("log has been saved")) {
            return CheckResult.wrong("Your program should notify the user if the log file was saved with the message" +
                    " \"The log has been saved.\"");
        }
        
        boolean fileExists = checkFileExistence("todayLog.txt");
        if (!fileExists) {
            return CheckResult.wrong("Your program did not save the log file");
        }
        
        boolean validContent = checkLogFileLength("todayLog.txt");
        if (!validContent) {
            return CheckResult.wrong("The number of lines your program saves is less than the number of lines that " +
                    "was input/output to the console");
        }
    
        output = main.execute("exit").toLowerCase();
        if (!output.contains("bye")) {
            return CheckResult.wrong("Your program should print \"Bye bye!\" and terminate if the user enters " +
                    "\"exit\"");
        }
    
        if (!main.isFinished()) {
            return CheckResult.wrong("Your program should terminate if the user enters \"exit\"");
        }
        
        return CheckResult.correct();
    }
    
    //Test to check command line arguments"
    @DynamicTestingMethod
    CheckResult test17() {
        TestedProgram main = new TestedProgram();
        main.start("-export", "fridayThe13th.txt");
        
        String output;
        String[] lines;
        
        output = main.execute("hardest card").toLowerCase();
        lines = output.split("\n");
        if (lines[0].contains("hardest card")) {
            return CheckResult.wrong("The initial set of cards should be empty if no import argument is provided");
        }
        
        main.execute("add");
        main.execute("Movie");
        main.execute("A recorded sequence of images displayed on a screen at a rate sufficiently fast");
    
        output = main.execute("exit").toLowerCase();
        if (!output.contains("bye")) {
            return CheckResult.wrong("Your program should print \"Bye bye!\" and terminate if the user enters " +
                    "\"exit\"");
        }
        
        boolean fileExist = checkFileExistence("fridayThe13th.txt");
        if (!fileExist) {
            return CheckResult.wrong("Your program did not save a file after exiting");
        }
        
        boolean emptyFile = isEmpty("fridayThe13th.txt");
        if (emptyFile) {
            return CheckResult.wrong("The file your program saves is empty");
        }
    
        if (!main.isFinished()) {
            return CheckResult.wrong("Your program should terminate if the user enters \"exit\"");
        }
        
        return CheckResult.correct();
    }
    
    @DynamicTestingMethod
    CheckResult test18() {
        TestedProgram main = new TestedProgram();
        main.start("-export", "newFridayThe13th.txt", "-import", "fridayThe13th.txt");
    
        String output;
        
        main.execute("add");
        output = main.execute("Movie").toLowerCase();
        if (output.contains("definition of the card")) {
            return CheckResult.wrong("Seems like your program did not import the file specified in the " +
                    "command-line argument");
        }
        
        main.execute("remove");
        main.execute("Movie");
        
        main.execute("add");
        main.execute("Series");
        main.execute("A television program which consists of several episodes");
    
        output = main.execute("exit").toLowerCase();
        if (!output.contains("bye")) {
            return CheckResult.wrong("Your program should print \"Bye bye!\" and terminate if the user enters " +
                    "\"exit\"");
        }
    
        boolean fileExist = checkFileExistence("newFridayThe13th.txt");
        if (!fileExist) {
            return CheckResult.wrong("Your program did not save a file after exiting");
        }
    
        boolean emptyFile = isEmpty("newFridayThe13th.txt");
        if (emptyFile) {
            return CheckResult.wrong("The file your program saves is empty");
        }
    
        if (!main.isFinished()) {
            return CheckResult.wrong("Your program should terminate if the user enters \"exit\"");
        }
        
        return CheckResult.correct();
    }
    
    private boolean checkLogFileLength(String fileName) {
        int lineCount = 0;
        int minimumLineExpected = 32;
        
        File file = new File(fileName);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            
            while (reader.readLine() != null) {
                lineCount++;
            }
        } catch (IOException e) {}
        
        return minimumLineExpected < lineCount;
    }
    
    private boolean checkFileExistence(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }
    
    private boolean isEmpty(String fileName) {
        int lineCount = 0;
        
        File file = new File(fileName);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            
            while (reader.readLine() != null) {
                lineCount++;
            }
        } catch (IOException e) {}
        
        return lineCount <= 0;
    }
    
    private String getTerm(String output) {
        int start = output.indexOf("\"");
        int end = output.lastIndexOf("\"");

        if (start == -1 || end == - 1) {
            throw new WrongAnswer("The card should be surrounded with \"\" when you ask to print its definition!\n" +
                "For example: Print the definition of \"str()\"");
        }
        
        return output.substring((start + 1), end);
    }
    
    private static List<String> getListOfTerms() {
        return Arrays.asList(
                "print()",
                "str()",
                "son",
                "daughter",
                "uncle",
                "ankle");
    }
    
    private static List<String> getListOfDefinitions() {
        return Arrays.asList(
                "outputs text",
                "converts to a string",
                "a male child",
                "a female child",
                "a brother of one's parent",
                "a part of the body where the foot and the leg meet"
        );
    }
    
    //Extra wrongs invalidate the definition
    private static List<String> getListOfWrongDefinitions1() {
        return Arrays.asList(
                "outputs text line by line",
                "converts to a string and an integer",
                "a male child or a female child",
                "a female child or a male child",
                "a brother of one's parent or grandparents",
                "a part of the body where the foot and the leg meet or the arm"
        );
    }
    
    private static List<String> getListOfCountries() {
        return Arrays.asList(
                "Canada",
                "Netherlands",
                "Russia",
                "United Kingdom",
                "Nigeria",
                "Germany"
        );
    }
    
    private static List<String> getListOfCapitals() {
        return Arrays.asList(
                "Ottawa",
                "Amsterdam",
                "Moscow",
                "London",
                "Abuja",
                "Berlin"
        );
    }
    
}
