package tictactoe;

import java.util.Arrays;
import java.util.Scanner;

class TicTacToe {

    Maneuver firstManeuver = null;
    Maneuver secondManeuver = null;

    protected static char[][] mainGrid = new char[3][3];
    protected final Scanner scanner = new Scanner(System.in);

    static StringBuilder rowTracker = new StringBuilder("000111222");
    static StringBuilder columnTracker = new StringBuilder("000111222");

    static int depth = 0;

    TicTacToe() {}

    TicTacToe(String playerOne, String playerTwo) {

        for (char[] arr : mainGrid) {
            Arrays.fill(arr,' ');
        }

        switch (playerOne) {

            case "user":
                firstManeuver = new HumanManeuver();
                break;

            case "easy":
                firstManeuver = new EasyCpuManeuver();
                break;

            case "medium":
                firstManeuver = new MediumCpuManeuver();
                break;

            case "hard":
                firstManeuver = new HardCPUManeuver();
                break;
        }

        switch (playerTwo) {

            case "user":
                secondManeuver = new HumanManeuver();
                break;

            case "easy":
                secondManeuver = new EasyCpuManeuver();
                break;

            case "medium":
                secondManeuver = new MediumCpuManeuver();
                break;

            case "hard":
                secondManeuver = new HardCPUManeuver();
                break;

        }
    }

    void letsPlay() {

        printGrid();

        for (int i = 0; i < 9; i++) {

            if (i % 2 == 0) {
                firstManeuver.makeManeuver('X');
                printGrid();

                if (checkGrid('X')) {
                    System.out.println("X wins");
                    return;
                }
            } else {
                secondManeuver.makeManeuver('O');
                printGrid();

                if (checkGrid('O')) {
                    System.out.println("O wins");
                    return;
                }
            }
        }
        System.out.println("Draw");
    }

    void printGrid() {

        System.out.println("---------");
        for (char[] arr : mainGrid) {
            System.out.print("| ");
            for (char c : arr) {
                System.out.print(c + " ");
            }
            System.out.print("|\n");
        }
        System.out.println("---------");
    }

    private boolean checkGrid(char symbol) {

        return (mainGrid[0][0] == symbol && mainGrid[0][1] == symbol && mainGrid[0][2] == symbol) ||
                (mainGrid[1][0] == symbol && mainGrid[1][1] == symbol && mainGrid[1][2] == symbol) ||
                (mainGrid[2][0] == symbol && mainGrid[2][1] == symbol && mainGrid[2][2] == symbol) ||
                (mainGrid[0][0] == symbol && mainGrid[1][0] == symbol && mainGrid[2][0] == symbol) ||
                (mainGrid[0][1] == symbol && mainGrid[1][1] == symbol && mainGrid[2][1] == symbol) ||
                (mainGrid[0][2] == symbol && mainGrid[1][2] == symbol && mainGrid[2][2] == symbol) ||
                (mainGrid[0][0] == symbol && mainGrid[1][1] == symbol && mainGrid[2][2] == symbol) ||
                (mainGrid[0][2] == symbol && mainGrid[1][1] == symbol && mainGrid[2][0] == symbol);
    }
}