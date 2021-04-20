import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import java.util.*;

import static java.lang.Math.abs;

class Grid {

    char[][] rows;
    Grid(String[] rows) throws Exception {
        this.rows = new char[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            this.rows[i] = rows[i].toCharArray();
            for (char c : this.rows[i]) {
                if (c != '.' && c != '*' && !(c >= '0' && c <= '9')) {
                    throw new Exception(
                        "A row of the grid should contain '.' or '*' or numbers. \n" +
                            "Found: '" + c + "' in row \"" + rows[i] + "\""
                    );
                }
            }
        }
    }

    @Override
    public String toString() {
        String res = "";
        for (int i = 0; i < this.rows.length; i++) {
            res += new String(this.rows[i]) + "\n";
        }
        return res.trim();
    }

    int count(char c) {
        int sum = 0;
        for (char[] row : rows) {
            for (char ch : row) {
                sum += ch == c ? 1 : 0;
            }
        }
        return sum;
    }

    int countAround(int x, int y, char c) {
        int[] around = new int[] {-1, 0, 1};
        int count = 0;
        for (int dx : around) {
            for (int dy : around) {

                int newX = x + dx;
                int newY = y + dy;

                if (1 <= newX && newX <= 9 &&
                    1 <= newY && newY <= 9) {
                    if (get(newX, newY) == c) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    int distanceToCenter(int x, int y) {
        return abs(x - 5) + abs(y - 5);
    }

    void replaceAround(int x, int y, char from, char to) {
        int[] around = new int[] {-1, 0, 1};
        int count = 0;
        for (int dx : around) {
            for (int dy : around) {

                int newX = x + dx;
                int newY = y + dy;

                if (1 <= newX && newX <= 9 &&
                    1 <= newY && newY <= 9) {
                    if (get(newX, newY) == from) {
                        set(newX, newY, to);
                    }
                }
            }
        }
    }

    void markImpossibles() {
        for (int x = 1; x <= 9; x++) {
            for (int y = 1; y <= 9; y++) {
                char curr = get(x, y);
                if (curr >= '1' && curr <= '9') {
                    int num = curr - '0';
                    int minesAround = countAround(x, y, '*');
                    if (num == minesAround) {
                        replaceAround(x, y, '.', 'X');
                    }
                }
            }
        }
    }

    char get(int x, int y) {
        return rows[y-1][x-1];
    }

    void set(int x, int y, char c) {
        rows[y-1][x-1] = c;
    }

    Grid copy() {
        String[] rows = new String[this.rows.length];
        for (int i = 0; i < this.rows.length; i++) {
            rows[i] = new String(this.rows[i]);
        }
        try {
            return new Grid(rows);
        } catch (Exception ex) {
            return null;
        }
    }

    int differences(Grid other) {
        int diff = 0;
        for (int x = 1; x <= 9; x++) {
            for (int y = 1; y <= 9; y++) {
                diff += get(x, y) != other.get(x, y) ? 1 : 0;
            }
        }
        return diff;
    }

    static List<Grid> parse(String output) throws Exception {

        output = output.replaceAll("│", "|");
        output = output.replaceAll("—", "-");

        List<Grid> grids = new LinkedList<>();
        String[] lines = output.split("\n");

        boolean gridStarted = false;
        List<String> newGrid = new LinkedList<>();
        for (String line : lines) {
            line = line.trim();
            if (line.contains("-|--")) {
                gridStarted = !gridStarted;
                if (gridStarted) {
                    newGrid = new LinkedList<>();
                } else {
                    if (newGrid.size() != 9) {
                        throw new Exception(
                            "Found grid that contains " + newGrid.size() +
                                " but grid should contain 9 lines. \n" +
                                "The tests assume that the grid is " +
                                "between the lines containing the line \"-│--\"."
                        );
                    }
                    grids.add(
                        new Grid(newGrid.toArray(new String[0]))
                    );
                }
                continue;
            }
            if (gridStarted) {

                char toFind = '|';

                long countBrackets =
                    line.chars().filter(c -> c == toFind).count();

                if (countBrackets != 2) {
                    throw new Exception(
                        "Grid should contain " +
                            "two '|' symbols, at the beginning " +
                            "(after row number) " +
                            "and at the end of the row. \n" +
                            "Your line: \"" + line + "\"."
                    );
                }

                int first = line.indexOf(toFind) + 1;
                int second = line.indexOf(toFind, first);

                int rowSize = second - first;

                if (rowSize != 9) {
                    throw new Exception(
                        "Every row of the grid should contain " +
                            "9 symbols between '|' chars. \nThis line has " +
                            rowSize + " symbols: \"" + line + "\"."
                    );
                }

                String row = line.substring(first, second);

                newGrid.add(row);
            }
        }

        return grids;
    }

}

class State {
    int xStar;
    int yStar;
    Grid first;
    int starCount = 0;
}

public class MinesweeperTest extends StageTest<Integer> {

    @Override
    public List<TestCase<Integer>> generate() {
        List<TestCase<Integer>> tests = new ArrayList<>();

        State state = new State();

        TestCase<Integer> test = new TestCase<Integer>()
            .addInput("1")
            .addInput(out -> {
                out = out.trim();

                List<Grid> grids;
                try {
                    grids = Grid.parse(out);
                } catch (Exception ex) {
                    return CheckResult.wrong(ex.getMessage());
                }

                if (grids.size() != 1) {
                    return CheckResult.wrong(
                        "Expected to see one grid after printing the number of mines. " +
                            "Found: " + grids.size() + " grids."
                    );
                }

                Grid grid = grids.get(0);
                int starCount = grid.count('*');

                if (starCount != 0) {
                    return CheckResult.wrong(
                        "There should be no '*' symbols in the " +
                            "initial grid showdown. Found: " + starCount
                    );
                }

                int onesCount = grid.count('1');

                if (onesCount != 3 && onesCount != 5 && onesCount != 8) {
                    return CheckResult.wrong(
                        "If there is one mine the grid should show 3, 5 or 8 '1' symbols. " +
                            "Found: " + onesCount
                    );
                }

                int dotsCount = 9 * 9 - onesCount;
                int realDotsCount = grid.count('.');

                if (realDotsCount != dotsCount) {
                    return CheckResult.wrong(
                        "There should be " + dotsCount + " '.' symbols in the grid. " +
                            "Found: " + realDotsCount
                    );
                }

                for (int x = 1; x <= 9; x++) {
                    for (int y = 1; y <= 9; y++) {
                        if (x == y) {
                            continue;
                        }
                        if (grid.get(x, y) == '.') {
                            state.xStar = x;
                            state.yStar = y;
                            state.first = grid;
                            return x + " " + y;
                        }
                    }
                }
                return null;
            })
            .addInput(out -> {
                out = out.trim();

                if (out.toLowerCase().contains("congratulations")) {
                    return CheckResult.correct();
                }

                List<Grid> grids;
                try {
                    grids = Grid.parse(out);
                } catch (Exception ex) {
                    return CheckResult.wrong(ex.getMessage());
                }

                if (grids.size() != 1) {
                    return CheckResult.wrong(
                        "Expected to see one grid after printing the coordinates. " +
                            "Found: " + grids.size() + " grids."
                    );
                }

                Grid grid = grids.get(0);
                int starCount = grid.count('*');

                if (starCount != 1) {
                    return CheckResult.wrong(
                        "There should be one '*' symbol in the " +
                            "grid after printing the coordinates. Found: " + starCount
                    );
                }

                char checkStar = grid.get(state.xStar, state.yStar);
                if (checkStar != '*') {
                    return CheckResult.wrong(
                        "There should be '*' symbol in the coordinates " +
                            state.xStar + " " + state.yStar + " but found \'" + checkStar + "\'"
                    );
                }

                int difference = grid.differences(state.first);

                if (difference != 1) {
                    return CheckResult.wrong(
                        "The first and second grid must match except " +
                            "for one *' character. " +
                            "There are " + difference + " differences between the grids."
                    );
                }

                return state.xStar + " " + state.yStar;
            })
            .addInput(out -> {
                out = out.trim();

                List<Grid> grids;
                try {
                    grids = Grid.parse(out);
                } catch (Exception ex) {
                    return CheckResult.wrong(ex.getMessage());
                }

                if (grids.size() != 1) {
                    return CheckResult.wrong(
                        "Expected to see one grid after printing the coordinates. " +
                            "Found: " + grids.size() + " grids."
                    );
                }

                Grid grid = grids.get(0);

                int starCount = grid.count('*');

                if (starCount != 0) {
                    return CheckResult.wrong(
                        "There should be no '*' symbols in the " +
                            "grid after printing the coordinates twice. Found: " + starCount
                    );
                }

                int difference = grid.differences(state.first);

                if (difference != 0) {
                    return CheckResult.wrong(
                        "The first and third grid must match after printing " +
                            "the coordinates twice. " +
                            "There are " + difference + " differences between the grids."
                    );
                }

                int potentialX = 0;
                int potentialY = 0;
                int maxOnesAround = 0;
                for (int x = 1; x <= 9; x++) {
                    for (int y = 1; y <= 9; y++) {
                        int onesAround = 0;
                        if (grid.get(x, y) == '.') {
                            onesAround = grid.countAround(x, y, '1');
                        }
                        if (onesAround > maxOnesAround) {
                            maxOnesAround = onesAround;
                            potentialX = x;
                            potentialY = y;
                        }
                    }
                }

                return potentialX + " " + potentialY;
            });

        for (int i = 0; i < 10; i++) {
            tests.add(test);
        }

        for (int i = 0; i < 3; i++) {
            State state2 = new State();
            tests.add(new TestCase<Integer>()
                .addInput("5")
                .addInput(5, out -> {
                    out = out.trim();

                    List<Grid> grids;
                    try {
                        grids = Grid.parse(out);
                    } catch (Exception ex) {
                        return CheckResult.wrong(ex.getMessage());
                    }

                    if (grids.size() == 0) {
                        if (out.toLowerCase().contains("there is a number")) {
                            return CheckResult.wrong(
                                "Solver doesn't input cells with numbers," +
                                    " only dots. Maybe, you messed up " +
                                    "with X and Y coordinates?"
                            );
                        }

                        return CheckResult.wrong(
                            "Cannot find a field after the last input. Make sure you output " +
                                "this field using '|' and '-' characters."
                        );
                    }

                    Grid grid = grids.get(0);
                    int starCount = grid.count('*');

                    if (starCount != state2.starCount) {
                        return CheckResult.wrong(
                            "There should be " + state2.starCount + " '*' symbols in the " +
                                "grid. Found: " + starCount
                        );
                    }

                    if (state2.first == null) {
                        state2.first = grid;
                    }

                    int potentialX = 0;
                    int potentialY = 0;
                    int maxNumsAround = 0;
                    int maxDistToCenter = 0;
                    for (int x = 1; x <= 9; x++) {
                        for (int y = 1; y <= 9; y++) {
                            int numsAround = 0;
                            if (state2.first.get(x, y) == '.') {
                                for (char c = '1'; c <= '9'; c++) {
                                    numsAround += state2.first.countAround(x, y, c);
                                }
                            }

                            boolean needUpdate = numsAround > maxNumsAround;
                            if (numsAround == maxNumsAround) {
                                int currDistToCenter =
                                    state2.first.distanceToCenter(x, y);
                                needUpdate |= currDistToCenter > maxDistToCenter;
                            }

                            if (needUpdate) {
                                maxNumsAround = numsAround;
                                potentialX = x;
                                potentialY = y;
                                maxDistToCenter =
                                    state2.first.distanceToCenter(x, y);
                            }
                        }
                    }

                    state2.starCount++;
                    state2.first.set(potentialX, potentialY, '*');
                    state2.first.markImpossibles();
                    return potentialX + " " + potentialY;
                })
                .addInput(out -> {
                    return CheckResult.wrong(
                        "Solver can't solve your grid with 5 mines. " +
                            "Maybe your program shows wrong numbers? " +
                            "But solver misses 1% of the time, so it is worth trying " +
                            "to test one more time."
                    );
                })
            );
        }

        return tests;
    }

    @Override
    public CheckResult check(String reply, Integer attach) {
        reply = reply.toLowerCase();

        if (reply.contains("congratulations")) {
            return CheckResult.correct();
        }

        return CheckResult.wrong("After guessing right there should be " +
            "\"Congratulations\" word printed.");
    }
}
