import java.util.ListResourceBundle;

public class ErrorMessages extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"feedback", "The input data: {0}\nThe expected output: {1}\nYour program output: {2}"}
        };
    }
}