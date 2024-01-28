package org.go_game.go_game_1_0;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.go_game.go_game_1_0.Board.Board;

public class GameScene {
    private Stage stage;
    private Scene scene;
    private int BOARD_SIZE=9;
    private double BOARD_WIDTH=600.0;
    private double BOARD_HEIGHT=600.0;
    private Color BACKGROUD_COLOR=Color.web("#262626");
    private Color BOARD_COLOR= Color.BURLYWOOD;
    private Board board;
    final boolean[] isWhiteTurn = {false};
    public GameScene(Stage stage){
        this.stage=stage;
        this.board=new Board(9);
        initGameScene();
    }

    public GameScene(Stage stage,int size) {
        this.stage=stage;
        this.board=new Board(size);
        initGameScene();
    }
    private void initGameScene(){
        BorderPane layout = new BorderPane();
        layout.setBackground(Background.fill(BACKGROUD_COLOR));
        layout.setPadding(new Insets(10));

        ObservableList<String> moveHistory = FXCollections.observableArrayList();

        Group root = new Group();

        Rectangle background = new Rectangle(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        background.setFill(Color.BURLYWOOD);
        root.getChildren().add(background);

        for (int i = 0; i < BOARD_SIZE; i++) {
            double x = (i + 1.0) * BOARD_WIDTH / (BOARD_SIZE + 1);
            Line line = new Line(x, 0, x, BOARD_HEIGHT);
            line.setStroke(BACKGROUD_COLOR);
            line.setStrokeWidth(5);
            root.getChildren().add(line);
        }
        for (int i = BOARD_SIZE - 1; i >= 0; i--) {
            double y = (i + 1.0) * BOARD_HEIGHT / (BOARD_SIZE + 1);
            Line line = new Line(0, y, BOARD_WIDTH, y);
            line.setStroke(BACKGROUD_COLOR);
            line.setStrokeWidth(5);
            root.getChildren().add(line);

            for (int j = 0; j < BOARD_SIZE; j++) {
                double x = (j + 1.0) * BOARD_WIDTH / (BOARD_SIZE + 1);

                Circle circle = new Circle(x,y, 15);
                circle.setFill(BOARD_COLOR);
                circle.setStroke(BACKGROUD_COLOR);
                circle.setStrokeWidth(3);
                circle.setFill(Color.BURLYWOOD);

                root.getChildren().add(circle);

                // Obsługa zdarzeń myszy dla okręgu
                circle.setOnMouseEntered(event -> circle.setStroke(Color.RED));
                circle.setOnMouseExited(event -> circle.setStroke(BACKGROUD_COLOR));

                // Obsługa zdarzeń kliknięcia myszy dla okręgu
                circle.setOnMouseClicked(event -> {

                    Color currentColor = (Color) circle.getFill();

                    if (currentColor.equals(Color.WHITE) || currentColor.equals(Color.BLACK)) {
                        // Kolor już ustawiony, nie dodawaj do historii
                        return;
                    }

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
        scene = new Scene(layout);
    }
    public Scene getScene(){
        return this.scene;
    }
}



