import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> input = new ArrayList<>();

        while (true) {

            int next = scanner.nextInt();

            if (next == 0) {
                break;
            }

            input.add(next);
        }

        int[] inputArr = input.stream().mapToInt(n -> n).toArray();

        int[] manipulated = Arrays.stream(inputArr)
                .sorted()
                .toArray();

        if (Arrays.compare(inputArr, manipulated) == 0) {
            System.out.println(true);
            return;
        }

        manipulated = Arrays.stream(inputArr)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .mapToInt(n -> n)
                .toArray();

        if (Arrays.compare(inputArr, manipulated) == 0) {
            System.out.println(true);
            return;
        }

        System.out.println(false);
    }
}