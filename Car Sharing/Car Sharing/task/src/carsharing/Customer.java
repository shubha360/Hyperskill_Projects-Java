package carsharing;

import java.util.ArrayList;
import java.util.Scanner;

public class Customer {

    void startInteraction() {

        Scanner scanner = new Scanner(System.in);
        ArrayList<CustomerAccount> customers = DB_Handler.obtainCustomerList();

        if (customers.size() == 0) {
            System.out.println("The customer list is empty!");
            System.out.println();
        } else {
            printCustomers(customers);

            int companySelection = Integer.parseInt(scanner.nextLine());
            System.out.println();

            if (companySelection == 0) {
                return;
            }

            CustomerAccount currentCustomer = new CustomerAccount(customers.get(companySelection - 1));
            currentCustomer.startInteraction();
        }
    }

    private void printCustomers(ArrayList<CustomerAccount> customers) {

        System.out.println("Customer list:");

        int id = 1;

        for (CustomerAccount customer : customers) {
            System.out.println(id + ". " + customer.name);
            id++;
        }
        System.out.println("0. Back");
    }
}
