package model;

import exceptions.NegativeMonetaryAmountException;

import java.time.LocalDate;

public class ExpenseItem extends Item {
    // EFFECTS: creates new ExpenseItem with specified fields
    public ExpenseItem(double amount, String category, LocalDate date, String note)
            throws NegativeMonetaryAmountException {
        super(amount, category, date, note);
    }

    @Override
    public String viewItem() {
        return "Expense of amount $" + amount + " in category " + category + " from date " + date
                + " with note: " + note;
    }
}
