package numbers;

import java.math.BigInteger;

public class OldStyledNumbers extends AmazingNumbers {

    Long number;

    public OldStyledNumbers(Long number) {
        this.number = number;
    }

    public void printProperties() {
        System.out.println("Properties of " + number);
        System.out.println("even: " + isEven(number));
        System.out.println("odd: " + !isEven(number));
        System.out.println("buzz: " + isBuzz(number));
        System.out.println("duck: " + isDuck(number));
        System.out.println("palindromic: " + isPalindromic(number));
        System.out.println("gapful: " + isGapful(number));
        System.out.println();
    }
}
