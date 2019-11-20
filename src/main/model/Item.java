package model;

import exceptions.NegativeMonetaryAmountException;

import java.time.LocalDate;

public abstract class Item {
    protected double amount;
    protected Category category;
    protected LocalDate date;
    protected String note;

    protected Item(double amount, Category category, LocalDate date, String note) {
        this.amount = amount;
        setCategory(category);
        this.date = date;
        this.note = note;
    }

    // EFFECTS: checks if monetary amount given is valid
    private boolean checkMonetaryValidity(double a) throws NegativeMonetaryAmountException {
        if (a < 0) {
            throw new NegativeMonetaryAmountException();
        }

        return true;
    }

    // MODIFIES: this
    // EFFECTS: reflexive relationship (one-to-many) - set category, then add this item into category's list of items
    public void setCategory(Category category) {
        if (!category.equals(this.category)) {
            this.category = category;
            category.addItem(this);
        }
    }

    // MODIFIES: this
    // EFFECTS: reflexive relationship (one-to-many) - set category to null, then remove this item from category's list
    public void removeCategory(Category category) {
        if (category.equals(this.category)) {
            this.category = null;
            category.removeItem(this);
        }
    }

    // EFFECTS: returns amount of item
    public double getAmount() {
        return amount;
    }

    // EFFECTS: returns category of item
    public Category getCategory() {
        return category;
    }

    // EFFECTS: returns category name of item
    public String getCategoryName() {
        return category.getName();
    }

    // EFFECTS: returns date of item
    public LocalDate getDate() {
        return date;
    }

    // EFFECTS: returns note of item
    public String getNote() {
        return note;
    }

    public void edit(double amount, Category category, LocalDate date, String note) {
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.note = note;
    }

    /*
    // REQUIRES: valid monetary amount newAmount > 0.0
    // MODIFIES: this
    // EFFECTS: changes the item amount to newAmount
    public void changeAmount(double newAmount) throws NegativeMonetaryAmountException {
        if (checkMonetaryValidity(newAmount)) {
            amount = newAmount;
        }
    }

    // MODIFIES: this
    // EFFECTS: changes the item category to newCategory
    public void changeCategory(Category newCategory) {
        removeCategory(category);
        setCategory(newCategory);
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
     */

    // EFFECTS: prints a user-friendly sentence of item details
    public abstract String viewItem();
}
