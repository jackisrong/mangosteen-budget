package model;

import exceptions.InvalidMonetaryAmountException;
import exceptions.NegativeMonetaryAmountException;

import java.time.LocalDate;

public abstract class Item {
    protected double amount;
    protected String category;
    protected LocalDate date;
    protected String note;

    protected Item(double amount, String category, LocalDate date, String note) throws NegativeMonetaryAmountException {
        if (checkMonetaryValidity(amount)) {
            this.amount = amount;
        }
        this.category = category;
        this.date = date;
        this.note = note;
    }

    private boolean checkMonetaryValidity(double a) throws NegativeMonetaryAmountException {
        if (a < 0) {
            throw new NegativeMonetaryAmountException();
        }

        return true;
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
    public void changeAmount(double newAmount) throws NegativeMonetaryAmountException {
        if (checkMonetaryValidity(newAmount)) {
            amount = newAmount;
        }
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

    // EFFECTS: prints a user-friendly sentence of item details
    public abstract String viewItem();
}
