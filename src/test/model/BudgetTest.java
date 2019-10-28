package model;

import exceptions.NegativeMonetaryAmountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BudgetTest {
    private Budget budget;

    @BeforeEach
    public void setup() {
        budget = new Budget();
        budget.parseCategoryFile(budget.load("incomeCategories.txt"), "income");
        budget.parseCategoryFile(budget.load("expenseCategories.txt"), "expense");
    }

    @Test
    public void getAddToAllIncomeItemsTest() throws NegativeMonetaryAmountException {
        assertEquals(0, budget.getAllIncomeItems().size());
        budget.addToAllIncomeItems(new IncomeItem(500, new IncomeCategory("Salary"), LocalDate.of(2019, 10, 9), ""));
        budget.addToAllIncomeItems(new IncomeItem(5.99, new IncomeCategory("Refund"), LocalDate.of(2019, 10, 12), ""));
        budget.addToAllIncomeItems(new IncomeItem(200, new IncomeCategory("Investments"), LocalDate.of(2019, 10, 25), ""));
        budget.addToAllIncomeItems(new IncomeItem(25, new IncomeCategory("Gift"), LocalDate.of(2019, 10, 30), ""));
        assertEquals(4, budget.getAllIncomeItems().size());
    }

    @Test
    public void getAddToAllExpenseItemsTest() throws NegativeMonetaryAmountException {
        assertEquals(0, budget.getAllExpenseItems().size());
        budget.addToAllExpenseItems(new ExpenseItem(76.78, new ExpenseCategory("Food"), LocalDate.of(2019, 10, 9), ""));
        budget.addToAllExpenseItems(new ExpenseItem(30, new ExpenseCategory("Gift"), LocalDate.of(2019, 10, 12), ""));
        budget.addToAllExpenseItems(new ExpenseItem(10.99, new ExpenseCategory("Entertainment"), LocalDate.of(2019, 10, 15), ""));
        assertEquals(3, budget.getAllExpenseItems().size());
    }

    @Test
    public void getAddToAllSubBudgetsTest() {
        assertEquals(0, budget.getAllSubBudgets().size());
        budget.addToAllSubBudgets(new SubBudget(new ExpenseCategory("Food"), 300));
        budget.addToAllSubBudgets(new SubBudget(new ExpenseCategory("Entertainment"), 100));
        assertEquals(2, budget.getAllSubBudgets().size());
    }

    @Test
    public void saveLoadTestWithEmptyFile() throws IOException {
        ArrayList<String> saveContent = new ArrayList<String>();
        budget.save(saveContent, "loadSaveTest.txt");

        List<String> content = budget.load("loadSaveTest.txt");
        assertEquals(0, content.size());
    }

    @Test
    public void saveLoadTestWithContent() throws IOException {
        ArrayList<String> content = new ArrayList<String>();
        content.add("hello");
        content.add("this is testing the load and save functions");
        content.add("given f(x), find the indefinite integral of f(x)");
        content.add("i like big golden retrievers");
        content.add("platypus");
        content.add("end of file");
        budget.save(content, "loadSaveTest.txt");

        List<String> loadedContent = budget.load("loadSaveTest.txt");
        assertEquals(6, loadedContent.size());
        assertEquals("hello", loadedContent.get(0));
        assertEquals("this is testing the load and save functions", loadedContent.get(1));
        assertEquals("given f(x), find the indefinite integral of f(x)", loadedContent.get(2));
        assertEquals("i like big golden retrievers", loadedContent.get(3));
        assertEquals("platypus", loadedContent.get(4));
        assertEquals("end of file", loadedContent.get(5));
    }

    @Test
    public void loadSaveAllExistingDataTest() {
        assertTrue(budget.loadAllExistingData());
        assertTrue(budget.saveAllExistingData());
    }

    @Test
    public void parseItemFilesTest() throws IOException {
        ArrayList<Item> parsed = budget.parseItemFiles(budget.load("incomeItemsTest.txt"), "income");
        assertEquals(3, parsed.size());

        assertEquals(500.99, parsed.get(0).getAmount());
        assertEquals("Salary", parsed.get(0).getCategoryName());
        assertEquals(LocalDate.of(2019, 10, 6), parsed.get(0).getDate());
        assertEquals("hello my old friend", parsed.get(0).getNote());

        assertEquals(25678, parsed.get(1).getAmount());
        assertEquals("Investments", parsed.get(1).getCategoryName());
        assertEquals(LocalDate.of(2019, 10, 10), parsed.get(1).getDate());
        assertEquals("from my investment into Apple", parsed.get(1).getNote());

        assertEquals(5.99, parsed.get(2).getAmount());
        assertEquals("Gift", parsed.get(2).getCategoryName());
        assertEquals(LocalDate.of(2019, 10, 31), parsed.get(2).getDate());
        assertEquals("birthday gift from Serena", parsed.get(2).getNote());
    }

    @Test
    public void parseSubBudgetFileTest() throws IOException {
        ArrayList<SubBudget> parsed = budget.parseSubBudgetFile(budget.load("subBudgetsTest.txt"));
        assertEquals(4, parsed.size());

        assertEquals("Food", parsed.get(0).getCategoryName());
        assertEquals(360.78, parsed.get(0).getAmount());

        assertEquals("Transportation", parsed.get(1).getCategoryName());
        assertEquals(56.23, parsed.get(1).getAmount());

        assertEquals("Savings", parsed.get(2).getCategoryName());
        assertEquals(450.1, parsed.get(2).getAmount());

        assertEquals("Gift", parsed.get(3).getCategoryName());
        assertEquals(10.0, parsed.get(3).getAmount());
    }

    @Test
    public void parseItemsForSaveTest() throws NegativeMonetaryAmountException {
        ArrayList<Item> items = new ArrayList<Item>();
        items.add(new IncomeItem(100.0, new IncomeCategory("Salary"), LocalDate.of(2019, 10, 6), "hello test"));
        items.add(new IncomeItem(1789.0, new IncomeCategory("Investments"), LocalDate.of(2019, 10, 16), "Microsoft"));
        items.add(new IncomeItem(9.99, new IncomeCategory("Gift"), LocalDate.of(2019, 10, 27), "gift from mom"));

        ArrayList<String> parsed = budget.parseItemsForSave(items);
        assertEquals(3, parsed.size());

        assertEquals("100.0,Salary,2019-10-06,hello test", parsed.get(0));
        assertEquals("1789.0,Investments,2019-10-16,Microsoft", parsed.get(1));
        assertEquals("9.99,Gift,2019-10-27,gift from mom", parsed.get(2));
    }

    @Test
    public void parseSubBudgetsForSaveTest() {
        ArrayList<SubBudget> items = new ArrayList<SubBudget>();
        items.add(new SubBudget(new ExpenseCategory("Transportation"), 100.0));
        items.add(new SubBudget(new ExpenseCategory("Food"), 259.1));
        items.add(new SubBudget(new ExpenseCategory("Savings"), 19.99));

        ArrayList<String> parsed = budget.parseSubBudgetsForSave(items);
        assertEquals(3, parsed.size());

        assertEquals("Transportation,100.0", parsed.get(0));
        assertEquals("Food,259.1", parsed.get(1));
        assertEquals("Savings,19.99", parsed.get(2));
    }
}
