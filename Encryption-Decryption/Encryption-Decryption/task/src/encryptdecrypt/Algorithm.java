package encryptdecrypt;

public interface Algorithm {
    StringBuilder execute(StringBuilder data, int key);
}
