package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class ExpenseItem extends Item {
    public ArrayList<String> expenseCategories = new ArrayList<String>();

    // EFFECTS: creates new ExpenseItem with specified fields
    public ExpenseItem(double amount, String category, LocalDate date, String note) {
        super(amount, category, date, note);
    }

    @Override
    public void viewItem() {
        System.out.println("Expense of amount " + amount + " in category " + category + " from date " + date
                + "with note: " + note);
    }
}
