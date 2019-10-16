package model;

import exceptions.NegativeMonetaryAmountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpenseItemTest {
    private ExpenseItem item;

    @BeforeEach
    public void setup() throws NegativeMonetaryAmountException {
        item = new ExpenseItem(51.29, "Food", LocalDate.of(2019, 9, 20), "hello");
    }

    @Test
    public void viewItemTest() {
        assertEquals("Expense of amount $51.29 in category Food from date 2019-09-20 with note: hello", item.viewItem());
    }
}
