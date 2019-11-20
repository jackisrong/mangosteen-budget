package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SpendingMenu extends Menu {
    private MainMenu mainMenu;
    private ItemMenu itemMenu;

    @FXML
    private Label yearLabel;
    private int year;
    private Month month;
    private ArrayList<Button> allMonthButtons = new ArrayList<Button>();
    @FXML
    private GridPane monthGrid;
    @FXML
    private Label summaryTitle;
    @FXML
    private Label incomeAmount;
    @FXML
    private Label expenseAmount;
    @FXML
    private Label balanceAmount;
    @FXML
    private VBox transactionsContainer;

    public SpendingMenu(MainMenu mainMenu, ItemMenu itemMenu, Budget budget) {
        this.mainMenu = mainMenu;
        this.itemMenu = itemMenu;
        this.budget = budget;
    }

    public void run(Stage stage) {
        this.stage = stage;
        try {
            loadGUI();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException occurred while initializing spending menu GUI.");
        }
    }

    private void loadGUI() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ui/resources/spendingMenu.fxml"));
        loader.setController(this);
        VBox panel = loader.load();
        scene = new Scene(panel);
        scene.getStylesheets().add("/ui/resources/spendingMenu.css");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setResizable(false);
    }

    @FXML
    private void initialize() {
        summaryMonthNumbers(getCurrentMonth(), getCurrentYear(), summaryTitle, incomeAmount, expenseAmount,
                balanceAmount);
        year = getCurrentYear();
        yearLabel.setText(Integer.toString(year));
        month = getCurrentMonth();
        addMonthButtons();
        updateTransactions();
    }

    private void addMonthButtons() {
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 6; col++) {
                int month = row * 6 + col + 1;
                String text = Month.of(month).getDisplayName(TextStyle.SHORT, Locale.US);
                Button b = new Button(text);
                b.setOnAction(this::choseMonth);
                if (Month.of(month).equals(getCurrentMonth())) {
                    b.setStyle(b.getStyle() + "-fx-background-color: #930fff; -fx-text-fill: #ffffff;");
                }
                allMonthButtons.add(b);
                monthGrid.add(b, col, row);
            }
        }

    }

    @FXML
    private void leftYear() {
        year = year - 1;
        yearLabel.setText(Integer.toString(year));
        summaryMonthNumbers(month, year, summaryTitle, incomeAmount, expenseAmount, balanceAmount);
        updateTransactions();
    }

    @FXML
    private void rightYear() {
        year = year + 1;
        yearLabel.setText(Integer.toString(year));
        summaryMonthNumbers(month, year, summaryTitle, incomeAmount, expenseAmount, balanceAmount);
        updateTransactions();
    }

    private Month getMonthFromAbbreviation(String abbreviation) {
        Month month = null;
        try {
            Date date = new SimpleDateFormat("MMM").parse(abbreviation);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            month = Month.of(cal.get(Calendar.MONTH) + 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return month;
    }

    @FXML
    private void choseMonth(ActionEvent event) {
        Button b = (Button) event.getSource();
        month = getMonthFromAbbreviation(b.getText());
        int year = Integer.parseInt(this.yearLabel.getText());
        summaryMonthNumbers(month, year, summaryTitle, incomeAmount, expenseAmount, balanceAmount);
        for (Button button : allMonthButtons) {
            button.setStyle(button.getStyle() + "-fx-background-color: #ffffff; -fx-text-fill: #000000;");
        }
        b.setStyle(b.getStyle() + "-fx-background-color: #930fff; -fx-text-fill: #ffffff;");
        updateTransactions();
    }

    private void updateTransactions() {
        transactionsContainer.getChildren().clear();
        YearMonth chosenYearMonth = YearMonth.of(year, month);
        int daysInMonth = chosenYearMonth.lengthOfMonth();
        for (int i = daysInMonth; i >= 1; i--) {
            for (Item item : budget.getAllIncomeItems()) {
                if (item.getDate().equals(LocalDate.of(year, month, i))) {
                    addTransactionLine(item, "+");
                }
            }
            for (Item item : budget.getAllExpenseItems()) {
                if (item.getDate().equals(LocalDate.of(year, month, i))) {
                    addTransactionLine(item, "-");
                }
            }
        }
    }

    private HBox createTransactionLine(Item item, String plusMinusSymbol) {
        String plusMinusType = plusMinusSymbol.equals("+") ? "Income" : "Expense";

        Label month = new Label(this.month.getDisplayName(TextStyle.SHORT, Locale.US).toUpperCase());
        month.getStyleClass().add("transactionMonth");
        Label date = new Label(Integer.toString(item.getDate().getDayOfMonth()));
        date.getStyleClass().add("transactionDate");
        VBox dateBox = new VBox(month, date);
        dateBox.setAlignment(Pos.CENTER);

        Label category = new Label(item.getCategoryName());
        category.getStyleClass().add("transactionCategory");
        Label note = new Label(item.getNote());
        note.getStyleClass().add("transactionNote");
        VBox categoryNoteBox = new VBox(category, note);
        categoryNoteBox.getStyleClass().add("transactionCategoryNote");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label amount = new Label(plusMinusSymbol + formatMonetaryAmount(item.getAmount()));
        amount.getStyleClass().add("transactionAmount" + plusMinusType);

        return new HBox(dateBox, categoryNoteBox, spacer, amount);
    }

    private void addTransactionLine(Item item, String plusMinusSymbol) {
        HBox hbox = createTransactionLine(item, plusMinusSymbol);
        hbox.getStyleClass().add("transactionLineItem");
        hbox.setOnMouseClicked(this::transactionItemClicked);
        Tooltip tooltip = new Tooltip("Edit this item");
        Tooltip.install(hbox, tooltip);
        Label positionOfItemInAppropriateListLabel = new Label(Integer.toString(plusMinusSymbol.equals("+")
                ? budget.getAllIncomeItems().indexOf(item) : budget.getAllExpenseItems().indexOf(item)));
        positionOfItemInAppropriateListLabel.setManaged(false);
        hbox.getChildren().add(positionOfItemInAppropriateListLabel);
        transactionsContainer.getChildren().add(hbox);
    }

    private void transactionItemClicked(MouseEvent mouseEvent) {
        HBox hbox = (HBox) mouseEvent.getSource();
        VBox dateBox = (VBox) hbox.getChildren().get(0);
        VBox categoryNoteBox = (VBox) hbox.getChildren().get(1);
        Label amountLabel = (Label) hbox.getChildren().get(3);
        int day = Integer.parseInt(((Label) dateBox.getChildren().get(1)).getText());
        String note = ((Label) categoryNoteBox.getChildren().get(1)).getText();
        double amount = Double.parseDouble(amountLabel.getText().substring(2));
        LocalDate date = LocalDate.of(year, month, day);
        int positionOfItemInAppropriateList = Integer.parseInt(((Label) hbox.getChildren().get(4)).getText());
        Item i;
        if (amountLabel.getText().charAt(0) == '+') {
            Category c = budget.getIncomeCategories().get(((Label) categoryNoteBox.getChildren().get(0)).getText());
            i = new IncomeItem(amount, c, date, note);
        } else {
            Category c = budget.getExpenseCategories().get(((Label) categoryNoteBox.getChildren().get(0)).getText());
            i = new ExpenseItem(amount, c, date, note);
        }
        itemMenu.runEditItem(stage, i, positionOfItemInAppropriateList);
    }

    @FXML
    private void viewMain() {
        mainMenu.run(stage);
    }
}


