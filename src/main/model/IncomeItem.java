package model;

import exceptions.NegativeMonetaryAmountException;

import java.time.LocalDate;

public class IncomeItem extends Item {
    // EFFECTS: creates new IncomeItem with specified fields
    public IncomeItem(double amount, String category, LocalDate date, String note)
            throws NegativeMonetaryAmountException {
        super(amount, category, date, note);
    }

    @Override
    public String viewItem() {
        return "Income of amount $" + amount + " in category " + category + " from date " + date
                + " with note: " + note;
    }
}
