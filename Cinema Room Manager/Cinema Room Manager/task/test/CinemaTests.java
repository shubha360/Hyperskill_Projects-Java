import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

public class CinemaTests extends StageTest<String> {

    @DynamicTestingMethod
    CheckResult test1_checkExit() {

        TestedProgram program = new TestedProgram();
        String output = program.start().trim();

        if (!output.toLowerCase().contains("enter the number of rows")) {
            return CheckResult.wrong("At the beginning your program should ask for entering the number of rows.\n" +
                "Your output should contain 'Enter the number of rows:'.");
        }

        output = program.execute("7");

        if (!output.toLowerCase().contains("enter the number of seats in each row")) {
            return CheckResult.wrong("After entering the number of rows your program should ask for entering" +
                " the number of seats in ean 'Enter the number of seats in each row'.");
        }

        output = program.execute("8").toLowerCase();

        if (!output.contains("show the seats") ||
            !output.contains("buy a ticket") ||
            !output.contains("exit") ||
            !output.contains("statistics")) {
            return CheckResult.wrong("Your menu should contain 4 items:\n" +
                "1. Show the seats\n" +
                "2. Buy a ticket\n" +
                "3. Statistics\n" +
                "0. Exit");
        }

        program.execute("0");

        if (!program.isFinished()) {
            return CheckResult.wrong("After choosing 'Exit' item you should stop your program.");
        }

        return CheckResult.correct();
    }

    @DynamicTestingMethod
    CheckResult test2_checkSeatingArrangement() {

        TestedProgram program;
        String output;

        program = new TestedProgram();
        program.start();
        program.execute("7\n8");

        output = program.execute("1").toLowerCase();
        checkSeats(output, 7, 8);

        if (!output.contains("show the seats") ||
            !output.contains("buy a ticket") ||
            !output.contains("exit") ||
            !output.contains("statistics")) {
            return CheckResult.wrong("After showing the seats arrangement you should print the menu again!");
        }

        program.stop();

        program = new TestedProgram();
        program.start();
        program.execute("2\n2");

        output = program.execute("1");
        checkSeats(output, 2, 2);

        program.stop();

        program = new TestedProgram();
        program.start();
        program.execute("9\n9");

        output = program.execute("1");
        checkSeats(output, 9, 9);

        program.execute("0");
        return CheckResult.correct();
    }

    @DynamicTestingMethod
    CheckResult test3_checkBuyTicket() {

        TestedProgram program;
        String output;

        program = new TestedProgram();
        program.start();
        program.execute("7\n8");

        output = program.execute("2");

        if (!output.toLowerCase().contains("enter a row number:")) {
            return CheckResult.wrong("After choosing 'Buy a ticket' item you should ask for entering a row number.\n" +
                "Your output should contain 'Enter a row number:'.");
        }

        output = program.execute("2");

        if (!output.toLowerCase().contains("enter a seat number in that row:")) {
            return CheckResult.wrong("After entering a row number you should ask for entering a seat number in that row.\n" +
                "Your output should contain 'Enter a seat number in that row:'.");
        }

        output = program.execute("4").toLowerCase();

        if (!output.contains("ticket price")) {
            return CheckResult.wrong("After entering a row number and a seat number in that row you should print" +
                " the ticket price.\n" +
                "Your output should contain 'Ticket price:'.");
        }

        if (!output.contains("$10")) {
            return CheckResult.wrong("Looks like you miscalculated the ticket price. Can't find '$10' in your output.");
        }

        if (!output.contains("show the seats") ||
            !output.contains("buy a ticket") ||
            !output.contains("exit") ||
            !output.contains("statistics")) {
            return CheckResult.wrong("After buying a ticket you should print the menu again!");
        }

        output = program.execute("1");
        checkTakenSeat(output, 7, 8, 2, 4);

        output = program.execute("2\n3\n5\n1");
        checkTakenSeat(output, 7, 8, 3, 5);

        return CheckResult.correct();
    }

