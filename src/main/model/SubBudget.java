package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class SubBudget {
    private Category category;
    private double amount;
    private String note;

    // EFFECTS: creates new SubBudget with specified fields
    public SubBudget(Category category, double amount, String note) {
        this.category = category;
        this.amount = amount;
        this.note = note;
    }

    // EFFECTS: returns category of sub-budget
    public Category getCategory() {
        return category;
    }

    // EFFECTS: returns category name of sub-budget
    public String getCategoryName() {
        return category.getName();
    }

    // EFFECTS: returns amount of sub-budget
    public double getAmount() {
        return amount;
    }

    // EFFECTS: returns note/subcategory of sub-budget
    public String getNote() {
        return note;
    }

    // MODIFIES: this
    // EFFECTS: sets amount to be newAmount
    public void edit(Category category, double amount, String note) {
        this.category = category;
        this.amount = amount;
        this.note = note;
    }

    // EFFECTS: returns total amount of expenses in category in this month
    public double getAmountUsedThisMonth(ArrayList<Item> allExpenseItems) {
        LocalDate currentDate = LocalDate.now();
        double amountAdder = 0;
        if (allExpenseItems != null) {
            for (Item i : allExpenseItems) {
                if (i.getCategory().equals(category)
                        && i.getDate().getMonthValue() == currentDate.getMonthValue()) {
                    amountAdder += i.getAmount();
                }
            }
        }

        return amountAdder;
    }

    // EFFECTS: returns amount of money left to spend in category to say in budget in this month
    public double getAmountLeft(ArrayList<Item> allExpenseItems) {
        return amount - getAmountUsedThisMonth(allExpenseItems);
    }
}
