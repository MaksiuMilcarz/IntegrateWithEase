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
        // Tab menuTab = new Tab("Menu");
        // menuTab.setClosable(false);
        Tab calculatorTab = new Tab("Calculator");
        calculatorTab.setClosable(false);
        // Tab visualizerTab = new Tab("Visualizer");
        // visualizerTab.setClosable(false);
        tabPane.getTabs().addAll(calculatorTab);

        GridPane calculatorGrid = createCalculatorGrid();

        calculatorTab.setContent(calculatorGrid);

        BorderPane root = new BorderPane();
        root.setCenter(tabPane);
        Scene scene = new Scene(root, 900, 600);
        primaryStage.setTitle("Integrate With Ease");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane createCalculatorGrid() {
        GridPane calculatorGrid = new GridPane();
        calculatorGrid.setHgap(10);
        calculatorGrid.setVgap(10);

        Label label1 = new Label("Choose Method");
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Simpson's Rule", "Trapezoidal Rule", "Midpoint Rule");

        Label label2 = new Label("Step Size");
        TextField stepSizeField = new TextField();
        stepSizeField.setText("0.01");

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
                calculateResult(comboBox.getValue(), stepSizeField.getText(), aTextField.getText(), bTextField.getText(), textField4.getText(), resultLabel);
            } catch (ScriptException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });

        calculatorGrid.addRow(0, label1, comboBox);
        calculatorGrid.addRow(1, label2, stepSizeField);
        calculatorGrid.addRow(2, label3, aTextField, bTextField);
        calculatorGrid.addRow(3, label4, textField4);
        calculatorGrid.addRow(4, calculateButton);
        calculatorGrid.addRow(5, label5, resultLabel);

        return calculatorGrid;
    }

    private void calculateResult(String method, String stepSizeStr, String intervalStartStr, String intervalEndStr, String function, Label resultLabel) throws ScriptException {
        try {
            double stepSize = Double.parseDouble(stepSizeStr);
            double intervalStart = Double.parseDouble(intervalStartStr);
            double intervalEnd = Double.parseDouble(intervalEndStr);

            double result = 0;
            switch (method) {
                case "Simpson's Rule":
                    result = Methods.SimpsonRule(stepSize, intervalStart, intervalEnd, function);
                case "Trapezoidal Rule":
                    result = Methods.TrapezoidalRule(stepSize, intervalStart, intervalEnd, function);
                    break;
                case "Midpoint Rule":
                    result = Methods.MidpointRule(stepSize, intervalStart, intervalEnd, function);
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
