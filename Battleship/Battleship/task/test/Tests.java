import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;


public class Tests extends StageTest<String> {
    @DynamicTestingMethod
    CheckResult testExample() {

        TestedProgram main = new TestedProgram();
        String output = main.start().trim();
        String[][] matrix;

        if (!matrixIsEmpty(getFieldMatrix(output))) {
            return CheckResult.wrong("Not an empty game field at the start of the game");
        }

        // Filling the first player field
        if (!output.toLowerCase().contains("aircraft carrier")) {
            return CheckResult.wrong("After starting the program, you should request " +
                "the coordinates of the Aircraft Carrier in that way:\n" +
                "\"Enter the coordinates of the Aircraft Carrier (5 cells):\"");
        }

        output = main.execute("F3 F7").trim();
        matrix = getFieldMatrix(output);
        findShipByCoordinates(matrix, "F3 F7");

        if (!output.toLowerCase().contains("battleship")) {
            return CheckResult.wrong("After asking for the Aircraft Carrier coordinates, you should request " +
                "the coordinates of the Battleship in that way:\n" +
                "\"Enter the coordinates of the Battleship (4 cells):\"");
        }

        output = main.execute("A1 D1").trim();
        matrix = getFieldMatrix(output);
        findShipByCoordinates(matrix, "A1 D1");

        if (!output.toLowerCase().contains("submarine")) {
            return CheckResult.wrong("After asking for the Battleship coordinates, you should request " +
                "the coordinates of the Submarine in that way:\n" +
                "\"Enter the coordinates of the Submarine (3 cells):\"");
        }

        output = main.execute("J7 J10").trim();
        if (isGameFieldPrinted(output)) {
            return CheckResult.wrong("Your program should not print a game field if there is an input mistake.");
        }

        output = main.execute("J10 J8").trim();
        matrix = getFieldMatrix(output);
        findShipByCoordinates(matrix, "J10 J8");

        if (!output.toLowerCase().contains("cruiser")) {
            return CheckResult.wrong("After asking for the Submarine coordinates, you should request " +
                "the coordinates of the Cruiser in that way:\n" +
                "\"Enter the coordinates of the Cruiser (3 cells):\"");
        }

        output = main.execute("B9 D8").trim();
        if (isGameFieldPrinted(output)) {
            return CheckResult.wrong("Your program should not print a game field if there is an input mistake.");
        }

        output = main.execute("B9 D9").trim();
        matrix = getFieldMatrix(output);
        findShipByCoordinates(matrix, "B9 D9");

        if (!output.toLowerCase().contains("destroyer")) {
            return CheckResult.wrong("After asking for the Cruiser coordinates, you should request " +
                "the coordinates of the Destroyer in that way:\n" +
                "\"Enter the coordinates of the Destroyer (2 cells):\"");
        }

        output = main.execute("E6 D6").trim();
        if (isGameFieldPrinted(output)) {
            return CheckResult.wrong("Your program should not print a game field if there is an input mistake. " +
                "(Too close to another ship)");
        }

        output = main.execute("I2 J2").trim();
        matrix = getFieldMatrix(output);
        findShipByCoordinates(matrix, "I2 J2");

        if (!output.toLowerCase().contains("enter")) {
            return CheckResult.wrong("No offer found to give the move to another player");
        }

        output = main.execute("");

        // Filling the second player field

        if (!output.toLowerCase().contains("aircraft carrier")) {
            return CheckResult.wrong("After filling the first player field, you should request the second player's " +
                "coordinates of the Aircraft Carrier in that way:\n" +
                "\"Enter the coordinates of the Aircraft Carrier (5 cells):\"");
        }

        output = main.execute("H2 H6");
        matrix = getFieldMatrix(output);
        findShipByCoordinates(matrix, "H2 H6");

        if (!output.toLowerCase().contains("battleship")) {
            return CheckResult.wrong("After asking for the Aircraft Carrier coordinates, you should request " +
                "the coordinates of the Battleship in that way:\n" +
                "\"Enter the coordinates of the Battleship (4 cells):\"");
        }

        output = main.execute("F3 F6");
        matrix = getFieldMatrix(output);
        findShipByCoordinates(matrix, "F3 F6");

        if (!output.toLowerCase().contains("submarine")) {
            return CheckResult.wrong("After asking for the Battleship coordinates, you should request " +
                "the coordinates of the Submarine in that way:\n" +
                "\"Enter the coordinates of the Submarine (3 cells):\"");
        }

        output = main.execute("H8 F8").trim();
        matrix = getFieldMatrix(output);
        findShipByCoordinates(matrix, "H8 F8");

        if (!output.toLowerCase().contains("cruiser")) {
            return CheckResult.wrong("After asking for the Submarine coordinates, you should request " +
                "the coordinates of the Cruiser in that way:\n" +
                "\"Enter the coordinates of the Cruiser (3 cells):\"");
        }

        output = main.execute("D4 D6").trim();
        matrix = getFieldMatrix(output);
        findShipByCoordinates(matrix, "D4 D6");

        if (!output.toLowerCase().contains("destroyer")) {
            return CheckResult.wrong("After asking for the Cruiser coordinates, you should request " +
                "the coordinates of the Destroyer in that way:\n" +
                "\"Enter the coordinates of the Destroyer (2 cells):\"");
        }

        output = main.execute("D8 C8");
        matrix = getFieldMatrix(output);
        findShipByCoordinates(matrix, "D8 C8");

        if (!output.toLowerCase().contains("enter")) {
            return CheckResult.wrong("no offer found to give the move to another player");
        }
        output = main.execute("");

        // Players' moves
        String[] splittedOutput = output.split("---\n");
        if (splittedOutput.length != 2) {
            return CheckResult.wrong("An incorrect number of game fields.\nThere is should 2 fields separated by \"---------------------\"");
        }
        if (!matrixIsEmpty(getFieldMatrix(splittedOutput[0]))) {
            return CheckResult.wrong("At the start of the game the upper field should be empty");
        }
        findAllShips(getFieldMatrix(splittedOutput[1]),
            new String[]{"F3 F7", "A1 D1", "J10 J8", "B9 D9", "I2 J2"});

        output = main.execute("I3");
        if (!output.toLowerCase().contains("missed")) {
            return CheckResult.wrong("Incorrect reaction of the program if the player missed");
        }

        output = main.execute("");
        checkMissing(getFieldMatrix(output), "I3");

        splittedOutput = output.split("---\n");
        if (splittedOutput.length != 2) {
            return CheckResult.wrong("An incorrect number of game fields (2 should be)");
        }
        if (!matrixIsEmpty(getFieldMatrix(splittedOutput[0]))) {
            return CheckResult.wrong("At the start of the game the upper field should be empty");
        }
        findAllShips(getFieldMatrix(splittedOutput[1]),
            new String[]{"H2 H6", "F3 F6", "H8 F8", "D4 D6", "D8 C8"});

        output = main.execute("C9");
        if (!output.toLowerCase().contains("hit")) {
            return CheckResult.wrong("Incorrect reaction of the program if the player hit the ship");
        }
        main.execute("");

        makeMoveTillTheEnd(main);

        return CheckResult.correct();
    }

