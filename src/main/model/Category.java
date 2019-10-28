package model;

import exceptions.CategoryAlreadyExistsException;

import java.util.ArrayList;
import java.util.Objects;

public abstract class Category {
    protected String name;
    protected ArrayList<Item> itemsInCategory = new ArrayList<Item>();

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

    public String getName() {
        return name;
    }

    public ArrayList<Item> getAllItems() {
        return itemsInCategory;
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
