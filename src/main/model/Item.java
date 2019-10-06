package model;

import java.time.LocalDate;

public abstract class Item {
    protected double amount;
    protected String category;
    protected LocalDate date;
    protected String note;

    public Item(double amount, String category, LocalDate date, String note) {
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
    public LocalDate getDate() {
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
    public void changeDate(LocalDate newDate) {
        date = newDate;
    }

    // MODIFIES: this
    // EFFECTS: changes the item note to newNote
    public void changeNote(String newNote) {
        note = newNote;
    }
}
