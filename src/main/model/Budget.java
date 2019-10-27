package model;

import exceptions.NegativeMonetaryAmountException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Budget implements Loadable, Saveable {
    private ArrayList<Item> allIncomeItems = new ArrayList<Item>();
    private ArrayList<Item> allExpenseItems = new ArrayList<Item>();
    private ArrayList<SubBudget> allSubBudgets = new ArrayList<SubBudget>();
    private Map<String, IncomeCategory> incomeCategories = new HashMap<>();
    private Map<String, ExpenseCategory> expenseCategories = new HashMap<>();

    // EFFECTS: returns ArrayList allIncomeItems
    public ArrayList<Item> getAllIncomeItems() {
        return allIncomeItems;
    }

    // EFFECTS: returns ArrayList allExpenseItems
    public ArrayList<Item> getAllExpenseItems() {
        return allExpenseItems;
    }

    // EFFECTS: returns ArrayList allSubBudgets
    public ArrayList<SubBudget> getAllSubBudgets() {
        return allSubBudgets;
    }

    // MODIFIES: this
    // EFFECTS: adds Item i into allIncomeItems
    public void addToAllIncomeItems(Item i) {
        allIncomeItems.add(i);
    }

    // MODIFIES: this
    // EFFECTS: adds Item i into allExpenseItems
    public void addToAllExpenseItems(Item i) {
        allExpenseItems.add(i);
    }

    // MODIFIES: this
    // EFFECTS: adds SubBudget s into allSubBudgets
    public void addToAllSubBudgets(SubBudget s) {
        allSubBudgets.add(s);
    }

    @Override
    // Code used from example load function project on EdX deliverable 4 page
    // EFFECTS: loads file with name file within data directory and returns line by line contents as ArrayList
    public List<String> load(String file) {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get("./data/" + file));
        } catch (IOException e) {
            System.out.println("Failed to load data from " + file);
        }
        return lines;
    }

    // EFFECTS: loads all relevant data files, returns true when completed
    public boolean loadAllExistingData() {
        System.out.println("Attempting to load previous session...");

        allIncomeItems = parseItemFiles(load("incomeItems.txt"), "income");
        allExpenseItems = parseItemFiles(load("expenseItems.txt"), "expense");
        allSubBudgets = parseSubBudgetFile(load("subBudgets.txt"));
        parseCategoryFile(load("incomeCategories.txt"), "income");
        parseCategoryFile(load("expenseCategories.txt"), "expense");

        System.out.println("Loading completed.");
        return true;
    }

    // EFFECTS: reads content and parses data into new items, adding them into parsedData ArrayList and returns it
    public ArrayList<Item> parseItemFiles(List<String> content, String type) {
        ArrayList<Item> parsedData = new ArrayList<Item>();
        for (String rawLine : content) {
            String[] splitLine = rawLine.split(",", 4);
            double amount = Double.parseDouble(splitLine[0]);
            String category = splitLine[1];
            LocalDate date = LocalDate.parse(splitLine[2]);
            String note = splitLine[3];
            try {
                if (type.equals("income")) {
                    parsedData.add(new IncomeItem(amount, category, date, note));
                } else if (type.equals("expense")) {
                    parsedData.add(new ExpenseItem(amount, category, date, note));
                }
            } catch (NegativeMonetaryAmountException e) {
                System.out.println("Error in parsing saved date. Monetary amount of an item is negative.");
            }
        }

        return parsedData;
    }

    // EFFECTS: reads content and parses data into new sub-budgets, adding them into parsedData ArrayList and returns it
    public ArrayList<SubBudget> parseSubBudgetFile(List<String> content) {
        ArrayList<SubBudget> parsedData = new ArrayList<SubBudget>();
        for (String rawLine : content) {
            String[] splitLine = rawLine.split(",");
            String category = splitLine[0];
            double amount = Double.parseDouble(splitLine[1]);
            parsedData.add(new SubBudget(category, amount));
        }

        return parsedData;
    }

    // EFFECTS: reads content and parses data into new categories and adds it with key into appropriate hash-map
    public void parseCategoryFile(List<String> content, String type) {
        for (String line : content) {
            if (type.equals("income")) {
                incomeCategories.put(line, new IncomeCategory());
            } else if (type.equals("expense")) {
                expenseCategories.put(line, new ExpenseCategory());
            }
        }
    }

    @Override
    // Code used from example save function project on EdX deliverable 4 page
    // EFFECTS: saves content into file with name file in data directory
    public void save(ArrayList<String> content, String file) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("./data/" + file, "UTF-8");
        } catch (FileNotFoundException e) {
            System.out.println("Failed to save to " + file + ". File does not exist.");
        } catch (UnsupportedEncodingException e) {
            System.out.println("Failed to save to " + file + ". Unsupported encoding.");
        }

        for (String i : content) {
            writer.println(i);
        }
        writer.close();
    }

    // EFFECTS: save all necessary data files
    public boolean saveAllExistingData() {
        System.out.println("Attempting to save this session...");

        save(parseItemsForSave(allIncomeItems), "incomeItems.txt");
        save(parseItemsForSave(allExpenseItems), "expenseItems.txt");
        save(parseSubBudgetsForSave(allSubBudgets), "subBudgets.txt");

        System.out.println("Saving completed.");
        return true;
    }

    // EFFECTS: parses ArrayList items into correct format for save
    public ArrayList<String> parseItemsForSave(ArrayList<Item> items) {
        ArrayList<String> parsedData = new ArrayList<String>();
        for (Item i : items) {
            parsedData.add(i.getAmount() + "," + i.getCategory() + "," + i.getDate() + "," + i.getNote());
        }
        return parsedData;
    }

    // EFFECTS: parses ArrayList subBudgets into correct format for save
    public ArrayList<String> parseSubBudgetsForSave(ArrayList<SubBudget> subBudgets) {
        ArrayList<String> parsedData = new ArrayList<String>();
        for (SubBudget i : subBudgets) {
            parsedData.add(i.getCategory() + "," + i.getAmount());
        }
        return parsedData;
    }
}
