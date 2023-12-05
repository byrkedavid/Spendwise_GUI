package Backend;

public class EventNodeTest {
    private int month;
    private int day;
    private String category;
    private double amount;
    private boolean expense;
    private String paymentMethod;
    private String description;

    public EventNodeTest(int month, int day, String category, double amount, boolean expense, String paymentMethod, String description){
        this.month = month;
        this.day = day;
        this.category = category;
        this.amount = amount;
        this.expense = expense;
        this.paymentMethod = paymentMethod;
        this.description = description;
    }

    public int getMonth() {
        return month;
    }
    public void setMonth(int month){
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
    public String getDate(){
        return "" + month + "/" + day;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean getExpense() {
        return expense;
    }

    public void setExpense(boolean expense) {
        this.expense = expense;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getInfo(){
        return "" + month + "," + day + "," + category + "," + amount + "," + expense + "," + paymentMethod + "," + description;
    }
}