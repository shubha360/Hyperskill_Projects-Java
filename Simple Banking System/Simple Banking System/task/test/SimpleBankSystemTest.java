import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleBankSystemTest extends StageTest<String> {

    private static final String databaseFileName = "card.s3db";
    private static final String tempDatabaseFileName = "tempDatabase.s3db";
    private static final String[] args = {"-fileName", databaseFileName};
    private static final Map<String, String> correctData = new HashMap<>();

    private static final Pattern cardNumberPattern = Pattern.compile("^400000\\d{10}$", Pattern.MULTILINE);
    private static final Pattern pinPattern = Pattern.compile("^\\d{4}$", Pattern.MULTILINE);

    private static Connection connection;


    @DynamicTest
    CheckResult test1_checkDatabaseFile() {

        TestedProgram program = new TestedProgram();
        program.start(args);

        stopAndCheckIfUserProgramWasStopped(program);

        File file = new File(databaseFileName);

        if (!file.exists()) {
            return CheckResult.wrong("You should create a database file " +
                "named " + databaseFileName + ". The file name should be taken from the command line arguments.\n" +
                "The database file shouldn't be deleted after stopping the program!");
        }

        return CheckResult.correct();
    }

    @DynamicTest
    CheckResult test2_checkConnection() {

        TestedProgram program = new TestedProgram();
        program.start(args);

        stopAndCheckIfUserProgramWasStopped(program);

        getConnection();
        closeConnection();

        return CheckResult.correct();
    }

    @DynamicTest
    CheckResult test3_checkIfTableExists() {

        TestedProgram program = new TestedProgram();
        program.start(args);

        stopAndCheckIfUserProgramWasStopped(program);

        try {
            ResultSet resultSet = getConnection().createStatement().executeQuery(
                "SELECT name FROM sqlite_master WHERE type ='table' AND name NOT LIKE 'sqlite_%';");
            while (resultSet.next()) {
                if (resultSet.getString("name").equals("card")) {
                    return CheckResult.correct();
                }
            }
        } catch (SQLException e) {
            return CheckResult.wrong("Can't execute a query in your database! Make sure that your database isn't broken and you close your connection at the end of the program!");
        }

        closeConnection();
        return CheckResult.wrong("Your database doesn't have a table named 'card'");
    }

    @DynamicTest
    CheckResult test4_checkColumns() {

        TestedProgram program = new TestedProgram();
        program.start(args);

        stopAndCheckIfUserProgramWasStopped(program);

        try {

            ResultSet resultSet = getConnection().createStatement().executeQuery("PRAGMA table_info(card);");
            Map<String, String> columns = new HashMap<>();

            while (resultSet.next()) {
                columns.put(resultSet.getString("name").toLowerCase(), resultSet.getString("type").toUpperCase());
            }

            String[][] correctColumns = {
                {"id", "INTEGER", "INT"},
                {"number", "TEXT", "VARCHAR"},
                {"pin", "TEXT", "VARCHAR"},
                {"balance", "INTEGER", "INT"}};

            for (String[] correctColumn : correctColumns) {
                String errorMessage = "Can't find '" + correctColumn[0] + "' column with '" + correctColumn[1] + "' type.\n" +
                    "Your table should have columns described in " +
                    "the stage instructions.";
                if (!columns.containsKey(correctColumn[0])) {
                    return CheckResult.wrong(errorMessage);
                } else if (!columns.get(correctColumn[0]).contains(correctColumn[1]) && !columns.get(correctColumn[0]).contains(correctColumn[2])) {
                    return CheckResult.wrong(errorMessage);
                }
            }
        } catch (SQLException e) {
            return CheckResult.wrong("Can't connect to the database!");
        }

        closeConnection();
        return CheckResult.correct();
    }


    @DynamicTest
    CheckResult test5_checkAddingRowsToTheTable() {

        deleteAllRows();

        TestedProgram program = new TestedProgram();
        program.start(args);

        String output = program.execute("1");

        if (!getData(output)) {
            return CheckResult.wrong("You should output card number and PIN like in example\n" +
                "Or it doesn't pass the Luhn algorithm");
        }

        output = program.execute("1");

        if (!getData(output)) {
            return CheckResult.wrong("You should output card number and PIN like in example\n" +
                "Or it doesn't pass the Luhn algorithm");
        }

        output = program.execute("1");

        if (!getData(output)) {
            return CheckResult.wrong("You should output card number and PIN like in example\n" +
                "Or it doesn't pass the Luhn algorithm");
        }

        output = program.execute("1");

        if (!getData(output)) {
            return CheckResult.wrong("You should output card number and PIN like in example\n" +
                "Or it doesn't pass the Luhn algorithm");
        }

        output = program.execute("1");

        if (!getData(output)) {
            return CheckResult.wrong("You should output card number and PIN like in example\n" +
                "Or it doesn't pass the Luhn algorithm");
        }

        stopAndCheckIfUserProgramWasStopped(program);

        try {

            ResultSet resultSet = getConnection().createStatement().executeQuery("SELECT * FROM card");
            Map<String, String> userData = new HashMap<>();

            while (resultSet.next()) {
                System.out.println(resultSet.getString("number"));
                if (resultSet.getString("number") == null) {
                    return CheckResult.wrong("The card number shouldn't be null in the database!");
                }
                if (resultSet.getInt("balance") != 0) {
                    return CheckResult.wrong("Default balance value should be 0 in the database!");
                }
                if (resultSet.getString("pin") == null) {
                    return CheckResult.wrong("The PIN shouldn't be null in the database!");
                }
                userData.put(resultSet.getString("number"), resultSet.getString("pin"));
            }

            for (Map.Entry<String, String> entry : correctData.entrySet()) {
                if (!userData.containsKey(entry.getKey())) {
                    return CheckResult.wrong("Your database doesn't save newly created cards.");
                } else if (!userData.get(entry.getKey()).equals(entry.getValue())) {
                    return CheckResult.wrong("Correct PIN for card number " + entry.getKey() + " should " +
                        "be " + entry.getValue());
                }
            }


        } catch (SQLException e) {
            return CheckResult.wrong("Can't connect the database!");
        }

        closeConnection();
        return CheckResult.correct();
    }

    @DynamicTest
    CheckResult test6_checkLogIn() {

        TestedProgram program = new TestedProgram();
        program.start(args);

        String output = program.execute("1");

        Matcher cardNumberMatcher = cardNumberPattern.matcher(output);

        if (!cardNumberMatcher.find()) {
            return CheckResult.wrong("You are printing the card number " +
                "incorrectly. The card number should look like in the example:" +
                " 400000DDDDDDDDDD, where D is a digit.");
        }

        Matcher pinMatcher = pinPattern.matcher(output);

        if (!pinMatcher.find()) {
            return CheckResult.wrong("You are printing the card PIN " +
                "incorrectly. The PIN should look like in the example: DDDD, where D is a digit.");
        }

        String correctPin = pinMatcher.group().trim();
        String correctCardNumber = cardNumberMatcher.group();

        program.execute("2");
        output = program.execute(correctCardNumber + "\n" + correctPin);

        if (!output.toLowerCase().contains("successfully")) {
            return CheckResult.wrong("The user should be signed in after" +
                " entering the correct card information.");
        }

        stopAndCheckIfUserProgramWasStopped(program);

        return CheckResult.correct();
    }

    @DynamicTest
    CheckResult test7_checkLogInWithWrongPin() {

        TestedProgram program = new TestedProgram();
        program.start(args);

        String output = program.execute("1");

        Matcher cardNumberMatcher = cardNumberPattern.matcher(output);
        Matcher pinMatcher = pinPattern.matcher(output);

        if (!cardNumberMatcher.find() || !pinMatcher.find()) {
            return CheckResult.wrong("You should output card number and PIN like in example");
        }

        String correctCardNumber = cardNumberMatcher.group();
        String correctPin = pinMatcher.group();

        Random random = new Random();

        String incorrectPin = correctPin;

        while (correctPin.equals(incorrectPin)) {
            incorrectPin = String.valueOf(1000 + random.nextInt(8999));
        }

        program.execute("2");
        output = program.execute(correctCardNumber + "\n" + incorrectPin);

        if (output.toLowerCase().contains("successfully")) {
            return CheckResult.wrong("The user should not be signed in" +
                " after entering incorrect card information.");
        }

        stopAndCheckIfUserProgramWasStopped(program);
        return CheckResult.correct();
    }

    @DynamicTest
    CheckResult test8_checkLogInToNotExistingAccount() {

        TestedProgram program = new TestedProgram();
        program.start(args);

        String output = program.execute("1");

        Matcher cardNumberMatcher = cardNumberPattern.matcher(output);
        Matcher pinMatcher = pinPattern.matcher(output);

        if (!cardNumberMatcher.find() || !pinMatcher.find()) {
            return CheckResult.wrong("You should output card number and PIN like in example");
        }

        String correctCardNumber = cardNumberMatcher.group();
        String correctPin = pinMatcher.group();

        Random random = new Random();

        String incorrectCardNumber = correctCardNumber;

        while (correctCardNumber.equals(incorrectCardNumber)) {
            incorrectCardNumber = "400000" + (1_000_000_00 + random.nextInt(8_000_000_00));
        }

        program.execute("2");
        output = program.execute(incorrectCardNumber + "\n" + correctPin);

        if (output.toLowerCase().contains("successfully")) {
            return CheckResult.wrong("The user should not be signed in" +
                " after entering incorrect card information.");
        }

        stopAndCheckIfUserProgramWasStopped(program);
        return CheckResult.correct();
    }

    @DynamicTest
    CheckResult test9_checkBalance() {

        TestedProgram program = new TestedProgram();
        program.start(args);

        String output = program.execute("1");

        Matcher cardNumberMatcher = cardNumberPattern.matcher(output);
        Matcher pinMatcher = pinPattern.matcher(output);

        if (!cardNumberMatcher.find() || !pinMatcher.find()) {
            return CheckResult.wrong("You should output card number and PIN like in example");
        }

        String correctPin = pinMatcher.group().trim();
        String correctCardNumber = cardNumberMatcher.group();

        program.execute("2");
        program.execute(correctCardNumber + "\n" + correctPin);

        output = program.execute("1");

        if (!output.contains("0")) {
            return CheckResult.wrong("Expected balance: 0");
        }

        stopAndCheckIfUserProgramWasStopped(program);
        return CheckResult.correct();
    }

    private static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:" + databaseFileName);
            } catch (SQLException exception) {
                throw new WrongAnswer("Can't connect to the database! Make sure you close your database" +
                    " connection at the end of the program!");
            }
        }
        return connection;
    }

    private static void closeConnection() {
        if (connection == null)
            return;
        try {
            connection.close();
        } catch (SQLException ignored) {
        }
        connection = null;
    }

    @BeforeClass
    public static void createTempDatabase() throws IOException {

        closeConnection();

        Path userDbFile = Paths.get(databaseFileName);
        Path tempDbFile = Paths.get(tempDatabaseFileName);

        if (!userDbFile.toFile().exists())
            return;

        try {
            Files.deleteIfExists(tempDbFile);
            Files.copy(userDbFile, tempDbFile);
        } catch (Exception ignored) {}
    }

    @AfterClass
    public static void deleteTempDatabase() throws IOException {

        closeConnection();

        Path userDbFile = Paths.get(databaseFileName);
        Path tempDbFile = Paths.get(tempDatabaseFileName);

        if (!tempDbFile.toFile().exists())
            return;

        try {
            Files.deleteIfExists(userDbFile);
            Files.move(tempDbFile, userDbFile);
        } catch (Exception ignored) {}
    }

    private boolean getData(String out) {

        Pattern cardNumberPattern = Pattern.compile("400000\\d{10}");
        Matcher cardNumberMatcher = cardNumberPattern.matcher(out);

        Pattern pinPattern = Pattern.compile("^\\d{4}$", Pattern.MULTILINE);
        Matcher pinMatcher = pinPattern.matcher(out);

        if (!cardNumberMatcher.find() || !pinMatcher.find()) {
            return false;
        }

        String number = cardNumberMatcher.group();
        String PIN = pinMatcher.group();

        if (!checkLuhnAlgorithm(number)) {
            return false;
        }

        correctData.put(number, PIN);

        return true;
    }

    private boolean checkLuhnAlgorithm(String cardNumber) {
        int result = 0;
        for (int i = 0; i < cardNumber.length(); i++) {
            int digit = Character.getNumericValue(cardNumber.charAt(i));
            if (i % 2 == 0) {
                int doubleDigit = digit * 2 > 9 ? digit * 2 - 9 : digit * 2;
                result += doubleDigit;
                continue;
            }
            result += digit;
        }
        return result % 10 == 0;
    }

    private void deleteAllRows() {
        try {
            getConnection().createStatement().execute("DELETE FROM card");
            closeConnection();
        } catch (SQLException exception) {
            throw new WrongAnswer("Can't execute a query in your database! Make sure that your database isn't broken and you close your connection at the end of the program!");
        }
    }

    private void stopAndCheckIfUserProgramWasStopped(TestedProgram program) {
        program.execute("0");
        if (!program.isFinished()) {
            throw new WrongAnswer("After choosing 'Exit' item you should stop your program" +
                " and close database connection!");
        }
    }
}
