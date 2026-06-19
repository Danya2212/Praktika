public class Calculator {

    private final Operation operation;

    public Calculator(Operation operation) {
        this.operation = operation;
    }

    public double calculate(double a, double b) {
        return operation.calculate(a, b);
    }
}
