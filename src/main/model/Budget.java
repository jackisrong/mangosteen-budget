package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
    private Map<String, Category> incomeCategories = new HashMap<String, Category>();
    private Map<String, Category> expenseCategories = new HashMap<String, Category>();

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

    // EFFECTS: returns HashMap of income categories
    public Map<String, Category> getIncomeCategories() {
        return incomeCategories;
    }

    // EFFECTS: returns HashMap of expense categories
    public Map<String, Category> getExpenseCategories() {
        return expenseCategories;
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

        parseCategoryFile(load("incomeCategories.txt"), "income");
        parseCategoryFile(load("expenseCategories.txt"), "expense");

        allIncomeItems = parseItemFiles(load("incomeItems.txt"), "income");
        allExpenseItems = parseItemFiles(load("expenseItems.txt"), "expense");
        allSubBudgets = parseSubBudgetFile(load("subBudgets.txt"));

        System.out.println("Session data loading completed.");
        return true;
    }

    // EFFECTS: reads content and parses data into new items, adding them into parsedData ArrayList and returns it
    public ArrayList<Item> parseItemFiles(List<String> content, String type) {
        ArrayList<Item> parsedData = new ArrayList<Item>();
        for (String rawLine : content) {
            String[] splitLine = rawLine.split(",", 4);
            double amount = Double.parseDouble(splitLine[0]);
            String categoryKey = splitLine[1];
            LocalDate date = LocalDate.parse(splitLine[2]);
            String note = splitLine[3];
            if (type.equals("income")) {
                parsedData.add(new IncomeItem(amount, incomeCategories.get(categoryKey), date, note));
            } else {
                parsedData.add(new ExpenseItem(amount, expenseCategories.get(categoryKey), date, note));
            }
        }

        return parsedData;
    }

    // EFFECTS: reads content and parses data into new sub-budgets, adding them into parsedData ArrayList and returns it
    public ArrayList<SubBudget> parseSubBudgetFile(List<String> content) {
        ArrayList<SubBudget> parsedData = new ArrayList<SubBudget>();
        for (String rawLine : content) {
            String[] splitLine = rawLine.split(",", 2);
            String categoryKey = splitLine[0];
            double amount = Double.parseDouble(splitLine[1]);
            Category category = expenseCategories.get(categoryKey);
            parsedData.add(new SubBudget(category, amount));
        }

        return parsedData;
    }

    // EFFECTS: reads content and parses data into new categories and adds it with key into appropriate hash-map
    public void parseCategoryFile(List<String> content, String type) {
        for (String line : content) {
            if (type.equals("income")) {
                incomeCategories.put(line, new IncomeCategory(line));
            } else {
                expenseCategories.put(line, new ExpenseCategory(line));
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

        System.out.println("Session data saving completed.");
        return true;
    }

    // EFFECTS: parses ArrayList items into correct format for save
    public ArrayList<String> parseItemsForSave(ArrayList<Item> items) {
        ArrayList<String> parsedData = new ArrayList<String>();
        for (Item i : items) {
            parsedData.add(i.getAmount() + "," + i.getCategoryName() + "," + i.getDate() + "," + i.getNote());
        }
        return parsedData;
    }

    // EFFECTS: parses ArrayList subBudgets into correct format for save
    public ArrayList<String> parseSubBudgetsForSave(ArrayList<SubBudget> subBudgets) {
        ArrayList<String> parsedData = new ArrayList<String>();
        for (SubBudget i : subBudgets) {
            parsedData.add(i.getCategoryName() + "," + i.getAmount());
        }
        return parsedData;
    }
}
