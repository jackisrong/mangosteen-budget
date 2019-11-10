package ui;

import exceptions.NegativeMonetaryAmountException;
import model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;

public class ItemMenu extends Observable {
    private Scanner scanner = new Scanner(System.in);
    private Budget budget;

    public ItemMenu(Budget budget) {
        this.budget = budget;
        // ADDING OBSERVER FOR DELIVERABLE 10 OBSERVER PATTERN
        addObserver(new ConsolePrinter());
    }

    public void createItemPrompt() {
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
            createItem(amount, budget.getIncomeCategories().get(categoryKey), date, note, type);
        } else {
            createItem(amount, budget.getExpenseCategories().get(categoryKey), date, note, type);
        }
    }

    private void createItem(double amount, Category category, LocalDate date, String note, String type) {
        try {
            Item i;
            if (type.equals("income")) {
                i = new IncomeItem(amount, category, date, note);
                budget.addToAllIncomeItems(i);
            } else {
                i = new ExpenseItem(amount, category, date, note);
                budget.addToAllExpenseItems(i);
            }
            // SETTING TRIGGER FOR DELIVERABLE 10 OBSERVER PATTERN
            setChanged();
            notifyObservers(i);
        } catch (NegativeMonetaryAmountException e) {
            System.out.println("Invalid monetary amount! Your amount is negative!");
        } finally {
            System.out.println("Item creation process has finished.");
        }
    }

    public void displayAllItems() {
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

    public void editItemChooseType() {
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

    // THIS METHOD IS FOR TESTING ONLY, REMOVE IN FINAL DELIVERABLE
    public void viewAllItemsInAllCategories() {
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
