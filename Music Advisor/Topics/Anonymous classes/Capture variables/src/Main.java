import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        final Scanner scanner = new Scanner(System.in);
        final String str = scanner.nextLine();
        final int number = Integer.parseInt(scanner.nextLine());

        Returner returner = new Returner() {
            @Override
            public String returnString() {
                return str;
            }

            @Override
            public int returnInt() {
                return number;
            }
        };

        System.out.println(returner.returnString());
        System.out.println(returner.returnInt()); 
    }
}

interface Returner {

    public String returnString();

    public int returnInt();
}