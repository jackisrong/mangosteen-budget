package model;

public class SubBudget {
    private Category category;
    private double amount;

    // MODIFIES: this
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
    public void edit(Category category, double amount) {
        this.category = category;
        this.amount = amount;
    }

    // EFFECTS: returns total amount of expenses in category for current month
    public double getAmountUsedThisMonth() {
        return category.getAllItemAmountThisMonthTotal();
    }

    // EFFECTS: returns amount of money left to spend in category to say in budget for current month
    public double getAmountLeft() {
        return amount - getAmountUsedThisMonth();
    }
}
