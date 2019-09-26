package model;

import java.util.ArrayList;

public class SubBudget {
    private String category;
    private double amount;

    // EFFECTS: creates new SubBudget with specified fields
    public SubBudget(String category, double amount) {
        this.category = category;
        this.amount = amount;
    }

    // EFFECTS: returns category of sub-budget
    public String getCategory() {
        return category;
    }

    // EFFECTS: returns amount of sub-budget
    public double getAmount() {
        return amount;
    }

    // EFFECTS: returns total amount of expenses in category TODO: in this month
    public double getAmountUsed(ArrayList<IncomeItem> allIncomeItems) {
        double amountAdder = 0;
        if (allIncomeItems != null) {
            for (IncomeItem i : allIncomeItems) {
                // TODO: check if expenses are in the current month
                if (i.getCategory().equals(category)) {
                    amountAdder += i.getAmount();
                }
            }
        }

        return amountAdder;
    }

    // EFFECTS: returns amount of money left to spend in category to say in budget TODO: in this month
    public double getAmountLeft(ArrayList<IncomeItem> allIncomeItems) {
        return amount - getAmountUsed(allIncomeItems);
    }
}
