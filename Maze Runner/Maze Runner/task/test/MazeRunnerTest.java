import maze.Main;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


enum Elem {
    WALL, EMPTY, PATH, DUMMY;

    static Elem get(char c) {
        if (c == '\u2588') {
            return WALL;
        } else if (c == ' ') {
            return EMPTY;
        } else if (c == '/') {
            return PATH;
        } else {
            return null;
        }
    }
}

class Maze {

    // maze[height][width]
    // maze[row][col]
    Elem[][] maze;

    Maze(Maze other) {
        maze = new Elem[other.getHeight()][other.getWidth()];
        for (int h = 0; h < other.getHeight(); h++) {
            for (int w = 0; w < other.getWidth(); w++) {
                maze[h][w] = other.maze[h][w];
            }
        }
    }

    Maze(List<String> lines) throws Exception {

        int lineCount = 1;
        for (String line : lines) {
            if (line.length() % 2 != 0) {
                throw new Exception("Line " + lineCount + " of the maze " +
                    "contains odd number of characters. Should be always even.");
            }

            for (char c : line.toCharArray()) {
                if (Elem.get(c) == null) {
                    String hex = Integer.toHexString((int)c);
                    throw new Exception(
                        "Found strange symbol in the " + lineCount +
                            " line of the maze: \\u" + hex);
                }
            }

            int lineWidth = line.length() / 2;

            for (int currWidth = 0; currWidth < lineWidth; currWidth++) {
                int currIndex = currWidth * 2;
                int nextIndex = currIndex + 1;

                char currChar = line.charAt(currIndex);
                char nextChar = line.charAt(nextIndex);

                if (currChar != nextChar) {
                    throw new Exception("There are symbols in " +
                        "this line that don't appear twice in a row " +
                        "(at indexes " + currIndex + " and " + nextIndex + ").\n" +
                        "Line: \"" + line + "\"");
                }
            }

            lineCount++;
        }

        int mazeWidth = lines.get(0).length() / 2;
        int mazeHeight = lines.size();

        lineCount = 1;
        for (String line : lines) {
            if (line.length() / 2 != mazeWidth) {
                throw new Exception("The first line of the maze contains " +
                    lines.get(0).length() + " characters, but the line #" +
                    lineCount + " contains " + line.length() + " characters.");
            }
            lineCount++;
        }

        maze = new Elem[mazeHeight][mazeWidth];

        for (int currHeight = 0; currHeight < mazeHeight; currHeight++) {
            String line = lines.get(currHeight);
            for (int currWidth = 0; currWidth < mazeWidth; currWidth++) {
                char c = line.charAt(currWidth * 2);
                maze[currHeight][currWidth] = Elem.get(c);
            }
        }

        if (maze[0][0] != Elem.WALL ||
            maze[0][mazeWidth - 1] != Elem.WALL ||
            maze[mazeHeight - 1][0] != Elem.WALL ||
            maze[mazeHeight - 1][mazeWidth - 1] != Elem.WALL) {
            throw new Exception("All four corners of the maze must be walls.");
        }

        for (int h = 0; h <= mazeHeight - 3; h++) {
            for (int w = 0; w <= mazeWidth - 3; w++) {
                if (getElem(h, w) == Elem.WALL &&
                    getElem(h, w + 1) == Elem.WALL &&
                    getElem(h, w + 2) == Elem.WALL &&

                    getElem(h + 1, w) == Elem.WALL &&
                    getElem(h + 1, w + 1) == Elem.WALL &&
                    getElem(h + 1, w + 2) == Elem.WALL &&

                    getElem(h + 2, w) == Elem.WALL &&
                    getElem(h + 2, w + 1) == Elem.WALL &&
                    getElem(h + 2, w + 2) == Elem.WALL) {

                    throw new Exception("There are 3x3 block in the maze " +
                        "consisting only of walls. Such blocks are not allowed.");
                }
            }
        }
    }

    Elem[] getRow(int rowNum) {
        Elem[] newRow = new Elem[getWidth()];
        for (int i = 0; i < getWidth(); i++) {
            newRow[i] = maze[rowNum][i];
        }
        return newRow;
    }

    Elem[] getCol(int colNum) {
        Elem[] newCol = new Elem[getHeight()];
        for (int i = 0; i < getHeight(); i++) {
            newCol[i] = maze[i][colNum];
        }
        return newCol;
    }

    Elem getElem(int height, int width) {
        return maze[height][width];
    }

    void setElem(int height, int width, Elem elem) {
        maze[height][width] = elem;
    }

    int getWidth() {
        return maze[0].length;
    }

    int getHeight() {
        return maze.length;
    }

    Maze copy() {
        return new Maze(this);
    }

