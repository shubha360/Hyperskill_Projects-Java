package tictactoe;

import java.util.InputMismatchException;

class HumanManeuver extends TicTacToe implements Maneuver {

    @Override
    public void makeManeuver(char symbol) {

        int row;
        int column;

        while (true) {

            System.out.print("Enter the coordinates: ");

            try {
                row = scanner.nextInt();
                column = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("You should enter numbers!");
                scanner.next();
                continue;
            }

            if (row < 1 || row > 3 || column < 1 || column > 3) {
                System.out.println("Coordinates should be from 1 to 3!");
            } else if (mainGrid[row - 1][column - 1] != ' ') {
                System.out.println("This cell is occupied! Choose another one!");
            } else {
                break;
            }
        }
        mainGrid[row - 1][column - 1] = symbol;
        rowTracker.deleteCharAt(rowTracker.indexOf(String.valueOf(row - 1)));
        columnTracker.deleteCharAt(columnTracker.indexOf(String.valueOf(column - 1)));
        depth++;
    }
}
