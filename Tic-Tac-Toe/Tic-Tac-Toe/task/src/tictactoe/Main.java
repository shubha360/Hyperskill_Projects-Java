package tictactoe;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // write your code here
        Scanner sc = new Scanner(System.in);

        char[] grid = new char[9];

        Arrays.fill(grid, ' ');

        printGrid(grid);

        for (int i = 0; i < 9; i++) {

            try {

                System.out.print("Enter the coordinates: ");

                int x = sc.nextInt();
                int y = sc.nextInt();
                int target = -1;

                if ((x < 1 || x > 3) || (y < 1 || y > 3)) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    i--;
                    continue;
                } else {
                    if (x == 1) {
                        target = y - x;
                    } else if (x == 2) {
                        target = x + y;
                    } else if (x == 3 && y == 1) {
                        target = 6;
                    } else if (x == 3 && y == 2) {
                        target = 7;
                    } else if (x == 3 && y == 3) {
                        target = 8;
                    }
                }

                if (target != -1 && grid[target] == ' ') {

                    grid[target] = i % 2 == 0 ? 'X' : 'O';
                    printGrid(grid);

                    if (xWon(grid)) {
                        System.out.println("X wins");
                        return;
                    } else if (oWon(grid)) {
                        System.out.println("O wins");
                        return;
                    }
                } else {
                    System.out.println("This cell is occupied! Choose another one!");
                    i--;
                }

            } catch (InputMismatchException e) {
                System.out.println("You should enter numbers!");
                i--;
                sc.next();
            }
        }

        System.out.println("Draw!");
    }
    
    public static void printGrid(char[] grid) {

        System.out.println("---------");

        System.out.println("| " + grid[0] + " " + grid[1] + " " + grid[2] + " |");
        System.out.println("| " + grid[3] + " " + grid[4] + " " + grid[5] + " |");
        System.out.println("| " + grid[6] + " " + grid[7] + " " + grid[8] + " |");

        System.out.println("---------");
    }

    public static boolean xWon(char[] grid) {

        if ((grid[0] == 'X' && grid[1] == 'X' && grid[2] == 'X') ||
                (grid[3] == 'X' && grid[4] == 'X' && grid[5] == 'X') ||
                (grid[6] == 'X' && grid[7] == 'X' && grid[8] == 'X') ||
                (grid[0] == 'X' && grid[3] == 'X' && grid[6] == 'X') ||
                (grid[1] == 'X' && grid[4] == 'X' && grid[7] == 'X') ||
                (grid[2] == 'X' && grid[5] == 'X' && grid[8] == 'X') ||
                (grid[0] == 'X' && grid[4] == 'X' && grid[8] == 'X') ||
                (grid[2] == 'X' && grid[4] == 'X' && grid[6] == 'X')) {
            return true;
        }

        return false;
    }

    public static boolean oWon(char[] grid) {

        if ((grid[0] == 'O' && grid[1] == 'O' && grid[2] == 'O') ||
                (grid[3] == 'O' && grid[4] == 'O' && grid[5] == 'O') ||
                (grid[6] == 'O' && grid[7] == 'O' && grid[8] == 'O') ||
                (grid[0] == 'O' && grid[3] == 'O' && grid[6] == 'O') ||
                (grid[1] == 'O' && grid[4] == 'O' && grid[7] == 'O') ||
                (grid[2] == 'O' && grid[5] == 'O' && grid[8] == 'O') ||
                (grid[0] == 'O' && grid[4] == 'O' && grid[8] == 'O') ||
                (grid[2] == 'O' && grid[4] == 'O' && grid[6] == 'O')) {
            return true;
        }

        return false;
    }
}
