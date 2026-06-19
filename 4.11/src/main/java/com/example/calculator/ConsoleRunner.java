package com.example.calculator;

import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final CalculatorService calculatorService;

    public ConsoleRunner(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите число a");
        double a = scanner.nextDouble();

        System.out.println("Введите число b");
        double b = scanner.nextDouble();

        System.out.println("Введите тип операции: [adder, divider, multiplier, subtractor]");
        String operationType = scanner.next();

        System.out.println(calculatorService.calculate(operationType, a, b));
    }
}
