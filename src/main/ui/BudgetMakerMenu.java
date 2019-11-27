package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Budget;
import model.Category;
import model.SubBudget;

import java.io.IOException;

public class BudgetMakerMenu extends CreationMenu {
    private SubBudgetMenu subBudgetMenu;

    @FXML
    private GridPane categoriesContainer;
    @FXML
    private VBox enterInfoContainer;

    // MODIFIES: this
    // EFFECTS: create new BudgetMakerMenu with specified fields
    public BudgetMakerMenu(SubBudgetMenu subBudgetMenu, Budget budget) {
        this.subBudgetMenu = subBudgetMenu;
        this.budget = budget;
    }

    @Override
    // EFFECTS: do run actions and load GUI
    protected void run(Stage stage) {
        this.stage = stage;
        editing = false;
        try {
            loadGUI();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException occurred while initializing item menu GUI.");
        }
    }

    // EFFECTS: do run actions and set appropriate settings for editing chosen sub-budget
    public void runEditItem(Stage stage, SubBudget s, int positionOfItemInAppropriateList) {
        run(stage);
        editing = true;
        for (Button b : allCategoryButtons) {
            if (b.getText().equals(s.getCategoryName())) {
                choseCategory(new ActionEvent(b, b));
                b.setDisable(false);
            }
        }
        amountLabel.setText(formatMonetaryAmount(s.getAmount()));
        positionOfEditItemInAppropriateList = positionOfItemInAppropriateList;
    }

    private void loadGUI() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ui/resources/budgetMakerMenu.fxml"));
        loader.setController(this);
        VBox panel = loader.load();
        scene = new Scene(panel);
        scene.getStylesheets().add("/ui/resources/budgetMakerMenu.css");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setResizable(false);
    }

    @FXML
    private void initialize() {
        loadCategories(budget.getExpenseCategories().values());
        disableCategoriesAlreadyWithSubBudget();
    }

    @FXML
    private void backToPreviousMenu() {
        subBudgetMenu.run(stage);
    }

    @FXML
    private void disableCategoriesAlreadyWithSubBudget() {
        for (Button b : allCategoryButtons) {
            for (SubBudget s : budget.getAllSubBudgets()) {
                if (s.getCategoryName().equals(b.getText())) {
                    b.setDisable(true);
                }
            }
        }
    }

    @Override
    // EFFECTS: check for error cases, create new sub-budget
    protected void createItem() {
        if (!checkAmountGreaterThanZero()) {
            showAmountZeroNoCreationWarning();
        } else {
            Category category = budget.getExpenseCategories().get(chosenCategory.getText().substring(10));
            double amount = Double.parseDouble(amountLabel.getText().substring(1));
            budget.addToAllSubBudgets(new SubBudget(category, amount));
            backToPreviousMenu();
        }
    }

    @Override
    // EFFECTS: check for error cases, edit chosen sub-budget
    protected void editItem() {
        if (!checkAmountGreaterThanZero()) {
            showAmountZeroDeletionWarning();
        } else {
            Category category = budget.getExpenseCategories().get(chosenCategory.getText().substring(10));
            double amount = Double.parseDouble(amountLabel.getText().substring(1));
            budget.getAllSubBudgets().get(positionOfEditItemInAppropriateList).edit(category, amount);
            backToPreviousMenu();
        }
    }

    private void showAmountZeroNoCreationWarning() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Budget Creation");
        alert.setHeaderText("Budget will not be created");
        alert.setContentText("The amount is $0.00 so the budget will not be created. Continue?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            backToPreviousMenu();
        } else {
            alert.close();
        }
    }

    private void showAmountZeroDeletionWarning() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Budget Deletion");
        alert.setHeaderText("Budget will be deleted");
        alert.setContentText("The amount is $0.00 so the budget will be deleted. Continue?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            budget.getAllSubBudgets().remove(positionOfEditItemInAppropriateList);
            backToPreviousMenu();
        } else {
            alert.close();
        }
    }
}
