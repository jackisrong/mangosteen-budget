package ui;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private MainMenu mainMenu = new MainMenu();

    // EFFECTS: start GUI
    public static void main(String[] args) {
        System.out.println("Loading GUI stage...");
        Application.launch(args);
    }

    @Override
    // EFFECTS: create GUI stage, run main menu
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Mangosteen Budget");
        primaryStage.setWidth(411 * (4.0 / 5.0));
        primaryStage.setHeight(731 * (4.0 / 5.0));
        primaryStage.setResizable(false);
        System.out.println("GUI stage loading completed.");

        mainMenu.run(primaryStage);

        primaryStage.show();
    }

    @Override
    // EFFECTS: save information
    public void stop() {
        mainMenu.save();
        System.out.println("Thank you for using Mangosteen Budget.");
    }
}