    void makeMoveTillTheEnd(TestedProgram main) {

        String[] secondPlayerMoves = {"A1", "B1", "C1", "D1", "B9", "C9", "D9", "F3", "F4", "F5", "F6", "F7", "I2", "J2", "J8", "J9", "J10"};
        String[] firstPlayerMoves = {"D4", "D5", "D6", "C8", "D8", "F3", "F4", "F5", "F6", "F7", "D10", "E10", "F10", "G10", "J1", "J2", "J3"};

        String[][] matrix;
        String output;
        int i;

        for (i = 0; i < 3; i++) {
            main.execute(firstPlayerMoves[i]);
            main.execute("");
            main.execute(secondPlayerMoves[i]);
            main.execute("");
        }

        main.execute(firstPlayerMoves[i]);
        main.execute("");

        output = main.execute(secondPlayerMoves[i]);
        if (!output.contains("sank")) {
            throw new WrongAnswer("After a ship was sunk you should print \"You sank a ship!\" and ask to press Enter.");
        }
        output = main.execute("");

        String[] splittedOutput = output.split("---\n");
        if (splittedOutput.length != 2) {
            throw new WrongAnswer("An incorrect number of game fields.\nThere is should 2 fields separated by \"---------------------\"");
        }

        matrix = getFieldMatrix(splittedOutput[1]);
        checkShot(matrix, "A1");
        checkShot(matrix, "B1");
        checkShot(matrix, "C1");
        checkShot(matrix, "D1");

        for (i = i + 1; i < secondPlayerMoves.length - 1; i++) {
            main.execute(firstPlayerMoves[i]);
            main.execute("");
            main.execute(secondPlayerMoves[i]);
            main.execute("");
        }

        main.execute(firstPlayerMoves[i]);
        main.execute("");
        output = main.execute(secondPlayerMoves[i]).toLowerCase();

        if (!output.contains("won") || !output.contains("congratulations")) {
            throw new WrongAnswer("If a player has sunk all enemy ships you should print:\n" +
                "\"You sank the last ship. You won. Congratulations!\"");
        }
    }

