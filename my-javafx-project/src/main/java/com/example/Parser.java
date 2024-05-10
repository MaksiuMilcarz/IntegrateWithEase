package com.example;

import java.util.function.Function;

public class Parser {
    public static Function<Double, Double> parse(String function) {
        return (x) -> evaluateExpression(function, x);
    }

    private static double evaluateExpression(String expression, double x) {
        // Replace 'x' with the actual value
        expression = expression.replaceAll("x", String.valueOf(x));

        // Evaluate the expression
        try {
            return evaluate(expression);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid expression: " + expression);
        }
    }

    private static double evaluate(String expression) {
        // Perform simple parsing and evaluation
        try {
            return Double.parseDouble(expression);
        } catch (NumberFormatException ex) {
            // Not a simple number, continue evaluation
        }

        // Evaluate functions
        if (expression.startsWith("sin(")) {
            double arg = evaluate(expression.substring(4, expression.length() - 1));
            return Math.sin(arg);
        } else if (expression.startsWith("cos(")) {
            double arg = evaluate(expression.substring(4, expression.length() - 1));
            return Math.cos(arg);
        } else if (expression.startsWith("tan(")) {
            double arg = evaluate(expression.substring(4, expression.length() - 1));
            return Math.tan(arg);
        } else if (expression.startsWith("log(")) {
            int startIndex = 4; // Index after "log("
            int endIndex = expression.lastIndexOf(')'); // Index of closing parenthesis
            if (endIndex == -1 || startIndex >= endIndex) {
                throw new IllegalArgumentException("Invalid log expression: " + expression);
            }
            double arg = evaluate(expression.substring(startIndex, endIndex));
            return Math.log10(arg);
        } else if (expression.startsWith("ln(")) {
            int startIndex = 3; // Index after "ln("
            int endIndex = expression.lastIndexOf(')'); // Index of closing parenthesis
            if (endIndex == -1 || startIndex >= endIndex) {
                throw new IllegalArgumentException("Invalid ln expression: " + expression);
            }
            double arg = evaluate(expression.substring(startIndex, endIndex));
            return Math.log(arg);
        } else if (expression.startsWith("sqrt(")) {
            double arg = evaluate(expression.substring(5, expression.length() - 1));
            return Math.sqrt(arg);
        }


        int index = expression.lastIndexOf("^");
        if (index != -1) {
            int endIndex = index + 1; // End index of exponent
            while (endIndex < expression.length() && Character.isDigit(expression.charAt(endIndex))) {
                endIndex++;
            }
            double base = evaluate(expression.substring(0, index));
            double exponent = evaluate(expression.substring(index + 1, endIndex));
            return Math.pow(base, exponent);
        }

        // Evaluate basic arithmetic operations
        index = -1;
        for (int i = expression.length() - 1; i >= 0; i--) {
            char c = expression.charAt(i);
            if (c == '+' || c == '-') {
                index = i;
                break;
            }
            if (c == '*' || c == '/') {
                index = i;
            }
        }
        if (index != -1) {
            double left = evaluate(expression.substring(0, index));
            double right = evaluate(expression.substring(index + 1));
            switch (expression.charAt(index)) {
                case '+':
                    return left + right;
                case '-':
                    return left - right;
                case '*':
                    return left * right;
                case '/':
                    if (right == 0) {
                        throw new IllegalArgumentException("Division by zero");
                    }
                    return left / right;
            }
        }

        throw new IllegalArgumentException("Invalid expression: " + expression);
    }
}
