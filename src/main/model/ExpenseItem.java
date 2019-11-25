package model;

import java.time.LocalDate;

public class ExpenseItem extends Item {
    // EFFECTS: creates new ExpenseItem with specified fields
    public ExpenseItem(double amount, Category category, LocalDate date, String note) {
        super(amount, category, date, note);
    }
}
