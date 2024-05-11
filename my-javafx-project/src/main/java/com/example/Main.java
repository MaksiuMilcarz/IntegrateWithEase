package com.example;

import javax.script.ScriptException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        TabPane tabPane = new TabPane();
        Tab menuTab = new Tab("Derivatives");
        menuTab.setClosable(false);
        Tab calculatorTab = new Tab("Definite Integrals");
        calculatorTab.setClosable(false);
        Tab visualizerTab = new Tab("Visualizer");
        visualizerTab.setClosable(false);
        tabPane.getTabs().addAll(calculatorTab, menuTab);

        GridPane calculatorGrid = createCalculatorGrid();
        GridPane derivativeGrid = createDerivativeGrid();

        calculatorTab.setContent(calculatorGrid);
        menuTab.setContent(derivativeGrid);

        BorderPane root = new BorderPane();
        root.setCenter(tabPane);
        Scene scene = new Scene(root, 900, 600);
        primaryStage.setTitle("Integrate With Ease");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane createDerivativeGrid() {
        GridPane derivativeGrid = new GridPane();
        derivativeGrid.setHgap(10);
        derivativeGrid.setVgap(10);

        Label label1 = new Label("Choose Method");
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("First Derivative 3point","First Derivative 5point", "Second Derivative");
        comboBox.setValue("First Derivative 3point");

        Label label2 = new Label("step size");
        TextField textField1 = new TextField();
        textField1.setText("0.01");

        Label label3 = new Label("point");
        TextField textField2 = new TextField();

        Label label4 = new Label("function");
        TextField textField3 = new TextField();

        Label label5 = new Label("Result");
        Label resultLabel = new Label();

        Button calculateButton = new Button("Calculate");
        calculateButton.setOnAction(e -> {
            try {
                calculateResultDerivative(comboBox.getValue(), textField1.getText(), textField2.getText(), textField3.getText(), resultLabel);
            } catch (ScriptException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });

        derivativeGrid.addRow(0, label1, comboBox);
        derivativeGrid.addRow(1, label2, textField1);
        derivativeGrid.addRow(2, label3, textField2);
        derivativeGrid.addRow(3, label4, textField3);
        derivativeGrid.addRow(2, calculateButton);
        derivativeGrid.addRow(3, label5, resultLabel);

        return derivativeGrid;
    }

    private void calculateResultDerivative(String method, String stepSize, String pointStr, String function, Label resultLabel) throws ScriptException {
        try {
            double h = Double.parseDouble(stepSize);
            double point = Double.parseDouble(pointStr);

            double result = 0;
            switch (method) {
                case "First Derivative 3point":
                    result = DerivationMethods.firstDerivative(h, point, function);
                    break;
                case "Second Derivative":
                    result = DerivationMethods.secondDerivative(h, point, function);
                    break;
                case "First Derivative 5point":
                    result = DerivationMethods.firstDerivative5point(h, point, function);
                    break;
            }

            resultLabel.setText(String.valueOf(result));
        } catch (NumberFormatException ex) {
            resultLabel.setText("Error: Invalid input");
        }
    }

    private GridPane createCalculatorGrid() {
        GridPane calculatorGrid = new GridPane();
        calculatorGrid.setHgap(10);
        calculatorGrid.setVgap(10);

        Label label1 = new Label("Choose Method");
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Simpson's Rule", "Trapezoidal Rule", "Midpoint Rule");
        comboBox.setValue("Simpson's Rule");

        Label label2 = new Label("Step Size");
        TextField textField2 = new TextField();
        textField2.setText("0.01");

        Label label3 = new Label("Interval");
        TextField aTextField = new TextField();
        TextField bTextField = new TextField();
        aTextField.setText("0");
        bTextField.setText("1");

        Label label4 = new Label("Function");
        TextField textField4 = new TextField();

        Label label5 = new Label("Result");
        Label resultLabel = new Label();

        Button calculateButton = new Button("Calculate");
        calculateButton.setOnAction(e -> {
            try {
                calculateResult(comboBox.getValue(),textField2.getText(), aTextField.getText(), bTextField.getText(), textField4.getText(), resultLabel);
            } catch (ScriptException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });

        calculatorGrid.addRow(0, label1, comboBox);
        calculatorGrid.addRow(1, label2, textField2);
        calculatorGrid.addRow(2, label3, aTextField, bTextField);
        calculatorGrid.addRow(3, label4, textField4);
        calculatorGrid.addRow(4, calculateButton);
        calculatorGrid.addRow(5, label5, resultLabel);

        return calculatorGrid;
    }

    private void calculateResult(String method,String StepSize, String intervalStartStr, String intervalEndStr, String function, Label resultLabel) throws ScriptException {
        try {
            double h = Double.parseDouble(StepSize);
            double intervalStart = Double.parseDouble(intervalStartStr);
            double intervalEnd = Double.parseDouble(intervalEndStr);

            double result = 0;
            switch (method) {
                case "Simpson's Rule":
                    result = IntegrationMethods.SimpsonRule(h, intervalStart, intervalEnd, function);
                case "Trapezoidal Rule":
                    result = IntegrationMethods.TrapezoidalRule(h, intervalStart, intervalEnd, function);
                    break;
                case "Midpoint Rule":
                    result = IntegrationMethods.MidpointRule(h, intervalStart, intervalEnd, function);
                    break;
            }

            resultLabel.setText(String.valueOf(result));
        } catch (NumberFormatException ex) {
            resultLabel.setText("Error: Invalid input");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
