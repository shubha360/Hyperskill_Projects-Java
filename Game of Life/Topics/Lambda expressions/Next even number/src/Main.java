import java.util.function.LongUnaryOperator;

class Operator {

    public static LongUnaryOperator unaryOperator = x -> { 
        return x % 2 == 1 ? x + 1 : x + 2; 
        };
}
