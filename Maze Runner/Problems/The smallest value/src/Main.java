import java.math.BigInteger;
import java.util.Scanner;

class Main {

    static BigInteger factorial(int i) {

        BigInteger result = new BigInteger("1");

        for (int multiple = i; multiple > 1; multiple--) {
            result = result.multiply(new BigInteger(String.valueOf(multiple)));
        }
        return result;
    }

    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        BigInteger input = scanner.nextBigInteger();

        int lastFact = 1;

        while (input.compareTo(factorial(lastFact)) > 0) {
            lastFact++;
        }
        System.out.println(lastFact);
    }
}