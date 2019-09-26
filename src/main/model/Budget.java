package model;

import java.util.ArrayList;
import java.util.Scanner;

public class Budget {
    public ArrayList<IncomeItem> allIncomeItems = new ArrayList<IncomeItem>();
    public ArrayList<ExpenseItem> allExpenseItems = new ArrayList<ExpenseItem>();
    public ArrayList<SubBudget> allSubBudgets = new ArrayList<SubBudget>();
    public Scanner scanner = new Scanner(System.in);
    public ArrayList<String> incomeCategories = new ArrayList<String>();

    public void run() {
        while (true) {
            displayChoices();
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                createIncomeItem();
            } else if (choice == 3) {
                displayIncomeItems();
            } else if (choice == 5) {
                editIncomeItem();
            } else if (choice == 7) {
                createSubBudget();
            } else if (choice == 8) {
                viewSubBudgets();
            } else {
                break;
            }
        }
    }

    private void createPresetIncomeCategories() {
        incomeCategories.add("Salary");
        incomeCategories.add("Rentals");
        incomeCategories.add("Investments");
        incomeCategories.add("Gift");
    }

    public void displayChoices() {
        System.out.println("What would you like to do?");
        System.out.println("[1] Add income item for current month");
        System.out.println("[2] Add expense item for current month (not currently implemented)");
        System.out.println("[3] Display all income items");
        System.out.println("[4] Display all expense items (not currently implemented)");
        System.out.println("[5] Edit an income item");
        System.out.println("[6] Edit an expense item (not currently implemented)");
        System.out.println("[7] Create a monthly sub-budget to track money spent on individual categories");
        System.out.println("[8] View sub-budgets");
        System.out.println("[other] Exit");
    }

    public void createIncomeItem() {
        System.out.println("How much is your income? (Enter an amount)");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Where is your income from? (Enter a category)");
        String category = scanner.nextLine();
        System.out.println("When is your income from? (Enter a date)");
        String date = scanner.nextLine();
        System.out.println("Any additional info? (Enter a note)");
        String note = scanner.nextLine();
        allIncomeItems.add(new IncomeItem(amount, category, date, note));
        System.out.println("Income item has been successfully created.");
    }

    public void displayIncomeItems() {
        int counter = 0;
        for (IncomeItem i : allIncomeItems) {
            System.out.print("[" + counter + "] $" + i.getAmount() + " in category " + i.getCategory() + " on ");
            System.out.println(i.getDate() + " with note: " + i.getNote());
            counter++;
        }
    }

    public void editIncomeItem() {
        displayIncomeItems();
        System.out.println("Which item would you like to change? (Enter the item number)");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < allIncomeItems.size()) {
            System.out.println("What would you like to change?");
            System.out.println("[1] Amount");
            System.out.println("[2] Category");
            System.out.println("[3] Date");
            System.out.println("[4] Note");
            int fieldChoice = scanner.nextInt();
            scanner.nextLine();

            if (fieldChoice == 1 || fieldChoice == 2 || fieldChoice == 3 || fieldChoice == 4) {
                editCorrectIncomeItemField(allIncomeItems.get(choice), fieldChoice);
            }
        } else {
            System.out.println("That's an invalid item!");
        }
    }

    public void editCorrectIncomeItemField(IncomeItem item, int fieldChoice) {
        if (fieldChoice == 1) {
            System.out.println("Enter new amount:");
            double newAmount = scanner.nextDouble();
            scanner.nextLine();
            item.changeAmount(newAmount);
        } else {
            System.out.println("Enter new value:");
            String newField = scanner.nextLine();
            if (fieldChoice == 2) {
                item.changeCategory(newField);
            } else if (fieldChoice == 3) {
                item.changeDate(newField);
            } else if (fieldChoice == 4) {
                item.changeNote(newField);
            }
        }
        System.out.println("Change successful");
    }

    public void createSubBudget() {
        System.out.println("Which category would you like to create a sub-budget for?");
        String category = scanner.nextLine();
        System.out.println("What amount would you like to set the budget for?");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        allSubBudgets.add(new SubBudget(category, amount));
    }

    public void viewSubBudgets() {
        for (SubBudget b : allSubBudgets) {
            System.out.print("Sub-budget for " + b.getCategory() + " has used " + b.getAmountUsed(allIncomeItems));
            System.out.print(" of " + b.getAmount() + " (" + b.getAmountLeft(allIncomeItems) + " left in sub-budget)");
            System.out.println();
        }
    }
}
