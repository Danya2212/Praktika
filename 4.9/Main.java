import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите число a:");
        double a = scanner.nextDouble();

        System.out.println("Введите число b:");
        double b = scanner.nextDouble();

        Calculator adder = new Calculator(new Adder());
        Calculator subtractor = new Calculator(new Subtractor());
        Calculator multiplier = new Calculator(new Multiplier());
        Calculator divider = new Calculator(new Divider());

        System.out.println("Результат сложения a и b: " + adder.calculate(a, b));
        System.out.println("Результат вычитания a и b: " + subtractor.calculate(a, b));
        System.out.println("Результат умножения a и b: " + multiplier.calculate(a, b));
        System.out.println("Результат деления a и b: " + divider.calculate(a, b));
    }
}
