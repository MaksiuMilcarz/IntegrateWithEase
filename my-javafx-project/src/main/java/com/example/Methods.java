package com.example;

import java.util.function.Function;

import javax.script.ScriptException;

public class Methods {
    public static double MidpointRule(double stepSize, double intervalStart, double intervalEnd, String function) throws ScriptException {
        Function<Double, Double> parsedFunction = Parser.parse(function);
        double result = 0;
        for (double i = intervalStart; i < intervalEnd; i += stepSize) {
            result += stepSize * parsedFunction.apply(i + stepSize / 2);
        }
        return result;
    }
    public static double TrapezoidalRule(double stepSize, double intervalStart, double intervalEnd, String function) throws ScriptException {
        Function<Double, Double> parsedFunction = Parser.parse(function);
        double result = 0;
        result += stepSize * 0.5 * parsedFunction.apply(intervalStart);
        for (double i = intervalStart + stepSize; i < intervalEnd; i += stepSize) {
            result += stepSize * parsedFunction.apply(i);
        }
        result += stepSize * 0.5 * parsedFunction.apply(intervalEnd);
        return result;
    }
    public static double SimpsonRule(double stepSize, double intervalStart, double intervalEnd, String function) throws ScriptException {
        Function<Double, Double> parsedFunction = Parser.parse(function);
        double result = 0;
        
        return result;
    }
}
