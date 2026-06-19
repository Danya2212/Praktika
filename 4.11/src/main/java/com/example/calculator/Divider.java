package com.example.calculator;

import org.springframework.stereotype.Component;

@Component("divider")
public class Divider implements Operation {

    @Override
    public String getName() {
        return "divider";
    }

    @Override
    public double calculate(double a, double b) {
        return a / b;
    }
}
