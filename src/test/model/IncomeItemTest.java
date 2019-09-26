package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IncomeItemTest {
    private IncomeItem item;

    @BeforeEach
    public void setup() {
        item = new IncomeItem(100, "Job", "2019-09-25", "");
    }

    @Test
    public void getAmountTest() {
        assertEquals(100, item.getAmount());
    }

    @Test
    public void getCategoryTest() {
        assertEquals("Job", item.getCategory());
    }

    @Test
    public void getDateTest() {
        assertEquals("2019-09-25", item.getDate());
    }

    @Test
    public void getNoteTest() {
        assertEquals("", item.getNote());
    }

    @Test
    public void changeAmountTest() {
        assertEquals(100, item.getAmount());
        item.changeAmount(200);
        assertEquals(200, item.getAmount());
        item.changeAmount(161.87);
        assertEquals(161.87, item.getAmount());
    }

    @Test
    public void changeCategoryTest() {
        assertEquals("Job", item.getCategory());
        item.changeCategory("Gift");
        assertEquals("Gift", item.getCategory());
        item.changeCategory("Investments");
        assertEquals("Investments", item.getCategory());
    }

    @Test
    public void changeDateTest() {
        assertEquals("2019-09-25", item.getDate());
        item.changeDate("2019-09-05");
        assertEquals("2019-09-05", item.getDate());
        item.changeDate("2019-09-07");
        assertEquals("2019-09-07", item.getDate());
    }

    @Test
    public void changeNoteTest() {
        assertEquals("", item.getNote());
        item.changeNote("From September 1st to 15th pay period");
        assertEquals("From September 1st to 15th pay period", item.getNote());
        item.changeNote("Birthday gift from Justin");
        assertEquals("Birthday gift from Justin", item.getNote());
    }
}
