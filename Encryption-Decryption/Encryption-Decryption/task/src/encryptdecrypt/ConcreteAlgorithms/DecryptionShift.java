package encryptdecrypt.ConcreteAlgorithms;

import encryptdecrypt.Algorithm;

public class DecryptionShift implements Algorithm {

    @Override
    public StringBuilder execute(StringBuilder data, int key) {

        for (int i = 0; i < data.length(); i++) {

            char c = data.charAt(i);

            if (data.charAt(i) >= 'A' && data.charAt(i) <= 'Z') {

                c = (char) (c - key);
                if (c < 'A') {
                    char temp = (char) ('A' - c);
                    c = (char) (91 - temp);
                }
            } else if (data.charAt(i) >= 'a' && data.charAt(i) <= 'z') {

                c = (char) (c - key);
                if (c < 'a') {
                    char temp = (char) ('a' - c);
                    c = (char) (123 - temp);
                }
            }
            data.replace(i, i+1, String.valueOf(c));
        }
        return data;
    }
}
