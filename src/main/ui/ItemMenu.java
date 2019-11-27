package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.time.LocalDate;

public class ItemMenu extends CreationMenu {
    private Menu previousMenu;

    @FXML
    private ChoiceBox itemTypeChoice;
    @FXML
    private TextField noteField;
    @FXML
    private DatePicker datePicker;

    // MODIFIES: this
    // EFFECTS: creates new ItemMenu with specified fields
    public ItemMenu(Budget budget) {
        this.budget = budget;
    }

    @Override
    protected void run(Stage stage) {
        // do nothing
    }

    // EFFECTS: do run actions and load GUI
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

    // EFFECTS: do run actions, set appropriate settings for editing chosen item
    public void runEditItem(Stage stage, String type, int positionOfItemInAppropriateList, Menu previousMenu) {
        run(stage, previousMenu);
        editing = true;
        chooseItemType(type);
        itemTypeChoice.setDisable(true);
        Item i = type.equals("Income") ? budget.getAllIncomeItems().get(positionOfItemInAppropriateList) :
                budget.getAllExpenseItems().get(positionOfItemInAppropriateList);
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
        datePicker.setValue(LocalDate.now());
    }

    @FXML
    private void backToPreviousMenu() {
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
        if (!checkAmountGreaterThanZero()) {
            showAmountZeroNoCreationWarning();
        } else if (!checkDateFilledIn()) {
            showNoDateWarning();
        } else {
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
            backToPreviousMenu();
        }
    }

    protected void editItem() {
        if (!checkAmountGreaterThanZero()) {
            showAmountZeroDeletionWarning((String) itemTypeChoice.getSelectionModel().getSelectedItem());
        } else if (!checkDateFilledIn()) {
            showNoDateWarning();
        } else {
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
            backToPreviousMenu();
        }
    }

    private void showAmountZeroNoCreationWarning() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Item Creation");
        alert.setHeaderText("Item will not be created");
        alert.setContentText("The amount is $0.00 so the item will not be created. Continue?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            backToPreviousMenu();
        } else {
            alert.close();
        }
    }

    private void showAmountZeroDeletionWarning(String type) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Item Deletion");
        alert.setHeaderText("Item will be deleted");
        alert.setContentText("The amount is $0.00 so the item will be deleted. Continue?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            Item i = type.equals("Income") ? budget.getAllIncomeItems().get(positionOfEditItemInAppropriateList) :
                    budget.getAllExpenseItems().get(positionOfEditItemInAppropriateList);
            i.removeCategory(i.getCategory());
            if (type.equals("Income")) {
                budget.getAllIncomeItems().remove(positionOfEditItemInAppropriateList);
            } else {
                budget.getAllExpenseItems().remove(positionOfEditItemInAppropriateList);
            }
            backToPreviousMenu();
        } else {
            alert.close();
        }
    }

    private void showNoDateWarning() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("No Date");
        alert.setHeaderText("Missing date field");
        alert.setContentText("Please enter a date!");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            alert.close();
        }
    }

    private boolean checkDateFilledIn() {
        return !(datePicker.getValue() == null);
    }
}
