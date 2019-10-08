package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class IncomeItem extends Item {
    public ArrayList<String> incomeCategories = new ArrayList<String>();

    // EFFECTS: creates new IncomeItem with specified fields
    public IncomeItem(double amount, String category, LocalDate date, String note) {
        super(amount, category, date, note);
        incomeCategories.add("Salary");
        incomeCategories.add("Rentals");
        incomeCategories.add("Investments");
        incomeCategories.add("Gift");
    }

    @Override
    public String viewItem() {
        return "Income of amount $" + amount + " in category " + category + " from date " + date
                + " with note: " + note;
    }
}
