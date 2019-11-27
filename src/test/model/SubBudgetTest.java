package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubBudgetTest {
    private Category c;
    private SubBudget subBudget;

    @BeforeEach
    public void setup() {
        c = new ExpenseCategory("Food");
        subBudget = new SubBudget(c, 500);
    }

    @Test
    public void getCategoryTest() {
        assertEquals(new ExpenseCategory("Food"), subBudget.getCategory());
    }

    @Test
    public void getCategoryNameTest() {
        assertEquals("Food", subBudget.getCategoryName());
    }

    @Test
    public void getAmountTest() {
        assertEquals(500, subBudget.getAmount());
    }

    @Test
    public void getAmountUsedTest() {
        Category gift = new ExpenseCategory("Gift");
        assertEquals(0, subBudget.getAmountUsedThisMonth());

        new ExpenseItem(0.51, gift, LocalDate.now(), "");
        assertEquals(0, subBudget.getAmountUsedThisMonth());

        new ExpenseItem(109.81, c, LocalDate.now(), "");
        assertEquals(109.81, subBudget.getAmountUsedThisMonth());

        new ExpenseItem(203.09, gift, LocalDate.now(), "");
        assertEquals(109.81, subBudget.getAmountUsedThisMonth());

        new ExpenseItem(6.98, c, LocalDate.now(), "");
        assertEquals(109.81 + 6.98, subBudget.getAmountUsedThisMonth());
    }

    @Test
    public void getAmountLeftTest() {
        Category gift = new ExpenseCategory("Gift");
        assertEquals(500, subBudget.getAmountLeft());

        ArrayList<Item> expenses = new ArrayList<Item>();
        expenses.add(new ExpenseItem(0.51, gift, LocalDate.now(), ""));
        assertEquals(500, subBudget.getAmountLeft());

        expenses.add(new ExpenseItem(109.81, c, LocalDate.now(), ""));
        assertEquals(500 - 109.81, subBudget.getAmountLeft());

        expenses.add(new ExpenseItem(203.09, gift, LocalDate.now(), ""));
        assertEquals(500 - 109.81, subBudget.getAmountLeft());

        expenses.add(new ExpenseItem(6.98, c, LocalDate.now(), ""));
        assertEquals(500 - 109.81 - 6.98, subBudget.getAmountLeft());
    }

    @Test
    public void editTest() {
        subBudget.edit(new ExpenseCategory("Food"), 300.51);
        assertEquals("Food", subBudget.getCategoryName());
        assertEquals(300.51, subBudget.getAmount());
    }
}
