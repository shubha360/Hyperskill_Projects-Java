package banking;

import org.sqlite.SQLiteDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.Scanner;

public class Banking {

    private final SQLiteDataSource database = new SQLiteDataSource();
    String dbLocation;
    static int id = -1;
    Account currentAccount;
    Scanner scanner = new Scanner(System.in);

    public Banking(String dbLocation) {

        this.dbLocation = dbLocation;
        database.setUrl("jdbc:sqlite:" + dbLocation);

        try (Connection connection = database.getConnection()) {

            String createTable = "CREATE TABLE IF NOT EXISTS card (" +
                    "id INTEGER PRIMARY KEY," +
                    "number STRING NOT NULL," +
                    "pin STRING NOT NULL," +
                    "balance INTEGER DEFAULT 0" +
                    ");";

            String checkIfTableIsEmpty = "SELECT Count(*) FROM card;";
            String getHighestID = "SELECT Max(id) FROM card;";

            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(createTable);

                ResultSet resultSet = statement.executeQuery(checkIfTableIsEmpty);

                if (resultSet.getInt(1) == 0) {
                    id = 1;
                } else {
                    resultSet = statement.executeQuery(getHighestID);
                    id = resultSet.getInt(1) + 1;
                }
            }

        } catch (SQLException throwables) {
            System.out.println("Problem in creating table.");
            throwables.printStackTrace();
        }
    }

    public void start() {

        while (true) {

            int selection = printMainMenu();

            switch (selection) {

                case 1:
                    createNewCard();
                    System.out.println();
                    break;

                case 2:

                    System.out.println("Enter your card number:");
                    String cardNumber = scanner.nextLine();
                    System.out.println("Enter your PIN:");
                    String cardPin = scanner.nextLine();
                    System.out.println();

                    boolean login = selectAccount(cardNumber, cardPin);

                    if (!login) {
                        System.out.println("Wrong card number or PIN!\n");
                    } else {
                        System.out.println("You have successfully logged in!\n");
                        AccountInteraction interaction = new AccountInteraction(currentAccount, database);
                        int res = interaction.startInteracting();

                        if (res == -1) {
                            return;
                        }
                    }
                    break;

                case 0:
                    System.out.println("Bye!");
                    return;
            }
        }
    }

    private boolean saveAccount(Account account) {

        String addNewAccount = "INSERT INTO card(id,number,pin,balance) VALUES(" +
                account.getId() + "," +
                "'" + account.getAccountNumber() + "'," +
                "'" + account.getPin() + "'," +
                account.getBalance() + ")";

        try (Connection connection = database.getConnection()) {

            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(addNewAccount);
            }
        } catch (SQLException throwables) {
            return false;
        }
        id++;
        return true;
    }

    private int printMainMenu() {

        System.out.println("1. Create an account\n" +
                "2. Log into account\n" +
                "0. Exit");

        int selection = Integer.parseInt(scanner.nextLine());
        System.out.println();
        return selection;
    }

    private void createNewCard() {

        Account account;
        while (true) {

            account = new Account(id, generateNewAccountNumber(), generateNewPin(), 0);
            boolean notExists = saveAccount(account);

            if (notExists) {
                break;
            }
        }

        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        System.out.println(account.getAccountNumber());
        System.out.println("Your card PIN:");
        System.out.println(account.getPin());
    }

    private String generateNewAccountNumber() {

        String bin = "400000";

        Random random = new Random();
        StringBuilder customerAccountNumber = new StringBuilder(String.valueOf(random.nextInt(Integer.MAX_VALUE)));

        if (customerAccountNumber.length() > 9) {
            customerAccountNumber.deleteCharAt(random.nextInt(customerAccountNumber.length()));
        } else if (customerAccountNumber.length() < 9) {

            while (customerAccountNumber.length() < 9) {
                customerAccountNumber.insert(0, 0);
            }
        }

        String checksum = String.valueOf(generateChecksum(bin + customerAccountNumber));

        return bin + customerAccountNumber + checksum;
    }

    static int generateChecksum(String initialNumber) {

        int total = 0;

        for (int i = 1; i <= initialNumber.length(); i++) {

            int current = ((int) initialNumber.charAt(i - 1) - 48);

            if (i % 2 == 1) {

                current *= 2;

                if (current > 9) {
                    current -= 9;
                }

            }
            total += current;
        }

        int checkSum = 0;
        while ((total + checkSum) % 10 > 0) {
            checkSum++;
        }

        return checkSum;
    }

    private String generateNewPin() {

        Random random = new Random();
        int pin = random.nextInt(10000);

        StringBuilder newPin = new StringBuilder(String.valueOf(pin));

        if (newPin.length() < 4) {
            newPin.append("0");
        }

        return newPin.toString();
    }

    private boolean selectAccount(String cardNumber, String pin) {

        try (Connection connection = database.getConnection()) {

            String select = "SELECT * FROM card WHERE number = " +
                    "'" + cardNumber + "' AND pin = " +
                    "'" + pin +"';";

            try (Statement statement = connection.createStatement()) {

                ResultSet resultSet = statement.executeQuery(select);

                currentAccount = new Account(resultSet.getInt("id"),
                        resultSet.getString("number"),
                        resultSet.getString("pin"),
                        resultSet.getInt("balance")
                );
            }

        } catch (SQLException throwables) {
            return false;
        }
        return true;
    }

    public void printAccounts() {

        String selectAcc = "SELECT * FROM card;";

        try (Connection connection = database.getConnection()) {

            try (Statement statement = connection.createStatement()) {

                ResultSet resultSet = statement.executeQuery(selectAcc);

                while (resultSet.next()) {
                    System.out.println(resultSet.getInt("id") + " "
                            + resultSet.getString("number") + " "
                            + resultSet.getString("pin") + " "
                            + resultSet.getInt("balance"));
                }
            }

        } catch (SQLException throwables) {
            System.out.println("Problem in printing accounts.");
            throwables.printStackTrace();
        }
    }
}
