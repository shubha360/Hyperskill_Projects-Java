package numbers;

public class ModernStyledNumbers extends AmazingNumbers {

    Long number;
    int limit;

    public ModernStyledNumbers(Long number, int limit) {
        this.number = number;
        this.limit = limit;
    }

    public void printProperties() {

        for (long i = number; i < number + limit; i++) {

            StringBuilder output = new StringBuilder(i + " is");

            if (isEven(i)) {
                output.append(" even,");
            } else {
                output.append(" odd,");
            }

            if (isBuzz(i)) {
                output.append(" buzz,");
            }

            if (isDuck(i)) {
                output.append(" duck,");
            }

            if (isPalindromic(i)) {
                output.append(" palindromic,");
            }

            if (isGapful(i)) {
                output.append(" gapful,");
            }

            output.deleteCharAt(output.length() - 1);
            System.out.println(output);
        }
        System.out.println();
    }
}
