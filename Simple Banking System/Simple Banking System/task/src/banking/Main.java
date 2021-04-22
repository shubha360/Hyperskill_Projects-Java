package banking;

public class Main {
    public static void main(String[] args) {

        Banking banking = new Banking(args[1]);
        banking.start();
    }
}