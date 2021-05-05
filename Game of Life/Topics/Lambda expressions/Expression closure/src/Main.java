import java.util.function.DoubleUnaryOperator;

class Operator {

    public static int a = 10;
    public static int b = 20;
    public static int c = 30;

    public static DoubleUnaryOperator unaryOperator = x -> a * (x * x) + b * x + c;
}