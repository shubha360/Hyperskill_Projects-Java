package converter;

/*
Input : {"jdk":"1.8.9"}
Output : <jdk>1.8.9</jdk>
*/

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ToXmlConverter implements Converter {

    private final String input;

    public ToXmlConverter(String input) {
        System.out.println(input);
        input = input.replaceAll("\\s", "");
        this.input = input;
    }

    @Override
    public String convert() {

        String name = input.substring(2, input.indexOf(":") - 1);

        if (!input.substring(1).contains("{")) {

            if (input.substring(input.indexOf(":") + 1).equals("null}")) {
                return String.format("<%s/>", name);
            }


        }

        return "...";

//        Pattern pattern = Pattern.compile("\"@.+\":");
//        Matcher matcher = pattern.matcher(input);
//
//        if (!matcher.find()) {
//
//            String[] divided = input.split(":");
//
//            String name = divided[0].substring(2, divided[0].length() - 1);
//
//            if (divided[1].equals("null}")) {
//                return String.format("<%s/>", name);
//            }
//
//            String value = divided[1].substring(1, divided[1].length() - 2);
//
//            return String.format("<%s>%s</%s>", name, value, name);
//        }
    }
}
