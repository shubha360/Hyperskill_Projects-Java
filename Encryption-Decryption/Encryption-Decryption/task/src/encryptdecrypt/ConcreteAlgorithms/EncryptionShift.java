package encryptdecrypt.ConcreteAlgorithms;

import encryptdecrypt.Algorithm;

public class EncryptionShift implements Algorithm {

    @Override
    public StringBuilder execute(StringBuilder data, int key) {

        for (int i = 0; i < data.length(); i++) {

            char c = data.charAt(i);

            if (data.charAt(i) >= 'A' && data.charAt(i) <= 'Z') {

                c = (char) (c + key);
                if (c > 'Z') {
                    c = (char) (c - 90 + 64);
                }
            } else if (data.charAt(i) >= 'a' && data.charAt(i) <= 'z') {

                c = (char) (c + key);
                if (c > 'z') {
                    c = (char) (c - 122 + 96);
                }
            }
            data.replace(i, i+1, String.valueOf(c));
        }
        return data;
    }
}
