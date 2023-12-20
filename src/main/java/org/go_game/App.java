package org.go_game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.go_game.scene.GameScene;
import org.go_game.scene.MenuScene;

public class App extends Application {
    private static App instance;
    private Stage stage;
    public static void main (String[] args){
        launch(args);
    }
    @Override
    public void start(Stage stage){
        MenuScene menuScene = new MenuScene(stage);

        stage.setScene(menuScene.getScene());
        stage.show();
    }
    public static void switchScene(Stage stage, Scene scene) {
        stage.setScene(scene);
    }
}
