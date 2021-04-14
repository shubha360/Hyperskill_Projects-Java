package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        ArrayList<String> peopleList = readFile(new File(args[1]));
        Map<String, ArrayList<Integer>> words = mapWords(peopleList);


        while (true) {

            int selection = printMenu();

            switch (selection) {

                case 1:
                    find(peopleList, words);
                    break;

                case 2:
                    printList(peopleList);
                    break;

                case 0:
                    System.out.println("\nBye!");
                    return;

                default:
                    System.out.println("\nIncorrect option! Try again.");
                    break;
            }
        }
    }

    static ArrayList<String> readFile(File file) {

        ArrayList<String> list = new ArrayList<>();

        try (Scanner fileReader = new Scanner(file)) {

            while (fileReader.hasNextLine()) {
                list.add(fileReader.nextLine());
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
        return list;
    }

    static Map<String, ArrayList<Integer>> mapWords(ArrayList<String> lines) {

        Map<String, ArrayList<Integer>> map = new HashMap<>();
        int i = 0;

        for (String s : lines) {

            String[] words = s.split(" +");

            for (String word : words) {

                word = word.toUpperCase();

                if (map.containsKey(word)) {
                    map.get(word).add(i);
                    map.put(word, map.get(word));
                } else {
                    ArrayList<Integer> arrayList = new ArrayList<>();
                    arrayList.add(i);
                    map.put(word, arrayList);
                }
            }
            i++;
        }
        return map;
    }

    static int printMenu() {

        System.out.println("\n=== Menu ===\n" +
                "1. Find a person\n" +
                "2. Print all people\n" +
                "0. Exit");

        int selection = scanner.nextInt();
        scanner.nextLine();
        return selection;
    }

    static void printList(ArrayList<String> list) {

        System.out.println("\n=== List of people ===");
        for (String person : list) {
            System.out.println(person);
        }
    }

    static void find(ArrayList<String> list, Map<String, ArrayList<Integer>> map) {

        System.out.println("\nSelect a matching strategy: ALL, ANY, NONE");
        String strategy = scanner.nextLine();

        System.out.println("\nEnter a name or email to search all suitable people.");
        String query = scanner.nextLine();

        Search matchingStrategy = null;

        switch (strategy) {

            case "ALL":
                matchingStrategy = new AllSearch();
                matchingStrategy.search(list, map, query);
                break;

            case "ANY":
                matchingStrategy = new AnySearch();
                matchingStrategy.search(list, map, query);
                break;

            case "NONE":
                matchingStrategy = new NoneSearch();
                matchingStrategy.search(list, map, query);
                break;
        }
    }

    static void printMap(Map<String, ArrayList<Integer>> map) {

        System.out.println();
        for (var entry : map.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}
