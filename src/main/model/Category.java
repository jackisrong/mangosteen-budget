package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public abstract class Category {
    protected String name;
    protected ArrayList<Item> itemsInCategory = new ArrayList<Item>();

    // MODIFIES: this
    // EFFECTS: creates new Category with name name
    public Category(String name) {
        this.name = name;
    }

    // MODIFIES: this
    // EFFECTS: reflexive relationship (one-to-many) - add item to list, then set category of item to this
    public void addItem(Item item) {
        if (!itemsInCategory.contains(item)) {
            itemsInCategory.add(item);
            item.setCategory(this);
        }
    }

    // MODIFIES: this
    // EFFECTS: reflexive relationship (one-to-many) - remove item from list, then set category of item to null
    public void removeItem(Item item) {
        if (itemsInCategory.contains(item)) {
            itemsInCategory.remove(item);
            item.removeCategory(this);
        }
    }

    // EFFECTS: returns category's name
    public String getName() {
        return name;
    }

    // EFFECTS: returns all items currently in category
    public ArrayList<Item> getAllItems() {
        return itemsInCategory;
    }

    // EFFECTS: returns total amount of all items currently in category
    public double getAllItemAmountTotal() {
        double total = 0.0;
        for (Item i : itemsInCategory) {
            total += i.getAmount();
        }
        return total;
    }

    // EFFECTS: returns total amount of all items currently in category from current month
    public double getAllItemAmountThisMonthTotal() {
        double total = 0.0;
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();
        for (Item i : itemsInCategory) {
            if (i.getDate().getYear() == year && i.getDate().getMonthValue() == month) {
                total += i.getAmount();
            }
        }
        return total;
    }

    /*
    // MODIFIES: this
    // EFFECTS: adds category with name into categories list if does not already exist
    public void addCategory(String name) throws CategoryAlreadyExistsException {
        for (String s : categories) {
            if (s.equals(name)) {
                throw new CategoryAlreadyExistsException();
            }
        }
        categories.add(name);
    }

    // MODIFIES: this
    // EFFECTS: deletes category with name from categories list
    public void deleteCategory(String name) {
        for (String s : categories) {
            if (s.equals(name)) {
                categories.remove(s);
                break;
            }
        }
    }
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Category category = (Category) o;
        return name.equals(category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
