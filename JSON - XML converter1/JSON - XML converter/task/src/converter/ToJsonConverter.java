package converter;

/*
Input : <host>127.0.0.1</host>
Output : {"host":"127.0.0.1"}
*/

import java.util.LinkedHashMap;

public class ToJsonConverter implements Converter {

    private final String input;

    public ToJsonConverter(String input) {
        this.input = input;
    }

    @Override
    public String convert() {

        String name = "";
        String value = "";

        // statement is empty
        if (input.matches("<.+/>")) {

            // empty statement contains attributes
            if (input.contains("=")) {

                name += input.substring(1, input.indexOf(' '));
                LinkedHashMap<String, String> attributes = parseXmlAttributes(input, true);

                return outputBuilder(name, attributes);
            }

            // empty statement has no attributes
            name += input.substring(1, input.lastIndexOf('/'));
            return String.format("{\"%s\":null}", name);
        }

        // non-empty statement contains attributes
        value += input.substring(input.indexOf('>') + 1, input.lastIndexOf('<'));

        if (input.substring(0, input.indexOf('>')).contains("=")) {

            name += input.substring(1, input.indexOf(' '));
            LinkedHashMap<String, String> attributes =
                    parseXmlAttributes(input.substring(0, input.indexOf('>')), false);
            return outputBuilder(name, attributes, value);
        }

        // non-empty statement has no attributes
        name += input.substring(1, input.indexOf('>'));

        if (value.length() == 0) {
            return String.format("{\"%s\":null}", name);
        }

        return String.format("{\"%s\":\"%s\"}", name, value);
    }

    private LinkedHashMap<String, String> parseXmlAttributes(String input, boolean isEmpty) {

        StringBuilder stringBuilder = new StringBuilder(input);

        stringBuilder.delete(0, stringBuilder.indexOf(" "));
        stringBuilder.deleteCharAt(0);

        if (isEmpty) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1).deleteCharAt(stringBuilder.length() - 1);
        } else {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }

        String formatted = stringBuilder.toString().replaceAll("= ", "");
        formatted = formatted.replaceAll("\"", "");
        String[] divided = formatted.split(" ");

        LinkedHashMap<String, String> output = new LinkedHashMap<>();

        for (int i = 0; i < divided.length; i += 2) {
            output.put(divided[i], divided[i + 1]);
        }

        return output;
    }

    private String outputBuilder(String name, LinkedHashMap<String, String> attributes) {

        StringBuilder output = new StringBuilder(String.format("{\"%s\":{", name));

        for (var entry : attributes.entrySet()) {
            output.append(String.format("\"@%s\":\"%s\",", entry.getKey(), entry.getValue()));
        }
        output.append(String.format("\"#%s\":null}}", name));

        return output.toString();
    }

    private String outputBuilder(String name, LinkedHashMap<String, String> attributes, String value) {

        StringBuilder output = new StringBuilder(String.format("{\"%s\":{", name));

        for (var entry : attributes.entrySet()) {
            output.append(String.format("\"@%s\":\"%s\",", entry.getKey(), entry.getValue()));
        }

        if (value.length() > 0) {
            output.append(String.format("\"#%s\":\"%s\"}}", name, value));
        } else {
            output.append(String.format("\"#%s\":null}}", name));
        }

        return output.toString();
    }
}
