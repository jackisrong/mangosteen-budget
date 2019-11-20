package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Budget;

import java.io.IOException;

public class BudgetMakerMenu extends CreationMenu {
    private SubBudgetMenu subBudgetMenu;

    @FXML
    private GridPane categoriesContainer;
    @FXML
    private VBox enterInfoContainer;

    public BudgetMakerMenu(SubBudgetMenu subBudgetMenu, Budget budget) {
        this.subBudgetMenu = subBudgetMenu;
        this.budget = budget;
    }

    @Override
    protected void run(Stage stage) {
        this.stage = stage;
        try {
            loadGUI();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException occurred while initializing item menu GUI.");
        }
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

    @FXML
    private void amountNumberPadPressed() {

    }

    @Override
    protected void createItem() {

    }

    @Override
    protected void editItem() {

    }
}
