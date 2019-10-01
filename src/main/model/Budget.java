package model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Budget implements Loadable, Saveable {
    public ArrayList<IncomeItem> allIncomeItems = new ArrayList<IncomeItem>();
    public ArrayList<ExpenseItem> allExpenseItems = new ArrayList<ExpenseItem>();
    // TODO: Change this
    public ArrayList<Item> allItems = new ArrayList<Item>();
    public ArrayList<SubBudget> allSubBudgets = new ArrayList<SubBudget>();
    public Scanner scanner = new Scanner(System.in);
    public ArrayList<String> incomeCategories = new ArrayList<String>();

    public void run() {
        load("income.txt");
        boolean keepRunning = true;
        while (keepRunning) {
            displayChoices();
            int choice = scanner.nextInt();
            scanner.nextLine();

            keepRunning = runAppropriateFunctionBasedOnChoice(choice);
        }
    }

    private boolean runAppropriateFunctionBasedOnChoice(int choice) {
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
            save(null, "income.txt");
            return false;
        }
        return true;
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
        System.out.println("[other] Save and quit");
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
        // TODO: Change this
        Item i = new IncomeItem(amount, category, date, note);
        allItems.add(i);
        allIncomeItems.add((IncomeItem) i);
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

    @Override
    public List<String> load(String file) {
        System.out.println("Attempting to load previous session...");
        List<String> lines = null;

        try {
            lines = Files.readAllLines(Paths.get("./data/" + file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Loading completed.");

        return lines;
    }

    @Override
    public void save(ArrayList<String> content, String file) {
        System.out.println("Attempting to save this session...");

        PrintWriter writer = null;
        try {
            writer = new PrintWriter("./data/" + file, "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        for (String i : content) {
            writer.println(i);
        }
        writer.close();

        System.out.println("Saving completed.");
    }
}