    @DynamicTestingMethod
    CheckResult test4_checkTicketPrice() {

        TestedProgram program;
        String output;

        program = new TestedProgram();
        program.start();
        program.execute("9\n9");

        program.execute("2");
        output = program.execute("1\n1");

        if (!output.contains("$10")) {
            return CheckResult.wrong("Looks like you miscalculated the ticket price. Can't find '$10' in your output.");
        }

        program.execute("2");
        output = program.execute("4\n5\n1");

        if (!output.contains("$10")) {
            return CheckResult.wrong("Looks like you miscalculated the ticket price. Can't find '$10' in your output.");
        }
        checkTakenSeat(output, 9, 9, 4, 5);


        program.execute("2");
        output = program.execute("5\n5\n1");

        if (!output.contains("$8")) {
            return CheckResult.wrong("Looks like you miscalculated the ticket price. Can't find '$8' in your output.");
        }
        checkTakenSeat(output, 9, 9, 4, 5);
        checkTakenSeat(output, 9, 9, 5, 5);

        program.execute("2");
        output = program.execute("6\n6\n1");

        if (!output.contains("$8")) {
            return CheckResult.wrong("Looks like you miscalculated the ticket price. Can't find '$8' in your output.");
        }
        checkTakenSeat(output, 9, 9, 4, 5);
        checkTakenSeat(output, 9, 9, 5, 5);
        checkTakenSeat(output, 9, 9, 6, 6);

        return CheckResult.correct();
    }

    @DynamicTestingMethod
    CheckResult test5_checkErrorHandling() {

        TestedProgram program;
        String output;

        program = new TestedProgram();
        program.start();

        program.execute("9\n9");
        program.execute("2\n1\n1");

        output = program.execute("2\n1\n1");

        if (!output.toLowerCase().contains("that ticket has already been purchased")) {
            return CheckResult.wrong("If the user tries to buy a ticket that already has been purchased you should print: " +
                "'That ticket has already been purchased'");
        }

        if (!output.toLowerCase().contains("enter a row number")) {
            return CheckResult.wrong("If the user tries to buy a ticket that already has been purchased you should " +
                "ask to enter a seat coordinates again.");
        }

        output = program.execute("9");

        if (!output.toLowerCase().contains("enter a seat number in that row:")) {
            return CheckResult.wrong("After entering a row number you should ask for entering a seat number in that row.\n" +
                "Your output should contain 'Enter a seat number in that row:'.");
        }

        output = program.execute("5").toLowerCase();

        if (!output.contains("ticket price")) {
            return CheckResult.wrong("After entering a row number and a seat number in that row you should print" +
                " the ticket price.\n" +
                "Your output should contain 'Ticket price:'.");
        }

        if (!output.contains("8")) {
            return CheckResult.wrong("Looks like you miscalculated the ticket price. Can't find '$8' in your output.");
        }

        output = program.execute("2\n9\n5");

        if (!output.toLowerCase().contains("that ticket has already been purchased")) {
            return CheckResult.wrong("If the user tries to buy a ticket that already has been purchased you should print:\n" +
                "'That ticket has already been purchased' and ask to enter a seat coordinates again.");
        }

        program.execute("2\n3");

        output = program.execute("2\n10\n1");

        if (!output.toLowerCase().contains("wrong input")) {
            return CheckResult.wrong("If the user input coordinates is out of bounds you should print 'Wrong input'");
        }

        if (!output.toLowerCase().contains("enter a row number")) {
            return CheckResult.wrong("If the user input coordinates is out of bounds you should " +
                "ask to enter a seat coordinates again.");
        }

        program.execute("5\n10");

        if (!output.toLowerCase().contains("wrong input")) {
            return CheckResult.wrong("If the user input coordinates is out of bounds you should print 'Wrong input'");
        }

        program.execute("-5\n12");

        if (!output.toLowerCase().contains("wrong input")) {
            return CheckResult.wrong("If the user input coordinates is out of bounds you should print 'Wrong input'");
        }

        program.execute("5\n5");

        return CheckResult.correct();
    }

