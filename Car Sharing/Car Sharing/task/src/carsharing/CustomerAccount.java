package carsharing;

import java.util.Scanner;

public class CustomerAccount {

    int id;
    String name;
    int rentedCarId;

    public CustomerAccount(int id, String name, int rentedCarId) {
        this.id = id;
        this.name = name;
        this.rentedCarId = rentedCarId;
    }

    public CustomerAccount(CustomerAccount customerAccount) {
        this.id = customerAccount.id;
        this.name = customerAccount.name;
        this.rentedCarId = customerAccount.rentedCarId;
    }

    void startInteraction() {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            printCustomerMenu();

            int selection = Integer.parseInt(scanner.nextLine());
            System.out.println();

            switch (selection) {

                case 1:
                    DB_Handler.rentACar(this);
                    break;

                case 2:
                    DB_Handler.returnCar(this);
                    break;

                case 3:
                    DB_Handler.getCustomersRentedCar(this);
                    break;

                case 0:
                    return;
            }

        }
    }

    private void printCustomerMenu() {

        System.out.println("1. Rent a car\n" +
                "2. Return a rented car\n" +
                "3. My rented car\n" +
                "0. Back");
    }
}
