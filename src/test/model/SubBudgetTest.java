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
    public void getAmountUsedTest() {
        assertEquals(0, subBudget.getAmountUsed(null));

        ArrayList<IncomeItem> items = new ArrayList<IncomeItem>();
        items.add(new IncomeItem(0.51, "Gift", LocalDate.of(2019, 9, 20), ""));
        assertEquals(0, subBudget.getAmountUsed(items));

        items.add(new IncomeItem(109.81, "Food", LocalDate.of(2019, 9, 23), ""));
        assertEquals(109.81, subBudget.getAmountUsed(items));

        items.add(new IncomeItem(203.09, "Transportation", LocalDate.of(2019, 9, 17), ""));
        assertEquals(109.81, subBudget.getAmountUsed(items));

        items.add(new IncomeItem(6.98, "Food", LocalDate.of(2019, 9, 5), ""));
        assertEquals(109.81 + 6.98, subBudget.getAmountUsed(items));
    }

    @Test
    public void getAmountLeftTest() {
        assertEquals(500, subBudget.getAmountLeft(null));

        ArrayList<IncomeItem> items = new ArrayList<IncomeItem>();
        items.add(new IncomeItem(0.51, "Gift", LocalDate.of(2019, 9, 20), ""));
        assertEquals(500, subBudget.getAmountLeft(items));

        items.add(new IncomeItem(109.81, "Food", LocalDate.of(2019, 9, 23), ""));
        assertEquals(500 - 109.81, subBudget.getAmountLeft(items));

        items.add(new IncomeItem(203.09, "Transportation", LocalDate.of(2019, 9, 17), ""));
        assertEquals(500 - 109.81, subBudget.getAmountLeft(items));

        items.add(new IncomeItem(6.98, "Food", LocalDate.of(2019, 9, 5), ""));
        assertEquals(500 - 109.81 - 6.98, subBudget.getAmountLeft(items));
    }
}
