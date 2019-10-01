package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BudgetTest {
    private Budget budget;

    @BeforeEach
    public void setup() {
        budget = new Budget();
    }

    @Test
    public void saveLoadTestWithEmptyFile() {
        ArrayList<String> saveContent = new ArrayList<String>();
        budget.save(saveContent, "loadSaveTest.txt");

        List<String> content = budget.load("loadSaveTest.txt");
        assertEquals(0, content.size());
    }

    @Test
    public void saveLoadTestWithContent() {
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
}
