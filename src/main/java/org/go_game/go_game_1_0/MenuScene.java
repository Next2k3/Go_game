package org.go_game.go_game_1_0;


import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MenuScene {
    Stage stage;
    Scene scene;
    public MenuScene(Stage stage) {
        this.stage = stage;
        initScene();
    }
    public void initScene(){
        VBox menuLayout = new VBox(10);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setBackground(Background.fill(Color.web("#262626")));
        menuLayout.setPadding(new Insets(10));
        menuLayout.setMinWidth(300);

        Label label = new Label("Goo...");
        label.setFont(Font.font(75));
        label.setTextFill(Color.WHITE);

        // Przyciski
        Button button1 = new Button("Gra Online");
        Button button2 = new Button("Gra z botem");
        Button button3 = new Button("Wyłącz gre");


        button1.setFont(new Font(15));
        button2.setFont(new Font(15));
        button3.setFont(new Font(15));

        button1.setMinSize(200,15);
        button2.setMinSize(200,15);
        button3.setMinSize(200,15);

        button1.setBackground(Background.fill(Color.rgb(59,59,59)));
        button2.setBackground(Background.fill(Color.rgb(59,59,59)));
        button3.setBackground(Background.fill(Color.rgb(59,59,59)));

        button1.setTextFill(Color.WHITE);
        button2.setTextFill(Color.WHITE);
        button3.setTextFill(Color.WHITE);

        button1.setOnMouseEntered(e -> button1.setStyle("-fx-background-color: lightgray;"));
        button1.setOnMouseExited(e -> button1.setStyle("-fx-background-color: default;"));

        button2.setOnMouseEntered(e -> button2.setStyle("-fx-background-color: lightgray;"));
        button2.setOnMouseExited(e -> button2.setStyle("-fx-background-color: default;"));

        button3.setOnMouseEntered(e -> button3.setStyle("-fx-background-color: lightgray;"));
        button3.setOnMouseExited(e -> button3.setStyle("-fx-background-color: default;"));


        // Obsługa zdarzeń przycisków
        button1.setOnAction(e -> {
            App.switchScene(stage,new GameScene(stage).getScene());
        });

        button2.setOnAction(e -> {
            // App.switchScene(stage,new MenuScene(stage).getScene());
        });


        button3.setOnAction(e -> {
            Platform.exit();
        });

        menuLayout.getChildren().addAll(label,button1, button2, button3);
        this.scene = new Scene(menuLayout);
    }
    public Scene getScene(){
        return this.scene;
    }

}
