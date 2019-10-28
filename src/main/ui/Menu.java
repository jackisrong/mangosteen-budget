package ui;

import exceptions.NegativeMonetaryAmountException;
import model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private Scanner scanner = new Scanner(System.in);
    private Budget budget = new Budget();

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
            createItem();
        } else if (choice == 2) {
            displayAllItems();
        } else if (choice == 3) {
            editItemChooseType();
        } else if (choice == 4) {
            createSubBudget();
        } else if (choice == 5) {
            viewSubBudgets();
        } else if (choice == 6) {
            changeSubBudgetAmount();
        } else if (choice == 7) {
            viewAllItemsInAllCategories();
        } else {
            return false;
        }
        return true;
    }

    private void createItem() {
        System.out.println("Which item would you like to create?");
        System.out.println("[1] Income item");
        System.out.println("[2] Expense item");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice == 1) {
            createItemGetValues("income");
        } else {
            createItemGetValues("expense");
        }
    }

    private void createItemGetValues(String type) {
        System.out.println("How much is your " + type + "? (Enter an amount)");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Where is your " + type + " from? (Enter a category)");
        String categoryKey = scanner.nextLine();
        System.out.println("When is your " + type + " from? (Enter a date in format YYYY MM DD)");
        int year = scanner.nextInt();
        int month = scanner.nextInt();
        int day = scanner.nextInt();
        scanner.nextLine();
        LocalDate date = LocalDate.of(year, month, day);
        System.out.println("Any additional info? (Enter a note)");
        String note = scanner.nextLine();

        if (type.equals("income")) {
            createIncomeItem(amount, budget.getIncomeCategories().get(categoryKey), date, note);
        } else {
            createExpenseItem(amount, budget.getExpenseCategories().get(categoryKey), date, note);
        }
    }

    private void createIncomeItem(double amount, Category category, LocalDate date, String note) {
        try {
            budget.addToAllIncomeItems(new IncomeItem(amount, category, date, note));
        } catch (NegativeMonetaryAmountException e) {
            System.out.println("Invalid monetary amount!  Your amount is negative!");
        } finally {
            System.out.println("Income item creation process has finished.");
        }
    }

    private void createExpenseItem(double amount, Category category, LocalDate date, String note) {
        try {
            budget.addToAllExpenseItems(new ExpenseItem(amount, category, date, note));
        } catch (NegativeMonetaryAmountException e) {
            System.out.println("Invalid monetary amount!  Your amount is negative!");
        } finally {
            System.out.println("Expense item creation process has finished.");
        }
    }

    private void displayAllItems() {
        System.out.println("--------------------");
        System.out.println("Income Items");
        displayAllItemsInList(budget.getAllIncomeItems());
        System.out.println("--------------------");
        System.out.println("Expense Items");
        displayAllItemsInList(budget.getAllExpenseItems());
        System.out.println("--------------------");
    }

    private void displayAllItemsInList(ArrayList<Item> items) {
        int counter = 0;
        for (Item i : items) {
            System.out.print("[" + counter + "] $" + i.getAmount() + " in category " + i.getCategoryName() + " on ");
            System.out.println(i.getDate() + " with note: " + i.getNote());
            counter++;
        }
    }

    private void editItemChooseType() {
        System.out.println("Which item would you like to edit?");
        System.out.println("[1] Income item");
        System.out.println("[2] Expense item");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice == 1) {
            displayAllItemsInList(budget.getAllIncomeItems());
            editItem(budget.getAllIncomeItems(), "income");
        } else {
            displayAllItemsInList(budget.getAllExpenseItems());
            editItem(budget.getAllExpenseItems(), "expense");
        }
    }

    private void editItem(ArrayList<Item> items, String type) {
        System.out.println("Which item would you like to change? (Enter the item number)");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < items.size()) {
            System.out.println("What would you like to change?");
            System.out.println("[1] Amount");
            System.out.println("[2] Category");
            System.out.println("[3] Date");
            System.out.println("[4] Note");
            int fieldChoice = scanner.nextInt();
            scanner.nextLine();

            if (fieldChoice >= 1 && fieldChoice <= 4) {
                editCorrectItemField(items.get(choice), fieldChoice, type);
            }
        } else {
            System.out.println("That's an invalid item!");
        }
    }

    private void editCorrectItemField(Item item, int fieldChoice, String type) {
        System.out.println("Enter new value:");
        if (fieldChoice == 1) {
            double newAmount = scanner.nextDouble();
            scanner.nextLine();
            try {
                item.changeAmount(newAmount);
            } catch (NegativeMonetaryAmountException e) {
                System.out.println("Invalid monetary amount!  Your amount is negative!");
            }
        } else if (fieldChoice == 2) {
            String newCategory = scanner.nextLine();
            item.changeCategory(changeCategory(newCategory, type));
        } else if (fieldChoice == 3) {
            item.changeDate(changeDate());
        } else if (fieldChoice == 4) {
            String newNote = scanner.nextLine();
            item.changeNote(newNote);
        }
    }

    private Category changeCategory(String newCategory, String type) {
        if (type.equals("income")) {
            return budget.getIncomeCategories().get(newCategory);
        } else {
            return budget.getExpenseCategories().get(newCategory);
        }
    }

    private LocalDate changeDate() {
        System.out.println("Use format YYYY MM DD");
        int year = scanner.nextInt();
        int month = scanner.nextInt();
        int day = scanner.nextInt();
        scanner.nextLine();
        return LocalDate.of(year, month, day);
    }

    private void createSubBudget() {
        System.out.println("Which category would you like to create a sub-budget for?");
        String categoryKey = scanner.nextLine();
        System.out.println("What amount would you like to set the budget for?");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        ExpenseCategory category = budget.getExpenseCategories().get(categoryKey);
        budget.addToAllSubBudgets(new SubBudget(category, amount));
    }

    private void viewSubBudgets() {
        for (SubBudget b : budget.getAllSubBudgets()) {
            System.out.println("Sub-budget for " + b.getCategory() + " has used "
                    + b.getAmountUsedThisMonth(budget.getAllExpenseItems(), LocalDate.now()) + " of " + b.getAmount()
                    + " (" + b.getAmountLeft(budget.getAllExpenseItems(), LocalDate.now()) + " left in sub-budget)");
        }
    }

    private void changeSubBudgetAmount() {
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

    // THIS METHOD IS FOR TESTING ONLY, REMOVE IN FINAL DELIVERABLE
    private void viewAllItemsInAllCategories() {
        System.out.println("--------------------");
        System.out.println("Income Categories");
        for (Category c : budget.getIncomeCategories().values()) {
            System.out.println(c.getName());
            displayAllItemsInList(c.getAllItems());
        }
        System.out.println("--------------------");
        System.out.println("Expense Categories");
        for (Category c : budget.getExpenseCategories().values()) {
            System.out.println(c.getName());
            displayAllItemsInList(c.getAllItems());
        }
        System.out.println("--------------------");
    }
}
