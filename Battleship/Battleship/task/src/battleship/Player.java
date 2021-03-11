package battleship;

class Player {

    String playerName;

    public char[][] mainGrid;
    public char[][] fogGrid;

    java.util.Scanner scanner;

    public int aHealth;
    public int bHealth;
    public int sHealth;
    public int cHealth;
    public int dHealth;

    public boolean aIsAlive;
    public boolean bIsAlive;
    public boolean sIsAlive;
    public boolean cIsAlive;
    public boolean dIsAlive;

    public Player(String playerName) {

        this.playerName = playerName;
        this.scanner = new java.util.Scanner(System.in);

        this.mainGrid = new char[10][10];
        this.fogGrid = new char[10][10];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                this.mainGrid[i][j] = this.fogGrid[i][j] = '~';
            }
        }
        this.placeShips();
    }

    private void printMainGrid() {

        for (int i = -1; i < 10; i++) {

            if (i == -1) {

                System.out.print(" ");
                for (int k = 1; k <= 10; k++) {
                    System.out.printf(" " + k);
                }
            } else {

                for (int j = -1; j < 10; j++) {

                    if (j == -1) {
                        System.out.print((char) ('A' + i));
                    } else {
                        if (mainGrid[i][j] != '~') {
                            System.out.print(" " + 'O');
                        } else {
                            System.out.print(" " + mainGrid[i][j]);
                        }
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private void printEmptyGrid() {

        for (int i = -1; i < 10; i++) {

            if (i == -1) {

                System.out.print(" ");
                for (int k = 1; k <= 10; k++) {
                    System.out.printf(" " + k);
                }
            } else {

                for (int j = -1; j < 10; j++) {

                    if (j == -1) {
                        System.out.print((char) ('A' + i));
                    } else {
                        System.out.print(" " + "~");
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private void printFogGrid() {

        for (int i = -1; i < 10; i++) {

            if (i == -1) {

                System.out.print(" ");
                for (int k = 1; k <= 10; k++) {
                    System.out.printf(" " + k);
                }
            } else {

                for (int j = -1; j < 10; j++) {

                    if (j == -1) {
                        System.out.print((char) ('A' + i));
                    } else {
                        System.out.print(" " + fogGrid[i][j]);
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private void placeShips() {

        System.out.println(playerName + ", place your ships on the game field\n");

        printEmptyGrid();
        initializeAircraftCarrier();
        printMainGrid();
        initializeBattleship();
        printMainGrid();
        initializeSubmarine();
        printMainGrid();
        initializeCruiser();
        printMainGrid();
        initializeDestroyer();
        printMainGrid();

        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();
        String temp = scanner.nextLine();
    }

    private void initializeAircraftCarrier() {

        System.out.println("Enter the coordinates of the Aircraft Carrier (5 cells):\n");

        takePosition("Aircraft Carrier",5, 'A');

        this.aHealth = 5;
        this.aIsAlive = true;

        System.out.println();
    }

    private void initializeBattleship() {

        System.out.println("Enter the coordinates of the Battleship (4 cells):\n");

        takePosition("Battleship", 4, 'B');

        this.bHealth = 4;
        this.bIsAlive = true;

        System.out.println();
    }

    private void initializeSubmarine() {

        System.out.println("Enter the coordinates of the Submarine (3 cells):\n");

        takePosition("Submarine", 3, 'S');

        this.sHealth = 3;
        this.sIsAlive = true;

        System.out.println();
    }

    private void initializeCruiser() {

        System.out.println("Enter the coordinates of the Cruiser (3 cells):\n");

        takePosition("Cruiser", 3, 'C');

        this.cHealth = 3;
        this.cIsAlive = true;

        System.out.println();
    }

    private void initializeDestroyer() {

        System.out.println("Enter the coordinates of the Destroyer (2 cells):\n");

        takePosition("Destroyer", 2, 'D');

        this.dHealth = 2;
        this.dIsAlive = true;

        System.out.println();
    }

    private void takePosition(String nameOfCraft, int lengthOfCell, char shipCode) {

        while (true) {

            char firstAlpha;
            char secondAlpha;
            int firstNumber;
            int secondNumber;

            String firstCoordinate = scanner.next();
            String secondCoordinate = scanner.next();

            firstAlpha = firstCoordinate.charAt(0);
            secondAlpha = secondCoordinate.charAt(0);

            firstNumber = Integer.parseInt(firstCoordinate.substring(1));
            secondNumber = Integer.parseInt(secondCoordinate.substring(1));

            if (firstAlpha < 'A' || firstAlpha > 'J' ||
                    secondAlpha < 'A' || secondAlpha > 'J' ||
                    firstNumber < 1 || firstNumber > 10 ||
                    secondNumber < 1 || secondNumber > 10) {

                System.out.println("\nError! You entered the wrong coordinates! Try again:\n");
            } else if ((firstAlpha != secondAlpha) && (firstNumber != secondNumber)) {

                System.out.println("\nError! Wrong ship location! Try again:\n");
            } else if (Math.abs(firstAlpha - secondAlpha) != lengthOfCell - 1 && Math.abs(firstNumber - secondNumber) != lengthOfCell - 1) {

                System.out.println("\nError! Wrong length of the " + nameOfCraft + "! Try again:\n");
            } else if (!positionIsVacant(firstAlpha, secondAlpha, firstNumber, secondNumber)) {

                System.out.println("\nError! You placed it too close to another one. Try again:\n");
            }
            else {

                fillGrid(firstAlpha, secondAlpha, firstNumber, secondNumber, shipCode);
                break;
            }
        }
    }

    private boolean positionIsVacant(char firstAlpha, char secondAlpha, int firstNumber, int secondNumber) {

        if (firstAlpha == secondAlpha && firstNumber != secondNumber) {

            if (firstNumber < secondNumber) {

                for (int i = firstNumber - 1; i < secondNumber; i++) {

                    if (mainGrid[firstAlpha - 'A'][i] != '~') {
                        return false;
                    }
                }

                if (firstNumber == 1) {

                    if (mainGrid[firstAlpha - 'A'][secondNumber] != '~') {
                        return false;
                    }
                } else if (secondNumber == 10) {

                    if (mainGrid[firstAlpha - 'A'][firstNumber - 1] != '~') {
                        return false;
                    }
                } else {

                    if (mainGrid[firstAlpha - 'A'][firstNumber - 2] != '~' || mainGrid[firstAlpha - 'A'][secondNumber] != '~') {
                        return false;
                    }
                }

                if (firstAlpha == 'A') {

                    for (int i = firstNumber - 1; i < secondNumber; i++) {

                        if (mainGrid[1][i] != '~') {
                            return false;
                        }
                    }
                } else if (firstAlpha == 'J') {

                    for (int i = firstNumber - 1; i < secondNumber; i++) {

                        if (mainGrid[firstAlpha - 'A' - 1][i] != '~') {
                            return false;
                        }
                    }
                } else {

                    for (int i = firstNumber - 1; i < secondNumber; i++) {

                        if (mainGrid[firstAlpha - 'A' - 1][i] != '~' || mainGrid[firstAlpha - 'A' + 1][i] != '~') {
                            return false;
                        }
                    }
                }
            } else if (secondNumber < firstNumber) {

                for (int i = secondNumber - 1; i < firstNumber; i++) {

                    if (mainGrid[firstAlpha - 'A'][i] != '~') {
                        return false;
                    }
                }

                if (secondNumber == 1) {

                    if (mainGrid[firstAlpha - 'A'][firstNumber] != '~') {
                        return false;
                    }
                } else if (firstNumber == 10) {

                    if (mainGrid[firstAlpha - 'A'][secondNumber - 1] != '~') {
                        return false;
                    }
                } else {

                    if (mainGrid[firstAlpha - 'A'][secondNumber - 2] != '~' || mainGrid[firstAlpha - 'A'][firstNumber] != '~') {
                        return false;
                    }
                }

                if (firstAlpha == 'A') {

                    for (int i = secondNumber - 1; i < firstNumber; i++) {

                        if (mainGrid[1][i] != '~') {
                            return false;
                        }
                    }
                } else if (firstAlpha == 'J') {

                    for (int i = secondNumber - 1; i < firstNumber; i++) {

                        if (mainGrid[firstAlpha - 'A' - 1][i] != '~') {
                            return false;
                        }
                    }
                } else {

                    for (int i = secondNumber - 1; i < firstNumber; i++) {

                        if (mainGrid[firstAlpha - 'A' - 1][i] != '~' || mainGrid[firstAlpha - 'A' + 1][i] != '~') {
                            return false;
                        }
                    }
                }
            }
        } else if (firstAlpha != secondAlpha && firstNumber == secondNumber) {

            if (firstAlpha < secondAlpha) {

                for (int i = firstAlpha - 'A'; i <= secondAlpha - 'A'; i++) {

                    if (mainGrid[i][firstNumber - 1] != '~') {
                        return false;
                    }
                }

                if (firstAlpha == 'A') {

                    if (mainGrid[secondAlpha - 'A' + 1][firstNumber - 1] != '~') {
                        return false;
                    }
                } else if (secondAlpha == 'J') {

                    if (mainGrid[firstAlpha - 'A' - 1][firstNumber - 1] != '~') {
                        return false;
                    }
                } else {

                    if (mainGrid[secondAlpha - 'A' + 1][firstNumber - 1] != '~' || mainGrid[firstAlpha - 'A' - 1][firstNumber - 1] != '~') {
                        return false;
                    }
                }

                if (firstNumber == 1) {

                    for (int i = firstAlpha - 'A'; i <= secondAlpha - 'A'; i++) {

                        if (mainGrid[i][firstNumber] != '~') {
                            return false;
                        }
                    }
                } else if (firstNumber == 10) {

                    for (int i = firstAlpha - 'A'; i <= secondAlpha - 'A'; i++) {

                        if (mainGrid[i][firstNumber - 2] != '~') {
                            return false;
                        }
                    }
                } else {

                    for (int i = firstAlpha - 'A'; i <= secondAlpha - 'A'; i++) {

                        if (mainGrid[i][firstNumber] != '~' || mainGrid[i][firstNumber - 2] != '~') {
                            return false;
                        }
                    }
                }
            } else if (secondAlpha < firstAlpha) {

                for (int i = secondAlpha - 'A'; i <= firstAlpha - 'A'; i++) {

                    if (mainGrid[i][firstNumber - 1] != '~') {
                        return false;
                    }
                }

                if (secondAlpha == 'A') {

                    if (mainGrid[firstAlpha - 'A' + 1][firstNumber - 1] != '~') {
                        return false;
                    }
                } else if (firstAlpha == 'J') {

                    if (mainGrid[secondAlpha - 'A' - 1][firstNumber - 1] != '~') {
                        return false;
                    }
                } else {

                    if (mainGrid[firstAlpha - 'A' + 1][firstNumber - 1] != '~' || mainGrid[secondAlpha - 'A' - 1][firstNumber - 1] != '~') {
                        return false;
                    }
                }

                if (firstNumber == 1) {

                    for (int i = secondAlpha - 'A'; i <= firstAlpha - 'A'; i++) {

                        if (mainGrid[i][firstNumber] != '~') {
                            return false;
                        }
                    }
                } else if (firstNumber == 10) {

                    for (int i = secondAlpha - 'A'; i <= firstAlpha - 'A'; i++) {

                        if (mainGrid[i][firstNumber - 2] != '~') {
                            return false;
                        }
                    }
                } else {

                    for (int i = secondAlpha - 'A'; i <= firstAlpha - 'A'; i++) {

                        if (mainGrid[i][firstNumber] != '~' || mainGrid[i][firstNumber - 2] != '~') {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private void fillGrid(char firstAlpha, char secondAlpha, int firstNumber, int secondNumber, char shipCode) {

        if (firstAlpha == secondAlpha) {

            if (firstNumber < secondNumber) {

                for (int i = firstNumber - 1; i < secondNumber; i++) {

                    mainGrid[firstAlpha - 'A'][i] = shipCode;
                    fogGrid[firstAlpha - 'A'][i] = 'O';
                }
            } else if (secondNumber < firstNumber) {

                for (int i = secondNumber - 1; i < firstNumber; i++) {

                    mainGrid[firstAlpha - 'A'][i] = shipCode;
                    fogGrid[firstAlpha - 'A'][i] = 'O';
                }
            }
        } else if (firstNumber == secondNumber) {

            if (firstAlpha < secondAlpha) {

                for (int i = firstAlpha - 'A'; i <= secondAlpha - 'A'; i++) {

                    mainGrid[i][firstNumber - 1] = shipCode;
                    fogGrid[i][firstNumber - 1] = 'O';
                }
            } else if (secondAlpha < firstAlpha) {

                for (int i = secondAlpha - 'A'; i <= firstAlpha - 'A'; i++) {

                    mainGrid[i][firstNumber - 1] = shipCode;
                    fogGrid[i][firstNumber - 1] = 'O';
                }
            }
        }
    }

    private void fillOpponentFogGrid(char alpha, int number, char decider, Player p2) {

        p2.fogGrid[alpha - 'A'][number - 1] = decider;
    }

    public boolean takeAShot(Player p2) {

        printEmptyGrid();
        System.out.println("---------------------");
        printFogGrid();

        System.out.println(playerName + ", it's your turn:\n");


        char alpha;
        int number;

        while (true) {

            String coordinates = scanner.next();

            alpha = coordinates.charAt(0);
            number = Integer.parseInt(coordinates.substring(1));

            if (alpha < 'A' || alpha > 'J' ||
                    number < 1 || number > 10) {

                System.out.println("\nError! You entered the wrong coordinates! Try again:\n");
            } else {

                if (p2.fogGrid[alpha - 'A'][number - 1] == 'X' || p2.fogGrid[alpha - 'A'][number - 1] == 'M') {

                    System.out.println("\nYou hit a ship!");
                    break;
                } else {

                    if (p2.mainGrid[alpha - 'A'][number - 1] != '~') {

                        fillOpponentFogGrid(alpha, number, 'X', p2);

                        switch (p2.mainGrid[alpha - 'A'][number - 1]) {

                            case 'A' :
                                p2.aHealth--;
                                break;

                            case 'B' :
                                p2.bHealth--;
                                break;

                            case 'S' :
                                p2.sHealth--;
                                break;

                            case 'C' :
                                p2.cHealth--;
                                break;

                            case 'D' :
                                p2.dHealth--;
                                break;
                        }

                        if (p2.aHealth == 0 && p2.bHealth == 0 && p2.sHealth == 0 && p2.cHealth == 0 && p2.dHealth == 0) {

                            System.out.printf("%s sank the last ship! %s won! Congratulations!\n", playerName, playerName);
                            return true;
                        } else if (p2.aHealth == 0 && p2.aIsAlive) {
                            System.out.println("You sank a ship!");
                            p2.aIsAlive = false;
                            break;
                        } else if (p2.bHealth == 0 && p2.bIsAlive) {
                            System.out.println("You sank a ship!");
                            p2.bIsAlive = false;
                            break;
                        } else if (p2.sHealth == 0 && p2.sIsAlive) {
                            System.out.println("You sank a ship!");
                            p2.sIsAlive = false;
                            break;
                        } else if (p2.cHealth == 0 && p2.cIsAlive) {
                            System.out.println("You sank a ship!");
                            p2.cIsAlive = false;
                            break;
                        } else if (p2.dHealth == 0 && p2.dIsAlive) {
                            System.out.println("You sank a ship!");
                            p2.dIsAlive = false;
                            break;
                        } else {

                            System.out.println("You hit a ship!");
                            break;
                        }
                    } else {

                        fillOpponentFogGrid(alpha, number, 'M', p2);
                        System.out.println("You missed!");
                        break;
                    }
                }
            }
        }
        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();
        String temp = scanner.nextLine();
        return false;
    }
}
