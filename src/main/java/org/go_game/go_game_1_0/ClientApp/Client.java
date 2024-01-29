package org.go_game.go_game_1_0.ClientApp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.go_game.go_game_1_0.Board.Stone;
import org.go_game.go_game_1_0.Board.StoneColor;
import org.go_game.go_game_1_0.BoardPane;
import org.go_game.go_game_1_0.TestXD;

import java.io.*;
import java.net.Socket;

public class Client extends Application {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Circle[][] circles = new Circle[9][9];
    private boolean myTurn = false;
    private String myColor;


    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Stage primaryStage;
    private GridPane gameGrid;

    @Override
    public void start(Stage primaryStage) {
//        this.primaryStage = primaryStage;
//        this.primaryStage.setTitle("Go...");
//        showMenu();
//        connectToServer();
        try {
            socket = new Socket("localhost", 6789);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            myColor = in.readLine();
            myTurn = myColor.equals("CZ");
            GridPane grid = new GridPane();
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    circles[i][j] = new Circle(25, Color.BROWN);
                    circles[i][j].setStroke(Color.BLACK);
                    int finalI = i;
                    int finalJ = j;
                    circles[i][j].setOnMouseClicked(e -> makeMove(finalI, finalJ));
                    grid.add(circles[i][j], j, i);
                }
            }
            Scene scene = new Scene(grid, 600, 600);
            primaryStage.setTitle("Gra GO - " + myColor);
            primaryStage.setScene(scene);
            primaryStage.show();

            new Thread(this::run).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void makeMove(int i, int j) {
        if (myTurn && circles[i][j].getFill() == Color.BROWN) {
            Color color = myColor.equals("CZ") ? Color.BLACK : Color.WHITE;
            circles[i][j].setFill(color);
            out.println(i + "," + j);
            myTurn = false;
        }
    }

    private void run() {
        try {
            String serverInput;
            while ((serverInput = in.readLine()) != null) {
                String[] parts = serverInput.split(",");
                int i = Integer.parseInt(parts[0]);
                int j = Integer.parseInt(parts[1]);
                String symbol = parts[2];
                Color color = symbol.equals("CZ") ? Color.BLACK : Color.WHITE;
                updateCircle(i, j, color);
                myTurn = !symbol.equals(myColor);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateCircle(int i, int j, Color color) {
        javafx.application.Platform.runLater(() -> circles[i][j].setFill(color));
    }
    public static void main(String[] args) {
        launch(args);
    }




    private void showMenu() {

    }

//    private void startGame() {
//        int size=9;
//        StoneColor[][] stoneColors = new StoneColor[size][size];
//
//        ObservableList<String> moveHistory = FXCollections.observableArrayList();
//        ListView<String> moveHistoryListView = new ListView<>(moveHistory);
//
//        HBox hBox = new HBox();
//        hBox.setBackground(Background.fill(Color.rgb(26,26,26)));
//
//        GridPane gridPane = new GridPane();
//        gridPane.setStyle("-fx-background-color: #DEB887");
//
//        for(int i=0;i<size;i++){
//            for(int j=0;j<size;j++) {
//                Circle circle = new Circle(17.5);
//
//                circle.setStroke(Color.rgb(26,26,26));
//                circle.setStrokeWidth(3);
//                if(stoneColors[i][j]==null) {
//                    circle.setFill(Color.BURLYWOOD);
//                }else if(stoneColors[i][j]== StoneColor.WHITE){
//                    circle.setFill(Color.WHITE);
//                }else{
//                    circle.setFill(Color.BLACK);
//                }
//
//                circle.setOnMouseClicked(e->{
//                            int x= (int) Math.round(e.getSceneX()/55.0)-1;
//                            int y= (int) Math.round(e.getSceneY()/55.0)-1;
//                            x = Math.max(0, x);
//                            y = Math.max(0, y);
//                            handleButtonClick(x,y);
//                            String moveInfo = "(" + x + ", " + y + ")";
//                            moveHistory.add(moveInfo);
//                 });
//
//                Line lineV = new Line();
//                lineV.setStartX(25);
//                lineV.setStartY(0);
//                lineV.setEndX(25);
//                lineV.setEndY(50);
//
//                Line lineH = new Line();
//                lineH.setStartX(0);
//                lineH.setStartY(25);
//                lineH.setEndX(50);
//                lineH.setEndY(25);
//
//                lineV.setStrokeWidth(5);
//                lineH.setStrokeWidth(5);
//
//                StackPane stackPane = new StackPane(lineH,lineV,circle);
//                gridPane.add(stackPane, j, i);
//
//                GridPane.setHalignment(stackPane, HPos.CENTER);
//                GridPane.setValignment(stackPane, VPos.CENTER);
//
//            }
//        }
//        gridPane.setMaxWidth(450);
//        gridPane.setMaxHeight(450);
//
//        VBox vBox = new VBox(15);
//
//        moveHistoryListView.setStyle("-fx-background-color: #262626");
//        moveHistoryListView.setMinHeight(450);
//
//        vBox.getChildren().add(moveHistoryListView);
//
//        HBox hBox1 = new HBox(15);
//
//        Button giveUpButton = new Button("Przerwij");
//        giveUpButton.setFont(new Font(15));
//        giveUpButton.setMinWidth(150);
//        giveUpButton.setStyle("-fx-background-color: #595959");
//        giveUpButton.setTextFill(Color.WHITE);
//
//        giveUpButton.setOnMouseEntered(e -> giveUpButton.setStyle("-fx-background-color: lightgray;"));
//        giveUpButton.setOnMouseExited(e -> giveUpButton.setStyle("-fx-background-color: #595959;"));
//
//        Button rematchButton = new Button("Rewanż");
//        rematchButton.setFont(new Font(15));
//        rematchButton.setMinWidth(150);
//        rematchButton.setStyle("-fx-background-color: #595959");
//        rematchButton.setTextFill(Color.WHITE);
//
//        rematchButton.setOnMouseEntered(e -> rematchButton.setStyle("-fx-background-color: lightgray;"));
//        rematchButton.setOnMouseExited(e -> rematchButton.setStyle("-fx-background-color: #595959;"));
//
//        HBox hBox2 = new HBox(gridPane);
//        hBox2.setPadding(new Insets(15));
//        hBox.getChildren().add(hBox2);
//        hBox1.getChildren().add(giveUpButton);
//        hBox1.getChildren().add(rematchButton);
//        vBox.getChildren().add(hBox1);
//        vBox.setPadding(new Insets(15));
//        hBox.getChildren().add(vBox);
//        Scene gameScene = new Scene(hBox);
//
//        primaryStage.setScene(gameScene);
//    }

//    private void handleButtonClick(int row, int col) {
//        try {
//            // Wysłanie kordynatów przycisku do serwera
//            output.writeObject(new int[]{row, col});
//            output.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    private void connectToServer() {
//        try {
//            Socket socket = new Socket("localhost", 12345);
//            output = new ObjectOutputStream(socket.getOutputStream());
//            input = new ObjectInputStream(socket.getInputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
