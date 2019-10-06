package model;

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
    public void loadAllExistingDataTest() {
        assertTrue(budget.loadAllExistingData());
    }

    @Test
    public void saveAllExistingDataTest() {
        assertTrue(budget.saveAllExistingData());
    }

    @Test
    public void parseItemFilesTest() throws IOException {
        ArrayList<Item> parsed = budget.parseItemFiles(budget.load("incomeItemsTest.txt"));
        assertEquals(3, parsed.size());

        assertEquals(500.99, parsed.get(0).getAmount());
        assertEquals("Salary", parsed.get(0).getCategory());
        assertEquals(LocalDate.of(2019, 10, 6), parsed.get(0).getDate());
        assertEquals("hello my old friend", parsed.get(0).getNote());

        assertEquals(25678, parsed.get(1).getAmount());
        assertEquals("Investments", parsed.get(1).getCategory());
        assertEquals(LocalDate.of(2019, 10, 10), parsed.get(1).getDate());
        assertEquals("from my investment into Apple", parsed.get(1).getNote());

        assertEquals(5.99, parsed.get(2).getAmount());
        assertEquals("Gift", parsed.get(2).getCategory());
        assertEquals(LocalDate.of(2019, 10, 31), parsed.get(2).getDate());
        assertEquals("birthday gift from Serena", parsed.get(2).getNote());
    }

    @Test
    public void parseSubBudgetFileTest() throws IOException {
        ArrayList<SubBudget> parsed = budget.parseSubBudgetFile(budget.load("subBudgetsTest.txt"));
        assertEquals(4, parsed.size());

        assertEquals("Food", parsed.get(0).getCategory());
        assertEquals(360.78, parsed.get(0).getAmount());

        assertEquals("Transportation", parsed.get(1).getCategory());
        assertEquals(56.23, parsed.get(1).getAmount());

        assertEquals("Savings", parsed.get(2).getCategory());
        assertEquals(450.1, parsed.get(2).getAmount());

        assertEquals("Gifts", parsed.get(3).getCategory());
        assertEquals(10.0, parsed.get(3).getAmount());
    }

    @Test
    public void parseItemsForSaveTest() {
        ArrayList<Item> items = new ArrayList<Item>();
        items.add(new IncomeItem(100.0, "Salary", LocalDate.of(2019, 10, 06), "hello test"));
        items.add(new IncomeItem(1789.0, "Investments", LocalDate.of(2019, 10, 16), "Microsoft"));
        items.add(new IncomeItem(9.99, "Gift", LocalDate.of(2019, 10, 27), "gift from mom"));

        ArrayList<String> parsed = budget.parseItemsForSave(items);
        assertEquals(3, parsed.size());

        assertEquals("100.0,Salary,2019-10-06,hello test", parsed.get(0));
        assertEquals("1789.0,Investments,2019-10-16,Microsoft", parsed.get(1));
        assertEquals("9.99,Gift,2019-10-27,gift from mom", parsed.get(2));
    }

    @Test
    public void parseSubBudgetsForSaveTest() {
        ArrayList<SubBudget> items = new ArrayList<SubBudget>();
        items.add(new SubBudget("Transportation", 100.0));
        items.add(new SubBudget("Food", 259.1));
        items.add(new SubBudget("Savings", 19.99));

        ArrayList<String> parsed = budget.parseSubBudgetsForSave(items);
        assertEquals(3, parsed.size());

        assertEquals("Transportation,100.0", parsed.get(0));
        assertEquals("Food,259.1", parsed.get(1));
        assertEquals("Savings,19.99", parsed.get(2));
    }
}
