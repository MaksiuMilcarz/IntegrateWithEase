package com.example;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Analyzes and evaluates mathematical expressions transmitted as strings.
 * The parser supports operations such as addition, subtraction, multiplication, division, exponentiation and logarithms.
 * The parser also handles variables supplied via a card.
 */
public class Parser {
    private String input;
    private int index = 0;
    private Map<String, Double> variables;

    /**
    * Constructs a new Parser instance.
    * The input string is prepared by removing all spaces to simplify parsing.
    *
    * @param input the mathematical expression to be parsed
    * @param variables a map of variable names to their corresponding values
    */
    public Parser(String input, Map<String, Double> variables) {
        this.input = input.replaceAll("\\s+", "");
        this.variables = new HashMap<>(variables);
    }

    /**
    * Analyzes and evaluates the entire expression stored in the input.
    *
    * @Returns the result of the evaluated expression as a double
    * @Throws IllegalArgumentException if unexpected characters or incorrect parts of the expression are present
    */
    public double parse() {
        double result = parseAddSubtract();
        if (index < input.length()) {
            throw new IllegalArgumentException("unexpected: " + input.charAt(index));
        }
        return result;
    }

    /**
    * Analyzes addition and subtraction operations within the expression.
    * This method handles the operations with the lowest priority by processing the terms one after the other.
    *
    * @Returns the result of the addition/subtraction operations as a double
    */
    private double parseAddSubtract() {
        double result = parseMultiplyDivide();
        while (index < input.length()) {
            if (input.charAt(index) == '+') {
                index++;
                result += parseMultiplyDivide();
            } else if (input.charAt(index) == '-') {
                index++;
                result -= parseMultiplyDivide();
            } else {
                break;
            }
        }
        return result;
    }

    /**
    * Analyzes multiplication and division operations within the expression.
    * This method processes these operations, which have a higher precedence than addition and subtraction.
    *
    * @Returns the result of the multiplication/division operations as a double
    * @throws ArithmeticException if a division by zero occurs
    */
    private double parseMultiplyDivide() {
        double result = parseExponent();
        while (index < input.length()) {
            if (input.charAt(index) == '*') {
                index++;
                result *= parseExponent();
            } else if (input.charAt(index) == '/') {
                index++;
                double divisor = parseExponent();
                if (divisor == 0) {
                    throw new ArithmeticException("division by zero");
                }
                result /= divisor;
            } else {
                break;
            }
        }
        return result;
    }

    /**
    * Analyzes exponentiation operations within the expression.
    * This method processes the operations with the highest priority and handles the right-associativity of the exponentiation.
    *
    * @Returns the result of the exponentiation operations as a double
    * @Throws IllegalArgumentException if the format of the exponentiation is incorrect
    */
    // private double parseExponent() {
    //     double base = parseFactor();
    //     while (index < input.length() && input.charAt(index) == '^') {
    //         index++; // Skipping '^'
    //         if (input.charAt(index) != '(') throw new IllegalArgumentException("Expected '(' after '^'");
    //         index++; //// Skip  '('
    //         double exponent = parseAddSubtract(); 
    //         if (input.charAt(index) != ')') {
    //             throw new IllegalArgumentException("Missing ) after exponent");
    //         }
    //         index++; // Skipping )
    //         base = (double) Math.pow(base, exponent);
    //     }
    //     return base;
    // }
    private double parseExponent() {
        double base = parseFactor();
        while (index < input.length() && input.charAt(index) == '^') {
            index++; // Skipping '^'
            double exponent = parseFactor(); // Parse the exponent directly
            base = Math.pow(base, exponent);
        }
        return base;
    }

