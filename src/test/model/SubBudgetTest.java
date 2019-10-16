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
        subBudget = new SubBudget("Food", 500);
    }

    @Test
    public void getCategoryTest() {
        assertEquals("Food", subBudget.getCategory());
    }

    @Test
    public void getAmountTest() {
        assertEquals(500, subBudget.getAmount());
    }

    @Test
    public void getAmountUsedTest() throws NegativeMonetaryAmountException {
        assertEquals(0, subBudget.getAmountUsedThisMonth(null, LocalDate.of(2019, 9, 1)));

        ArrayList<Item> expenses = new ArrayList<Item>();
        expenses.add(new ExpenseItem(0.51, "Gift", LocalDate.of(2019, 9, 20), ""));
        assertEquals(0, subBudget.getAmountUsedThisMonth(expenses, LocalDate.of(2019, 9, 1)));

        expenses.add(new ExpenseItem(109.81, "Food", LocalDate.of(2019, 9, 23), ""));
        assertEquals(109.81, subBudget.getAmountUsedThisMonth(expenses, LocalDate.of(2019, 9, 1)));

        expenses.add(new ExpenseItem(203.09, "Transportation", LocalDate.of(2019, 9, 17), ""));
        assertEquals(109.81, subBudget.getAmountUsedThisMonth(expenses, LocalDate.of(2019, 9, 1)));

        expenses.add(new ExpenseItem(6.98, "Food", LocalDate.of(2019, 9, 5), ""));
        assertEquals(109.81 + 6.98, subBudget.getAmountUsedThisMonth(expenses, LocalDate.of(2019, 9, 1)));
    }

    @Test
    public void getAmountLeftTest() throws NegativeMonetaryAmountException {
        assertEquals(500, subBudget.getAmountLeft(null, LocalDate.of(2019, 9, 1)));

        ArrayList<Item> expenses = new ArrayList<Item>();
        expenses.add(new ExpenseItem(0.51, "Gift", LocalDate.of(2019, 9, 20), ""));
        assertEquals(500, subBudget.getAmountLeft(expenses, LocalDate.of(2019, 9, 1)));

        expenses.add(new ExpenseItem(109.81, "Food", LocalDate.of(2019, 9, 23), ""));
        assertEquals(500 - 109.81, subBudget.getAmountLeft(expenses, LocalDate.of(2019, 9, 1)));

        expenses.add(new ExpenseItem(203.09, "Transportation", LocalDate.of(2019, 9, 17), ""));
        assertEquals(500 - 109.81, subBudget.getAmountLeft(expenses, LocalDate.of(2019, 9, 1)));

        expenses.add(new ExpenseItem(6.98, "Food", LocalDate.of(2019, 9, 5), ""));
        assertEquals(500 - 109.81 - 6.98, subBudget.getAmountLeft(expenses, LocalDate.of(2019, 9, 1)));
    }
}
