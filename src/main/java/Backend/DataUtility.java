package Backend;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * This class will save important data, such as expenses and budget, to their respective
 json files
 * Only once instance of the class is needed per session, so all methods are static
 * This class will make use of a list of event nodes that should be created in the main method
 */
public class DataUtility {

    private static final String MAIN_DIRECTORY = System.getProperty("user.dir");
    private static final File expensesFile = new File(MAIN_DIRECTORY + "\\expenses.json");
    private static final File budgetFile = new File(MAIN_DIRECTORY + "\\budget.json");

    /**
     * Makes the constructor private as no instances are needed other than the main instance
     */
    private DataUtility() {}

    /**
     * Saves both the nodes and the budget to their respective json files
     * @param nodes The list of nodes that will be saved to expenses.json
     * @param budget The budget that will be saved to budget.json
     * @return True if both were able to save, false otherwise
     */
    public static boolean updateAll(ArrayList<EventNodeTest> nodes, double budget) {
        return updateExpenseHistory(nodes) && updateMonthlyBudget(budget);
    }

    /**
     * This method saves a list of event nodes, or expense nodes, to a json file
     * named expenses.json
     * @param nodes The list of nodes that will be saved to the json file
     * @return True if the expenses were able to save, false otherwise
     */
    public static boolean updateExpenseHistory(ArrayList<EventNodeTest> nodes) {
        try {
            FileWriter fileWriter = new FileWriter(expensesFile);

            Gson gson = new Gson();
            gson.toJson(nodes, fileWriter);

            fileWriter.close();
            return true;
        } catch (IOException e) {
            System.err.println("Unable to save expense data to expenses.json");
            return false;
        }
    }

    /**
     * This method saves a budget double to a json file named budget.json
     * @param budget The budget that will be saved to the json file
     * @return True if the budget was able to save, false otherwise
     */
    public static boolean updateMonthlyBudget(double budget) {
        try {
            FileWriter fileWriter = new FileWriter(budgetFile);

            Gson gson = new Gson();
            gson.toJson(budget, fileWriter);

            fileWriter.close();
            return true;
        } catch (IOException e) {
            System.err.println("Unable to save budget data to budget.json");
            return false;
        }
    }

    /**
     * Reads the expenses.json file on the system or creates a new one if
     * one is not present
     * @return An arraylist of event nodes containing expense data or an empty arraylist
     */
    public static ArrayList<EventNodeTest> loadExpenseHistory() {
        ArrayList<EventNodeTest> nodes = new ArrayList<>();

        if (expensesFile.exists()) {
            try {
                FileReader fileReader = new FileReader(expensesFile);

                Type type = new TypeToken<ArrayList<EventNodeTest>>(){}.getType();
                Gson gson = new Gson();
                nodes = gson.fromJson(fileReader, type);

                fileReader.close();
            } catch (FileNotFoundException e) {
                System.err.println("Failed to load data from the file expenses.json");
            } catch (IOException e) {
                System.err.println("Failed to close FileReader reading the expenses.json file");
            }
        } else {
            try {
                expensesFile.createNewFile();
            } catch (IOException e) {
                System.err.println("Failed to create a new expenses.json file");
            }
        }

        return nodes;
    }

    /**
     * Reads the budget.json file on the system or creates a new one if
     * one is not present
     * @return The budget saved on the system or 0.0
     */
    public static double loadMonthlyBudget() {
        double budget = 0.0;

        if (budgetFile.exists()) {
            try {
                FileReader fileReader = new FileReader(budgetFile);

                Type type = new TypeToken<Double>(){}.getType();
                Gson gson = new Gson();
                budget = gson.fromJson(fileReader, type);

                fileReader.close();
            } catch (FileNotFoundException e) {
                System.err.println("Failed to load data from the file budget.json");
            } catch (IOException e) {
                System.err.println("Failed to close FileReader reading the budget.json file");
            }
        } else {
            try {
                budgetFile.createNewFile();
            } catch (IOException e) {
                System.err.println("Failed to create a new budget.json file");
            }
        }

        return budget;
    }
 /** method that uses a thread to call the saveData() method(which will just write the nodes and budget to the JSON file) every 5 mins
 */
 
 public class MultiThread extends Thread {

    @Override
    public void run() {
        while (true) {
            //updateAll(ArrayList < EventNodeTest > nodes, double budget);

            try {
                Thread.sleep(300000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

}
