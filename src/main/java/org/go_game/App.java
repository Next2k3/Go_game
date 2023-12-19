package org.go_game;

import javafx.application.Application;
import javafx.stage.Stage;
import org.go_game.scene.SceneManager;


public class App extends Application {
    public static void main (String[] args){
        launch(args);
    }
    @Override
    public void start(Stage primaryStage){
        SceneManager sceneManager = new SceneManager(primaryStage);
        sceneManager.showMenuScene();
    }
}
