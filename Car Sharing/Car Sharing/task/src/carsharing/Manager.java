package carsharing;

import java.util.ArrayList;
import java.util.Scanner;

public class Manager {

    void startManaging() {

        Scanner scanner = new Scanner(System.in);

        while (true) {

            printManagerMenu();
            int selection = Integer.parseInt(scanner.nextLine());
            System.out.println();

            switch (selection) {

                case 1:

                    ArrayList<Company> companies = DB_Handler.obtainCompanyList();

                    if (companies.size() == 0) {
                        System.out.println("The company list is empty!");
                        System.out.println();
                    } else {
                        printCompanies(companies);

                        int companySelection = Integer.parseInt(scanner.nextLine());
                        System.out.println();

                        if (companySelection == 0) {
                            break;
                        }

                        Company currentCompany = new Company(companies.get(companySelection - 1));
                        currentCompany.startInteraction();
                    }
                    break;

                case 2:

                    System.out.println("Enter the company name:");
                    String companyName = scanner.nextLine();
                    DB_Handler.createCompany(companyName);
                    break;

                case 0:
                    return;
            }
        }
    }

    private void printManagerMenu() {

        System.out.println("" +
                "1. Company list\n" +
                "2. Create a company\n" +
                "0. Back"
        );
    }

    static void printCompanies(ArrayList<Company> companies) {

        System.out.println("Choose the company:");

        int id = 1;

        for (Company company : companies) {
            System.out.println(id + ". " + company.companyName);
            id++;
        }
        System.out.println("0. Back");
    }
}
