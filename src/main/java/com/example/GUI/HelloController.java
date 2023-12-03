package com.example.GUI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.*;
import java.text.DecimalFormat;
import java.util.Optional;


public class HelloController {

    DecimalFormat df = new DecimalFormat("#,###.00");
    float budget;
    float remainingBudget;
    float deposit;

    @FXML
    private TextField budgetIn;
    @FXML
    private TextField depositIn;
    @FXML
    private Text monthlyBudgetOut;
    @FXML
    private Text remainingBudgetOut;
    @FXML
    private PieChart transactionChart;
    @FXML
    private TableView<Transaction> transactionTable;
    @FXML
    private TableColumn<Transaction, String> expenseNameColumn;
    @FXML
    private TableColumn<Transaction, String> categoryColumn;
    @FXML
    private TableColumn<Transaction, Double> amountColumn;
    @FXML
    private TableColumn<Transaction, String> deleteColumn;

    @FXML
    private void initialize() {
        // Do not initialize the columns in the table here
    }

    public void switchToScene1(ActionEvent event) throws IOException {
        // Load the FXML for the main page
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-page.fxml"));
        Parent root = loader.load();

        // Get the controller from the loader
        HelloController mainPageController = loader.getController();

        // Initialize the columns in the table after loading the main page FXML
        mainPageController.initializeTable();

        // Set the stage and scene
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Add this method to initialize the table in the main page
    public void initializeTable() {
        expenseNameColumn.setCellValueFactory(new PropertyValueFactory<>("expenseName"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        deleteColumn.setCellValueFactory(new PropertyValueFactory<>("delete"));
        deleteColumn.setCellFactory(col -> {
            TableCell<Transaction, String> cell = new TableCell<>() {
                final Button deleteButton = new Button("Delete");

                {
                    deleteButton.setOnAction(event -> {
                        Transaction transaction = getTableView().getItems().get(getIndex());
                        deleteTransaction(transaction);
                    });
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(deleteButton);
                    }
                }
            };
            return cell;
        });
    }

    // Method to handle the delete action
    private void deleteTransaction(Transaction transaction) {
        // Remove the transaction from the PieChart
        removeTransactionFromChart(transaction);

        // Update the remaining budget
        remainingBudget += transaction.getAmount();
        updateRemainingBudget(remainingBudget);

        // Remove the transaction from the TableView
        transactionTable.getItems().remove(transaction);
    }

    private void removeTransactionFromChart(Transaction transaction) {
        for (PieChart.Data data : transactionChart.getData()) {
            if (data.getName().equals(transaction.getCategory())) {
                data.setPieValue(data.getPieValue() - transaction.getAmount());
                // If the pie value becomes zero, remove the data from the chart
                if (data.getPieValue() == 0) {
                    transactionChart.getData().remove(data);
                }
                return;
            }
        }
    }

    @FXML
    void addDeposit(MouseEvent event) {
        deposit = Float.parseFloat(depositIn.getText());
        remainingBudget += deposit;
        updateRemainingBudget(remainingBudget);
    }

    @FXML
    void addExpense(float amount) {
        remainingBudget -= amount;
        updateRemainingBudget(remainingBudget);
    }

    @FXML
    void setBudget(MouseEvent event) {
        budget = Float.parseFloat(budgetIn.getText());
        remainingBudget = budget;
        monthlyBudgetOut.setText("Monthly Budget: $" + df.format(budget));
        updateRemainingBudget(budget);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Budget Updated!");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            //formatSystem();
        }
    }

    @FXML
    void updateRemainingBudget(Float budget) {
        remainingBudgetOut.setText("Remaining Budget: $" + df.format(budget));
    }

    @FXML
    private void recordExpense() {
        // Create a custom dialog
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Record Expense");
        dialog.setHeaderText("Please enter expense details:");

        // Set the button types
        ButtonType recordButtonType = new ButtonType("Record", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(recordButtonType, ButtonType.CANCEL);

        // Create the expense form grid
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField expenseNameField = new TextField();
        expenseNameField.setPromptText("Expense Name");
        ChoiceBox<String> categoryChoiceBox = new ChoiceBox<>();
        ObservableList<String> categories = FXCollections.observableArrayList("Select Category", "Groceries", "Utilities", "Entertainment", "Transportation");
        categoryChoiceBox.setItems(categories);
        categoryChoiceBox.setValue("Select Category");
        TextField amountField = new TextField();
        amountField.setPromptText("Amount");

        grid.add(new Label("Expense Name:"), 0, 0);
        grid.add(expenseNameField, 1, 0);
        grid.add(new Label("Category:"), 0, 1);
        grid.add(categoryChoiceBox, 1, 1);
        grid.add(new Label("Amount:"), 0, 2);
        grid.add(amountField, 1, 2);

        dialog.getDialogPane().setContent(grid);

        // Enable/Disable the record button depending on whether a name, category, and amount are entered
        Node recordButton = dialog.getDialogPane().lookupButton(recordButtonType);
        recordButton.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).
        expenseNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            recordButton.setDisable(newValue.trim().isEmpty() || "Select Category".equals(categoryChoiceBox.getValue()) || amountField.getText().trim().isEmpty());
        });

        categoryChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            recordButton.setDisable(expenseNameField.getText().trim().isEmpty() || "Select Category".equals(newValue) || amountField.getText().trim().isEmpty());
        });

        amountField.textProperty().addListener((observable, oldValue, newValue) -> {
            recordButton.setDisable(expenseNameField.getText().trim().isEmpty() || "Select Category".equals(categoryChoiceBox.getValue()) || newValue.trim().isEmpty());
        });

        // Set the result converter to convert the button type to a String
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == recordButtonType) {
                return String.format("%s (%s) - $%s", expenseNameField.getText(), categoryChoiceBox.getValue(), amountField.getText());
            }
            return null;
        });

        // Show the dialog and wait for the user's input
        String result = dialog.showAndWait().orElse(null);

        // If the user recorded an expense, update the PieChart and refresh the UI
        if (result != null) {
            // Parse the information from the result string
            Transaction newTransaction = createTransaction(result);

            // Add the new transaction to the PieChart
            updateTransactionChart(newTransaction);

            addExpense(Float.parseFloat(amountField.getText()));
        }
    }

    private Transaction createTransaction(String result) {
        // Parse the information from the result string (assuming a specific format)
        // Modify this part based on the actual format you decide for the result string
        String[] parts = result.split(" \\(");
        String expenseName = parts[0];
        String[] categoryAndAmount = parts[1].split("\\) - \\$");
        String category = categoryAndAmount[0];
        double amount = Double.parseDouble(categoryAndAmount[1]);

        // Create a new Transaction object
        return new Transaction(expenseName, category, amount);
    }

    private void updateTransactionChart(Transaction newTransaction) {
        // Check if there is an existing slice for the same category
        for (PieChart.Data existingData : transactionChart.getData()) {
            if (existingData.getName().equals(newTransaction.getCategory())) {
                // If found, update the existing slice by adding the new amount
                existingData.setPieValue(existingData.getPieValue() + newTransaction.getAmount());

                // Update the TableView
                updateTransactionTable(newTransaction);
                return; // Exit the method
            }
        }

        // If no existing slice found, create a new slice for the category
        PieChart.Data newData = new PieChart.Data(newTransaction.getCategory(), newTransaction.getAmount());
        transactionChart.getData().add(newData);

        // Update the TableView
        updateTransactionTable(newTransaction);
    }

    private void updateTransactionTable(Transaction newTransaction) {
        // Add the new transaction to the TableView
        transactionTable.getItems().add(newTransaction);
    }
}