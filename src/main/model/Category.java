package model;

import exceptions.CategoryAlreadyExistsException;

import java.util.ArrayList;

public abstract class Category {
    protected ArrayList<String> categories = new ArrayList<String>();

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
}
