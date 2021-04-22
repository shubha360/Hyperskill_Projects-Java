package banking;

import org.sqlite.SQLiteDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class AccountInteraction {

    private final Account account;
    Scanner scanner = new Scanner(System.in);
    SQLiteDataSource sqLiteDataSource;

    public AccountInteraction(Account account, SQLiteDataSource database) {
        this.account = account;
        this.sqLiteDataSource = database;
    }

    public int startInteracting() {

        while (true) {

            int selection = printMenu();

            switch (selection) {

                case 1:
                    System.out.println("Balance: " + account.getBalance());
                    System.out.println();
                    break;

                case 2:
                    System.out.println("Enter income:");
                    int income = scanner.nextInt();
                    scanner.nextLine();
                    account.setBalance(account.getBalance() + income);
                    System.out.println("Income was added!");
                    System.out.println();
                    break;

                case 3:
                    transfer();
                    break;

                case 4:
                    closeAccount();
                    System.out.println("The account has been closed!");
                    System.out.println();
                    return 0;

                case 5:
                    updateData(this.account);
                    System.out.println("You have been logged out.");
                    System.out.println();
                    return 0;

                case 0:
                    updateData(this.account);
                    System.out.println("Bye!");
                    return -1;
            }
        }
    }

    private int printMenu() {

        System.out.println("1. Balance\n" +
                "2. Add income\n" +
                "3. Do transfer\n" +
                "4. Close account\n" +
                "5. Log out\n" +
                "0. Exit");

        int selection = Integer.parseInt(scanner.nextLine());
        System.out.println();
        return selection;
    }

    private void closeAccount() {

        try (Connection connection = sqLiteDataSource.getConnection()){
            String command = "DELETE FROM card WHERE id = " + account.getId() + ";";

            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(command);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void updateData(Account accountToUpdate) {

        try (Connection connection = sqLiteDataSource.getConnection()){
            String command = "UPDATE card " +
                    "SET balance = " + accountToUpdate.getBalance() +
                    " WHERE id = " + accountToUpdate.getId() + ";";

            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(command);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void transfer() {

        System.out.println("Transfer");
        System.out.println("Enter card number:");
        String cardNumber = scanner.nextLine();

        if (cardNumber.equals(account.getAccountNumber())) {
            System.out.println("You can't transfer money to the same account!");
            System.out.println();
            return;
        }

        int checksum = Banking.generateChecksum(cardNumber.substring(0, cardNumber.length() - 1));
        int lastDigit = ((int) cardNumber.charAt(cardNumber.length() - 1)) - 48;

        if (checksum != lastDigit) {
            System.out.println("Probably you made a mistake in the card number.");
            System.out.println();
            return;
        }

        Account receivingAccount = new Account();

        boolean accountFound = findAccount(cardNumber, receivingAccount);

        if (!accountFound) {
            System.out.println("Such a card does not exist.");
            System.out.println();
            return;
        }

        System.out.println("Enter how much money you want to transfer:");
        int amount = Integer.parseInt(scanner.nextLine());

        if (amount > account.getBalance()) {
            System.out.println("Not enough money!");
            System.out.println();
            return;
        }

        account.setBalance(account.getBalance() - amount);
        receivingAccount.setBalance(receivingAccount.getBalance() + amount);

        updateData(this.account);
        updateData(receivingAccount);

        System.out.println("Success!");
        System.out.println();
    }

    private boolean findAccount(String cardNumber, Account accountToFind) {

        try (Connection connection = sqLiteDataSource.getConnection()) {

            String select = "SELECT * FROM card WHERE number = " +
                    "'" + cardNumber + "';";

            try (Statement statement = connection.createStatement()) {

                ResultSet resultSet = statement.executeQuery(select);

                accountToFind.setId(resultSet.getInt("id"));
                accountToFind.setAccountNumber(resultSet.getString("number"));
                accountToFind.setPin(resultSet.getString("pin"));
                accountToFind.setBalance(resultSet.getInt("balance"));
            }

        } catch (SQLException throwables) {
            return false;
        }
        return true;
    }
}