package Backend;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
public class Main {
    private static ArrayList<EventNodeTest> array;
    public static void main(String[] args) {
        Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        String dir = getDir();
        String fiile = dir + "expenses.json";

        try{
            File f = new File(fiile);
            Scanner filereader = new Scanner(f);
        }
        catch(Exception e){
            try{
                createFile(fiile, month, day);
            }
            catch(Exception err){
                System.out.println(err.getMessage());
            }
        }


        array = new ArrayList<EventNodeTest>();
        array.add(new EventNodeTest(month, day, "rent", 1500.25, true, "debit", "Paid rent "));
        try {
            FileWriter fw = new FileWriter(fiile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(array.get(0).getInfo());
            bw.newLine();


            bw.flush();
            bw.close();


        }catch(Exception e){


            System.out.println(e.getMessage());
        }


    }
    public static String getDir(){
        String dir = System.getProperty("user.dir");
        //commented code removes last portion of the running file's directory, allowing you to put the csv one level upward
       /*while(!dir.substring(dir.length() - 1, dir.length()).equals("\\")){
           dir = dir.substring(0, dir.length() - 1);
       }
       */
        return dir + "\\";
    }
    /*
    @param fi, the string name of the file to be created
    @param month, the currrent month of the year
    @param day, the current day of the month
    this method forces the creation of a new csv, then asks for some baseline information before recording it in the new file
     */
    public static void createFile(String fi, int month, int day){
        System.out.println("start creatFile");
        try {
            FileWriter fw = new FileWriter(fi, true);
            fw.close();


        }catch(Exception e){


            System.out.println(e.getMessage());
        }
    }
    public static double calcBudget(ArrayList<EventNodeTest> arr) {
        double budget = 0.0;
        for (EventNodeTest t : arr) {
            if (t.getExpense()) {
                budget -= t.getAmount();


            } else {
                budget += t.getAmount();
            }
        }
        return budget;
    }
    public static void addNode(EventNodeTest e){
        array.add(e);
    }
    public static void removeNode(int index){
        array.remove(index);
    }
    public static void replaceNode(int index, EventNodeTest e){
        array.set(index, e);
    }
    public static double[] getDailySpending(){
        Calendar c = Calendar.getInstance();
        int monthMaxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        double[] spending = new double[monthMaxDays];
        for(int i = 0; i < array.size(); i++){
            int day = array.get(i).getDay();
            if(array.get(i).getExpense())
                spending[day = 1] = array.get(i).getAmount();
        }
        return spending;


    }
    public static String[] getPaymentMethodSpending(){
        ArrayList<String> paymentTypes = new ArrayList<>();
        ArrayList<Double> paymentSpending = new ArrayList<>();
        for(int i = 0; i < array.size(); i++){
            if(array.get(i).getExpense()) {
                String paymentType = array.get(i).getPaymentMethod().toLowerCase();
                boolean found = false;
                for (int j = 0; j < paymentTypes.size(); j++) {
                    if (paymentTypes.get(j).equals(paymentType)) {
                        paymentSpending.set(j, paymentSpending.get(j) + array.get(i).getAmount());
                        found = true;
                        continue;
                    }
                    if (found)
                        continue;
                    else {
                        paymentTypes.add(paymentType);
                        paymentSpending.add(array.get(i).getAmount());
                    }
                }
            }
        }
        String[] spending = new String[paymentSpending.size()];
        for(int l = 0; l < spending.length; l++){
            spending[l] = paymentTypes.get(l) + "," + paymentSpending.get(l);
        }
        return spending;
    }
}
