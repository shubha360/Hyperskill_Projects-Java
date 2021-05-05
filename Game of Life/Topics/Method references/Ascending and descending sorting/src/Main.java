import java.util.*;
import java.util.function.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isAscending = scanner.nextLine().equals("ascending");
        int[] array = Arrays.stream(scanner.nextLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
        BiFunction<Integer, Integer, Integer> comparator = null;

        // write your code here
        if (isAscending) {
            comparator = Integer::min;
        } else {
            comparator = Integer::max;
        }

        sort(array, comparator);
        Arrays.stream(array).forEach(e -> System.out.print(e + " "));
    }

    public static void sort(int[] array, BiFunction<Integer, Integer, Integer> comparator) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (comparator.apply(array[j], array[j + 1]) == array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }
}