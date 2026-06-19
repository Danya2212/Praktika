package com.example.calculator;

import org.springframework.stereotype.Component;

@Component("subtractor")
public class Subtractor implements Operation {

    @Override
    public String getName() {
        return "subtractor";
    }

    @Override
    public double calculate(double a, double b) {
        return a - b;
    }
}
