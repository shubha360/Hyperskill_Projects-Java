import java.util.*;
import java.util.function.*;


public class Main {

    public static <T> void applyFunction(T[] array, Function<T, T> func) {

        for (int i = 0; i < array.length; i++) {
            array[i] = func.apply(array[i]);
        }
    }

    // do not change code below
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String method = scanner.nextLine();
        String[] array = scanner.nextLine().split(" ");
        applyFunction(array,
                "lower".equals(method) ? String::toLowerCase :
                "upper".equals(method) ? String::toUpperCase :
                "new".equals(method) ? String::new :
                "trim".equals(method) ? String::trim : String::intern);
        Arrays.stream(array).forEach(e -> System.out.print(e + " "));
    }
}