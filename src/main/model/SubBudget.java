package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class SubBudget {
    private Category category;
    private double amount;

    // EFFECTS: creates new SubBudget with specified fields
    public SubBudget(Category category, double amount) {
        this.category = category;
        this.amount = amount;
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

    // MODIFIES: this
    // EFFECTS: sets amount to be newAmount
    public void changeAmount(double newAmount) {
        amount = newAmount;
    }

    // EFFECTS: returns total amount of expenses in category in this month
    public double getAmountUsedThisMonth(ArrayList<Item> allExpenseItems, LocalDate currentDate) {
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
    public double getAmountLeft(ArrayList<Item> allExpenseItems, LocalDate currentDate) {
        return amount - getAmountUsedThisMonth(allExpenseItems, currentDate);
    }
}