    /**
    * Analyzes and evaluates a factor in the expression.
    * Factors can be numbers, variables, sub-expressions or functions such as logarithms. 
    * Processes signs and parenthesized expressions to support complex nested operations.
    *
    * @Returns the numeric result of the factor evaluation
    * @throws IllegalArgumentException if the factor is incorrectly formed or there are syntax errors
    */
    private double parseFactor() {
        double sign = 1.0; 
        while (index < input.length() && Character.isWhitespace(input.charAt(index))) {
            index++;
        }
        // Dealing with negative
        if (index < input.length() && input.charAt(index) == '-') {
            sign = -1.0;
            index++; // Skipping - 
        }

        double result;
        if (index < input.length() && input.charAt(index) == '(') {
            index++; // Skip (
            result = parseAddSubtract();
            if (index >= input.length() || input.charAt(index) != ')') {
                throw new IllegalArgumentException("Missing )");
            }
            index++; // Skip )
        } else if (index + 3 < input.length() && input.startsWith("log(", index)) {
            index += 4; // Skipping "log("
            double base = parseAddSubtract(); // Parsing log base
            if (index >= input.length() || input.charAt(index) != ',') {
                throw new IllegalArgumentException("missing comma after log base");
            }
            index++; // Skip ','
            double value = parseAddSubtract(); // Parsing value for log
            if (index >= input.length() || input.charAt(index) != ')') {
                throw new IllegalArgumentException("missing closing parenthesis for log");
            }
            index++; // Skipping ')'
            result = Math.log(value) / Math.log(base); 
        } else if (index + 3 < input.length() && input.startsWith("sin(", index)) {
            index += 4; // Skipping "sin("
            double value = parseAddSubtract(); // Parsing the argument of sin
            if (index >= input.length() || input.charAt(index) != ')') {
                throw new IllegalArgumentException("Missing closing parenthesis for sin");
            }
            index++; // Skip ')'
            result = Math.sin(value);
        }else if (index + 3 < input.length() && input.startsWith("cos(", index)) {
            index += 4; // Skipping "cos("
            double value = parseAddSubtract(); // Parsing the argument of cos
            if (index >= input.length() || input.charAt(index) != ')') {
                throw new IllegalArgumentException("Missing closing parenthesis for cos");
            }
            index++; // Skip ')'
            result = Math.cos(value);
        }else if (index + 3 < input.length() && input.startsWith("tan(", index)) {
            index += 4; // Skipping "tan("
            double value = parseAddSubtract(); // Parsing the argument of tan
            if (index >= input.length() || input.charAt(index) != ')') {
                throw new IllegalArgumentException("Missing closing parenthesis for tan");
            }
            index++; // Skip ')'
            result = Math.tan(value);
        }else if (index + 2 < input.length() && input.startsWith("ln(", index)) {
            index += 3; // Skipping "ln("
            double value = parseAddSubtract(); // Parsing the argument of ln
            if (index >= input.length() || input.charAt(index) != ')') {
                throw new IllegalArgumentException("Missing closing parenthesis for ln");
            }
            index++; // Skip ')'
            result = Math.log(value);
        }else if (index < input.length() && (Character.isDigit(input.charAt(index)) || input.charAt(index) == '.')) {
            StringBuilder number = new StringBuilder();
            if (sign < 0) number.append('-'); 
            while (index < input.length() && (Character.isDigit(input.charAt(index)) || input.charAt(index) == '.')) {
                number.append(input.charAt(index));
                index++;
            }
            result = Double.parseDouble(number.toString());
        } else if (index < input.length() && Character.isLetter(input.charAt(index))) {
            StringBuilder varName = new StringBuilder();
            while (index < input.length() && Character.isLetter(input.charAt(index))) {
                varName.append(input.charAt(index));
                index++;
            }
            String name = varName.toString();
            if (!variables.containsKey(name)) {
                throw new IllegalArgumentException("not defined a variable " + name);
            }
            result = variables.get(name);
        } else {
            throw new IllegalArgumentException("unexpected character: " + input.charAt(index));
        }
        return result; 
    }

    public static Function<Double, Double> getFunction(String expression) {
        Parser parser = new Parser(expression, new HashMap<>());
        return x -> {
            parser.index = 0;
            parser.variables.put("x", x);
            parser.variables.put("e", (double) Math.E);
            return parser.parse();
        };
    }
}