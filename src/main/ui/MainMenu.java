package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Category;
import model.Item;
import network.Quote;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;

public class MainMenu extends Menu {
    private ItemMenu itemMenu = new ItemMenu(budget);
    private SpendingMenu spendingMenu = new SpendingMenu(this, itemMenu, budget);
    private SubBudgetMenu subBudgetMenu = new SubBudgetMenu(this, budget);

    @FXML
    private Label quoteContent;
    @FXML
    private Label quoteAuthor;
    @FXML
    private Label summaryTitle;
    @FXML
    private Label incomeAmount;
    @FXML
    private Label expenseAmount;
    @FXML
    private Label balanceAmount;
    @FXML
    private Label incomePieChartTitle;
    @FXML
    private PieChart incomePieChart;
    @FXML
    private Label incomePieChartNotice;
    @FXML
    private Label expensePieChartTitle;
    @FXML
    private PieChart expensePieChart;
    @FXML
    private Label expensePieChartNotice;

    public MainMenu() {
        budget.loadAllExistingData();
    }

    public void run(Stage stage) {
        this.stage = stage;
        try {
            loadGUI();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException occurred while initializing main menu GUI.");
        }
    }

    private void loadGUI() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ui/resources/mainMenu.fxml"));
        loader.setController(this);
        VBox panel = loader.load();
        scene = new Scene(panel);
        scene.getStylesheets().add("/ui/resources/mainMenu.css");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setResizable(false);
    }

    @FXML
    private void initialize() {
        displayRandomQuote();
        summaryMonthNumbers(getCurrentMonth(), getCurrentYear(), summaryTitle, incomeAmount, expenseAmount,
                balanceAmount);
        summaryMonthPieCharts();
    }

    private void displayRandomQuote() {
        Quote quote = new Quote();
        quoteContent.setText("\"" + quote.getQuoteContent() + "\"");
        quoteContent.setWrapText(true);
        quoteAuthor.setText("\t- " + quote.getQuoteAuthor());
    }

    private void drawPieChart(ArrayList<Item> items, Collection<Category> hashMapValues, Label notice, PieChart chart) {
        double allItemsAmountThisMonth = getTotalItemAmounts(getCurrentMonth(), getCurrentYear(), items);
        if (allItemsAmountThisMonth == 0.0) {
            notice.setText("No items this month! (yet...)");
        } else {
            for (Category c : hashMapValues) {
                if (c.getAllItemAmountThisMonthTotal() != 0.0) {
                    DecimalFormat df = new DecimalFormat("#0");
                    String percentage = df.format(c.getAllItemAmountThisMonthTotal() / allItemsAmountThisMonth * 100);
                    String label = c.getName() + "\n" + formatMonetaryAmount(c.getAllItemAmountThisMonthTotal())
                            + " (" + percentage + "%)";
                    PieChart.Data data = new PieChart.Data(label, c.getAllItemAmountThisMonthTotal());
                    chart.getData().add(data);
                }
            }
        }
    }

    private void summaryMonthPieCharts() {
        incomePieChartTitle.setText("Income Summary for " + getCurrentMonth() + " " + getCurrentYear());
        expensePieChartTitle.setText("Expense Summary for " + getCurrentMonth() + " " + getCurrentYear());
        drawPieChart(budget.getAllIncomeItems(), budget.getIncomeCategories().values(), incomePieChartNotice,
                incomePieChart);
        drawPieChart(budget.getAllExpenseItems(), budget.getExpenseCategories().values(), expensePieChartNotice,
                expensePieChart);
    }

    @FXML
    public void addItem() {
        itemMenu.run(stage, this);
    }

    @FXML
    public void viewSpending() {
        spendingMenu.run(stage);
    }

    @FXML
    public void viewBudgets() {
        subBudgetMenu.run(stage);
    }

    @FXML
    public void save() {
        budget.saveAllExistingData();
    }

    /*
    private void displayChoices() {
        System.out.println("What would you like to do?");
        System.out.println("[1] Add income or expense item");
        System.out.println("[2] Display all income and expense items");
        System.out.println("[3] Edit an income or expense item");
        System.out.println("[4] Create a monthly sub-budget to track money spent on individual categories");
        System.out.println("[5] View sub-budgets");
        System.out.println("[6] Edit a sub-budget's amount");
        System.out.println("[7] FOR TESTING ONLY: View all items in each category");
        System.out.println("[other] Save and quit");
    }

    private boolean runAppropriateFunctionBasedOnChoice(int choice) {
        if (choice == 1) {
            itemMenu.createItemPrompt();
        } else if (choice == 2) {
            itemMenu.displayAllItems();
        } else if (choice == 3) {
            itemMenu.editItemChooseType();
        } else if (choice == 4) {
            subBudgetMenu.createSubBudget();
        } else if (choice == 5) {
            subBudgetMenu.viewSubBudgets();
        } else if (choice == 6) {
            subBudgetMenu.changeSubBudgetAmount();
        } else if (choice == 7) {
            //itemMenu.viewAllItemsInAllCategories();
        } else {
            return false;
        }
        return true;
    }
     */
}
