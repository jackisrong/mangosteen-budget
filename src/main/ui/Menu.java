package ui;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Budget;
import model.Item;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

public abstract class Menu {
    protected Stage stage;
    protected Scene scene;
    protected Budget budget = new Budget();

    protected abstract void run(Stage stage);

    // EFFECTS: return current year
    protected int getCurrentYear() {
        return LocalDate.now().getYear();
    }

    // EFFECTS: return current month
    protected Month getCurrentMonth() {
        return LocalDate.now().getMonth();
    }

    // EFFECTS: format a double into monetary String
    protected String formatMonetaryAmount(double amount) {
        DecimalFormat df = new DecimalFormat("#0.00");
        return "$" + df.format(amount);
    }

    // EFFECTS: get total amount of items within list items in month month and year year
    protected double getTotalItemAmounts(Month month, int year, ArrayList<Item> items) {
        double total = 0.0;
        for (Item i : items) {
            if (i.getDate().getYear() == year && i.getDate().getMonth() == month) {
                total += i.getAmount();
            }
        }
        return total;
    }

    // EFFECTS: show monthly summary module
    protected void summaryMonthNumbers(Month month, int year, Label summaryTitle, Label incomeAmount,
                                       Label expenseAmount, Label balanceAmount) {
        double totalMonthlyIncome = getTotalItemAmounts(month, year, budget.getAllIncomeItems());
        double totalMonthlyExpenses = getTotalItemAmounts(month, year, budget.getAllExpenseItems());
        summaryTitle.setText("Summary for " + month + " " + year);

        incomeAmount.setText(formatMonetaryAmount(totalMonthlyIncome));
        expenseAmount.setText("-" + formatMonetaryAmount(totalMonthlyExpenses));
        balanceAmount.setText(formatMonetaryAmount(totalMonthlyIncome - totalMonthlyExpenses));
    }
}
