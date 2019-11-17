package ui;

import model.Budget;
import model.Category;
import model.ExpenseCategory;
import model.SubBudget;

import java.time.LocalDate;
import java.util.Scanner;

public class SubBudgetMenu {
    private Scanner scanner = new Scanner(System.in);
    private Budget budget;

    public SubBudgetMenu(Budget budget) {
        this.budget = budget;
    }

    public void createSubBudget() {
        System.out.println("Which category would you like to create a sub-budget for?");
        String categoryKey = scanner.nextLine();
        System.out.println("What amount would you like to set the budget for?");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        Category category = budget.getExpenseCategories().get(categoryKey);
        budget.addToAllSubBudgets(new SubBudget(category, amount));
    }

    public void viewSubBudgets() {
        for (SubBudget b : budget.getAllSubBudgets()) {
            System.out.println("Sub-budget for " + b.getCategoryName() + " has used "
                    + b.getAmountUsedThisMonth(budget.getAllExpenseItems(), LocalDate.now()) + " of " + b.getAmount()
                    + " (" + b.getAmountLeft(budget.getAllExpenseItems(), LocalDate.now()) + " left in sub-budget)");
        }
    }

    public void changeSubBudgetAmount() {
        viewSubBudgets();
        System.out.println("Which category's sub-budget would you like to edit?");
        String category = scanner.nextLine();
        for (SubBudget s : budget.getAllSubBudgets()) {
            if (s.getCategory().equals(category)) {
                System.out.println("Enter new amount for sub-budget:");
                double newAmount = scanner.nextDouble();
                scanner.nextLine();
                s.changeAmount(newAmount);
                break;
            }
        }
    }
}
