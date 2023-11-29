package com.example.GUI;

public class Transaction {

    private String expenseName;
    private String category;
    private double amount;

    public Transaction(String expenseName, String category, double amount) {
        this.expenseName = expenseName;
        this.category = category;
        this.amount = amount;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public String getCategory(){
        return category;
    }

    public double getAmount() {
        return amount;
    }


}
