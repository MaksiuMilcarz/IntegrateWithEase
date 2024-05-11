package com.example;

import java.util.function.Function;

import javax.script.ScriptException;

public class IntegrationMethods {

    public static double MidpointRule(double h, double intervalStart, double intervalEnd, String function) throws ScriptException {
        Function<Double, Double> parsedFunction = Parser.getFunction(function);
        double result = 0;
        for (double i = intervalStart; i < intervalEnd; i += h) {
            result += parsedFunction.apply(i + h / 2);
        }
        return h * result;
    }

    public static double TrapezoidalRule(double h,double intervalStart, double intervalEnd, String function) throws ScriptException {
        Function<Double, Double> parsedFunction = Parser.getFunction(function);
        double result = 0.5 * (parsedFunction.apply(intervalStart) + parsedFunction.apply(intervalEnd));
        
        int n = (int) ((intervalEnd - intervalStart) / h);
        for (int i = 1; i < n; i++) {
            double x = intervalStart + i * h;
            result += parsedFunction.apply(x);
        }
        
        return h * result;
    }

    public static double SimpsonRule(double h, double intervalStart, double intervalEnd, String function) throws ScriptException {
        Function<Double, Double> parsedFunction = Parser.getFunction(function);
        int n = (int) ((intervalEnd - intervalStart) / h);
        if (n % 2 != 0) {
            n++;
        }
        h = (intervalEnd - intervalStart) / n;
        double sum = parsedFunction.apply(intervalStart) + parsedFunction.apply(intervalEnd);

        for (int i = 1; i < n; i++) {
            double x = intervalStart + i * h;
            sum += (i % 2 == 0 ? 2 : 4) * parsedFunction.apply(x);
        }
        return sum * h / 3;
    }
}
