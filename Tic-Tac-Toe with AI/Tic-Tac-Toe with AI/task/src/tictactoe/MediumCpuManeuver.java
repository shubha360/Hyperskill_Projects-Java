package tictactoe;

import java.util.Random;

public class MediumCpuManeuver extends TicTacToe implements Maneuver {

    @Override
    public void makeManeuver(char symbol) {

        System.out.println("Making move level \"medium\"");
        depth++;

        if (mainGrid[0][0] == ' ' &&
                (mainGrid[1][1] == symbol && mainGrid[2][2] == symbol ||
                mainGrid[1][0] == symbol && mainGrid[2][0] == symbol ||
                mainGrid[0][1] == symbol && mainGrid[0][2] == symbol)) {

            mainGrid[0][0] = symbol;
            rowTracker.deleteCharAt(rowTracker.indexOf(String.valueOf(0)));
            columnTracker.deleteCharAt(columnTracker.indexOf(String.valueOf(0)));
            return;
        }

        if (mainGrid[0][1] == ' ' &&
                (mainGrid[0][0] == symbol && mainGrid[0][2] == symbol ||
                mainGrid[1][1] == symbol && mainGrid[2][1] == symbol)) {

            mainGrid[0][1] = symbol;
            rowTracker.deleteCharAt(rowTracker.indexOf(String.valueOf(0)));
            columnTracker.deleteCharAt(columnTracker.indexOf(String.valueOf(1)));
            return;
        }

        if (mainGrid[0][2] == ' ' &&
                (mainGrid[1][1] == symbol && mainGrid[2][0] == symbol ||
                mainGrid[0][0] == symbol && mainGrid[0][1] == symbol ||
                mainGrid[1][2] == symbol && mainGrid[2][2] == symbol)) {

            mainGrid[0][2] = symbol;
            rowTracker.deleteCharAt(rowTracker.indexOf(String.valueOf(0)));
            columnTracker.deleteCharAt(columnTracker.indexOf(String.valueOf(2)));
            return;
        }

        if (mainGrid[1][0] == ' ' &&
                (mainGrid[0][0] == symbol && mainGrid[2][0] == symbol ||
                mainGrid[1][1] == symbol && mainGrid[1][2] == symbol)) {

            mainGrid[1][0] = symbol;
            rowTracker.deleteCharAt(rowTracker.indexOf(String.valueOf(1)));
            columnTracker.deleteCharAt(columnTracker.indexOf(String.valueOf(0)));
            return;
        }

        if (mainGrid[1][1] == ' ' &&
                (mainGrid[0][0] == symbol && mainGrid[2][2] == symbol ||
                mainGrid[0][2] == symbol && mainGrid[2][0] == symbol ||
                mainGrid[0][1] == symbol && mainGrid[2][1] == symbol ||
                mainGrid[1][0] == symbol && mainGrid[1][2] == symbol)) {

            mainGrid[1][1] = symbol;
            rowTracker.deleteCharAt(rowTracker.indexOf(String.valueOf(1)));
            columnTracker.deleteCharAt(columnTracker.indexOf(String.valueOf(1)));
            return;
        }

        if (mainGrid[1][2] == ' ' &&
                (mainGrid[1][1] == symbol && mainGrid[1][0] == symbol ||
                mainGrid[2][2] == symbol && mainGrid[0][2] == symbol)) {

            mainGrid[1][2] = symbol;
            rowTracker.deleteCharAt(rowTracker.indexOf(String.valueOf(1)));
            columnTracker.deleteCharAt(columnTracker.indexOf(String.valueOf(2)));
            return;
        }

        if (mainGrid[2][0] == ' ' &&
                (mainGrid[1][1] == symbol && mainGrid[0][2] == symbol ||
                mainGrid[1][0] == symbol && mainGrid[0][0] == symbol ||
                mainGrid[2][1] == symbol && mainGrid[2][2] == symbol)) {

            mainGrid[2][0] = symbol;
            rowTracker.deleteCharAt(rowTracker.indexOf(String.valueOf(2)));
            columnTracker.deleteCharAt(columnTracker.indexOf(String.valueOf(0)));
            return;
        }

        if (mainGrid[2][1] == ' ' &&
                (mainGrid[1][1] == symbol && mainGrid[0][1] == symbol ||
                mainGrid[2][0] == symbol && mainGrid[2][2] == symbol)) {

            mainGrid[2][1] = symbol;
            rowTracker.deleteCharAt(rowTracker.indexOf(String.valueOf(2)));
            columnTracker.deleteCharAt(columnTracker.indexOf(String.valueOf(1)));
            return;
        }

        if (mainGrid[2][2] == ' ' &&
                (mainGrid[1][1] == symbol && mainGrid[0][0] == symbol ||
                mainGrid[2][1] == symbol && mainGrid[2][0] == symbol ||
                mainGrid[1][2] == symbol && mainGrid[0][2] == symbol)) {

            mainGrid[2][2] = symbol;
            rowTracker.deleteCharAt(rowTracker.indexOf(String.valueOf(2)));
            columnTracker.deleteCharAt(columnTracker.indexOf(String.valueOf(2)));
            return;
        }

        char oppositionSymbol = symbol == 'O' ? 'X' : 'O';

        if (mainGrid[0][0] == ' ' &&
                (mainGrid[1][1] == oppositionSymbol && mainGrid[2][2] == oppositionSymbol ||
                mainGrid[1][0] == oppositionSymbol && mainGrid[2][0] == oppositionSymbol ||
                mainGrid[0][1] == oppositionSymbol && mainGrid[0][2] == oppositionSymbol)) {

            mainGrid[0][0] = symbol;
            rowTracker.deleteCharAt(rowTracker.indexOf(String.valueOf(0)));
            columnTracker.deleteCharAt(columnTracker.indexOf(String.valueOf(0)));
            return;
        }

        if (mainGrid[0][1] == ' ' &&
                (mainGrid[0][0] == oppositionSymbol && mainGrid[0][2] == oppositionSymbol ||
                mainGrid[1][1] == oppositionSymbol && mainGrid[2][1] == oppositionSymbol)) {

            mainGrid[0][1] = symbol;
            rowTracker.deleteCharAt(rowTracker.indexOf(String.valueOf(0)));
            columnTracker.deleteCharAt(columnTracker.indexOf(String.valueOf(1)));
            return;
        }

        if (mainGrid[0][2] == ' ' &&
                (mainGrid[1][1] == oppositionSymbol && mainGrid[2][0] == oppositionSymbol ||
                mainGrid[0][0] == oppositionSymbol && mainGrid[0][1] == oppositionSymbol ||
                mainGrid[1][2] == oppositionSymbol && mainGrid[2][2] == oppositionSymbol)) {

            mainGrid[0][2] = symbol;
            rowTracker.deleteCharAt(rowTracker.indexOf(String.valueOf(0)));
            columnTracker.deleteCharAt(columnTracker.indexOf(String.valueOf(2)));
            return;
        }

        if (mainGrid[1][0] == ' ' &&
                (mainGrid[0][0] == oppositionSymbol && mainGrid[2][0] == oppositionSymbol ||
                mainGrid[1][1] == oppositionSymbol && mainGrid[1][2] == oppositionSymbol)) {

            mainGrid[1][0] = symbol;
            rowTracker.deleteCharAt(rowTracker.indexOf(String.valueOf(1)));
            columnTracker.deleteCharAt(columnTracker.indexOf(String.valueOf(0)));
            return;
        }

        if (mainGrid[1][1] == ' ' &&
                (mainGrid[0][0] == oppositionSymbol && mainGrid[2][2] == oppositionSymbol ||
                mainGrid[0][2] == oppositionSymbol && mainGrid[2][0] == oppositionSymbol ||
                mainGrid[0][1] == oppositionSymbol && mainGrid[2][1] == oppositionSymbol ||
                mainGrid[1][0] == oppositionSymbol && mainGrid[1][2] == oppositionSymbol)) {

            mainGrid[1][1] = symbol;
            rowTracker.deleteCharAt(rowTracker.indexOf(String.valueOf(1)));
            columnTracker.deleteCharAt(columnTracker.indexOf(String.valueOf(1)));
            return;
        }

        if (mainGrid[1][2] == ' ' &&
                (mainGrid[1][1] == oppositionSymbol && mainGrid[1][0] == oppositionSymbol ||
                mainGrid[2][2] == oppositionSymbol && mainGrid[0][2] == oppositionSymbol)) {

            mainGrid[1][2] = symbol;
            rowTracker.deleteCharAt(rowTracker.indexOf(String.valueOf(1)));
            columnTracker.deleteCharAt(columnTracker.indexOf(String.valueOf(2)));
            return;
        }

        if (mainGrid[2][0] == ' ' &&
                (mainGrid[1][1] == oppositionSymbol && mainGrid[0][2] == oppositionSymbol ||
                mainGrid[1][0] == oppositionSymbol && mainGrid[0][0] == oppositionSymbol ||
                mainGrid[2][1] == oppositionSymbol && mainGrid[2][2] == oppositionSymbol)) {

            mainGrid[2][0] = symbol;
            rowTracker.deleteCharAt(rowTracker.indexOf(String.valueOf(2)));
            columnTracker.deleteCharAt(columnTracker.indexOf(String.valueOf(0)));
            return;
        }

        if (mainGrid[2][1] == ' ' &&
                (mainGrid[1][1] == oppositionSymbol && mainGrid[0][1] == oppositionSymbol ||
                mainGrid[2][0] == oppositionSymbol && mainGrid[2][2] == oppositionSymbol)) {

            mainGrid[2][1] = symbol;
            rowTracker.deleteCharAt(rowTracker.indexOf(String.valueOf(2)));
            columnTracker.deleteCharAt(columnTracker.indexOf(String.valueOf(1)));
            return;
        }

        if (mainGrid[2][2] == ' ' &&
                (mainGrid[1][1] == oppositionSymbol && mainGrid[0][0] == oppositionSymbol ||
                mainGrid[2][1] == oppositionSymbol && mainGrid[2][0] == oppositionSymbol ||
                mainGrid[1][2] == oppositionSymbol && mainGrid[0][2] == oppositionSymbol)) {

            mainGrid[2][2] = symbol;
            rowTracker.deleteCharAt(rowTracker.indexOf(String.valueOf(2)));
            columnTracker.deleteCharAt(columnTracker.indexOf(String.valueOf(2)));
            return;
        }

        Random random = new Random();

        int x, y, row, column;

        while (true) {

            // Determining the row
            x = random.nextInt(rowTracker.length());
            row = rowTracker.charAt(x) - 48;

            //Determining the column
            y = random.nextInt(columnTracker.length());
            column = columnTracker.charAt(y) - 48;

            if (mainGrid[row][column] == ' ') {
                break;
            }
        }
        rowTracker.deleteCharAt(x);
        columnTracker.deleteCharAt(y);
        mainGrid[row][column] = symbol;
    }
}
