package model;

import exceptions.NegativeMonetaryAmountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubBudgetTest {
    private SubBudget subBudget;

    @BeforeEach
    public void setup() {
        subBudget = new SubBudget(new ExpenseCategory("Food"), 500, "Groceries");
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
    public void getNoteTest() {
        assertEquals("Groceries", subBudget.getNote());
    }

    /*
    @Test
    public void getAmountUsedTest() throws NegativeMonetaryAmountException {
        assertEquals(0, subBudget.getAmountUsedThisMonth(null, LocalDate.of(2019, 9, 1)));

        ArrayList<Item> expenses = new ArrayList<Item>();
        expenses.add(new ExpenseItem(0.51, new ExpenseCategory("Gift"), LocalDate.of(2019, 9, 20), ""));
        assertEquals(0, subBudget.getAmountUsedThisMonth(expenses, LocalDate.of(2019, 9, 1)));

        expenses.add(new ExpenseItem(109.81, new ExpenseCategory("Food"), LocalDate.of(2019, 9, 23), ""));
        assertEquals(109.81, subBudget.getAmountUsedThisMonth(expenses, LocalDate.of(2019, 9, 1)));

        expenses.add(new ExpenseItem(203.09, new ExpenseCategory("Transportation"), LocalDate.of(2019, 9, 17), ""));
        assertEquals(109.81, subBudget.getAmountUsedThisMonth(expenses, LocalDate.of(2019, 9, 1)));

        expenses.add(new ExpenseItem(6.98, new ExpenseCategory("Food"), LocalDate.of(2019, 9, 5), ""));
        assertEquals(109.81 + 6.98, subBudget.getAmountUsedThisMonth(expenses, LocalDate.of(2019, 9, 1)));
    }

    @Test
    public void getAmountLeftTest() throws NegativeMonetaryAmountException {
        assertEquals(500, subBudget.getAmountLeft(null, LocalDate.of(2019, 9, 1)));

        ArrayList<Item> expenses = new ArrayList<Item>();
        expenses.add(new ExpenseItem(0.51, new ExpenseCategory("Gift"), LocalDate.of(2019, 9, 20), ""));
        assertEquals(500, subBudget.getAmountLeft(expenses, LocalDate.of(2019, 9, 1)));

        expenses.add(new ExpenseItem(109.81, new ExpenseCategory("Food"), LocalDate.of(2019, 9, 23), ""));
        assertEquals(500 - 109.81, subBudget.getAmountLeft(expenses, LocalDate.of(2019, 9, 1)));

        expenses.add(new ExpenseItem(203.09, new ExpenseCategory("Transportation"), LocalDate.of(2019, 9, 17), ""));
        assertEquals(500 - 109.81, subBudget.getAmountLeft(expenses, LocalDate.of(2019, 9, 1)));

        expenses.add(new ExpenseItem(6.98, new ExpenseCategory("Food"), LocalDate.of(2019, 9, 5), ""));
        assertEquals(500 - 109.81 - 6.98, subBudget.getAmountLeft(expenses, LocalDate.of(2019, 9, 1)));
    }
     */
}
