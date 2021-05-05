class Seven {
    public static SeptenaryStringFunction fun = (a, b, c, d, e, f, g) -> {
        String res = a + b + c + d + e + f + g;
        return res.toUpperCase();
    };
}

@FunctionalInterface
interface SeptenaryStringFunction {
    String apply(String s1, String s2, String s3, String s4, String s5, String s6, String s7);
}