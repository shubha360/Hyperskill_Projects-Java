package converter;

import java.util.*;

public class Main {

    static String readNumber(int sourceRadix) {

        Scanner scanner = new Scanner(System.in);
        String number;

        if (scanner.hasNext("[0-9A-Za-z]+(.[0-9A-Za-z]+)?")) {

            number = scanner.nextLine();

            for (int i = 0; i < number.length(); i++) {

                if (number.charAt(i) == '.') {
                    continue;
                }
                if (Character.getNumericValue(number.charAt(i)) >= sourceRadix) {
                    System.out.println("error : Number input is greater than source radix.");
                    return "^ERROR^";
                }
            }

        } else {
            System.out.println("error : Unexpected number input.");
            return "^ERROR^";
        }
        return number;
    }

    static int readRadix(String name) {

        Scanner scanner = new Scanner(System.in);

        int sourceRadix;

        if (scanner.hasNextInt()) {

            sourceRadix = scanner.nextInt();

            if (sourceRadix < 1 || sourceRadix > 36) {
                System.out.println("error : " + name + " is out of range");
                return -1;
            }
        } else {
            System.out.println("error : Unexpected " + name + " input.");
            return -1;
        }
        return sourceRadix;
    }

    public static void main(String[] args) {

        int sourceRadix = readRadix("Source radix");

        if (sourceRadix == -1) {
            return;
        }

        if (sourceRadix == 1) {

            Scanner scanner = new Scanner(System.in);
            int sourceNumber = scanner.nextInt();
            String s = String.valueOf(sourceNumber);
            sourceNumber = s.length();
            int targetRadix = scanner.nextInt();

            System.out.println(Integer.toString(sourceNumber, targetRadix));
            return;
        }

        String sourceNumber = readNumber(sourceRadix);

        if ("^ERROR^".equals(sourceNumber)) {
            return;
        }

        int targetRadix = readRadix("Target radix");

        if (targetRadix == -1) {
            return;
        }

        if (targetRadix == 1) {

            double d = Double.parseDouble(sourceNumber);

            char[] arr = new char[(int) d];
            Arrays.fill(arr, '1');

            for (char c : arr) {
                System.out.print(c);
            }
            System.out.println();
            return;
        }

        if (!sourceNumber.contains(".")) {

            int n = Integer.parseInt(sourceNumber, sourceRadix);
            System.out.println(Integer.toString(n, targetRadix));
            return;
        }

        int decimalInteger = Integer.parseInt(sourceNumber.substring(0, sourceNumber.indexOf('.')), sourceRadix);

        String fractionPart = sourceNumber.substring(sourceNumber.indexOf('.') + 1);

        double decimalFraction = 0;

        for (int i = 0; i < fractionPart.length(); i++) {
            decimalFraction += Character.getNumericValue(fractionPart.charAt(i)) / (Math.pow(sourceRadix, i + 1));
        }

        if (targetRadix == 10) {
            System.out.println(decimalInteger + decimalFraction);
            return;
        }

        String resultInteger = Integer.toString(decimalInteger, targetRadix);
        System.out.println(resultInteger);

        String resultFraction = ".";

        for (int i = 0; i < 5; i++) {

            double temp = decimalFraction * targetRadix;
            int x = (int) temp; //
            String s = Integer.toString(x, targetRadix);
            resultFraction += s;

            decimalFraction = temp - x;
        }

        System.out.println(resultInteger + resultFraction);
    }
}
