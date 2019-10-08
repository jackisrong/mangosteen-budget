package model;

import java.time.LocalDate;

public abstract class Item {
    protected double amount;
    protected String category;
    protected LocalDate date;
    protected String note;

    protected Item(double amount, String category, LocalDate date, String note) {
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

    // REQUIRES: valid monetary amount newAmount > 0.0
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

    // MODIFIES: this
    // EFFECTS: changes the item amount to newAmount, category to newCategory
    public void changeItemDetails(double newAmount, String newCategory) {
        amount = newAmount;
        category = newCategory;
    }

    // MODIFIES: this
    // EFFECTS: changes the item amount to newAmount, category to newCategory, date to newDate
    public void changeItemDetails(double newAmount, String newCategory, LocalDate newDate) {
        amount = newAmount;
        category = newCategory;
        date = newDate;
    }

    // MODIFIES: this
    // EFFECTS: changes the item amount to newAmount, category to newCategory, date to newDate, note to newNote
    public void changeItemDetails(double newAmount, String newCategory, LocalDate newDate, String newNote) {
        amount = newAmount;
        category = newCategory;
        date = newDate;
        note = newNote;
    }

    // EFFECTS: prints a user-friendly sentence of item details
    public abstract String viewItem();
}