    int count(Elem toCount) {
        int sum = 0;
        for (Elem[] row : maze) {
            for (Elem e : row) {
                if (e == toCount) {
                    sum++;
                }
            }
        }
        return sum;
    }

    int countAround(int h, int w, Elem elem) {
        int sum = 0;

        if (h + 1 < getHeight() &&
            getElem(h + 1, w) == elem) {
            sum++;
        }

        if (h - 1 >= 0 &&
            getElem(h - 1, w) == elem) {
            sum++;
        }

        if (w + 1 < getWidth() &&
            getElem(h, w + 1) == elem) {
            sum++;
        }

        if (w - 1 >= 0 &&
            getElem(h, w - 1) == elem) {
            sum++;
        }

        return sum;
    }

    int countEntrances() {
        int entranceCount = 0;
        for (Elem[] line : new Elem[][] {
            getCol(0),
            getCol(getWidth() - 1),
            getRow(0),
            getRow(getHeight() - 1)
        }) {

            for (Elem e : line) {
                if (e != Elem.WALL) {
                    entranceCount++;
                }
            }
        }
        return entranceCount;
    }

    private void propagate(Elem from, Elem to) {
        boolean didPropagate = true;
        while (didPropagate) {
            didPropagate = false;
            for (int h = 0; h < getHeight(); h++) {
                for (int w = 0; w < getWidth(); w++) {
                    if (getElem(h, w) == from) {
                        if (countAround(h, w, to) > 0) {
                            didPropagate = true;
                            setElem(h, w, to);
                        }
                    }
                }
            }
        }
    }

    int checkAccessibility() {
        int entranceHeight = 0;
        int entranceWidth = 0;

        findPoints: {
            for (int currWidth : new int[] {0, getWidth() - 1}) {
                for (int currHeight = 0; currHeight < getHeight(); currHeight++) {
                    if (getElem(currHeight, currWidth) != Elem.WALL) {
                        entranceHeight = currHeight;
                        entranceWidth = currWidth;
                        break findPoints;
                    }
                }
            }

            for (int currHeight : new int[] {0, getHeight() - 1}) {
                for (int currWidth = 0; currWidth < getWidth(); currWidth++) {
                    if (getElem(currHeight, currWidth) != Elem.WALL) {
                        entranceHeight = currHeight;
                        entranceWidth = currWidth;
                        break findPoints;
                    }
                }
            }
        }

        Maze copy = copy();
        copy.setElem(entranceHeight, entranceWidth, Elem.PATH);
        copy.propagate(Elem.EMPTY, Elem.PATH);

        return copy.count(Elem.EMPTY);
    }

    int checkPath() throws Exception {
        int entranceHeight = 0;
        int entranceWidth = 0;

        for (int currWidth : new int[] {0, getWidth() - 1}) {
            for (int currHeight = 0; currHeight < getHeight(); currHeight++) {
                if (getElem(currHeight, currWidth) == Elem.EMPTY) {
                    throw new Exception("If the maze is solved all " +
                        "the entrances should be marked with '//' characters");
                }
                if (getElem(currHeight, currWidth) == Elem.PATH) {
                    entranceHeight = currHeight;
                    entranceWidth = currWidth;
                }
            }
        }

        for (int currHeight : new int[] {0, getHeight() - 1}) {
            for (int currWidth = 0; currWidth < getWidth(); currWidth++) {
                if (getElem(currHeight, currWidth) == Elem.EMPTY) {
                    throw new Exception("If the maze is solved all " +
                        "the entrances should be marked with '//' characters");
                }
                if (getElem(currHeight, currWidth) == Elem.PATH) {
                    entranceHeight = currHeight;
                    entranceWidth = currWidth;
                }
            }
        }

        for (int h = 0; h < getHeight(); h++) {
            for (int w = 0; w < getWidth(); w++) {
                if (getElem(h, w) == Elem.PATH) {
                    if (countAround(h, w, Elem.PATH) >= 3) {
                        throw new Exception("The escape path shouldn't branch off, " +
                            "it should go in one direction.");
                    }
                }
            }
        }

        Maze copy = copy();
        copy.setElem(entranceHeight, entranceWidth, Elem.DUMMY);
        copy.propagate(Elem.PATH, Elem.DUMMY);

        return copy.count(Elem.PATH);
    }

    boolean equals(Maze other) {
        if (getWidth() != other.getWidth() || getHeight() != other.getHeight()) {
            return false;
        }
        for (int h = 0; h < getHeight(); h++) {
            for (int w = 0; w < getWidth(); w++) {
                if (getElem(h, w) == Elem.WALL && other.getElem(h, w) != Elem.WALL ||
                    getElem(h, w) != Elem.WALL && other.getElem(h, w) == Elem.WALL) {
                    return false;
                }
            }
        }
        return true;
    }

