package budget;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class BudgetManager {

    private double balance;

    private Map<String, Double> foodPurchaseList;
    private double totalExpenseFood;

    private Map<String, Double> clothPurchaseList;
    private double totalExpenseCloth;

    private Map<String, Double> entertainmentPurchaseList;
    private double totalExpenseEntertainment;

    private Map<String, Double> otherPurchaseList;
    private double totalExpenseOther;

    private Map<String, Double> allPurchaseList;
    private double totalExpenseAll;

    private final Scanner scanner;

    public BudgetManager() {

        this.balance = 0.0;

        this.foodPurchaseList = new LinkedHashMap<>();
        this.clothPurchaseList = new LinkedHashMap<>();
        this.entertainmentPurchaseList = new LinkedHashMap<>();
        this.otherPurchaseList = new LinkedHashMap<>();
        this.allPurchaseList = new LinkedHashMap<>();

        this.totalExpenseFood = 0.0;
        this.totalExpenseCloth = 0.0;
        this.totalExpenseEntertainment = 0.0;
        this.totalExpenseOther = 0.0;
        this.totalExpenseAll = 0.0;

        this.scanner = new Scanner(System.in);
    }

    public void startProgram() {

        while (true) {

            System.out.println("Choose your action:\n" +
                    "1) Add income\n" +
                    "2) Add purchase\n" +
                    "3) Show list of purchases\n" +
                    "4) Balance\n" +
                    "5) Save\n" +
                    "6) Load\n" +
                    "7) Analyze (Sort)\n" +
                    "0) Exit");

            int selection = scanner.nextInt();
            scanner.nextLine();

            switch (selection) {

                case 1:
                    addIncome();
                    break;

                case 2:
                    addPurchase();
                    break;

                case 3:
                    showList();
                    break;

                case 4:
                    showBalance();
                    break;

                case 5:
                    saveData();
                    break;

                case 6:
                    loadData();
                    break;

                case 7:
                    analyze();
                    break;

                case 0:

                    System.out.println();
                    System.out.println("Bye!");
                    return;
            }
        }
    }

    private void showBalance() {

        System.out.println();
        System.out.printf("Balance: $%.2f\n", balance);
        System.out.println();
    }

    private void addIncome() {

        System.out.println();
        System.out.println("Enter income:");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        balance += amount;
        System.out.println("Income was added!");
        System.out.println();
    }

    private void addPurchase() {

        String name;
        double price;

        while (true) {

            System.out.println();
            System.out.println("Choose the type of purchase\n" +
                    "1) Food\n" +
                    "2) Clothes\n" +
                    "3) Entertainment\n" +
                    "4) Other\n" +
                    "5) Back");

            int selection = scanner.nextInt();
            scanner.nextLine();

            switch (selection) {

                case 1:

                    System.out.println();

                    name = obtainName();
                    price = obtainPrice();

                    foodPurchaseList.put(name, price);
                    totalExpenseFood += price;

                    allPurchaseList.put(name, price);
                    totalExpenseAll += price;
                    balance -= price;

                    System.out.println();
                    break;

                case 2:

                    System.out.println();

                    name = obtainName();
                    price = obtainPrice();

                    clothPurchaseList.put(name, price);
                    totalExpenseCloth += price;

                    allPurchaseList.put(name, price);
                    totalExpenseAll += price;
                    balance -= price;

                    System.out.println();
                    break;

                case 3:

                    System.out.println();

                    name = obtainName();
                    price = obtainPrice();

                    entertainmentPurchaseList.put(name, price);
                    totalExpenseEntertainment += price;

                    allPurchaseList.put(name, price);
                    totalExpenseAll += price;
                    balance -= price;

                    System.out.println();
                    break;

                case 4:

                    System.out.println();

                    name = obtainName();
                    price = obtainPrice();

                    otherPurchaseList.put(name, price);
                    totalExpenseOther += price;

                    allPurchaseList.put(name, price);
                    totalExpenseAll += price;
                    balance -= price;

                    System.out.println();
                    break;

                case 5:
                    System.out.println();
                    return;
            }
        }
    }

    private String obtainName() {

        System.out.println("Enter purchase name:");
        return scanner.nextLine();
    }

    private double obtainPrice() {

        System.out.println("Enter its price:");
        double price = scanner.nextDouble();
        scanner.nextLine();
        return price;
    }

    private void showList() {

        if (allPurchaseList.isEmpty()) {

            System.out.println();
            System.out.println("The purchase list is empty.");
            System.out.println();
            return;
        }

        while (true) {

            System.out.println();
            System.out.println("Choose the type of purchases\n" +
                    "1) Food\n" +
                    "2) Clothes\n" +
                    "3) Entertainment\n" +
                    "4) Other\n" +
                    "5) All\n" +
                    "6) Back");

            int selection = scanner.nextInt();
            scanner.nextLine();

            switch (selection) {

                case 1:

                    System.out.println();
                    System.out.println("Food:");

                    if (foodPurchaseList.isEmpty()) {
                        System.out.println("The purchase list is empty!");
                    } else {
                        printList(foodPurchaseList);
                        System.out.printf("Total sum: $%.2f\n", totalExpenseFood);
                    }

                    System.out.println();
                    break;

                case 2:

                    System.out.println();
                    System.out.println("Cloth:");

                    if (clothPurchaseList.isEmpty()) {
                        System.out.println("The purchase list is empty!");
                    } else {
                        printList(clothPurchaseList);
                        System.out.printf("Total sum: $%.2f\n", totalExpenseCloth);
                    }

                    System.out.println();
                    break;

                case 3:

                    System.out.println();
                    System.out.println("Entertainment:");

                    if (entertainmentPurchaseList.isEmpty()) {
                        System.out.println("The purchase list is empty!");
                    } else {
                        printList(entertainmentPurchaseList);
                        System.out.printf("Total sum: $%.2f\n", totalExpenseEntertainment);
                    }

                    System.out.println();
                    break;

                case 4:

                    System.out.println();
                    System.out.println("Other:");

                    if (entertainmentPurchaseList.isEmpty()) {
                        System.out.println("The purchase list is empty!");
                    } else {
                        printList(otherPurchaseList);
                        System.out.printf("Total sum: $%.2f\n", totalExpenseOther);
                    }

                    System.out.println();
                    break;

                case 5:

                    System.out.println();
                    System.out.println("All:");
                    printList(allPurchaseList);
                    System.out.printf("Total sum: $%.2f\n", totalExpenseAll);
                    System.out.println();
                    break;

                case 6:
                    System.out.println();
                    return;
            }
        }
    }

    private void printList(Map<String, Double> list) {

        for (var entry : list.entrySet()) {
            System.out.printf("%s $%.2f\n", entry.getKey(), entry.getValue());
        }
    }

    private void saveData() {

        try (PrintWriter printWriter = new PrintWriter("purchases.txt")) {

            printWriter.println(balance);
            printWriter.println();

            printWriter.println(foodPurchaseList.size());
            for (var entry : foodPurchaseList.entrySet()) {
                printWriter.println(entry.getKey());
                printWriter.println(entry.getValue());
            }
            printWriter.println();

            printWriter.println(clothPurchaseList.size());
            for (var entry : clothPurchaseList.entrySet()) {
                printWriter.println(entry.getKey());
                printWriter.println(entry.getValue());
            }
            printWriter.println();

            printWriter.println(entertainmentPurchaseList.size());
            for (var entry : entertainmentPurchaseList.entrySet()) {
                printWriter.println(entry.getKey());
                printWriter.println(entry.getValue());
            }
            printWriter.println();

            printWriter.println(otherPurchaseList.size());
            for (var entry : otherPurchaseList.entrySet()) {
                printWriter.println(entry.getKey());
                printWriter.println(entry.getValue());
            }

        } catch (IOException e) {
            System.out.println("Exception occurred.");
        }

        System.out.println();
        System.out.println("Purchases were saved!");
        System.out.println();
    }

    private void loadData() {

        this.foodPurchaseList = new LinkedHashMap<>();
        this.clothPurchaseList = new LinkedHashMap<>();
        this.entertainmentPurchaseList = new LinkedHashMap<>();
        this.otherPurchaseList = new LinkedHashMap<>();
        this.allPurchaseList = new LinkedHashMap<>();

        this.totalExpenseFood = 0.0;
        this.totalExpenseCloth = 0.0;
        this.totalExpenseEntertainment = 0.0;
        this.totalExpenseOther = 0.0;
        this.totalExpenseAll = 0.0;

        try (Scanner loader = new Scanner(new File("purchases.txt"))) {

            if (loader.hasNextDouble()) {
                balance = Double.parseDouble(loader.nextLine());
                loader.nextLine();
            }

            int foodPurchaseCount = Integer.parseInt(loader.nextLine());

            for (int i = 0; i < foodPurchaseCount; i++) {

                String name = loader.nextLine();
                double price = Double.parseDouble(loader.nextLine());

                foodPurchaseList.put(name, price);
                allPurchaseList.put(name, price);
                totalExpenseFood += price;
                totalExpenseAll += price;
            }

            loader.nextLine();

            int clothPurchaseCount = Integer.parseInt(loader.nextLine());

            for (int i = 0; i < clothPurchaseCount; i++) {

                String name = loader.nextLine();
                double price = Double.parseDouble(loader.nextLine());

                clothPurchaseList.put(name, price);
                allPurchaseList.put(name, price);
                totalExpenseCloth += price;
                totalExpenseAll += price;
            }

            loader.nextLine();

            int entertainmentPurchaseCount = Integer.parseInt(loader.nextLine());

            for (int i = 0; i < entertainmentPurchaseCount; i++) {

                String name = loader.nextLine();
                double price = Double.parseDouble(loader.nextLine());

                entertainmentPurchaseList.put(name, price);
                allPurchaseList.put(name, price);
                totalExpenseEntertainment += price;
                totalExpenseAll += price;
            }

            loader.nextLine();

            int otherPurchaseCount = Integer.parseInt(loader.nextLine());

            for (int i = 0; i < otherPurchaseCount; i++) {

                String name = loader.nextLine();
                double price = Double.parseDouble(loader.nextLine());

                otherPurchaseList.put(name, price);
                allPurchaseList.put(name, price);
                totalExpenseOther += price;
                totalExpenseAll += price;
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }

        System.out.println();
        System.out.println("Purchases were loaded!");
        System.out.println();
    }

    private void analyze() {

        while (true) {

            System.out.println();
            System.out.println("How do you want to sort?\n" +
                    "1) Sort all purchases\n" +
                    "2) Sort by type\n" +
                    "3) Sort certain type\n" +
                    "4) Back");

            int selection = scanner.nextInt();
            scanner.nextLine();

            switch (selection) {

                case 1:

                    System.out.println();

                    if (allPurchaseList.isEmpty()) {
                        System.out.println("The purchase list is empty!");
                        break;
                    }

                    ArrayList<Product> sortedList = toSortedArray(allPurchaseList);

                    System.out.println("All:");

                    for (Product p : sortedList) {
                        System.out.printf("%s $%.2f\n", p.name, p.price);
                    }
                    System.out.printf("Total $%.2f\n", totalExpenseAll);

                    break;

                case 2:

                    System.out.println();

                    ArrayList<Product> priceByCategory = new ArrayList<>(4);

                    addToSortedArray(priceByCategory, new Product("Food", totalExpenseFood));
                    addToSortedArray(priceByCategory, new Product("Clothes", totalExpenseCloth));
                    addToSortedArray(priceByCategory, new Product("Entertainment", totalExpenseEntertainment));
                    addToSortedArray(priceByCategory, new Product("Other", totalExpenseOther));

                    System.out.println("Types:");

                    for (Product p : priceByCategory) {
                        System.out.printf("%s - $%.2f\n", p.name, p.price);
                    }

                    System.out.printf("Total sum: $%.2f\n", totalExpenseAll);
                    break;

                case 3:

                    System.out.println();
                    System.out.println("Choose the type of purchase\n" +
                             "1) Food\n" +
                             "2) Clothes\n" +
                             "3) Entertainment\n" +
                             "4) Other");

                    int selectCategory = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println();

                    switch (selectCategory) {

                        case 1:

                            if (foodPurchaseList.isEmpty()) {
                                System.out.println("The purchase list is empty!");
                                break;
                            }

                            System.out.println("Food:");
                            ArrayList<Product> foodList = toSortedArray(foodPurchaseList);

                            for (Product p : foodList) {
                                System.out.printf("%s $%.2f\n", p.name, p.price);
                            }
                            System.out.printf("Total sum: $%.2f\n", totalExpenseFood);
                            break;

                        case 2:

                            if (clothPurchaseList.isEmpty()) {
                                System.out.println("The purchase list is empty!");
                                break;
                            }

                            System.out.println("Cloth:");
                            ArrayList<Product> clothList = toSortedArray(foodPurchaseList);

                            for (Product p : clothList) {
                                System.out.printf("%s $%.2f\n", p.name, p.price);
                            }
                            System.out.printf("Total sum: $%.2f\n", totalExpenseCloth);
                            break;

                        case 3:

                            if (entertainmentPurchaseList.isEmpty()) {
                                System.out.println("The purchase list is empty!");
                                break;
                            }

                            System.out.println("Cloth:");
                            ArrayList<Product> entertainmentList = toSortedArray(foodPurchaseList);

                            for (Product p : entertainmentList) {
                                System.out.printf("%s $%.2f\n", p.name, p.price);
                            }
                            System.out.printf("Total sum: $%.2f\n", totalExpenseCloth);
                            break;

                        case 4:

                            if (otherPurchaseList.isEmpty()) {
                                System.out.println("The purchase list is empty!");
                                break;
                            }

                            System.out.println("Other:");
                            ArrayList<Product> otherList = toSortedArray(foodPurchaseList);

                            for (Product p : otherList) {
                                System.out.printf("%s $%.2f\n", p.name, p.price);
                            }
                            System.out.printf("Total sum: $%.2f\n", totalExpenseCloth);
                            break;
                    }
                    break;

                case 4:
                    System.out.println();
                    return;
            }
        }
    }

    private ArrayList<Product> toSortedArray(Map<String, Double> list) {

        ArrayList<Product> sortedList = new ArrayList<>(list.size());

        for (var entry: list.entrySet()) {

            Product product = new Product(entry.getKey(), entry.getValue());

            if (sortedList.isEmpty()) {
                sortedList.add(product);
            } else {

                int i = 0;

                while (i < sortedList.size() && sortedList.get(i).price >= product.price) {
                    i++;
                }
                sortedList.add(i, product);
            }
        }
        return sortedList;
    }

    private void addToSortedArray(ArrayList<Product> array, Product product) {

        if (array.isEmpty()) {
            array.add(product);
        } else {

            int i = 0;

            while (i < array.size() && array.get(i).price >= product.price) {
                i++;
            }
            array.add(i, product);
        }
    }
}
