package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class ItemMenu extends CreationMenu {
    private Menu previousMenu;
    private int positionOfEditItemInAppropriateList;

    @FXML
    private ChoiceBox itemTypeChoice;
    @FXML
    private TextField noteField;
    @FXML
    private DatePicker datePicker;

    public ItemMenu(Budget budget) {
        this.budget = budget;
    }

    @Override
    protected void run(Stage stage) {
        // do nothing
    }

    public void run(Stage stage, Menu previousMenu) {
        this.stage = stage;
        this.previousMenu = previousMenu;
        editing = false;
        try {
            loadGUI();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException occurred while initializing item menu GUI.");
        }
    }

    public void runEditItem(Stage stage, Item i, int positionOfItemInAppropriateList, Menu previousMenu) {
        run(stage, previousMenu);
        editing = true;
        if (i.getClass().getName().equals("model.IncomeItem")) {
            chooseItemType("Income");
        } else {
            chooseItemType("Expense");
        }
        for (Button b : allCategoryButtons) {
            if (b.getText().equals(i.getCategoryName())) {
                choseCategory(new ActionEvent(b, b));
            }
        }
        noteField.setText(i.getNote());
        datePicker.setValue(i.getDate());
        amountLabel.setText(formatMonetaryAmount(i.getAmount()));
        positionOfEditItemInAppropriateList = positionOfItemInAppropriateList;
    }

    private void loadGUI() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ui/resources/itemMenu.fxml"));
        loader.setController(this);
        VBox panel = loader.load();
        scene = new Scene(panel);
        scene.getStylesheets().add("/ui/resources/itemMenu.css");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setResizable(false);
    }

    @FXML
    private void initialize() {
        loadCategories(budget.getIncomeCategories().values());
    }

    @FXML
    protected void backToPreviousMenu() {
        previousMenu.run(stage);
    }

    private void chooseItemType(String type) {
        if (type.equals("Income")) {
            itemTypeChoice.getSelectionModel().select(0);
            loadCategories(budget.getIncomeCategories().values());
        } else {
            itemTypeChoice.getSelectionModel().select(1);
            loadCategories(budget.getExpenseCategories().values());
        }
    }

    @FXML
    private void choseItemType() {
        enterInfoContainer.setVisible(false);
        if (itemTypeChoice.getSelectionModel().getSelectedItem().equals("Income")) {
            loadCategories(budget.getIncomeCategories().values());
        } else {
            loadCategories(budget.getExpenseCategories().values());
        }
    }

    protected void createItem() {
        double amount = Double.parseDouble(amountLabel.getText().substring(1));
        LocalDate date = datePicker.getValue();
        String note = noteField.getText();
        if (itemTypeChoice.getSelectionModel().getSelectedItem().equals("Income")) {
            Category category = budget.getIncomeCategories().get(chosenCategory.getText().substring(10));
            budget.addToAllIncomeItems(new IncomeItem(amount, category, date, note));
        } else {
            Category category = budget.getExpenseCategories().get(chosenCategory.getText().substring(10));
            budget.addToAllExpenseItems(new ExpenseItem(amount, category, date, note));
        }
    }

    protected void editItem() {
        double amount = Double.parseDouble(amountLabel.getText().substring(1));
        LocalDate date = datePicker.getValue();
        String note = noteField.getText();
        if (itemTypeChoice.getSelectionModel().getSelectedItem().equals("Income")) {
            Category category = budget.getIncomeCategories().get(chosenCategory.getText().substring(10));
            budget.getAllIncomeItems().get(positionOfEditItemInAppropriateList).edit(amount, category, date, note);
        } else {
            Category category = budget.getExpenseCategories().get(chosenCategory.getText().substring(10));
            budget.getAllExpenseItems().get(positionOfEditItemInAppropriateList).edit(amount, category, date, note);
        }
    }


    /*
    private void createItemGetValues(String type) {
        System.out.println("How much is your " + type + "? (Enter an amount)");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Where is your " + type + " from? (Enter a category)");
        String categoryKey = scanner.nextLine();
        System.out.println("When is your " + type + " from? (Enter a date in format YYYY MM DD)");
        int year = scanner.nextInt();
        int month = scanner.nextInt();
        int day = scanner.nextInt();
        scanner.nextLine();
        LocalDate date = LocalDate.of(year, month, day);
        System.out.println("Any additional info? (Enter a note)");
        String note = scanner.nextLine();
    }

    private void createItem(double amount, Category category, LocalDate date, String note, String type) {
        try {
            Item i;
            if (type.equals("income")) {
                i = new IncomeItem(amount, category, date, note);
                budget.addToAllIncomeItems(i);
            } else {
                i = new ExpenseItem(amount, category, date, note);
                budget.addToAllExpenseItems(i);
            }
        } catch (NegativeMonetaryAmountException e) {
            System.out.println("Invalid monetary amount! Your amount is negative!");
        } finally {
            System.out.println("Item creation process has finished.");
        }
    }

    public void displayAllItems() {
        System.out.println("--------------------");
        System.out.println("Income Items");
        displayAllItemsInList(budget.getAllIncomeItems());
        System.out.println("--------------------");
        System.out.println("Expense Items");
        displayAllItemsInList(budget.getAllExpenseItems());
        System.out.println("--------------------");
    }

    public void editItemChooseType() {
        System.out.println("Which item would you like to edit?");
        System.out.println("[1] Income item");
        System.out.println("[2] Expense item");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice == 1) {
            displayAllItemsInList(budget.getAllIncomeItems());
            editItem(budget.getAllIncomeItems(), "income");
        } else {
            displayAllItemsInList(budget.getAllExpenseItems());
            editItem(budget.getAllExpenseItems(), "expense");
        }
    }

    private void editItem(ArrayList<Item> items, String type) {
        System.out.println("Which item would you like to change? (Enter the item number)");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < items.size()) {
            System.out.println("What would you like to change?");
            System.out.println("[1] Amount");
            System.out.println("[2] Category");
            System.out.println("[3] Date");
            System.out.println("[4] Note");
            int fieldChoice = scanner.nextInt();
            scanner.nextLine();

            if (fieldChoice >= 1 && fieldChoice <= 4) {
                editCorrectItemField(items.get(choice), fieldChoice, type);
            }
        } else {
            System.out.println("That's an invalid item!");
        }
    }

    private void editCorrectItemField(Item item, int fieldChoice, String type) {
        System.out.println("Enter new value:");
        if (fieldChoice == 1) {
            double newAmount = scanner.nextDouble();
            scanner.nextLine();
            try {
                item.changeAmount(newAmount);
            } catch (NegativeMonetaryAmountException e) {
                System.out.println("Invalid monetary amount!  Your amount is negative!");
            }
        } else if (fieldChoice == 2) {
            String newCategory = scanner.nextLine();
            item.changeCategory(changeCategory(newCategory, type));
        } else if (fieldChoice == 3) {
            item.changeDate(changeDate());
        } else if (fieldChoice == 4) {
            String newNote = scanner.nextLine();
            item.changeNote(newNote);
        }
    }

    private Category changeCategory(String newCategory, String type) {
        if (type.equals("income")) {
            return budget.getIncomeCategories().get(newCategory);
        } else {
            return budget.getExpenseCategories().get(newCategory);
        }
    }

    private LocalDate changeDate() {
        System.out.println("Use format YYYY MM DD");
        int year = scanner.nextInt();
        int month = scanner.nextInt();
        int day = scanner.nextInt();
        scanner.nextLine();
        return LocalDate.of(year, month, day);
    }
     */
}
