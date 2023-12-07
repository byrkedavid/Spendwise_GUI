package com.example.GUI;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Transaction {
    private String expenseName;
    private String category;
    private DoubleProperty amount;
    private String delete;

    public Transaction(String expenseName, String category, double amount, String delete) {
        this.expenseName = expenseName;
        this.category = category;
        this.amount = new SimpleDoubleProperty(amount);
        this.delete = delete;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount.get();
    }

    public String getExpenseName(){
        return expenseName;
    }

    public String getDelete(){
        return null;
    }

}
