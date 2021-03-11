package cinema;

import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {
        // Write your code here

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the number of rows:");

        final int rows = sc.nextInt();

        System.out.println("Enter the number of seats in each row:");

        final int seats = sc.nextInt();

        char[][] cinemaGrid = new char[rows][seats];

        initializeGrid(cinemaGrid, rows, seats);

        int ticketSold = 0;
        int currentIncome = 0;
        int totalIncome = ((rows * seats) <= 60) ? (rows * seats * 10) : (((rows / 2) * seats * 10) + ((rows - (rows / 2)) * seats * 8));

        while (true) {

            System.out.println("\n1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");

            int selection = sc.nextInt();

            switch (selection) {

                case 1 :
                    System.out.println();
                    printCinema(cinemaGrid, rows, seats);
                    break;

                case 2 :
                     while (true) {

                         System.out.println("\nEnter a row number:");

                         int selectedRow = sc.nextInt();

                         System.out.println("Enter a seat number in that row:");

                         int selectedSeat = sc.nextInt();

                         if ((selectedRow < 1 || selectedRow > rows) || (selectedSeat < 1 || selectedSeat > seats)) {

                             System.out.println("\nWrong input!");
                         } else if (cinemaGrid[selectedRow - 1][selectedSeat - 1] == 'B') {
                             System.out.println("\nThat ticket has already been purchased!");
                         } else {
                             cinemaGrid[selectedRow - 1][selectedSeat - 1] = 'B';
                             ticketSold++;

                             if (rows * seats < 60) {
                                 System.out.println("\nTicket price: $10");
                                 currentIncome += 10;
                             } else {

                                 if (selectedRow <= (rows / 2)) {

                                     System.out.println("\nTicket price: $10");
                                     currentIncome += 10;
                                 } else {
                                     System.out.println("\nTicket price: $8");
                                     currentIncome += 8;
                                 }
                             }
                             break;
                         }
                     }
                     break;

                case 3 :

                    double percentage = ( (100.00 / (rows * seats)) * (double) ticketSold);

                    System.out.println("\nNumber of purchased tickets: " + ticketSold);
                    System.out.printf("Percentage: %.2f%%\n", percentage);
                    System.out.println("Current income: $" + currentIncome);
                    System.out.println("Total income: $" + totalIncome);
                    break;

                case 0 :
                    return;
            }
        }
    }

    public static void initializeGrid(char[][] grid, int rows, int columns) {

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                grid[i][j] = 'S';
            }
        }
    }

    public static void printCinema(char[][] grid, int rows, int seats) {

        System.out.println("\nCinema:");

        for (int i = -1; i < rows; i++) {

            if (i == -1) {

                System.out.print(" ");
                for (int j = 1; j <= seats; j++) {

                    System.out.print(" " + j);
                }
                System.out.println();
            } else {
                for (int k = 0; k < seats; k++) {

                    if (k == 0) {
                        System.out.print((i + 1) + " " + grid[i][k]);
                    } else {
                        System.out.print(" " + grid[i][k]);
                    }
                }
                System.out.println();
            }
        }
    }
}