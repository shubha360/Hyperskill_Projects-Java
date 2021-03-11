package encryptdecrypt.ConcreteAlgorithms;

import encryptdecrypt.Algorithm;

public class EncryptionUnicode implements Algorithm {

    @Override
    public StringBuilder execute(StringBuilder data, int key) {

        for (int i = 0; i < data.length(); i++) {

            char c = (char) (data.charAt(i) + key);
            data.replace(i, i + 1, String.valueOf(c));
        }
        return data;
    }
}
