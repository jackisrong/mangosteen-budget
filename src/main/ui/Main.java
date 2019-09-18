package ui;

import model.IncomeItem;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static ArrayList<IncomeItem> allIncomeItems = new ArrayList<IncomeItem>();
    public static Scanner scanner = new Scanner(System.in);

    public static void displayChoices() {
        System.out.println("What would you like to do?");
        System.out.println("[1] Add income item for current month");
        System.out.println("[2] Add expense item for current month");
        System.out.println("[3] Display all income items");
        System.out.println("[4] Display all expense items");
        System.out.println("[5] Change income item amount");
        System.out.println("[other] Exit");
    }

    public static void createIncomeItem() {
        System.out.println("How much is your income? (Enter an amount)");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Where is your income from? (Enter a category)");
        String category = scanner.nextLine();
        System.out.println("When is your income from? (Enter a date)");
        String date = scanner.nextLine();
        System.out.println("Any additional info? (Enter a note)");
        String note = scanner.nextLine();
        IncomeItem incomeItem = new IncomeItem(amount, category, date, note);
        allIncomeItems.add(incomeItem);
        System.out.println("Income item has been successfully created.");
    }

    public static void displayIncomeItems() {
        int counter = 0;
        for (IncomeItem i : allIncomeItems) {
            System.out.println("[" + counter + "] $" + i.getAmount() + " in category " + i.getCategory());
            counter++;
        }
    }

    public static void changeIncomeItemAmount() {
        displayIncomeItems();
        System.out.println("Which item would you like to change? (Enter the item number)");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < allIncomeItems.size()) {
            System.out.println("What is the amount you want to change it to?");
            double newAmount = scanner.nextDouble();
            scanner.nextLine();
            allIncomeItems.get(choice).changeIncomeAmount(newAmount);
            System.out.println("Change successful");
        } else {
            System.out.println("That's an invalid item number!");
        }
    }

    public static void main(String[] args) {
        while (true) {
            displayChoices();
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                createIncomeItem();
            } else if (choice == 2 || choice == 4) {
                System.out.println("Feature coming soon!");
            } else if (choice == 3) {
                displayIncomeItems();
            } else if (choice == 5) {
                changeIncomeItemAmount();
            } else {
                break;
            }
        }
    }
}