    @DynamicTestingMethod
    CheckResult test6_checkStatistics() {

        TestedProgram program;
        String output;

        program = new TestedProgram();
        program.start();
        program.execute("9\n9");

        output = program.execute("3").toLowerCase();

        if (!output.contains("number of purchased tickets")) {
            return CheckResult.wrong("After choosing 'Statistics' item can't find information about the number of purchased tickets.\n" +
                "Your output should contain 'Number of purchased tickets'");
        }

        if (!output.contains("percentage")) {
            return CheckResult.wrong("After choosing 'Statistics' item can't find information about the percentage.\n" +
                "Your output should contain 'Percentage'");
        }

        if (!output.contains("current income")) {
            return CheckResult.wrong("After choosing 'Statistics' item can't find information about the current income.\n" +
                "Your output should contain 'Current income'");
        }

        if (!output.contains("total income")) {
            return CheckResult.wrong("After choosing 'Statistics' item can't find information about the total income.\n" +
                "Your output should contain 'Total income'");
        }

        checkNumberOfPurchasedTickets(output, "0");
        checkPercentage(output, "0.00%");
        checkCurrentIncome(output, "$0");
        checkTotalIncome(output, "$720");


        program.execute("2\n1\n7");
        program.execute("2\n1\n8");
        program.execute("2\n1\n9");
        output = program.execute("3");

        checkNumberOfPurchasedTickets(output, "3");
        checkPercentage(output, "3.70%");
        checkCurrentIncome(output, "$30");
        checkTotalIncome(output, "$720");

        program.execute("2\n9\n1");
        program.execute("2\n9\n2");
        program.execute("2\n9\n3");
        output = program.execute("3");

        checkNumberOfPurchasedTickets(output, "6");
        checkPercentage(output, "7.41%");
        checkCurrentIncome(output, "$54");
        checkTotalIncome(output, "$720");

        for (int i = 1; i < 8; i++) {
            for (int j = 0; j < 9; j++) {
                output = program.execute("2\n" + (i + 1) + "\n" + (j + 1));
                if (!output.toLowerCase().contains("ticket price")) {
                    return CheckResult.wrong("After choosing a not taken seat," +
                        " your output should contain 'Ticket price:'.");
                }
            }
        }

        output = program.execute("3");

        checkNumberOfPurchasedTickets(output, "69");
        checkPercentage(output, "85.19%");
        checkCurrentIncome(output, "$612");
        checkTotalIncome(output, "$720");

        for (int j = 0; j < 6; j++) {
            output = program.execute("2\n" + 1 + "\n" + (j + 1));
            if (!output.toLowerCase().contains("ticket price")) {
                return CheckResult.wrong("After choosing a not taken seat," +
                    " your output should contain 'Ticket price:'.");
            }
        }

        program.execute("1");

        for (int j = 3; j < 9; j++) {
            output = program.execute("2\n" + 9 + "\n" + (j + 1));
            if (!output.toLowerCase().contains("ticket price")) {
                return CheckResult.wrong("After choosing a not taken seat," +
                    " your output should contain 'Ticket price:'.");
            }
        }

        output = program.execute("3");

        checkNumberOfPurchasedTickets(output, "81");
        checkPercentage(output, "100.00%");
        checkCurrentIncome(output, "$720");
        checkTotalIncome(output, "$720");

        return CheckResult.correct();
    }


    private void checkNumberOfPurchasedTickets(String output, String correctNumber) {
        String[] splittedOutput = output.trim().split("\n");
        boolean isFound = false;

        for (String line : splittedOutput) {
            if (line.toLowerCase().contains("number of purchased tickets")) {
                line = line.toLowerCase().replace("number of purchased tickets", "").replace(":", "").trim();
                isFound = true;
                if (!line.equals(correctNumber)) {
                    throw new WrongAnswer("Wrong number of purchased tickets!\n" +
                        "Found: " + line + "\n" +
                        "Expected: " + correctNumber);
                }
            }
        }

        if (!isFound) {
            throw new WrongAnswer("After choosing 'Statistics' item can't find information about the number of purchased tickets.\n" +
                "Your output should contain 'Number of purchased tickets'");
        }
    }

    private void checkPercentage(String output, String correctNumber) {
        String[] splittedOutput = output.trim().split("\n");
        boolean isFound = false;

        for (String line : splittedOutput) {
            if (line.toLowerCase().contains("percentage")) {
                line = line.toLowerCase().replace("percentage", "").replace(":", "").replace(",", ".").trim();
                isFound = true;
                if (!line.equals(correctNumber)) {
                    throw new WrongAnswer("Wrong percentage!\n" +
                        "Found: " + line + "\n" +
                        "Expected: " + correctNumber);
                }
            }
        }
        if (!isFound) {
            throw new WrongAnswer("After choosing 'Statistics' item can't find information about the percentage.\n" +
                "Your output should contain 'Percentage'");
        }
    }

    private void checkCurrentIncome(String output, String correctNumber) {
        String[] splittedOutput = output.trim().split("\n");
        boolean isFound = false;

        for (String line : splittedOutput) {
            if (line.toLowerCase().contains("current income")) {
                line = line.toLowerCase().replace("current income", "").replace(":", "").trim();
                isFound = true;
                if (!line.equals(correctNumber)) {
                    throw new WrongAnswer("Wrong current income!\n" +
                        "Found: " + line + "\n" +
                        "Expected: " + correctNumber);
                }
            }
        }
        if (!isFound) {
            throw new WrongAnswer("After choosing 'Statistics' item can't find information about the current income.\n" +
                "Your output should contain 'Current income'");
        }
    }

