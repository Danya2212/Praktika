package com.example.calculator;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    private final Map<String, Operation> operations;

    public CalculatorService(List<Operation> operationList) {
        this.operations = operationList.stream()
                .collect(Collectors.toMap(Operation::getName, Function.identity()));
    }

    public double calculate(String operationType, double a, double b) {
        Operation operation = operations.get(operationType);
        if (operation == null) {
            throw new IllegalArgumentException("Unknown operation: " + operationType);
        }
        return operation.calculate(a, b);
    }
}
