package model;

public class IncomeItem {
    private double amount;
    private String category;
    private String date;
    private String note;

    public IncomeItem(double amount, String category, String date, String note) {
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.note = note;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getNote() {
        return note;
    }

    public void changeIncomeAmount(double newAmount) {
        amount = newAmount;
    }
}
