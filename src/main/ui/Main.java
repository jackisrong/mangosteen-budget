package ui;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private MainMenu mainMenu = new MainMenu();

    public static void main(String[] args) {
        System.out.println("Loading GUI stage...");
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Guava");
        primaryStage.setWidth(411 * (4.0 / 5.0));
        primaryStage.setHeight(731 * (4.0 / 5.0));
        primaryStage.setResizable(false);
        System.out.println("GUI stage loading completed.");

        mainMenu.run(primaryStage);

        primaryStage.show();
    }

    @Override
    public void stop() {
        mainMenu.save();
        System.out.println("Thank you for using Guava.");
    }
}
