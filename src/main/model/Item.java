package model;

public interface Item {
    // TODO: Change this into a superclass for deliverable 5.

    public double getAmount();

    public String getCategory();

    public String getDate();

    public String getNote();

    public void changeAmount(double newAmount);

    public void changeCategory(String newCategory);

    public void changeDate(String newDate);

    public void changeNote(String newNote);
}
