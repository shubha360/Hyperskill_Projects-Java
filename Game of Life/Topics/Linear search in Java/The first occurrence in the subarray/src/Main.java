import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static int searchInSubArray(int[] numbers, int startIndex, int endIndex, int value) {
        // write your code here
    }

    /* Do not change code below */
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final int[] numbers = Arrays.stream(scanner.nextLine().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
        final String[] parts = scanner.nextLine().split("\\s+");
        final int startIndex = Integer.parseInt(parts[0]);
        final int endIndex = Integer.parseInt(parts[1]);
        final int k = Integer.parseInt(scanner.nextLine());
        System.out.println(searchInSubArray(numbers, startIndex, endIndex, k));
    }
}