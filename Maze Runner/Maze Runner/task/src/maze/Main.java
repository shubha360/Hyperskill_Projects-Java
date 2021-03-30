package maze;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Maze maze = new Maze();
    }
}

class Maze {

    int[][] mainMaze;
    int height;
    int width;

    int entranceHeight;

    Maze() {
        boolean b = initializeMaze();

        if (b) {
            fillTopAndBelowBoundaries();
            fillEntranceAndExit();
            constructMainPathway();
            printMaze();
            checkSquares();
            printMaze();
        }
    }

    private boolean initializeMaze() {

        System.out.println("Please, enter the size of a maze");

        Scanner scanner = new Scanner(System.in);
        this.height = scanner.nextInt();
        this.width = scanner.nextInt();

        if (height < 3 || width < 3) {
            System.out.println("Height or weight of a maze cannot be lesser than 3.");
            return false;
        }

        this.mainMaze = new int[height][width];

        for (int i = 0; i < height; i++) {
            Arrays.fill(mainMaze[i], 5);
        }
        return true;
    }

    private void printOriginalMaze() {

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(mainMaze[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void printMaze() {

        for (int i = 0; i <  height; i++) {
            for (int j = 0; j < width; j++) {

                if (mainMaze[i][j] == 5 || mainMaze[i][j] == 1) {
                    System.out.print("\u2588\u2588");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }

    private void fillTopAndBelowBoundaries() {
        Arrays.fill(mainMaze[0], 1);
        Arrays.fill(mainMaze[height - 1], 1);
    }

    private void fillEntranceAndExit() {

        Random random = new Random();

        this.entranceHeight = random.nextInt(height - 2) + 1;

        mainMaze[entranceHeight][0] = 0;

        for (int i = 1; i < height - 1; i++) {

            if (mainMaze[i][0] == 5) {
                mainMaze[i][0] = 1;
            }
        }
    }

    private void constructMainPathway() {

        int[] last = {entranceHeight, 0};

        for (int i = 1; i < width; i++) {

            Random random = new Random();

            int next = random.nextInt(3) + 1;

            switch (next) {

                case 1:

                    if (mainMaze[last[0] - 1][last[1]] == 5) {
                        mainMaze[last[0] - 1][last[1]] = 0;
                        last[0] = last[0] - 1;
                    }
                    i--;
                    break;

                case 2:

                    if (mainMaze[last[0]][last[1] + 1] == 5) {
                        mainMaze[last[0]][last[1] + 1] = 0;
                        last[1] = last[1] + 1;
                    }
                    break;

                case 3:

                    if (mainMaze[last[0] + 1][last[1]] == 5) {
                        mainMaze[last[0] + 1][last[1]] = 0;
                        last[0] = last[0] + 1;
                    }
                    i--;
                    break;
            }
        }
    }

    private void checkSquares() {

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                boolean hasPathway = false;

                int a = i;
                int b = j;

                for (; a < (i + 3) && a < height - 2; a++) {
                    for (; b < (j + 3) && b < width - 2; b++) {

                        if (mainMaze[a][b] == 0) {
                            hasPathway = true;
                            break;
                        }
                    }
                    if (hasPathway) {
                        break;
                    }
                }

                if (!hasPathway) {

                    System.out.println("Found a defect");
                    Random random = new Random();

                    int row = 0;

                    while (row == 0 || row >= height - 1) {
                        System.out.println("Finding row");
                        System.out.println("a is " + a);
                        row = random.nextInt(3) + a;
                        System.out.println("Found row " + row);
                    }

                    int column = 0;

                    while (column == 0 || column >= width - 1) {
                        System.out.println("Finding column");
                        System.out.println("b is " + b);
                        column = random.nextInt(3) + b;
                        System.out.println("Found column " + column);
                    }

                    System.out.println("Managing defect");
                    mainMaze[row][column] = 0;
                    System.out.println("Managed defect");
                }
            }
        }
    }
}
