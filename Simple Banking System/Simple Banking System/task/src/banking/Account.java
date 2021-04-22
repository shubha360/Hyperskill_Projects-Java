package banking;

public class Account {

    private int id;
    private String accountNumber;
    private String pin;
    private int balance;

    public Account() {
        id = 0;
        accountNumber = null;
        pin = null;
        balance = Integer.MIN_VALUE;
    }

    public Account(int id, String accountNumber, String pin, int balance) {

        this.id = id;
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}

