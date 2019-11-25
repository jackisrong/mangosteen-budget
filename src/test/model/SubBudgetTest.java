package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubBudgetTest {
    private SubBudget subBudget;

    @BeforeEach
    public void setup() {
        subBudget = new SubBudget(new ExpenseCategory("Food"), 500);
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
        assertEquals(0, subBudget.getAmountUsedThisMonth(null));

        ArrayList<Item> expenses = new ArrayList<Item>();
        expenses.add(new ExpenseItem(0.51, new ExpenseCategory("Gift"), LocalDate.now(), ""));
        assertEquals(0, subBudget.getAmountUsedThisMonth(expenses));

        expenses.add(new ExpenseItem(109.81, new ExpenseCategory("Food"), LocalDate.now(), ""));
        assertEquals(109.81, subBudget.getAmountUsedThisMonth(expenses));

        expenses.add(new ExpenseItem(203.09, new ExpenseCategory("Transportation"), LocalDate.now(), ""));
        assertEquals(109.81, subBudget.getAmountUsedThisMonth(expenses));

        expenses.add(new ExpenseItem(6.98, new ExpenseCategory("Food"), LocalDate.now(), ""));
        assertEquals(109.81 + 6.98, subBudget.getAmountUsedThisMonth(expenses));
    }

    @Test
    public void getAmountLeftTest() {
        assertEquals(500, subBudget.getAmountLeft(null));

        ArrayList<Item> expenses = new ArrayList<Item>();
        expenses.add(new ExpenseItem(0.51, new ExpenseCategory("Gift"), LocalDate.now(), ""));
        assertEquals(500, subBudget.getAmountLeft(expenses));

        expenses.add(new ExpenseItem(109.81, new ExpenseCategory("Food"), LocalDate.now(), ""));
        assertEquals(500 - 109.81, subBudget.getAmountLeft(expenses));

        expenses.add(new ExpenseItem(203.09, new ExpenseCategory("Transportation"), LocalDate.now(), ""));
        assertEquals(500 - 109.81, subBudget.getAmountLeft(expenses));

        expenses.add(new ExpenseItem(6.98, new ExpenseCategory("Food"), LocalDate.now(), ""));
        assertEquals(500 - 109.81 - 6.98, subBudget.getAmountLeft(expenses));
    }

    @Test
    public void editTest() {
        subBudget.edit(new ExpenseCategory("Food"), 300.51);
        assertEquals("Food", subBudget.getCategoryName());
        assertEquals(300.51, subBudget.getAmount());
    }
}
