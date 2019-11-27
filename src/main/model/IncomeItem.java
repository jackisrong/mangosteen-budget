package model;

import java.time.LocalDate;

public class IncomeItem extends Item {
    // MODIFIES: this
    // EFFECTS: creates new IncomeItem with specified fields
    public IncomeItem(double amount, Category category, LocalDate date, String note) {
        super(amount, category, date, note);
    }
}
