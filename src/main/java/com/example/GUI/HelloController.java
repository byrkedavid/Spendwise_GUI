package com.example.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.text.DecimalFormat;




public class HelloController {

    DecimalFormat df = new DecimalFormat("#,###.00");
    float budget;
    float remainingBudget;
    float expense;
    float deposit;

    @FXML
    private TextField budgetIn;

    @FXML
    private TextField depositIn;

    @FXML
    private TextField expenseIn;

    @FXML
    private Text monthlyBudgetOut;

    @FXML
    private Text remainingBudgetOut;

    @FXML
    void addDeposit(MouseEvent event) {
        deposit = Float.parseFloat(depositIn.getText());
        remainingBudget += deposit;
        updateRemainingBudget(remainingBudget);
    }

    @FXML
    void addExpense(MouseEvent event) {
        expense = Float.parseFloat(expenseIn.getText());
        remainingBudget -= expense;
        updateRemainingBudget(remainingBudget);
    }

    @FXML
    void setBudget(MouseEvent event) {
        budget = Float.parseFloat(budgetIn.getText());
        remainingBudget = budget;
        monthlyBudgetOut.setText("Monthly Budget: $" + df.format(budget));
        updateRemainingBudget(budget);
    }

    @FXML
    void updateRemainingBudget(Float budget) {
        remainingBudgetOut.setText("Remaining Budget: $" + df.format(budget));
    }

}