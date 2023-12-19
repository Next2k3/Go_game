package org.go_game.scene;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SceneManager {
    private Stage primaryStage;
    private Scene menuScene;
    private Scene onlineGameScene;
    private Scene botGameScene;

    public SceneManager(Stage primaryStage){
        this.primaryStage = primaryStage;
        initMenuScene();
        initOnlineGameScene();
        initBotGameScene();
    }
    public void showMenuScene(){
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }
    private void initMenuScene(){
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
            //Scene scene1 = createScene("Gra Online");
            primaryStage.setScene(onlineGameScene);
        });

        button2.setOnAction(e -> {
            primaryStage.setScene(botGameScene);
        });

        button3.setOnAction(e -> {
            Platform.exit();
        });

        menuLayout.getChildren().addAll(label,button1, button2, button3);
        menuScene = new Scene(menuLayout);
    }
    private void initOnlineGameScene() {
        BorderPane layout = new BorderPane();
        layout.setBackground(Background.fill(Color.web("#262626")));
        layout.setPadding(new Insets(10));
        layout.setMinWidth(300);

        int BOARD_SIZE = 9;
        double BOARD_WIDTH = 600.0;
        double BOARD_HEIGHT = 600.0;
        Color LINE_COLOR = Color.BLACK;
        Color BOARD_COLOR = Color.web("#262626");
        final boolean[] isWhiteTurn = {false};
        ObservableList<String> moveHistory = FXCollections.observableArrayList();

        Group root = new Group();

        Rectangle background = new Rectangle(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        background.setFill(Color.BURLYWOOD);
        root.getChildren().add(background);

        for (int i = 0; i < BOARD_SIZE; i++) {
            double x = (i + 1.0) * BOARD_WIDTH / (BOARD_SIZE + 1);
            Line line = new Line(x, 0, x, BOARD_HEIGHT);
            line.setStroke(LINE_COLOR);
            line.setStrokeWidth(5);
            root.getChildren().add(line);
        }

        for (int i = BOARD_SIZE - 1; i >= 0; i--) {
            double y = (i + 1.0) * BOARD_HEIGHT / (BOARD_SIZE + 1);
            Line line = new Line(0, y, BOARD_WIDTH, y);
            line.setStroke(LINE_COLOR);
            line.setStrokeWidth(5);
            root.getChildren().add(line);

            for (int j = 0; j < BOARD_SIZE; j++) {
                double x = (j + 1.0) * BOARD_WIDTH / (BOARD_SIZE + 1);

                Circle circle = new Circle(x,y, 15);
                circle.setFill(BOARD_COLOR);
                circle.setStroke(LINE_COLOR);
                circle.setStrokeWidth(3);
                circle.setFill(Color.BURLYWOOD);

                root.getChildren().add(circle);

                // Obsługa zdarzeń myszy dla okręgu
                circle.setOnMouseEntered(event -> circle.setStroke(Color.RED));
                circle.setOnMouseExited(event -> circle.setStroke(LINE_COLOR));

                // Obsługa zdarzeń kliknięcia myszy dla okręgu
                circle.setOnMouseClicked(event -> {
                    if (isWhiteTurn[0]) {
                        circle.setFill(Color.WHITE);
                    } else {
                        circle.setFill(Color.BLACK);
                    }

                    int x_c = (int) circle.getCenterX() / 60;
                    int y_c = (int) circle.getCenterY() / 60;
                    String color = (circle.getFill() == Color.BURLYWOOD) ? "Reset" : (isWhiteTurn[0] ? "Biały" : "Czarny");
                    String moveInfo = color + ": (" + x_c + ", " + y_c + ")";
                    moveHistory.add(moveInfo);

                    isWhiteTurn[0] = !isWhiteTurn[0];
                });
            }
        }

        VBox buttonPanel = new VBox();

        Button resetButton = new Button("Reset");
        resetButton.setFont(new Font(15));
        resetButton.setMinWidth(150);
        resetButton.setStyle("-fx-background-color: #595959;");
        resetButton.setTextFill(Color.WHITE);
        VBox.setMargin(resetButton, new Insets(10, 20, 10, 20));

        resetButton.setOnMouseEntered(e -> resetButton.setStyle("-fx-background-color: lightgray;"));
        resetButton.setOnMouseExited(e -> resetButton.setStyle("-fx-background-color: #595959;"));

        Button giveUpButton = new Button("Poddaj się");
        giveUpButton.setFont(new Font(15));
        giveUpButton.setMinWidth(150);
        giveUpButton.setStyle("-fx-background-color: #595959;");
        giveUpButton.setTextFill(Color.WHITE);
        VBox.setMargin(giveUpButton, new Insets(10, 20, 10, 20));

        giveUpButton.setOnMouseEntered(e -> giveUpButton.setStyle("-fx-background-color: lightgray;"));
        giveUpButton.setOnMouseExited(e -> giveUpButton.setStyle("-fx-background-color: #595959;"));

        resetButton.setOnAction(event -> {
            for (var node : root.getChildren()) {
                if (node instanceof Circle circle) {
                    circle.setFill(Color.BURLYWOOD);
                }
            }
            moveHistory.clear();
            isWhiteTurn[0] = false;
        });

        giveUpButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Koniec gry");
            alert.setHeaderText(null);
            alert.setContentText("Partia zakończona. Gracz się poddał.");

            alert.showAndWait();

            System.exit(0);
        });


        buttonPanel.getChildren().addAll(resetButton, giveUpButton);

        ListView<String> moveHistoryListView = new ListView<>(moveHistory);
        moveHistoryListView.setPrefHeight(BOARD_HEIGHT-120);
        moveHistoryListView.setPrefWidth(150);
        moveHistoryListView.setBackground(Background.fill(Color.rgb(59,59,59)));

        VBox container = new VBox();
        container.getChildren().addAll(buttonPanel, moveHistoryListView);
        container.setSpacing(10);
        container.setPadding(new Insets(10));

        container.setLayoutX(BOARD_WIDTH + 10);
        container.setLayoutY(50);

        layout.setCenter(root);
        layout.setRight(container);
        onlineGameScene = new Scene(layout);
    }
    public void initBotGameScene(){
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        Button button = new Button("Menu");
        button.setMinSize(225,25);

        button.setOnAction(e -> {
            primaryStage.setScene(menuScene);
        });
        layout.getChildren().add(button);
        botGameScene = new Scene(layout);
    }

}
