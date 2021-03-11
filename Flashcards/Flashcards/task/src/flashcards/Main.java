package flashcards;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

class FlashCardProcessor {

    private final Scanner scanner = new Scanner(System.in);

    private final List<String> arguments;

    private final Map<String, String> flashCards = new HashMap<>();
    private final Map<String, Integer> statStorage = new HashMap<>();
    private final ArrayList<String> termStorage = new ArrayList<>();

    private final StringBuilder logMessage = new StringBuilder();

    private int hardestNumber = 0;

    public FlashCardProcessor(String[] args) {

        this.arguments = Arrays.asList(args);

        if (arguments.contains("-import")) {
            importCard(arguments.get(arguments.indexOf("-import") + 1));
        }
    }

    public void play() {

        while(true) {

            printSave("Input the action (add, remove, import, export, ask, exit" +
                    ", log, hardest card, reset stats):");
            String input = scanner.nextLine();
            saveUserInput(input);

            switch (input) {

                case "add":
                    addCard();
                    break;

                case "remove":
                    removeCard();
                    break;

                case "import":
                    importCard(null);
                    break;

                case "export":
                    exportCard(null);
                    break;

                case "ask":
                    ask();
                    break;

                case "exit":
                    exit();
                    return;

                case "log":
                    log();
                    break;

                case "hardest card":
                    hardestCard();
                    break;

                case "reset stats":
                    resetStats();
                    break;

//                default:
//                    print();
//                    break;
            }
        }
    }

    private void printSave(String st) {

        if (logMessage.length() == 0) {
            logMessage.append(st);
        } else {
            logMessage.append("\n").append(st);
        }
        System.out.println(st);
    }

    private void printNewLine() {
        logMessage.append("\n");
        System.out.println();
    }

    private void saveUserInput(String st) {
        logMessage.append("\n").append(st);
    }

    private void addCard() {

        printSave("The card:");
        String term = scanner.nextLine();
        saveUserInput(term);

        if (flashCards.containsKey(term)) {
            printSave("The card \"" + term + "\" already exists.");
            printNewLine();
            return;
        }

        printSave("The definition of the card:");
        String definition = scanner.nextLine();
        saveUserInput(definition);

        if (flashCards.containsValue(definition)) {
            printSave("The definition \"" + definition + "\" already exists.");
            printNewLine();
            return;
        }
        flashCards.put(term, definition);
        statStorage.put(term, 0);
        termStorage.add(term);
        printSave("The pair (\"" + term + "\":\""+ definition + "\") has been added.");
        printNewLine();
    }

    private void removeCard() {

        printSave("Which card?");
        String term = scanner.nextLine();
        saveUserInput(term);

        String s = flashCards.remove(term);

        if (s == null) {
            printSave("Can't remove \"" + term + "\": there is no such card.");
        } else {
            printSave("The card has been removed.");
            statStorage.remove(term);
            termStorage.remove(term);
        }
        printNewLine();
    }

    private void importCard(String fileName) {

        File file = null;

        if (fileName == null) {

            printSave("File name:");
            String fileNameInput = scanner.nextLine();
            saveUserInput(fileNameInput);
            file = new File(fileNameInput);
        } else {
            file = new File(fileName);
        }

        try (Scanner fileScanner = new Scanner(file)){

            int cardsToLoad = fileScanner.nextInt();
            fileScanner.nextLine();

            for (int i = 0; i < cardsToLoad; i++) {

                String[] arr = fileScanner.nextLine().split(" : ");

                flashCards.put(arr[0], arr[1]);
                statStorage.put(arr[0], Integer.parseInt(arr[2]));

                if (statStorage.get(arr[0]) > hardestNumber) {
                    hardestNumber = statStorage.get(arr[0]);
                }

                if (!termStorage.contains(arr[0])) {
                    termStorage.add(arr[0]);
                }
            }
            printSave(cardsToLoad + " cards have been loaded.");

        } catch (FileNotFoundException e) {
            printSave("File not found.");
        }
        printNewLine();
    }

    private void exportCard(String fileName) {

        File file = null;

        if (fileName == null) {

            printSave("File name:");
            String fileNameInput = scanner.nextLine();
            saveUserInput(fileNameInput);
            file = new File(fileNameInput);
        } else {
            file = new File(fileName);
        }

        try (PrintWriter printWriter = new PrintWriter(file)) {

            printWriter.println(flashCards.size());

            for (var entry : flashCards.entrySet()) {
                printWriter.println(entry.getKey() + " : " + entry.getValue() + " : " + statStorage.get(entry.getKey()));
            }

            printSave(flashCards.size() + " cards have been saved.");

        } catch (FileNotFoundException e) {
            printSave("File not found.");
        }
        printNewLine();
    }

    private void ask() {
        printSave("How many times to ask?");
        int n = scanner.nextInt();
        saveUserInput(String.valueOf(n));
        scanner.nextLine();

        for (int i = 0; i < n; i++) {

            String termToAsk = termStorage.get(i);

            printSave("Print the definition of \"" + termToAsk + "\":");
            String answer = scanner.nextLine();
            saveUserInput(answer);

            if (flashCards.get(termToAsk).equals(answer)) {
                printSave("Correct!");
            } else {

                statStorage.put(termToAsk, statStorage.get(termToAsk) + 1);
                if (statStorage.get(termToAsk) > hardestNumber) {
                    hardestNumber = statStorage.get(termToAsk);
                }

                if (flashCards.containsValue(answer)) {

                    for (var entry : flashCards.entrySet()) {

                        if (entry.getValue().equals(answer)) {
                            printSave("Wrong. The right answer is \"" + flashCards.get(termToAsk) +
                                    "\", but your definition is correct for " +
                                    "\"" + entry.getKey() + "\".");
                        }
                    }
                } else {
                    printSave("Wrong. The right answer is \"" + flashCards.get(termToAsk) + "\".");
                }
            }
        }
        printNewLine();
    }

    private void exit() {

        if (arguments.contains("-export")) {

            exportCard(arguments.get(arguments.indexOf("-export") + 1));
        }
        printSave("Bye bye");
    }

    private void log() {
        printSave("File name:");
        String fileName = scanner.nextLine();
        saveUserInput(fileName);

        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(logMessage.toString());
            printSave("The log has been saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        printNewLine();
    }

    private void hardestCard() {

        if (hardestNumber == 0) {
            printSave("There are no cards with errors.");
            printNewLine();
            return;
        }

        ArrayList<String> hardList = new ArrayList<>();

        for (var entry : statStorage.entrySet()) {

            if (entry.getValue() == hardestNumber) {
                hardList.add(entry.getKey());
            }
        }

        if (hardList.size() == 1) {
            printSave("The hardest card is \"" + hardList.get(0) + "\". You have " + hardestNumber + " errors answering it");
        } else {

            StringBuilder output = new StringBuilder("The hardest cards are");

            for (String s : hardList) {
                output.append(" \"").append(s).append("\",");
            }
            output.deleteCharAt(output.length() - 1);
            output.append(". You have ").append(hardestNumber).append(" errors answering them");
            printSave(output.toString());
        }
        printNewLine();
    }

    private void resetStats() {
        statStorage.replaceAll((k, v) -> 0);
        hardestNumber = 0;
        printSave("Card statistics have been reset.");
        printNewLine();
    }

    private void print() {
        for (var entry : flashCards.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " : " + statStorage.get(entry.getKey()));
        }
    }
}

public class Main {
    public static void main(String[] args) {

        new FlashCardProcessor(args).play();
    }
}
