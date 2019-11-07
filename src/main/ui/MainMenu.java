package ui;

import model.*;

import java.time.LocalDate;
import java.util.Scanner;

public class MainMenu {
    private Scanner scanner = new Scanner(System.in);
    private Budget budget = new Budget();
    private ItemMenu itemMenu = new ItemMenu(budget);
    private SubBudgetMenu subBudgetMenu = new SubBudgetMenu(budget);

    public void run() {
        budget.loadAllExistingData();
        displayMonthlyView();
        boolean keepRunning = true;
        while (keepRunning) {
            displayChoices();
            int choice = scanner.nextInt();
            scanner.nextLine();
            keepRunning = runAppropriateFunctionBasedOnChoice(choice);
        }
        budget.saveAllExistingData();
    }

    private void displayChoices() {
        System.out.println("What would you like to do?");
        System.out.println("[1] Add income or expense item");
        System.out.println("[2] Display all income and expense items");
        System.out.println("[3] Edit an income or expense item");
        System.out.println("[4] Create a monthly sub-budget to track money spent on individual categories");
        System.out.println("[5] View sub-budgets");
        System.out.println("[6] Edit a sub-budget's amount");
        System.out.println("[7] FOR TESTING ONLY: View all items in each category");
        System.out.println("[other] Save and quit");
    }

    private void displayMonthlyView() {
        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();
        double totalMonthlyIncome = 0.0;
        double totalMonthlyExpenses = 0.0;
        for (Item i : budget.getAllIncomeItems()) {
            if (i.getDate().getYear() == currentYear && i.getDate().getMonthValue() == currentMonth) {
                totalMonthlyIncome += i.getAmount();
            }
        }
        for (Item e : budget.getAllExpenseItems()) {
            if (e.getDate().getYear() == currentYear && e.getDate().getMonthValue() == currentMonth) {
                totalMonthlyExpenses += e.getAmount();
            }
        }
        System.out.println("Total income this month: " + totalMonthlyIncome);
        System.out.println("Total expenses this month: " + totalMonthlyExpenses);
        System.out.println("Net income this month: " + (totalMonthlyIncome - totalMonthlyExpenses));
        System.out.println("--------------------");
    }

    private boolean runAppropriateFunctionBasedOnChoice(int choice) {
        if (choice == 1) {
            itemMenu.createItemPrompt();
        } else if (choice == 2) {
            itemMenu.displayAllItems();
        } else if (choice == 3) {
            itemMenu.editItemChooseType();
        } else if (choice == 4) {
            subBudgetMenu.createSubBudget();
        } else if (choice == 5) {
            subBudgetMenu.viewSubBudgets();
        } else if (choice == 6) {
            subBudgetMenu.changeSubBudgetAmount();
        } else if (choice == 7) {
            itemMenu.viewAllItemsInAllCategories();
        } else {
            return false;
        }
        return true;
    }
}
