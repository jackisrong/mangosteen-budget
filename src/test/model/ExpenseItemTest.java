package model;

import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;

public class ExpenseItemTest {
    private ExpenseItem item;

    @BeforeEach
    public void setup() {
        item = new ExpenseItem(100, "Job", LocalDate.of(2019, 9, 25), "");
    }
}
