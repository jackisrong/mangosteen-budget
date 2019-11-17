package ui;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Guava");
        primaryStage.setWidth(411 * (4.0 / 5.0));
        primaryStage.setHeight(731 * (4.0 / 5.0));
        primaryStage.setResizable(false);

        MainMenu mainMenu = new MainMenu();
        mainMenu.run(primaryStage);
    }

    @Override
    public void stop() {
        // save
    }
}
