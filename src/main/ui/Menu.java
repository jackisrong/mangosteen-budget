package ui;

import model.ExpenseItem;
import model.IncomeItem;
import model.Item;
import model.SubBudget;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private ArrayList<IncomeItem> allIncomeItems = new ArrayList<IncomeItem>();
    private ArrayList<ExpenseItem> allExpenseItems = new ArrayList<ExpenseItem>();
    private ArrayList<SubBudget> allSubBudgets = new ArrayList<SubBudget>();
    private Scanner scanner = new Scanner(System.in);

    public Menu(ArrayList<IncomeItem> allIncomeItems, ArrayList<ExpenseItem> allExpenseItems,
                ArrayList<SubBudget> allSubBudgets) {
        this.allIncomeItems = allIncomeItems;
        this.allExpenseItems = allExpenseItems;
        this.allSubBudgets = allSubBudgets;
    }

    public void displayChoices() {
        System.out.println("What would you like to do?");
        System.out.println("[1] Add income or expense item");
        System.out.println("[2] Display all income and expense items");
        System.out.println("[3] Edit an income or expense item");
        System.out.println("[4] Create a monthly sub-budget to track money spent on individual categories");
        System.out.println("[5] View sub-budgets");
        System.out.println("[other] Save and quit");
    }

    public void displayMonthlyView() {
        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();
        double totalMonthlyIncome = 0.0;
        double totalMonthlyExpenses = 0.0;
        for (IncomeItem i : allIncomeItems) {
            if (i.getDate().getYear() == currentYear && i.getDate().getMonthValue() == currentMonth) {
                totalMonthlyIncome += i.getAmount();
            }
        }
        for (ExpenseItem e : allExpenseItems) {
            if (e.getDate().getYear() == currentYear && e.getDate().getMonthValue() == currentMonth) {
                totalMonthlyExpenses += e.getAmount();
            }
        }
        System.out.println("Total income this month: " + totalMonthlyIncome);
        System.out.println("Total expenses this month: " + totalMonthlyExpenses);
        System.out.println("Net income this month: " + (totalMonthlyIncome - totalMonthlyExpenses));
        System.out.println("--------------------");
    }

    public void createItem() {
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
        String category = scanner.nextLine();
        System.out.println("When is your " + type + " from? (Enter a date in format YYYY MM DD)");
        int year = scanner.nextInt();
        int month = scanner.nextInt();
        int day = scanner.nextInt();
        scanner.nextLine();
        LocalDate date = LocalDate.of(year, month, day);
        System.out.println("Any additional info? (Enter a note)");
        String note = scanner.nextLine();

        if (type.equals("income")) {
            createIncomeItem(amount, category, date, note);
        } else {
            createExpenseItem(amount, category, date, note);
        }
    }

    private void createIncomeItem(double amount, String category, LocalDate date, String note) {
        allIncomeItems.add(new IncomeItem(amount, category, date, note));
        System.out.println("Income item has been successfully created.");
    }

    private void createExpenseItem(double amount, String category, LocalDate date, String note) {
        allExpenseItems.add(new ExpenseItem(amount, category, date, note));
        System.out.println("Expense item has been successfully created.");
    }

    public void displayAllItems() {
        System.out.println("--------------------");
        System.out.println("Income Items");
        displayIncomeItems();
        System.out.println("--------------------");
        System.out.println("Expense Items");
        displayExpenseItems();
        System.out.println("--------------------");
    }

    private void displayIncomeItems() {
        int counter = 0;
        for (IncomeItem i : allIncomeItems) {
            System.out.print("[" + counter + "] $" + i.getAmount() + " in category " + i.getCategory() + " on ");
            System.out.println(i.getDate() + " with note: " + i.getNote());
            counter++;
        }
    }

    private void displayExpenseItems() {
        int counter = 0;
        for (ExpenseItem i : allExpenseItems) {
            System.out.print("[" + counter + "] $" + i.getAmount() + " in category " + i.getCategory() + " on ");
            System.out.println(i.getDate() + " with note: " + i.getNote());
            counter++;
        }
    }

    public void editItem() {
        System.out.println("Which item would you like to edit?");
        System.out.println("[1] Income item");
        System.out.println("[2] Expense item");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice == 1) {
            editIncomeItem();
        } else {
            displayExpenseItems();
            createItemGetValues("expense");
        }
    }

    private void editIncomeItem() {
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

            if (fieldChoice >= 1 && fieldChoice <= 4) {
                editCorrectIncomeItemField(allIncomeItems.get(choice), fieldChoice);
            }
        } else {
            System.out.println("That's an invalid item!");
        }
    }

    private void editCorrectIncomeItemField(IncomeItem item, int fieldChoice) {
        System.out.println("Enter new value:");
        if (fieldChoice == 1) {
            double newAmount = scanner.nextDouble();
            scanner.nextLine();
            item.changeAmount(newAmount);
        } else if (fieldChoice == 2) {
            String newCategory = scanner.nextLine();
            item.changeCategory(newCategory);
        } else if (fieldChoice == 3) {
            item.changeDate(changeDate());
        } else if (fieldChoice == 4) {
            String newNote = scanner.nextLine();
            item.changeNote(newNote);
        }
        System.out.println("Change successful");
    }

    private LocalDate changeDate() {
        System.out.println("Use format YYYY MM DD");
        int year = scanner.nextInt();
        int month = scanner.nextInt();
        int day = scanner.nextInt();
        scanner.nextLine();
        return LocalDate.of(year, month, day);
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
