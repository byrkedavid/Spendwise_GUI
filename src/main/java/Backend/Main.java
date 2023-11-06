package Backend;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        String dir = getDir();
        String fiile = dir + "datastoragesheet" + month + ".csv";


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

        ArrayList<EventNodeTest> testnodes = new ArrayList<EventNodeTest>();
        testnodes.add(new EventNodeTest(month, day, "rent", 1500.25, true, "debit", "Paid rent "));
        try {
            FileWriter fw = new FileWriter(fiile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(testnodes.get(0).getInfo());
            bw.newLine();

            bw.flush();
            bw.close();

        }catch(Exception e){

            System.out.println(e.getMessage());
        }
        ArrayList<String[]>data = readFile(fiile);
        printData(data);
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
            Scanner input = new Scanner(System.in);
            System.out.print("Name: ");
            String name = input.nextLine();
            System.out.print("Starting $: ");
            int start = input.nextInt();
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(name + "," + start);
            bw.newLine();
            bw.flush();
            bw.close();

        }catch(Exception e){

            System.out.println(e.getMessage());
        }
    }
    public static ArrayList<String[]> readFile(String fi){
        ArrayList<String[]> data = new ArrayList<String[]>();
        try{
            File f = new File(fi);
            Scanner reader = new Scanner(f);
            while(reader.hasNextLine()){
                data.add(reader.nextLine().split(","));

            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return data;
    }
    public static void printData(ArrayList<String[]> arr){

        for(int i = 0; i < arr.size(); i++){
            String[] currline = arr.get(i);
            for(int j = 0; j < currline.length; j++){
                System.out.print(currline[j] + "  ");
            }
            System.out.println();
        }
    }
}
