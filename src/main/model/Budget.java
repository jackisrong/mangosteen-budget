package model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Budget implements Loadable, Saveable {
    private ArrayList<Item> allIncomeItems = new ArrayList<Item>();
    private ArrayList<Item> allExpenseItems = new ArrayList<Item>();
    private ArrayList<SubBudget> allSubBudgets = new ArrayList<SubBudget>();

    public ArrayList<Item> getAllIncomeItems() {
        return allIncomeItems;
    }

    public ArrayList<Item> getAllExpenseItems() {
        return allExpenseItems;
    }

    public ArrayList<SubBudget> getAllSubBudgets() {
        return allSubBudgets;
    }

    public void addToAllIncomeItems(Item i) {
        allIncomeItems.add(i);
    }

    public void addToAllExpenseItems(Item i) {
        allExpenseItems.add(i);
    }

    public void addToAllSubBudgets(SubBudget s) {
        allSubBudgets.add(s);
    }

    @Override
    // Code used from example load function project on EdX deliverable 4 page
    public List<String> load(String file) throws IOException {
        List<String> lines = null;
        return Files.readAllLines(Paths.get("./data/" + file));
    }

    public boolean loadAllExistingData() {
        System.out.println("Attempting to load previous session...");
        try {
            allIncomeItems = parseItemFiles(load("incomeItems.txt"));
            allExpenseItems = parseItemFiles(load("expenseItems.txt"));
            allSubBudgets = parseSubBudgetFile(load("subBudgets.txt"));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        System.out.println("Loading completed.");
        return true;
    }

    public ArrayList<Item> parseItemFiles(List<String> content) {
        ArrayList<Item> parsedData = new ArrayList<Item>();
        for (String rawLine : content) {
            String[] splitLine = rawLine.split(",");
            double amount = Double.parseDouble(splitLine[0]);
            String category = splitLine[1];
            LocalDate date = LocalDate.parse(splitLine[2]);
            String note = splitLine[3];
            parsedData.add(new IncomeItem(amount, category, date, note));
        }

        return parsedData;
    }

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

    @Override
    // Code used from example save function project on EdX deliverable 4 page
    public void save(ArrayList<String> content, String file)
            throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("./data/" + file, "UTF-8");

        for (String i : content) {
            writer.println(i);
        }
        writer.close();
    }

    public boolean saveAllExistingData() {
        System.out.println("Attempting to save this session...");
        try {
            save(parseItemsForSave(allIncomeItems), "incomeItems.txt");
            save(parseItemsForSave(allExpenseItems), "expenseItems.txt");
            save(parseSubBudgetsForSave(allSubBudgets), "subBudgets.txt");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
        System.out.println("Saving completed.");
        return true;
    }

    public ArrayList<String> parseItemsForSave(ArrayList<Item> items) {
        ArrayList<String> parsedData = new ArrayList<String>();
        for (Item i : items) {
            parsedData.add(i.getAmount() + "," + i.getCategory() + "," + i.getDate() + "," + i.getNote());
        }
        return parsedData;
    }

    public ArrayList<String> parseSubBudgetsForSave(ArrayList<SubBudget> items) {
        ArrayList<String> parsedData = new ArrayList<String>();
        for (SubBudget i : items) {
            parsedData.add(i.getCategory() + "," + i.getAmount());
        }
        return parsedData;
    }
}
