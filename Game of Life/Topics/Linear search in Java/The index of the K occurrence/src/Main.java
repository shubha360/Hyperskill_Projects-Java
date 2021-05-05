import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static int searchIndexOfKOccurrence(int[] numbers, int value, int k) {
        // write your code here
    }

    /* Do not change code below */
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final int[] numbers;
        final int k;
        final int value;
        if (scanner.hasNextInt()) {
            numbers = Arrays.stream(scanner.nextLine().split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            value = Integer.parseInt(scanner.nextLine());
            k = Integer.parseInt(scanner.nextLine());
        } else {
            numbers = new int[0];
            value = 0;
            k = 1;
        }
        System.out.println(searchIndexOfKOccurrence(numbers, value, k));
    }
}