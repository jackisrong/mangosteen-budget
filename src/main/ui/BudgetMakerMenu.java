package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Budget;
import model.Category;
import model.Item;
import model.SubBudget;

import java.io.IOException;
import java.time.LocalDate;

public class BudgetMakerMenu extends CreationMenu {
    private SubBudgetMenu subBudgetMenu;

    @FXML
    private GridPane categoriesContainer;
    @FXML
    private VBox enterInfoContainer;
    @FXML
    private TextField noteField;

    public BudgetMakerMenu(SubBudgetMenu subBudgetMenu, Budget budget) {
        this.subBudgetMenu = subBudgetMenu;
        this.budget = budget;
    }

    @Override
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

    public void runEditItem(Stage stage, SubBudget s, int positionOfItemInAppropriateList) {
        run(stage);
        editing = true;
        for (Button b : allCategoryButtons) {
            if (b.getText().equals(s.getCategoryName())) {
                choseCategory(new ActionEvent(b, b));
            }
        }
        noteField.setText(s.getNote());
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
    }

    @FXML
    protected void backToPreviousMenu() {
        subBudgetMenu.run(stage);
    }

    @Override
    protected void createItem() {
        Category category = budget.getExpenseCategories().get(chosenCategory.getText().substring(10));
        double amount = Double.parseDouble(amountLabel.getText().substring(1));
        String note = noteField.getText();
        budget.addToAllSubBudgets(new SubBudget(category, amount, note));
    }

    @Override
    protected void editItem() {
        Category category = budget.getExpenseCategories().get(chosenCategory.getText().substring(10));
        double amount = Double.parseDouble(amountLabel.getText().substring(1));
        String note = noteField.getText();
        budget.getAllSubBudgets().get(positionOfEditItemInAppropriateList).edit(category, amount, note);
    }
}
