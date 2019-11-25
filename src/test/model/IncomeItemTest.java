package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IncomeItemTest {
    private IncomeItem item;

    @BeforeEach
    public void setup() {
        item = new IncomeItem(100, new IncomeCategory("Salary"), LocalDate.of(2019, 9, 25), "");
    }

    @Test
    public void getAmountTest() {
        assertEquals(100, item.getAmount());
    }

    @Test
    public void getCategoryTest() {
        assertEquals(new IncomeCategory("Salary"), item.getCategory());
    }

    @Test
    public void getCategoryNameTest() {
        assertEquals("Salary", item.getCategoryName());
    }

    @Test
    public void getDateTest() {
        assertEquals(LocalDate.of(2019, 9, 25), item.getDate());
    }

    @Test
    public void getNoteTest() {
        assertEquals("", item.getNote());
    }

    @Test
    public void editTest() {
        item.edit(500, new IncomeCategory("Salary"), LocalDate.of(2019, 9, 25), "From job");
        assertEquals(500, item.getAmount());
        assertEquals("Salary", item.getCategoryName());
        assertEquals(LocalDate.of(2019, 9, 25), item.getDate());
        assertEquals("From job", item.getNote());
    }
}
