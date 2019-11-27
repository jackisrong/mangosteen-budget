package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoryTest {
    private Category category;

    @BeforeEach
    public void setup() {
        category = new IncomeCategory("Food");
    }

    @Test
    public void getAllItemsTest() {
        IncomeItem i1 = new IncomeItem(51.0, category, LocalDate.now(), "hello");
        IncomeItem i2 = new IncomeItem(65.54, category, LocalDate.now(), "ni hao");
        IncomeItem i3 = new IncomeItem(23.2, category, LocalDate.now(), "bonjour");
        category.addItem(i1);
        category.addItem(i2);
        category.addItem(i3);
        assertEquals(3, category.getAllItems().size());
        assertEquals(i1, category.getAllItems().get(0));
        assertEquals(i2, category.getAllItems().get(1));
        assertEquals(i3, category.getAllItems().get(2));
    }

    @Test
    public void getAllItemAmountTotalTest() {
        IncomeItem i1 = new IncomeItem(51.0, category, LocalDate.now(), "hello");
        IncomeItem i2 = new IncomeItem(65.54, category, LocalDate.now(), "ni hao");
        IncomeItem i3 = new IncomeItem(23.2, category, LocalDate.now(), "bonjour");
        category.addItem(i1);
        category.addItem(i2);
        category.addItem(i3);
        assertEquals(51.0 + 65.54 + 23.2, category.getAllItemAmountTotal());
    }

    @Test
    public void getAllItemAmountThisMonthTotalTest() {
        IncomeItem i1 = new IncomeItem(51.0, category, LocalDate.now(), "hello");
        IncomeItem i2 = new IncomeItem(65.54, category, LocalDate.now(), "ni hao");
        IncomeItem i3 = new IncomeItem(23.2, category, LocalDate.of(2000, 06, 15), "bonjour");
        category.addItem(i1);
        category.addItem(i2);
        category.addItem(i3);
        assertEquals(51.0 + 65.54, category.getAllItemAmountThisMonthTotal());
    }
}