    void findShipByCoordinates(String[][] matrix, String coordinates) {
        int[] coordinatesInt = parseCoordinates(coordinates);

        if (coordinatesInt[0] > coordinatesInt[2]) {
            int swap = coordinatesInt[0];
            coordinatesInt[0] = coordinatesInt[2];
            coordinatesInt[2] = swap;
        } else if (coordinatesInt[1] > coordinatesInt[3]) {
            int swap = coordinatesInt[1];
            coordinatesInt[1] = coordinatesInt[3];
            coordinatesInt[3] = swap;
        }

        if (coordinatesInt[0] == coordinatesInt[2]) {
            int cord = coordinatesInt[0];
            for (int i = coordinatesInt[1]; i <= coordinatesInt[3]; i++) {
                if (!matrix[cord][i].toLowerCase().equals("x") && !matrix[cord][i].toLowerCase().equals("o")) {
                    throw new WrongAnswer("The ship's cells were not found at the coordinates \"" + coordinates + "\"");
                }
            }
        } else {
            int cord = coordinatesInt[1];
            for (int i = coordinatesInt[0]; i <= coordinatesInt[2]; i++) {
                if (!matrix[i][cord].toLowerCase().equals("x") && !matrix[i][cord].toLowerCase().equals("o")) {
                    throw new WrongAnswer("The ship's cells were not found at the \"" + coordinates + "\"");
                }
            }
        }
    }

    boolean matrixIsEmpty(String[][] matrix) {
        for (String[] strings : matrix) {
            for (int j = 0; j < matrix.length; j++) {
                if (!strings[j].trim().equals("~")) {
                    return false;
                }
            }
        }
        return true;
    }

    void checkShot(String[][] matrix, String coordinate) {
        int[] parsedCoordinate = new int[2];
        parsedCoordinate[0] = charToInt(coordinate.toLowerCase().substring(0, 1));
        parsedCoordinate[1] = Integer.parseInt(coordinate.substring(1)) - 1;

        if (!matrix[parsedCoordinate[0]][parsedCoordinate[1]].toLowerCase().contains("x")) {
            throw new WrongAnswer("Expected hit in \"" + coordinate + "\".");
        }
    }

    boolean checkMissing(String[][] matrix, String coordinate) {
        int[] parsedCoordinate = new int[2];
        parsedCoordinate[0] = charToInt(coordinate.toLowerCase().substring(0, 1));
        parsedCoordinate[1] = Integer.parseInt(coordinate.substring(1)) - 1;

        return matrix[parsedCoordinate[0]][parsedCoordinate[1]].toLowerCase().contains("m");
    }

    int[] parseCoordinates(String coordinatesString) {
        String[] splittedCoords = coordinatesString.split(" ");
        int[] parsedCoordinates = new int[4];

        parsedCoordinates[0] = charToInt(splittedCoords[0].substring(0, 1));
        parsedCoordinates[1] = Integer.parseInt(splittedCoords[0].substring(1)) - 1;
        parsedCoordinates[2] = charToInt(splittedCoords[1].substring(0, 1));
        parsedCoordinates[3] = Integer.parseInt(splittedCoords[1].substring(1)) - 1;

        return parsedCoordinates;
    }

    int charToInt(String charCoordinate) {
        charCoordinate = charCoordinate.toLowerCase();
        char character = charCoordinate.charAt(0);
        return (int) character - (int) 'a';
    }

    String[][] getFieldMatrix(String output) {

        WrongAnswer cantParseException = new WrongAnswer("Can't parse the game field\n" +
            "Make sure you print it like in examples!");

        String[] splittedOutput = output.split("\n");
        String[][] matrix = new String[10][10];

        try {
            int index = 0;
            while (!(splittedOutput[index].contains("1") &&
                splittedOutput[index].contains("2") &&
                splittedOutput[index].contains("10"))) {
                index++;
                if (index > 1000) {
                    throw cantParseException;
                }
            }
            index++;

            for (int i = 0; i < 10; i++) {
                String temp = splittedOutput[index].substring(2).trim();
                String[] splittedLine = temp.trim().split(" ");
                if (splittedLine.length != 10) {
                    throw cantParseException;
                }
                matrix[i] = splittedLine;
                index++;
            }
        } catch (IndexOutOfBoundsException ignored) {
            throw cantParseException;
        }

        return matrix;
    }

    boolean isGameFieldPrinted(String output) {
        return output.contains("1") && output.contains("2") && output.contains("10");
    }

    void findAllShips(String[][] matrix, String[] coordinates) {
        for (String item : coordinates) {
            findShipByCoordinates(matrix, item);
        }
    }
}