import java.util.function.IntBinaryOperator;

class Operator {

    public static IntBinaryOperator binaryOperator = (x, y) -> { return x > y ? x : y; };
}