package com.example.GUI;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Transaction {
    private String expenseName;
    private String category;
    private DoubleProperty amount;

    public Transaction(String expenseName, String category, double amount) {
        this.expenseName = expenseName;
        this.category = category;
        this.amount = new SimpleDoubleProperty(amount);
    }

    public String getExpenseName() {
        return expenseName;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount.get();
    }

}
