package com.example.calculator;

import org.springframework.stereotype.Component;

@Component("multiplier")
public class Multiplier implements Operation {

    @Override
    public String getName() {
        return "multiplier";
    }

    @Override
    public double calculate(double a, double b) {
        return a * b;
    }
}
