package model;

import exceptions.NegativeMonetaryAmountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class IncomeItemTest {
    private IncomeItem item;

    @BeforeEach
    public void setup() throws NegativeMonetaryAmountException {
        item = new IncomeItem(100, "Job", LocalDate.of(2019, 9, 25), "");
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
        assertEquals(LocalDate.of(2019, 9, 25), item.getDate());
    }

    @Test
    public void getNoteTest() {
        assertEquals("", item.getNote());
    }

    @Test
    public void changeAmountTest() {
        assertEquals(100, item.getAmount());
        try {
            item.changeAmount(200);
        } catch (NegativeMonetaryAmountException e) {
            fail();
        }
        assertEquals(200, item.getAmount());
        try {
            item.changeAmount(161.87);
        } catch (NegativeMonetaryAmountException e) {
            fail();
        }
        assertEquals(161.87, item.getAmount());
        try {
            item.changeAmount(-502.2);
            fail();
        } catch (NegativeMonetaryAmountException e) {
        }
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
        assertEquals(LocalDate.of(2019, 9, 25), item.getDate());
        item.changeDate(LocalDate.of(2019, 9, 5));
        assertEquals(LocalDate.of(2019, 9, 5), item.getDate());
        item.changeDate(LocalDate.of(2019, 9, 7));
        assertEquals(LocalDate.of(2019, 9, 7), item.getDate());
    }

    @Test
    public void changeNoteTest() {
        assertEquals("", item.getNote());
        item.changeNote("From September 1st to 15th pay period");
        assertEquals("From September 1st to 15th pay period", item.getNote());
        item.changeNote("Birthday gift from Justin");
        assertEquals("Birthday gift from Justin", item.getNote());
    }

    @Test
    public void viewItemTest() {
        assertEquals("Income of amount $100.0 in category Job from date 2019-09-25 with note: ", item.viewItem());
    }
}
