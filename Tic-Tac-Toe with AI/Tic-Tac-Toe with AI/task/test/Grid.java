import org.hyperskill.hstest.exception.outcomes.WrongAnswer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Grid {

    private final CellState[][] grid;

    private Grid() {
        grid = new CellState[3][3];
    }

    public static Grid fromLine(String line) {
        Grid grid = new Grid();

        if (line.length() != 9) {
            throw new WrongAnswer("Wrong input length. Expected 9.\nFound " + line.length());
        }

        for (int i = 0; i < line.length(); i++) {
            grid.grid[i / 3][i % 3] = CellState.get(line.charAt(i));
        }

        return grid;
    }

    public static Grid fromOutput(String stringField) {
        return fromOutput(stringField, 1);
    }

    public static Grid fromOutput(String stringField, int fieldNumber) {

        Grid grid = new Grid();

        List<String> fieldLines = Arrays.stream(stringField.split("\n"))
            .map(String::strip)
            .filter(line -> line.startsWith("|") && line.endsWith("|"))
            .collect(Collectors.toList());

        if (fieldLines.size() < 3 * fieldNumber) {
            throw new WrongAnswer("Expected not less than " + fieldNumber + " grids in the output!\n" +
                "Make sure you print the game grids in the correct format!");
        }

        fieldLines = fieldLines.subList(fieldNumber * 3 - 3, fieldNumber * 3);

        for (int i = 0; i < fieldLines.size(); i++) {
            String line = fieldLines.get(i);
            if (line.length() != 9) {
                throw new WrongAnswer("Can't parse the game field. The following line has wrong length:\n" + line);
            }
            for (int j = 0; j < 3; j++) {
                grid.grid[i][j] = CellState.get(line.charAt(j * 2 + 2));
            }
        }

        return grid;
    }

    public static List<Grid> allGridsFromOutput(String stringField) {

        List<Grid> gridList = new ArrayList<>();

        List<String> fieldLines = Arrays.stream(stringField.split("\n"))
            .map(String::strip)
            .filter(line -> line.startsWith("|") && line.endsWith("|"))
            .collect(Collectors.toList());

        if (fieldLines.size() % 3 != 0) {
            throw new WrongAnswer("Wrong grid output format! Each grid should contain 3 lines that starts and ends with '|' symbol!");
        }

        for (int i = 0; i < fieldLines.size() / 3; i++) {
            List<String> gridLines = fieldLines.subList(i * 3, i * 3 + 3);

            Grid grid = new Grid();

            for (int j = 0; j < gridLines.size(); j++) {
                String line = gridLines.get(j);
                if (line.length() != 9) {
                    throw new WrongAnswer("Can't parse the game field. The following line has wrong length:\n" + line);
                }
                for (int k = 0; k < 3; k++) {
                    grid.grid[j][k] = CellState.get(line.charAt(k * 2 + 2));
                }
            }

            gridList.add(grid);
        }

        return gridList;
    }

    public static Position getMove(Grid from, Grid to) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (from.grid[i][j] != to.grid[i][j]) {
                    return new Position(i, j);
                }
            }
        }
        throw new WrongAnswer("After making a move the grid was the same!");
    }

    public boolean isWin(CellState player) {
        for (int i = 0; i < 3; i++) {
            if (grid[i][0] == grid[i][1] && grid[i][1] == grid[i][2] && grid[i][2] == player) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (grid[0][i] == grid[1][i] && grid[1][i] == grid[2][i] && grid[2][i] == player) {
                return true;
            }
        }

        return grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2] && grid[2][2] == player
            || grid[2][0] == grid[1][1] && grid[1][1] == grid[0][2] && grid[0][2] == player;
    }

    public boolean hasEmptyCells() {
        return Arrays.stream(grid)
            .flatMap(Stream::of)
            .anyMatch(cell -> cell == CellState.EMPTY);
    }

    public int getNumOfEmptyCells() {
        return (int) Arrays.stream(grid)
            .flatMap(Stream::of)
            .filter(cell -> cell == CellState.EMPTY).count();
    }

    public GameState getGameState() {
        if (isWin(CellState.X)) return GameState.X_WIN;
        if (isWin(CellState.O)) return GameState.O_WIN;
        if (hasEmptyCells()) return GameState.NOT_FINISHED;
        else return GameState.DRAW;
    }

    public CellState[][] getGrid() {
        return grid;
    }

    public void setCell(int x, int y, CellState cellState) {
        grid[x][y] = cellState;
    }

    public boolean isCorrectNextGrid(Grid grid) {
        return getNumOfEmptyCells() - grid.getNumOfEmptyCells() == 1;
    }

    public boolean isValidGrid() {

        int numOfX = (int) Arrays.stream(grid)
            .flatMap(Stream::of)
            .filter(cell -> cell == CellState.X).count();
        int numOfO = (int) Arrays.stream(grid)
            .flatMap(Stream::of)
            .filter(cell -> cell == CellState.O).count();

        return Math.abs(numOfO - numOfX) <= 1;
    }

    public static void checkGridSequence(List<Grid> grids) {
        if (grids.size() <= 1) {
            return;
        }

        for (int i = 1; i < grids.size(); i++) {
            Grid prevGrid = grids.get(i - 1);
            Grid grid = grids.get(i);
            if (!grid.isValidGrid()) {
                throw new WrongAnswer("Impossible grid was printed! The difference between Os and Xs in the grid is greater than 1:\n" + grid);
            }
            if (!prevGrid.isCorrectNextGrid(grid)) {
                throw new WrongAnswer("After making a move on grid\n" + prevGrid + "\n it can't become\n" + grid);
            }
        }

        Grid lastGrid = grids.get(grids.size() - 1);

        if (lastGrid.getGameState() == GameState.NOT_FINISHED) {
            throw new WrongAnswer("Last grid is not terminal!\n" + lastGrid);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("---------").append("\n");
        for (int i = 0; i < 3; i++) {
            stringBuilder.append("| ").append(grid[i][0]).append(" ").append(grid[i][1]).append(" ").append(grid[i][2]).append(" |\n");
        }
        stringBuilder.append("---------");
        return stringBuilder.toString();
    }

    private String toLine() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            result.append(grid[i / 3][i % 3]);
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Grid)) {
            return false;
        }

        Grid grid = (Grid) o;

        for (int i = 0; i < 9; i++) {
            if (grid.grid[i / 3][i % 3] != this.grid[i / 3][i % 3]) {
                return false;
            }
        }
        return true;
    }

    public Grid copy() {
        return Grid.fromLine(toLine());
    }
}

enum GameState {

    X_WIN, O_WIN, DRAW, NOT_FINISHED

}

enum CellState {

    X("X"), O("O"), EMPTY(" ");

    String value;

    CellState(String value) {
        this.value = value;
    }

    static CellState get(char symbol) {
        switch (symbol) {
            case 'X':
                return X;
            case 'O':
                return O;
            case ' ':
            case '_':
                return EMPTY;
            default:
                throw new WrongAnswer("Bad symbol '" + symbol + "' in the game grid");
        }
    }

    static CellState getOpponent(CellState player) {
        if (player == X) {
            return O;
        } else {
            return X;
        }
    }

    @Override
    public String toString() {
        return value;
    }
}