    static List<Maze> parse(String text) throws Exception {

        List<Maze> mazes = new ArrayList<>();

        List<String> lines = text
            .lines()
            .collect(Collectors.toList());

        lines.add("");

        List<String> mazeLines = new ArrayList<>();

        boolean isStarted = false;
        for (String line : lines) {
            if (line.contains("\u2588")) {
                isStarted = true;
                mazeLines.add(line);
            } else if (isStarted) {
                isStarted = false;
                Maze maze = new Maze(mazeLines);
                mazes.add(maze);
                mazeLines.clear();
            }
        }

        return mazes;
    }

}

class Clue {
    int height;
    int width;
    Clue(int h, int w) {
        height = h;
        width = w;
    }
}

public class MazeRunnerTest extends StageTest<Clue> {

    List<Maze> previousMazes = new ArrayList<>();

    @Override
    public List<TestCase<Clue>> generate() {
        return List.of(
            new TestCase<Clue>()
                .setInput("7 9")
                .setAttach(new Clue(7, 9)),

            new TestCase<Clue>()
                .setInput("15 35")
                .setAttach(new Clue(15, 35)),

            new TestCase<Clue>()
                .setInput("15 35")
                .setAttach(new Clue(15, 35)),

            new TestCase<Clue>()
                .setInput("15 35")
                .setAttach(new Clue(15, 35)),

            new TestCase<Clue>()
                .setInput("15 35")
                .setAttach(new Clue(15, 35)),

            new TestCase<Clue>()
                .setInput("15 35")
                .setAttach(new Clue(15, 35)),

            new TestCase<Clue>()
                .setInput("15 35")
                .setAttach(new Clue(15, 35)),

            new TestCase<Clue>()
                .setInput("15 35")
                .setAttach(new Clue(15, 35)),

            new TestCase<Clue>()
                .setInput("15 35")
                .setAttach(new Clue(15, 35)),

            new TestCase<Clue>()
                .setInput("34 23")
                .setAttach(new Clue(34, 23)),

            new TestCase<Clue>()
                .setInput("34 23")
                .setAttach(new Clue(34, 23)),

            new TestCase<Clue>()
                .setInput("34 23")
                .setAttach(new Clue(34, 23)),

            new TestCase<Clue>()
                .setInput("34 23")
                .setAttach(new Clue(34, 23)),

            new TestCase<Clue>()
                .setInput("34 23")
                .setAttach(new Clue(34, 23)),

            new TestCase<Clue>()
                .setInput("34 23")
                .setAttach(new Clue(34, 23)),

            new TestCase<Clue>()
                .setInput("34 23")
                .setAttach(new Clue(34, 23)),

            new TestCase<Clue>()
                .setInput("34 23")
                .setAttach(new Clue(34, 23))

        );
    }

    @Override
    public CheckResult check(String reply, Clue clue) {

        List<Maze> mazes;
        try {
            mazes = Maze.parse(reply);
        } catch (Exception e) {
            return CheckResult.wrong(
                e.getMessage()
            );
        }

        if (mazes.size() == 0) {
            return CheckResult.wrong(
                "No mazes found in the output. Check if you are using " +
                    "\\u2588 character to print the maze."
            );
        }

        if (mazes.size() > 1) {
            return CheckResult.wrong(
                "Found " + mazes.size() + " mazes in the output. " +
                    "Should be only one maze."
            );
        }

        Maze maze = mazes.get(0);

        for (Maze prev : previousMazes) {
            if (prev.equals(maze)) {
                return CheckResult.wrong(
                    "This is the same maze that was in the previous tests. " +
                    "You should create an algorithm that generates different mazes."
                );
            }
        }
        previousMazes.add(maze);

        int entrances = maze.countEntrances();
        if (entrances != 2) {
            return new CheckResult(false,
                "There are " + entrances + " entrances to the maze, " +
                    "should be only two.");
        }

        int emptyLeft = maze.checkAccessibility();
        if (emptyLeft > 0) {
            return new CheckResult(false,
                "There are " + emptyLeft + " empty " +
                    "cells that are inaccessible from the entrance of the maze " +
                    "(or there is no way from the entrance to the exit)."
            );
        }

        if (maze.getHeight() != clue.height) {
            return new CheckResult(false,
                "Number of rows in the maze is incorrect. " +
                    "It's " + maze.getHeight() + ", but should be " + clue.height);
        }

        if (maze.getWidth() != clue.width) {
            return new CheckResult(false,
                "Number of columns in the maze is incorrect. " +
                    "It's " + maze.getWidth() + ", but should be " + clue.width);
        }

        return CheckResult.correct();
    }
}
