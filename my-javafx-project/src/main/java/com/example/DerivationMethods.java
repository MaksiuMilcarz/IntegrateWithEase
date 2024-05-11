package com.example;

import java.util.function.Function;

public class DerivationMethods {
    public static double firstDerivative(double h, double x, String function) {
        //3 point center difference
        Function<Double, Double> parsedFunction = Parser.getFunction(function);
        return (parsedFunction.apply(x + h) - parsedFunction.apply(x - h)) / (2 * h);
    }

    public static double firstDerivative5point(double h, double x, String function) {
        //5 point center difference
        Function<Double, Double> parsedFunction = Parser.getFunction(function);
        return (-parsedFunction.apply(x + 2 * h) + 8 * parsedFunction.apply(x + h) - 8 * parsedFunction.apply(x - h) + parsedFunction.apply(x - 2 * h)) / (12 * h);
    }

    public static double secondDerivative(double h, double x, String function) {
        Function<Double, Double> parsedFunction = Parser.getFunction(function);
        return (parsedFunction.apply(x + h) - 2 * parsedFunction.apply(x) + parsedFunction.apply(x - h)) / (h * h);
    }
}
