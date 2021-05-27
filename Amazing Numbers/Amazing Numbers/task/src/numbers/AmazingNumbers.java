package numbers;

public class AmazingNumbers {

    boolean isEven(Long number) {
        return number % 2 == 0;
    }

    boolean isBuzz(Long number) {
        String numberInText = number.toString();
        return number % 7 == 0 || numberInText.endsWith("7");
    }

    boolean isDuck(Long number) {
        String numberInText = Long.toString(number);
        return numberInText.contains("0");
    }

    boolean isPalindromic(Long number) {
        String numberInText = Long.toString(number);
        return numberInText.equals(new StringBuilder(numberInText).reverse().toString());
    }

    boolean isGapful(Long number) {

        String numberInText = Long.toString(number);

        if (numberInText.length() < 3) {
            return false;
        }

        String firstAndLast = "" + numberInText.charAt(0) + numberInText.charAt(numberInText.length() - 1);

        return number % Integer.parseInt(firstAndLast) == 0;
    }
}
