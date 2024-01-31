package org.go_game.go_game_1_0.Client_Server;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.go_game.go_game_1_0.Board.StoneColor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends Application implements Runnable {
    public static final int PLAYER1_WON = 1;
    public static final int PLAYER2_WON = 2;
    public static final int DRAW = 3;
    public static final int CORRECTMOVE = 4;
    private static final int UNCORRECTMOVE = 5;

    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private int rowSelected;
    private int columnSelected;
    private int size;
    private StoneColor[][] stoneColors;
    private Stage stage;
    private Scene scene;
    private StoneColor myColor;
    private boolean continueToPlay = true;
    private boolean waiting = true;
    private boolean myTurn = false;
    private void showMenu() {
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setBackground(Background.fill(Color.rgb(26,26,26)));
        vBox.setPadding(new Insets(10));
        vBox.setMinWidth(300);

        Label label = new Label("Goo...");
        label.setFont(Font.font(75));
        label.setTextFill(Color.WHITE);

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

        button1.setOnAction(e -> showChoseMenu());

        button2.setOnAction(e -> {

        });

        button3.setOnAction(e -> {
            Platform.exit();
        });
        vBox.getChildren().addAll(label,button1,button2,button3);
        Scene menuScene = new Scene(vBox);
        stage.setScene(menuScene);
        stage.setMinHeight(350);
        stage.setMinWidth(350);
        this.scene =menuScene;
        stage.show();
    }
    private void showChoseMenu(){
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setBackground(Background.fill(Color.rgb(26,26,26)));
        vBox.setPadding(new Insets(10));
        vBox.setMinWidth(300);

        Label label = new Label("Goo...");
        label.setFont(Font.font(75));
        label.setTextFill(Color.WHITE);

        Button button1 = new Button("9x9");
        Button button2 = new Button("13x13");
        Button button3 = new Button("19x19");

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

        button1.setOnAction(e -> {
            this.size = 9;
            stoneColors = new StoneColor[9][9];
            startGame();
        });

        button2.setOnAction(e -> {
            this.size = 13;
            stoneColors = new StoneColor[13][13];
            startGame();
        });

        button3.setOnAction(e -> {
            this.size = 19;
            stoneColors = new StoneColor[19][19];
            startGame();
        });
        vBox.getChildren().addAll(label,button1,button2,button3);
        Scene menuScene = new Scene(vBox);
        stage.setScene(menuScene);
        this.scene = menuScene;
        stage.show();
    }
    private void startGame() {
        HBox hBox = new HBox();
        hBox.setBackground(Background.fill(Color.rgb(26,26,26)));

        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: #DEB887");
        gridPane.setId("gridPane");  // Dodaj to ID

        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++) {
                Circle circle = new Circle(17.5);

                circle.setStroke(Color.rgb(26,26,26));
                circle.setStrokeWidth(3);
                if(stoneColors[i][j]==null) {
                    circle.setFill(Color.BURLYWOOD);
                }else if(stoneColors[i][j]== StoneColor.WHITE){
                    circle.setFill(Color.WHITE);
                }else{
                    circle.setFill(Color.BLACK);
                }

                circle.setOnMouseClicked(e->{
                    if(myTurn) {
                        int x= (int) Math.round(e.getSceneX()/55.0)-1;
                        int y= (int) Math.round(e.getSceneY()/55.0)-1;
                        rowSelected = Math.max(0, x);
                        columnSelected = Math.max(0, y);
                        waiting = false;
                    }
                });

                Line lineV = new Line();
                lineV.setStartX(25);
                lineV.setStartY(0);
                lineV.setEndX(25);
                lineV.setEndY(50);

                Line lineH = new Line();
                lineH.setStartX(0);
                lineH.setStartY(25);
                lineH.setEndX(50);
                lineH.setEndY(25);

                lineV.setStrokeWidth(5);
                lineH.setStrokeWidth(5);

                StackPane stackPane = new StackPane(lineH,lineV,circle);
                gridPane.add(stackPane, j, i);

                GridPane.setHalignment(stackPane, HPos.CENTER);
                GridPane.setValignment(stackPane, VPos.CENTER);

            }
        }
        gridPane.setMaxWidth(450);
        gridPane.setMaxHeight(450);

        VBox vBox = new VBox(15);


        VBox vBox1 = new VBox(15);

        Button button1 = new Button("Poddaj się");
        button1.setFont(new Font(15));
        button1.setMinWidth(150);
        button1.setStyle("-fx-background-color: #595959");
        button1.setTextFill(Color.WHITE);

        button1.setOnMouseEntered(e -> button1.setStyle("-fx-background-color: lightgray;"));
        button1.setOnMouseExited(e -> button1.setStyle("-fx-background-color: #595959;"));
        button1.setOnAction(e->{

        });

        Button button2 = new Button("Pomiń ture");
        button2.setFont(new Font(15));
        button2.setMinWidth(150);
        button2.setStyle("-fx-background-color: #595959");
        button2.setTextFill(Color.WHITE);

        button2.setOnMouseEntered(e -> button2.setStyle("-fx-background-color: lightgray;"));
        button2.setOnMouseExited(e -> button2.setStyle("-fx-background-color: #595959;"));
        button2.setOnAction(e->{
            waiting = false;
            rowSelected=-1;
            columnSelected=-1;
        });

        Button button3 = new Button("Wróć do menu ");
        button3.setFont(new Font(15));
        button3.setMinWidth(150);
        button3.setStyle("-fx-background-color: #595959");
        button3.setTextFill(Color.WHITE);

        button3.setOnMouseEntered(e -> button3.setStyle("-fx-background-color: lightgray;"));
        button3.setOnMouseExited(e -> button3.setStyle("-fx-background-color: #595959;"));
        button3.setOnAction(e->{
            showMenu();
            try {
                socket.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        HBox hBox2 = new HBox(gridPane);
        hBox2.setPadding(new Insets(15));
        hBox.getChildren().add(hBox2);
        vBox1.getChildren().add(button1);
        vBox1.getChildren().add(button2);
        vBox1.getChildren().add(button3);
        vBox.getChildren().add(vBox1);
        vBox.setPadding(new Insets(15));
        hBox.getChildren().add(vBox);
        Scene gameScene = new Scene(hBox);
        stage.setScene(gameScene);
        stage.setMinHeight(575);
        stage.setMinWidth(725);
        this.scene=gameScene;
        connectToServer();
    }
    public static void main(String[] args) {
        launch(args);
    }
    private void connectToServer() {
        try {
            socket = new Socket("localhost", 8000);
            outputStream = new DataOutputStream(socket.getOutputStream());
            inputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            System.err.println(ex);
        }

        Thread thread = new Thread(this);
        thread.start();
    }
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setTitle("Goo...");

        showMenu();
    }

    @Override
    public void run() {
        try {
            // Wyślij rozmiar planszy do serwera
            outputStream.writeInt(size);

            if(inputStream.readInt()==1){
                myColor=StoneColor.BLACK;
                myTurn=true;
            }else{
                myColor=StoneColor.WHITE;
                myTurn = false;
            }
            System.out.println(myColor);


            while(continueToPlay){
                if(myColor == StoneColor.BLACK){
                    waitForPlayerAction();
                    sendMove();
                    recieveInfoFromServer();
                    recieveInfoFromServer();
                }else if(myColor == StoneColor.WHITE){
                    recieveInfoFromServer();
                    waitForPlayerAction();
                    sendMove();
                    recieveInfoFromServer();
                }
            }
            socket.close();
        } catch (IOException ex) {
            System.err.println(ex);
        } catch (InterruptedException ex) {}
    }
    private void recieveInfoFromServer() throws IOException, InterruptedException {
        int status = inputStream.readInt();
        if (status == PLAYER1_WON) {
            continueToPlay = false;
            if (myColor == StoneColor.BLACK) {
                //statusLabel.setText("I Won! (X)");
            }
            else if (myColor == StoneColor.WHITE) {
                //statusLabel.setText("Player 1 (X) has won!");
                recieveMove();
            }
        }
        else if (status == PLAYER2_WON) {
            continueToPlay = false;
            if (myColor == StoneColor.WHITE) {
               // statusLabel.setText("I Won! (O)");
            }
            else if (myColor == StoneColor.BLACK) {
               // statusLabel.setText("Player 2 (O) has won!");
                recieveMove();
            }
        }
        else if (status == DRAW) {
            continueToPlay = false;
            //statusLabel.setText("Game is over, no winner!");

            if (myColor == StoneColor.WHITE) {
                recieveMove();
            }
        }else if(status == CORRECTMOVE){
            recieveMove();

            myTurn = false;
        }else if(status == UNCORRECTMOVE){
            waiting = true;
            waitForPlayerAction();
            sendMove();
            recieveInfoFromServer();
        }
        else {
            recieveMove();

            myTurn = true;
        }
    }
    private void waitForPlayerAction() throws InterruptedException {
        while (waiting) {
            Thread.sleep(100);
        }
        waiting = true;
    }
    private void sendMove() throws IOException {
        outputStream.writeInt(rowSelected);
        outputStream.writeInt(columnSelected);
    }
    private void refreshBoardView(GridPane gridPane) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Circle circle = (Circle) ((StackPane) gridPane.getChildren().get(i * size + j)).getChildren().get(2);

                if (stoneColors[i][j] == null) {
                    circle.setFill(Color.BURLYWOOD);
                } else if (stoneColors[i][j] == StoneColor.WHITE) {
                    circle.setFill(Color.WHITE);
                } else {
                    circle.setFill(Color.BLACK);
                }
            }
        }
    }

    private void recieveMove() throws IOException {
        String tablica = inputStream.readUTF();
        for (int i = 0; i < tablica.length(); i++) {
            char symbol = tablica.charAt(i);
            if (symbol == 'B') {
                stoneColors[i / size][i % size] = StoneColor.BLACK;
            } else if (symbol == 'W') {
                stoneColors[i / size][i % size] = StoneColor.WHITE;
            } else {
                stoneColors[i / size][i % size] = null;
            }
        }
        Platform.runLater(() -> {
            HBox hBox = (HBox) scene.getRoot();
            GridPane gridPane = (GridPane) hBox.lookup("#gridPane"); // ID kontenera GridPane
            refreshBoardView(gridPane);
        });
    }
}

