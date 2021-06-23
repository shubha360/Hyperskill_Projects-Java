package converter;

import java.io.FileInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        try (FileInputStream file = new FileInputStream("C:\\Users\\SweetHome\\Desktop\\JsonXml\\Gg.txt")) {

            String input = new String(file.readAllBytes());

            Converter converter = null;

            if (input.charAt(0) == '<') {
                converter = new ToJsonConverter(input.toString());
            } else {
                converter = new ToXmlConverter(input.toString());
            }
            System.out.println(converter.convert());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
