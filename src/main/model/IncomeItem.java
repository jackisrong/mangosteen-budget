package model;

public class IncomeItem {
    private double amount;
    private String category;
    private String date;
    private String note;

    // EFFECTS: creates new IncomeItem with specified fields
    public IncomeItem(double amount, String category, String date, String note) {
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.note = note;
    }

    // EFFECTS: returns amount of item
    public double getAmount() {
        return amount;
    }

    // EFFECTS: returns category of item
    public String getCategory() {
        return category;
    }

    // EFFECTS: returns date of item
    public String getDate() {
        return date;
    }

    // EFFECTS: returns note of item
    public String getNote() {
        return note;
    }

    // REQUIRES: valid monetary amount newAmount
    // MODIFIES: this
    // EFFECTS: changes the item amount to newAmount
    public void changeAmount(double newAmount) {
        amount = newAmount;
    }

    // MODIFIES: this
    // EFFECTS: changes the item category to newCategory
    public void changeCategory(String newCategory) {
        category = newCategory;
    }

    // TODO: change date to use an actual date system rather than a String
    // REQUIRES: valid date newDate
    // MODIFIES: this
    // EFFECTS: changes the item date to newDate
    public void changeDate(String newDate) {
        date = newDate;
    }

    // MODIFIES: this
    // EFFECTS: changes the item note to newNote
    public void changeNote(String newNote) {
        note = newNote;
    }
}














