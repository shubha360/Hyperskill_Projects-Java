package minesweeper;

import java.util.Random;
import java.util.Scanner;

public class Minesweeper {

    char[][] mainField;
    char[][] playerField;
    int row = 9;
    int column = 9;

    public Minesweeper() {

        this.mainField = new char[row][column];
        this.playerField = new char[row][column];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                mainField[i][j] = playerField[i][j] = '.';
            }
        }
    }

    public void start() {

        Scanner scanner = new Scanner(System.in);
        System.out.print("How many mines do you want on the field? ");
        int mines = scanner.nextInt();
        fillWithMines(mines);
        applyHints();
        printField();

        for (int i = 0; i < mines; i++) {

            int selectedColumn;
            int selectedRow;

            while (true) {
                System.out.print("Set/delete mines marks (x and y coordinates): ");
                selectedColumn = scanner.nextInt();
                selectedRow = scanner.nextInt();

                if (selectedRow < 1 || selectedRow > this.row || selectedColumn < 1 || selectedColumn > this.column) {
                    System.out.println("Wrong coordinates! Please try again.");
                } else if (playerField[selectedRow - 1][selectedColumn - 1] != '.' &&
                    playerField[selectedRow - 1][selectedColumn - 1] != '*') {
                    System.out.println("There is a number here!");
                } else {
                    break;
                }
            }

            if (mainField[selectedRow - 1][selectedColumn - 1] == '.') {

                if (playerField[selectedRow - 1][selectedColumn - 1] == '.') {
                    i -= 1;
                    playerField[selectedRow - 1][selectedColumn - 1] = '*';
                } else if (playerField[selectedRow - 1][selectedColumn - 1] == '*') {
                    i -= 1;
                    playerField[selectedRow - 1][selectedColumn - 1] = '.';
                }
            } else if (mainField[selectedRow - 1][selectedColumn - 1] == 'X') {

                if (playerField[selectedRow - 1][selectedColumn - 1] == '.') {
                    i = i;
                    playerField[selectedRow - 1][selectedColumn - 1] = '*';
                } else if (playerField[selectedRow - 1][selectedColumn - 1] == '*') {
                    i -= 1;
                    playerField[selectedRow - 1][selectedColumn - 1] = '.';
                }
            }
            printField();
        }
        System.out.println("Congratulations! You found all the mines!");
    }

    public void fillWithMines(int mines) {

        Random random = new Random();

        for (int i = 0; i < mines; i++) {

            int row = random.nextInt(9);
            int column = random.nextInt(9);

            if (mainField[row][column] != 'X') {
                mainField[row][column] = 'X';
            } else {
                i--;
            }
        }
    }

    public void applyHints() {

        int mineCount = 0;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {

                if (mainField[i][j] == 'X') {
                    continue;
                }

                if ((i > 0 && i < row - 1) && (j > 0 && j < column - 1)) {

                    mineCount += checkCell(i - 1, j - 1 );
                    mineCount += checkCell(i - 1, j);
                    mineCount += checkCell(i - 1, j + 1);
                    mineCount += checkCell(i, j - 1);
                    mineCount += checkCell(i, j + 1);
                    mineCount += checkCell(i + 1, j - 1);
                    mineCount += checkCell(i + 1, j);
                    mineCount += checkCell(i + 1, j + 1);

                } else if ((i > 0 && i < row - 1) && j == 0) {

                    mineCount += checkCell(i - 1, j);
                    mineCount += checkCell(i - 1, j + 1);
                    mineCount += checkCell(i, j + 1);
                    mineCount += checkCell(i + 1, j);
                    mineCount += checkCell(i + 1, j + 1);

                } else if ((i > 0 && i < row - 1) && j == column - 1) {

                    mineCount += checkCell(i - 1, j);
                    mineCount += checkCell(i - 1, j - 1);
                    mineCount += checkCell(i, j - 1);
                    mineCount += checkCell(i + 1, j);
                    mineCount += checkCell(i + 1, j - 1);
                }
                else if (i == 0) {

                    if (j == 0) {
                        mineCount += checkCell(i, j + 1);
                        mineCount += checkCell(i + 1, j);
                        mineCount += checkCell(i + 1, j + 1);
                    } else if (j == column - 1) {
                        mineCount += checkCell(i, j - 1);
                        mineCount += checkCell(i + 1, j);
                        mineCount += checkCell(i + 1, j - 1);
                    } else {
                        mineCount += checkCell(i, j - 1);
                        mineCount += checkCell(i, j + 1);
                        mineCount += checkCell(i + 1, j - 1);
                        mineCount += checkCell(i + 1, j);
                        mineCount += checkCell(i + 1, j + 1);
                    }
                } else if (i == row - 1) {

                    if (j == 0) {
                        mineCount += checkCell(i, j + 1);
                        mineCount += checkCell(i - 1, j);
                        mineCount += checkCell(i - 1, j + 1);
                    } else if (j == column - 1) {
                        mineCount += checkCell(i, j - 1);
                        mineCount += checkCell(i - 1, j);
                        mineCount += checkCell(i - 1, j - 1);
                    } else {
                        mineCount += checkCell(i, j - 1);
                        mineCount += checkCell(i, j + 1);
                        mineCount += checkCell(i - 1, j - 1);
                        mineCount += checkCell(i - 1, j);
                        mineCount += checkCell(i - 1, j + 1);
                    }
                }

                if (mineCount > 0) {
                    mainField[i][j] = playerField[i][j] = Character.forDigit(mineCount, 10);
                }
                mineCount = 0;
            }
        }
    }

    private int checkCell(int row, int column) {

        if (mainField[row][column] == 'X') {
           return 1;
        }
        return 0;
    }

    private void printField() {

        System.out.println();
        System.out.println(" |123456789|");
        System.out.println("—|—————————|");

        for (int i = 0; i < row; i++) {
            System.out.print((i + 1) + "|");
            for (int j = 0; j < column; j++) {
                System.out.print(playerField[i][j]);
            }
            System.out.print("|");
            System.out.println();
        }

        System.out.println("—|—————————|");
    }

    public void tempPlayerField() {

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                System.out.print(playerField[i][j]);
            }
            System.out.println();
        }
    }

    public void tempMainField() {

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                System.out.print(mainField[i][j]);
            }
            System.out.println();
        }
    }
}
