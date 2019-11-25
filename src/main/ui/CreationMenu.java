package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Category;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public abstract class CreationMenu extends Menu {
    protected Boolean editing = false;
    protected int positionOfEditItemInAppropriateList;

    @FXML
    protected GridPane categoriesContainer;
    protected ArrayList<Button> allCategoryButtons = new ArrayList<Button>();
    @FXML
    protected Label chosenCategory;
    @FXML
    protected VBox enterInfoContainer;
    @FXML
    protected Label amountLabel;

    @FXML
    protected void loadCategories(Collection<Category> allCategories) {
        categoriesContainer.getChildren().clear();
        allCategoryButtons.clear();
        int counter = 0;
        for (Category c : allCategories) {
            Button b = new Button(c.getName());
            b.setOnAction(this::choseCategory);
            categoriesContainer.add(b, counter % 4, counter / 4, 1, 1);
            allCategoryButtons.add(b);
            counter++;
        }
    }

    @FXML
    protected void choseCategory(ActionEvent event) {
        Button b = (Button) event.getSource();
        chosenCategory.setText("Category: " + b.getText());
        if (!enterInfoContainer.isVisible()) {
            enterInfoContainer.setVisible(true);
        }
        for (Button button : allCategoryButtons) {
            button.setStyle(button.getStyle() + "-fx-background-color: #ffffff; -fx-text-fill: #000000;");
            if (b.equals(button)) {
                b.setStyle(b.getStyle() + "-fx-background-color: #930fff; -fx-text-fill: #ffffff;");
            }
        }
    }

    private String getCurrentAmountWithoutSymbols() {
        String currentAmount = amountLabel.getText().substring(1);
        String currentAmountNoSymbols = currentAmount.substring(0, currentAmount.length() - 3)
                + currentAmount.substring(currentAmount.length() - 2);
        return currentAmountNoSymbols;
    }

    private String formatNewAmount(String newAmountString) {
        DecimalFormat df = new DecimalFormat("#0.00");
        double newAmount = Double.parseDouble(newAmountString.substring(0, newAmountString.length() - 2)
                + "." + newAmountString.substring(newAmountString.length() - 2));
        return "$" + df.format(newAmount);
    }

    @FXML
    protected void amountNumberPadPressed(ActionEvent event) {
        Button b = (Button) event.getSource();
        String currentAmountNoSymbols = getCurrentAmountWithoutSymbols();
        if (b.getId() == null) {
            long currentAmountInteger = Long.parseLong(currentAmountNoSymbols);
            String newAmountString = "000" + (currentAmountInteger * 10 + Integer.parseInt(b.getText()));
            amountLabel.setText(formatNewAmount(newAmountString));
        } else if (b.getId().equals("backButton")) {
            String newAmountString = "000" + currentAmountNoSymbols.substring(0, currentAmountNoSymbols.length() - 1);
            amountLabel.setText(formatNewAmount(newAmountString));
        } else if (b.getId().equals("okButton")) {
            if (!editing) {
                createItem();
            } else {
                editItem();
            }
        }
    }

    protected boolean checkAmountGreaterThanZero() {
        return !amountLabel.getText().equals("$0.00");
    }

    // TODO: create check to see if number they're entering is too high for double type

    protected abstract void createItem();

    protected abstract void editItem();
}
