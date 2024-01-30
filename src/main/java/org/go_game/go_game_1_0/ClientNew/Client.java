package org.go_game.go_game_1_0.ClientNew;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
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
import org.go_game.go_game_1_0.Board.StoneColor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends Application implements Runnable{
    public static final int PLAYER1 = 1;
    public static final int PLAYER2 = 2;
    public static final int PLAYER1_WON = 1;
    public static final int PLAYER2_WON = 2;
    public static final int DRAW = 3;
    Socket socket;
    private boolean myTurn = false;
    private StoneColor myColor;
    private StoneColor[][] stoneColors;
    private int rowSelected;
    private int columnSelected;
    private DataInputStream fromServer;
    private DataOutputStream toServer;
    private boolean continueToPlay = true;
    private boolean waiting = true;
    private boolean isChosen = false;
    private Stage stage;
    public static void main(String[] args){
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        this.stage.setTitle("Go...");

        showMenu();
    }
    @Override
    public void run() {
        try{
            int player = fromServer.readInt();

            if(player==PLAYER1){
                myColor = StoneColor.BLACK;

                fromServer.readInt();
                myTurn = true;
            }else if(player==PLAYER2){
                myColor = StoneColor.WHITE;
            }else{
                System.out.println("null XD");
            }

            while(continueToPlay){
                if(player == PLAYER1){
                    waitForPlayerAction();
                    System.out.println("1");
                    sendMove();
                    System.out.println("11");
                    recieveInfoFromServer();
                    System.out.println("12");
                }else if(player == PLAYER2){
                    recieveInfoFromServer();
                    System.out.println("2");
                    waitForPlayerAction();
                    System.out.println("21");
                    sendMove();
                    System.out.println("22");
                }
            }
        }catch (IOException ex) {
            System.err.println(ex);
        } catch (InterruptedException ex) {}
    }
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

        button1.setOnAction(e -> startGame());

        button2.setOnAction(e -> {

        });

        button3.setOnAction(e -> {
            Platform.exit();
        });
        vBox.getChildren().addAll(label,button1,button2,button3);
        Scene menuScene = new Scene(vBox);
        stage.setScene(menuScene);
        stage.show();
    }
    private void startGame() {
        int size=9;
        stoneColors = new StoneColor[size][size];

        ObservableList<String> moveHistory = FXCollections.observableArrayList();
        ListView<String> moveHistoryListView = new ListView<>(moveHistory);

        HBox hBox = new HBox();
        hBox.setBackground(Background.fill(Color.rgb(26,26,26)));

        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: #DEB887");

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
                    int x = (int) Math.round(e.getSceneX()/55.0)-1;
                    int y = (int) Math.round(e.getSceneY()/55.0)-1;
                    x = Math.max(0, x);
                    y = Math.max(0, y);
                    rowSelected = x;
                    columnSelected = y;
                    String moveInfo = "(" + x + ", " + y + ")";
                    moveHistory.add(moveInfo);
                    waiting = false;
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

        moveHistoryListView.setStyle("-fx-background-color: #262626");
        moveHistoryListView.setMinHeight(450);

        vBox.getChildren().add(moveHistoryListView);

        HBox hBox1 = new HBox(15);

        Button giveUpButton = new Button("Przerwij");
        giveUpButton.setFont(new Font(15));
        giveUpButton.setMinWidth(150);
        giveUpButton.setStyle("-fx-background-color: #595959");
        giveUpButton.setTextFill(Color.WHITE);

        giveUpButton.setOnMouseEntered(e -> giveUpButton.setStyle("-fx-background-color: lightgray;"));
        giveUpButton.setOnMouseExited(e -> giveUpButton.setStyle("-fx-background-color: #595959;"));

        Button rematchButton = new Button("Rewanż");
        rematchButton.setFont(new Font(15));
        rematchButton.setMinWidth(150);
        rematchButton.setStyle("-fx-background-color: #595959");
        rematchButton.setTextFill(Color.WHITE);

        rematchButton.setOnMouseEntered(e -> rematchButton.setStyle("-fx-background-color: lightgray;"));
        rematchButton.setOnMouseExited(e -> rematchButton.setStyle("-fx-background-color: #595959;"));

        HBox hBox2 = new HBox(gridPane);
        hBox2.setPadding(new Insets(15));
        hBox.getChildren().add(hBox2);
        hBox1.getChildren().add(giveUpButton);
        hBox1.getChildren().add(rematchButton);
        vBox.getChildren().add(hBox1);
        vBox.setPadding(new Insets(15));
        hBox.getChildren().add(vBox);
        Scene gameScene = new Scene(hBox);

        stage.setScene(gameScene);
        connectToServer();
    }
    private void waitForPlayerAction() throws InterruptedException {
        while (waiting) {
            Thread.sleep(100);
        }

        waiting = true;
    }
    private void sendMove() throws IOException {
        toServer.writeInt(rowSelected);
        toServer.writeInt(columnSelected);
        /*
        if(fromServer.readInt()==1){
            System.out.println(rowSelected+" "+columnSelected);
            stoneColors[rowSelected][columnSelected]=myColor;
        }
       */
    }
    private void recieveInfoFromServer() throws IOException {
        int status = fromServer.readInt();
        if(status == PLAYER1_WON){
            continueToPlay = false;
            if(myColor == StoneColor.BLACK){

            }else{
                recieveMove();
            }
        }else if(status == PLAYER2_WON){
            continueToPlay = false;
            if(myColor == StoneColor.WHITE){

            }else{
                recieveMove();
            }
        }else if(status == DRAW){

        }else{
            recieveMove();
            myTurn = true;
        }
    }
    private void recieveMove() throws IOException{
        int row = fromServer.readInt();
        int column = fromServer.readInt();
        if(myColor==StoneColor.WHITE){
            stoneColors[row][column]=StoneColor.BLACK;
        }else{
            stoneColors[row][column]=StoneColor.WHITE;
        }
    }
    private void connectToServer() {
        try {
            socket = new Socket("localhost", 8000);
            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            System.err.println(ex);
        }

        Thread thread = new Thread(this);
        thread.start();
    }
}
