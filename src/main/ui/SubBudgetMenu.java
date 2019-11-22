package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;

public class SubBudgetMenu extends Menu {
    private MainMenu mainMenu;
    private Budget budget;
    private Stage stage;
    private Scene scene;
    private BudgetMakerMenu budgetMakerMenu;

    @FXML
    private VBox contentContainer;

    public SubBudgetMenu(MainMenu mainMenu, Budget budget) {
        this.mainMenu = mainMenu;
        this.budget = budget;
        budgetMakerMenu = new BudgetMakerMenu(this, budget);
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
        loader.setLocation(getClass().getResource("/ui/resources/subBudgetMenu.fxml"));
        loader.setController(this);
        VBox panel = loader.load();
        scene = new Scene(panel);
        scene.getStylesheets().add("/ui/resources/subBudgetMenu.css");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setResizable(false);
    }

    @FXML
    private void initialize() {
        loadBudgets();
    }

    @FXML
    private void addBudget() {
        budgetMakerMenu.run(stage);
    }

    private HBox createBudgetSubHeading(SubBudget s) {
        Label note = new Label(s.getNote());
        note.getStyleClass().add("budgetSubHeading");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Label amountLeft = new Label(formatMonetaryAmount(s.getAmountLeft(budget.getAllExpenseItems())) + " left");
        amountLeft.getStyleClass().add("budgetSubHeading");
        return new HBox(note, spacer, amountLeft);
    }

    private StackPane createBudgetLineProgress(SubBudget s) {
        double used = s.getAmountUsedThisMonth(budget.getAllExpenseItems());
        ProgressBar progressBar;
        if (used / s.getAmount() >= 1.0) {
            progressBar = new ProgressBar(1);
            progressBar.setStyle(progressBar.getStyle() + "-fx-accent: #e00422;");
        } else {
            progressBar = new ProgressBar(used / s.getAmount());
            if (used / s.getAmount() >= 0.8) {
                progressBar.setStyle(progressBar.getStyle() + "-fx-accent: #f2e829;");
            } else {
                progressBar.setStyle(progressBar.getStyle() + "-fx-accent: #930fff;");
            }
        }
        Label amountUsed = new Label(formatMonetaryAmount(used) + " of " + formatMonetaryAmount(s.getAmount()));
        StackPane progress = new StackPane(progressBar, amountUsed);
        progress.getStyleClass().add("progressBarContainer");
        return progress;
    }

    private void loadBudgets() {
        if (budget.getAllSubBudgets().size() > 0) {
            contentContainer.getChildren().clear();
        }
        for (SubBudget s : budget.getAllSubBudgets()) {
            Label title = new Label(s.getCategoryName());
            title.getStyleClass().add("budgetTitle");
            VBox vbox = new VBox(title, createBudgetSubHeading(s), createBudgetLineProgress(s));
            vbox.getStyleClass().add("budgetItem");
            vbox.setOnMouseClicked(this::budgetClicked);
            Tooltip tooltip = new Tooltip("Edit this budget");
            Tooltip.install(vbox, tooltip);
            Label positionOfItemInListLabel = new Label(Integer.toString(budget.getAllSubBudgets().indexOf(s)));
            positionOfItemInListLabel.setManaged(false);
            vbox.getChildren().add(positionOfItemInListLabel);
            contentContainer.getChildren().add(vbox);
        }
    }

    private void budgetClicked(MouseEvent mouseEvent) {
        VBox vbox = (VBox) mouseEvent.getSource();
        HBox categoryNoteBox = (HBox) vbox.getChildren().get(1);
        StackPane amountBox = (StackPane) vbox.getChildren().get(2);
        String note = ((Label) categoryNoteBox.getChildren().get(0)).getText();
        String progressBarAmountText = ((Label) amountBox.getChildren().get(1)).getText();
        double amount = Double.parseDouble(progressBarAmountText.substring(progressBarAmountText.indexOf("of") + 4));
        int positionOfItemInAppropriateList = Integer.parseInt(((Label) vbox.getChildren().get(3)).getText());
        Category c = budget.getExpenseCategories().get(((Label) vbox.getChildren().get(0)).getText());
        SubBudget s = new SubBudget(c, amount, note);
        budgetMakerMenu.runEditItem(stage, s, positionOfItemInAppropriateList);
    }


    @FXML
    private void viewMain() {
        mainMenu.run(stage);
    }

    @FXML
    private void viewSpending() {
        mainMenu.viewSpending();
    }



    /*
    public void createSubBudget() {
        System.out.println("Which category would you like to create a sub-budget for?");
        String categoryKey = scanner.nextLine();
        System.out.println("What amount would you like to set the budget for?");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        Category category = budget.getExpenseCategories().get(categoryKey);
        budget.addToAllSubBudgets(new SubBudget(category, amount));
    }

    public void viewSubBudgets() {
        for (SubBudget b : budget.getAllSubBudgets()) {
            System.out.println("Sub-budget for " + b.getCategoryName() + " has used "
                    + b.getAmountUsedThisMonth(budget.getAllExpenseItems(), LocalDate.now()) + " of " + b.getAmount()
                    + " (" + b.getAmountLeft(budget.getAllExpenseItems(), LocalDate.now()) + " left in sub-budget)");
        }
    }

    public void changeSubBudgetAmount() {
        viewSubBudgets();
        System.out.println("Which category's sub-budget would you like to edit?");
        String category = scanner.nextLine();
        for (SubBudget s : budget.getAllSubBudgets()) {
            if (s.getCategory().equals(category)) {
                System.out.println("Enter new amount for sub-budget:");
                double newAmount = scanner.nextDouble();
                scanner.nextLine();
                s.changeAmount(newAmount);
                break;
            }
        }
    }
     */
}
