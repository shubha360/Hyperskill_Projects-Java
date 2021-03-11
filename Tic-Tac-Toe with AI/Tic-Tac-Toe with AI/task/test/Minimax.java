import java.util.ArrayList;
import java.util.List;

public class Minimax {

    private static Position bestPosition;

    public static Position getMove(Grid grid, CellState player) {

        int bestScore = Integer.MIN_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid.getGrid()[i][j] == CellState.EMPTY) {
                    grid.setCell(i, j, player);
                    int score = minimax(grid, CellState.getOpponent(player), false, player, 1);
                    grid.setCell(i, j, CellState.EMPTY);
                    if (score > bestScore) {
                        bestScore = score;
                        bestPosition = new Position(i, j);
                    }
                }
            }
        }

        return bestPosition;
    }

    private static int minimax(Grid grid, CellState player, boolean isMaximize, CellState startPlayer, int depth) {

        switch (grid.getGameState()) {
            case X_WIN:
                return startPlayer == CellState.X ? 10 - depth : depth - 10;
            case O_WIN:
                return startPlayer == CellState.O ? 10 - depth : depth - 10;
            case DRAW:
                return 0;
        }

        int bestScore = isMaximize ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid.getGrid()[i][j] == CellState.EMPTY) {
                    grid.setCell(i, j, player);
                    int score = minimax(grid, CellState.getOpponent(player), !isMaximize, startPlayer, depth + 1);
                    grid.setCell(i, j, CellState.EMPTY);
                    bestScore = isMaximize ? Math.max(bestScore, score) : Math.min(bestScore, score);
                }
            }
        }

        return bestScore;
    }

    public static List<Position> getAvailablePositions(Grid grid, CellState player) {

        List<Position> positions = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid.getGrid()[i][j] == CellState.EMPTY) {
                    grid.setCell(i, j, player);
                    int score = minimax(grid, CellState.getOpponent(player), false, player, 1);
                    if (score >= 0) {
                        positions.add(new Position(i, j));
                    }
                    grid.setCell(i, j, CellState.EMPTY);
                }
            }
        }

        return positions;
    }
}

class Position {

    int x;
    int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return x + " " + y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x &&
            y == position.y;
    }
}



