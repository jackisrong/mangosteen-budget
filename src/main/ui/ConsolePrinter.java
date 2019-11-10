package ui;

import model.Item;

import java.util.Observable;
import java.util.Observer;

public class ConsolePrinter implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        Item i = (Item) arg;
        System.out.println(i.viewItem() + " has been successfully created!");
    }
}
