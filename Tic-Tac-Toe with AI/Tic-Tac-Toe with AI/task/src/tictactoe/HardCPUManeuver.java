package tictactoe;

import java.util.Random;

public class HardCPUManeuver extends TicTacToe implements Maneuver {

    char playerSymbol;
    char opponentSymbol;

    @Override
    public void makeManeuver(char symbol) {

        if (symbol == 'O') {
            this.playerSymbol = 'O';
            this.opponentSymbol = 'X';
        } else {
            this.playerSymbol = 'X';
            this.opponentSymbol = 'O';
        }

        System.out.println("Making move level \"hard\"");

        if (depth == 0) {

            Random random = new Random();

            int row = random.nextInt(3);
            int column = random.nextInt(3);

            mainGrid[row][column] = symbol;
            depth++;
        } else {
            Move nextMove = findBestMove();
            mainGrid[nextMove.row][nextMove.column] = symbol;
            depth++;
        }
    }

    static class Move {

        int row;
        int column;

        Move() {
            row = -1;
            column = -1;
        }
    }

    int evaluateGrid() {

        if ((mainGrid[0][0] == playerSymbol && mainGrid[0][1] == playerSymbol && mainGrid[0][2] == playerSymbol) ||
                (mainGrid[1][0] == playerSymbol && mainGrid[1][1] == playerSymbol && mainGrid[1][2] == playerSymbol) ||
                (mainGrid[2][0] == playerSymbol && mainGrid[2][1] == playerSymbol && mainGrid[2][2] == playerSymbol) ||
                (mainGrid[0][0] == playerSymbol && mainGrid[1][0] == playerSymbol && mainGrid[2][0] == playerSymbol) ||
                (mainGrid[0][1] == playerSymbol && mainGrid[1][1] == playerSymbol && mainGrid[2][1] == playerSymbol) ||
                (mainGrid[0][2] == playerSymbol && mainGrid[1][2] == playerSymbol && mainGrid[2][2] == playerSymbol) ||
                (mainGrid[0][0] == playerSymbol && mainGrid[1][1] == playerSymbol && mainGrid[2][2] == playerSymbol) ||
                (mainGrid[0][2] == playerSymbol && mainGrid[1][1] == playerSymbol && mainGrid[2][0] == playerSymbol)) {
            return 10;
        } else if ((mainGrid[0][0] == opponentSymbol && mainGrid[0][1] == opponentSymbol && mainGrid[0][2] == opponentSymbol) ||
                (mainGrid[1][0] == opponentSymbol && mainGrid[1][1] == opponentSymbol && mainGrid[1][2] == opponentSymbol) ||
                (mainGrid[2][0] == opponentSymbol && mainGrid[2][1] == opponentSymbol && mainGrid[2][2] == opponentSymbol) ||
                (mainGrid[0][0] == opponentSymbol && mainGrid[1][0] == opponentSymbol && mainGrid[2][0] == opponentSymbol) ||
                (mainGrid[0][1] == opponentSymbol && mainGrid[1][1] == opponentSymbol && mainGrid[2][1] == opponentSymbol) ||
                (mainGrid[0][2] == opponentSymbol && mainGrid[1][2] == opponentSymbol && mainGrid[2][2] == opponentSymbol) ||
                (mainGrid[0][0] == opponentSymbol && mainGrid[1][1] == opponentSymbol && mainGrid[2][2] == opponentSymbol) ||
                (mainGrid[0][2] == opponentSymbol && mainGrid[1][1] == opponentSymbol && mainGrid[2][0] == opponentSymbol)) {
            return -10;
        }
        return 0;
    }

    int minimax(boolean isMax) {

        int score = evaluateGrid();

        if (score == 10 || score == -10) {
            return score;
        }

        if (!isMoveLeft()) {
            return 0;
        }

        int best = -1;

        if (isMax) {

            best = Integer.MIN_VALUE;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (mainGrid[i][j] == ' ') {

                        mainGrid[i][j] = playerSymbol;

                        best = Math.max(best, minimax(false));

                        mainGrid[i][j] = ' ';
                    }
                }
            }
        }

        if (!isMax) {

            best = Integer.MAX_VALUE;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (mainGrid[i][j] == ' ') {

                        mainGrid[i][j] = opponentSymbol;

                        best = Math.min(best, minimax(true));

                        mainGrid[i][j] = ' ';
                    }
                }
            }
        }
        return best;
    }

    Move findBestMove() {

        int bestValue = Integer.MIN_VALUE;
        Move bestMove = new Move();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (mainGrid[i][j] == ' ') {

                    mainGrid[i][j] = playerSymbol;

                    int moveValue = minimax(false);

                    mainGrid[i][j] = ' ';

                    if (moveValue > bestValue) {
                        bestMove.row = i;
                        bestMove.column = j;
                        bestValue = moveValue;
                    }
                }
            }
        }
        return bestMove;
    }


    boolean isMoveLeft() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (mainGrid[i][j] == ' ') {
                    return true;
                }
            }
        }
        return false;
    }
}
