package model;

import ui.Menu;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Budget implements Loadable, Saveable {
    private ArrayList<IncomeItem> allIncomeItems = new ArrayList<IncomeItem>();
    private ArrayList<ExpenseItem> allExpenseItems = new ArrayList<ExpenseItem>();
    private ArrayList<SubBudget> allSubBudgets = new ArrayList<SubBudget>();
    private Scanner scanner = new Scanner(System.in);
    private Menu menu = new Menu(allIncomeItems, allExpenseItems, allSubBudgets);

    public void run() {
        loadAllExistingData();
        menu.displayMonthlyView();
        boolean keepRunning = true;
        while (keepRunning) {
            menu.displayChoices();
            int choice = scanner.nextInt();
            scanner.nextLine();
            keepRunning = runAppropriateFunctionBasedOnChoice(choice);
        }
    }

    private boolean runAppropriateFunctionBasedOnChoice(int choice) {
        if (choice == 1) {
            menu.createItem();
        } else if (choice == 2) {
            menu.displayAllItems();
        } else if (choice == 3) {
            menu.editItem();
        } else if (choice == 4) {
            menu.createSubBudget();
        } else if (choice == 5) {
            menu.viewSubBudgets();
        } else {
            saveAllExistingData();
            return false;
        }
        return true;
    }

    @Override
    // Code used from example load function project on EdX deliverable 4 page
    public List<String> load(String file) {
        List<String> lines = null;

        try {
            lines = Files.readAllLines(Paths.get("./data/" + file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    private void loadAllExistingData() {
        System.out.println("Attempting to load previous session...");
        allIncomeItems = parseIncomeExpenseItemsFiles(load("incomeItems.txt"));
        load("expenseItems.txt");
        load("subBudgets.txt");
        System.out.println("Loading completed.");
    }

    private ArrayList parseIncomeExpenseItemsFiles(List<String> content) {
        ArrayList parsedData = new ArrayList();
        for (int i = 0; i < content.size(); i++) {
            String rawLine = content.get(i);
            String[] splitLine = rawLine.split(",");
            double amount = Double.parseDouble(splitLine[0]);
            String category = splitLine[1];
            LocalDate date = LocalDate.parse(splitLine[2]);
            String note = splitLine[3];
            parsedData.add(new IncomeItem(amount, category, date, note));
        }

        return parsedData;
    }

    @Override
    // Code used from example save function project on EdX deliverable 4 page
    public void save(ArrayList<String> content, String file) {
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
    }

    private void saveAllExistingData() {
        System.out.println("Attempting to save this session...");
        save(parseIncomeItemsForSave(), "incomeItems.txt");
        save(null, "expenseItems.txt");
        save(null, "subBudgets.txt");
        System.out.println("Saving completed.");
    }

    private ArrayList<String> parseIncomeItemsForSave() {
        ArrayList<String> parsedData = new ArrayList<String>();
        for (IncomeItem i : allIncomeItems) {
            parsedData.add(i.getAmount() + "," + i.getCategory() + "," + i.getDate() + "," + i.getNote());
        }
        return parsedData;
    }
}
