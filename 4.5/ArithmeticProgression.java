import java.util.stream.IntStream;

public class ArithmeticProgression {

    public static long getArithmeticProgressionSum(int a, int b) {
        return IntStream.range(a, b).asLongStream().sum();
    }
}
