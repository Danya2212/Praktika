package com.example.calculator;

import org.springframework.stereotype.Component;

@Component("adder")
public class Adder implements Operation {

    @Override
    public String getName() {
        return "adder";
    }

    @Override
    public double calculate(double a, double b) {
        return a + b;
    }
}