    private void checkTotalIncome(String output, String correctNumber) {
        String[] splittedOutput = output.trim().split("\n");
        boolean isFound = false;

        for (String line : splittedOutput) {
            if (line.toLowerCase().contains("total income")) {
                line = line.toLowerCase().replace("total income", "").replace(":", "").trim();
                isFound = true;
                if (!line.equals(correctNumber)) {
                    throw new WrongAnswer("Wrong total income!\n" +
                        "Found: " + line + "\n" +
                        "Expected: " + correctNumber);
                }
            }
        }
        if (!isFound) {
            throw new WrongAnswer("After choosing 'Statistics' item can't find information about the total income.\n" +
                "Your output should contain 'Total income'");
        }
    }


    private void checkSeats(String output, int rows, int columns) {

        if (!output.toLowerCase().contains("cinema:")) {
            throw new WrongAnswer("After entering a row number and a seat number in that row you should print" +
                " the seating arrangement like in example!\n" +
                "Your output should contain 'Cinema:'");
        }

        String arrangement = output.toLowerCase().split("cinema:")[1].trim();
        StringBuilder header = new StringBuilder();

        for (int i = 0; i < columns; i++) {
            header.append(i + 1);
            if (i != columns - 1) {
                header.append(" ");
            }
        }

        if (!arrangement.startsWith(header.toString())) {
            throw new WrongAnswer("The first line of the seating arrangement should be " +
                "'  " + header + "'");
        }

        String[] splittedOutput = arrangement.split("\n");

        for (int i = 1; i < rows + 1; i++) {

            if (i == splittedOutput.length) {
                throw new WrongAnswer("In the seating arrangement should be " + rows + " rows!");
            }

            String errorMessage = "The ";
            if (i == 1) {
                errorMessage += "second ";
            } else if (i == 2) {
                errorMessage += "third  ";
            } else {
                errorMessage += i + "th ";
            }

            errorMessage += "line of the seating arrangement ";

            if (!splittedOutput[i].contains("" + i)) {
                errorMessage += "should start with \"" + i + "\"";
                throw new WrongAnswer(errorMessage);
            }

            String line = splittedOutput[i].replace("" + i, "").trim();
            String seats = "";

            for (int j = 0; j < columns; j++) {
                seats += "S ";
            }

            if (!line.toUpperCase().equals(seats.trim())) {
                errorMessage += "should be \"" + i + " " + seats.trim() + "\"";
                throw new WrongAnswer(errorMessage);
            }
        }
    }

    private void checkTakenSeat(String output, int rows, int columns, int row, int column) {

        if (!output.toLowerCase().contains("cinema:")) {
            throw new WrongAnswer("After entering a row number and a seat number in that row you should print" +
                " the seating arrangement like in example!\n" +
                "Your output should contain 'Cinema:'");
        }

        String arrangement = output.toLowerCase().split("cinema:")[1].trim();
        StringBuilder header = new StringBuilder();

        for (int i = 0; i < columns; i++) {
            header.append(i + 1);
            if (i != columns - 1) {
                header.append(" ");
            }
        }

        if (!arrangement.startsWith(header.toString())) {
            throw new WrongAnswer("The first line of the seating arrangement should be " +
                "'  " + header + "'");
        }

        String[] splittedOutput = arrangement.split("\n");

        for (int i = 1; i < rows + 1; i++) {

            if (i == splittedOutput.length) {
                throw new WrongAnswer("In the seating arrangement should be " + rows + " rows!");
            }

            String errorMessage = "The ";
            if (i == 1) {
                errorMessage += "second ";
            } else if (i == 2) {
                errorMessage += "third ";
            } else {
                errorMessage += i + "th ";
            }

            errorMessage += "line of the seating arrangement ";

            if (!splittedOutput[i].contains("" + i)) {
                errorMessage += "should start with \"" + i + "\"";
                throw new WrongAnswer(errorMessage);
            }

            if (i == row) {
                String line = splittedOutput[i];

                if (!line.contains("b")) {
                    errorMessage += "should contain 'B' symbol.";
                    throw new WrongAnswer(errorMessage);
                }

                String[] splittedLine = line.trim().split(" ");

                if (splittedLine.length != columns + 1) {
                    errorMessage += "should be printed like in examples. Expected 1 number, " + columns + " symbols and a single space between of them!";
                    throw new WrongAnswer(errorMessage);
                }

                if (!splittedLine[column].equals("b")) {
                    errorMessage += "should contain 'B' symbol at " + column + " column";
                    throw new WrongAnswer(errorMessage);
                }
            }
        }
    }
}
