package model;

import exceptions.InvalidMonetaryAmountException;
import exceptions.NegativeMonetaryAmountException;

import java.time.LocalDate;
import java.util.ArrayList;

public class ExpenseItem extends Item {
    public ArrayList<String> expenseCategories = new ArrayList<String>();

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
