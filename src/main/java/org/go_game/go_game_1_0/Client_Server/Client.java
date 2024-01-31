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
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.go_game.go_game_1_0.Board.StoneColor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

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
    private Circle[][] circles = new Circle[size][size];
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
        Button button4 = new Button("Odtwórz grę z bazy");

        button1.setFont(new Font(15));
        button2.setFont(new Font(15));
        button3.setFont(new Font(15));
        button4.setFont(new Font(15));

        button1.setMinSize(200,15);
        button2.setMinSize(200,15);
        button3.setMinSize(200,15);
        button4.setMinSize(200, 15);

        button1.setBackground(Background.fill(Color.rgb(59,59,59)));
        button2.setBackground(Background.fill(Color.rgb(59,59,59)));
        button3.setBackground(Background.fill(Color.rgb(59,59,59)));
        button4.setBackground(Background.fill(Color.rgb(59, 59, 59)));

        button1.setTextFill(Color.WHITE);
        button2.setTextFill(Color.WHITE);
        button3.setTextFill(Color.WHITE);
        button4.setTextFill(Color.WHITE);

        button1.setOnMouseEntered(e -> button1.setStyle("-fx-background-color: lightgray;"));
        button1.setOnMouseExited(e -> button1.setStyle("-fx-background-color: default;"));

        button2.setOnMouseEntered(e -> button2.setStyle("-fx-background-color: lightgray;"));
        button2.setOnMouseExited(e -> button2.setStyle("-fx-background-color: default;"));

        button3.setOnMouseEntered(e -> button3.setStyle("-fx-background-color: lightgray;"));
        button3.setOnMouseExited(e -> button3.setStyle("-fx-background-color: default;"));

        button4.setOnMouseEntered(e -> button4.setStyle("-fx-background-color: lightgray;"));
        button4.setOnMouseExited(e -> button4.setStyle("-fx-background-color: default;"));

        button1.setOnAction(e -> showChoseMenu());

        button2.setOnAction(e -> showChoseMenuBot());

        button3.setOnAction(e -> {
            Platform.exit();
        });

        button4.setOnAction(e -> showDatabaseGame());

        vBox.getChildren().addAll(label,button1,button2,button3, button4);
        Scene menuScene = new Scene(vBox);
        stage.setScene(menuScene);
        stage.setMinHeight(350);
        stage.setMinWidth(350);
        this.scene =menuScene;
        stage.show();
    }

    private void showDatabaseGame() {
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setBackground(Background.fill(Color.rgb(26,26,26)));
        vBox.setPadding(new Insets(10));
        vBox.setMinWidth(300);

        Label label = new Label("Goo...");
        label.setFont(Font.font(75));
        label.setTextFill(Color.WHITE);
        Label label2 = new Label("Wybierz historię gry:");
        label2.setFont(Font.font(75));
        label2.setTextFill(Color.WHITE);

        Button buttonOnline = new Button("Historia gry online");
        Button buttonBot = new Button("Historia gry z botem");

        Button button1 = new Button("9x9");
        Button button2 = new Button("13x13");
        Button button3 = new Button("19x19");

        button1.setFont(new Font(15));
        button2.setFont(new Font(15));
        button3.setFont(new Font(15));
        buttonOnline.setFont(new Font(15));
        buttonBot.setFont(new Font(15));


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
            startReplayGame();
        });

        button2.setOnAction(e -> {
            this.size = 13;
            stoneColors = new StoneColor[13][13];
            startReplayGame();
        });

        button3.setOnAction(e -> {
            this.size = 19;
            stoneColors = new StoneColor[19][19];
            startReplayGame();
        });
        vBox.getChildren().addAll(label,button1,button2,button3);
        Scene menuScene = new Scene(vBox);
        stage.setScene(menuScene);
        this.scene = menuScene;
        stage.show();
    }

    private void startReplayGame() {
        // Połączenie z bazą danych i pobranie ruchów
        String sql = "SELECT wiersz, kolumna, kolor FROM moves ORDER BY id ASC";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            if (circles == null || circles.length != size || circles[0].length != size) {
                circles = new Circle[size][size];
            }

            VBox mainVBox = new VBox(10);
            mainVBox.setAlignment(Pos.CENTER);
            mainVBox.setPadding(new Insets(10));
            mainVBox.setBackground(Background.fill(Color.rgb(26, 26, 26)));

            GridPane gridPane = new GridPane();
            gridPane.setAlignment(Pos.CENTER);
            gridPane.setPadding(new Insets(10));
            gridPane.setStyle("-fx-background-color: #DEB887;");
            gridPane.setId("gridPane");

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    circles[i][j] = new Circle(17.5, Color.BURLYWOOD);
                    circles[i][j].setStroke(Color.rgb(26, 26, 26));
                    circles[i][j].setStrokeWidth(3);
                    circles[i][j].setFill(Color.BURLYWOOD);

                    Line lineV = new Line(25, 0, 25, 50);
                    Line lineH = new Line(0, 25, 50, 25);
                    StackPane stackPane = new StackPane(lineH, lineV, circles[i][j]);
                    gridPane.add(stackPane, j, i);
                }
            }

            Button buttonBackToMenu = new Button("Wróć do menu");
            buttonBackToMenu.setFont(new Font(15));
            buttonBackToMenu.setMinWidth(150);
            buttonBackToMenu.setStyle("-fx-background-color: #595959");
            buttonBackToMenu.setTextFill(Color.WHITE);
            buttonBackToMenu.setOnMouseEntered(e -> buttonBackToMenu.setStyle("-fx-background-color: lightgray;"));
            buttonBackToMenu.setOnMouseExited(e -> buttonBackToMenu.setStyle("-fx-background-color: #595959;"));
            buttonBackToMenu.setOnAction(e -> showMenu());

            mainVBox.getChildren().addAll(gridPane, buttonBackToMenu);

            while (rs.next()) {
                int row = rs.getInt("wiersz");
                int column = rs.getInt("kolumna");
                String colorStr = rs.getString("kolor").toUpperCase();
                StoneColor color = StoneColor.valueOf(colorStr);

                if (color == StoneColor.WHITE) {
                    circles[row][column].setFill(Color.WHITE);
                } else if (color == StoneColor.BLACK) {
                    circles[row][column].setFill(Color.BLACK);
                }
            }

            Scene replayScene = new Scene(mainVBox);
            replayScene.getRoot().setStyle("-fx-background-color: rgb(26, 26, 26);");

            stage.setScene(replayScene);
            stage.setTitle("Odtwarzanie gry");
            stage.show();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showChoseMenu(){
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setBackground(Background.fill(Color.rgb(26,26,26)));
        vBox.setPadding(new Insets(10));
        vBox.setMinWidth(350);

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

    private void showChoseMenuBot() {
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setBackground(Background.fill(Color.rgb(26,26,26)));
        vBox.setPadding(new Insets(10));
        vBox.setMinWidth(350);

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
            circles = new Circle[9][9];
            startGameBot();
        });

        button2.setOnAction(e -> {
            this.size = 13;
            stoneColors = new StoneColor[13][13];
            circles = new Circle[13][13];
            startGameBot();
        });

        button3.setOnAction(e -> {
            this.size = 19;
            stoneColors = new StoneColor[19][19];
            circles = new Circle[19][19];
            startGameBot();
        });
        vBox.getChildren().addAll(label,button1,button2,button3);
        Scene menuScene = new Scene(vBox);
        stage.setScene(menuScene);
        this.scene = menuScene;
        stage.show();
    }

    private void startGameBot() {
        HBox hBox = new HBox();
        hBox.setBackground(Background.fill(Color.rgb(26,26,26)));

        myTurn = true;

        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: #DEB887");
        gridPane.setId("gridPane");  // Dodaj to ID

        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++) {
                circles[i][j] = new Circle(17.5, Color.BURLYWOOD);

                circles[i][j].setStroke(Color.rgb(26, 26, 26));
                circles[i][j].setStrokeWidth(3);
                if (stoneColors[i][j] == null) {
                    circles[i][j].setFill(Color.BURLYWOOD);
                } else if (stoneColors[i][j] == StoneColor.WHITE) {
                    circles[i][j].setFill(Color.WHITE);
                } else {
                    circles[i][j].setFill(Color.BLACK);
                }

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

                int finalI = i;
                int finalJ = j;
                circles[i][j].setOnMouseClicked(e -> makeMove(finalI, finalJ));
                StackPane stackPane = new StackPane(lineH, lineV, circles[i][j]);
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
            // -> "Poddałeś się"
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

    }

    private void makeMove(int i, int j) {
        if (myTurn && stoneColors[i][j] == null) {
            stoneColors[i][j] = myColor;
            circles[i][j].setFill(Color.BLACK);
            saveMoveToDatabase(i, j, StoneColor.BLACK);
            myTurn = false;
            botMakeMove();
        }
    }

    private void botMakeMove() {
        Random random = new Random();
        int i, j;
        do {
            i = random.nextInt(size);
            j = random.nextInt(size);
        } while (stoneColors[i][j] != null);

        stoneColors[i][j] = StoneColor.WHITE;
        final int finalI = i;
        final int finalJ = j;
        Platform.runLater(() -> circles[finalI][finalJ].setFill(Color.WHITE));
        myTurn = true;
        saveMoveToDatabase(i, j, StoneColor.WHITE);
    }

    private void saveMoveToDatabase(int row, int column, StoneColor color) {
        String sql = "INSERT INTO moves_withbot (wiersz, kolumna, kolor) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, row);
            pstmt.setInt(2, column);
            pstmt.setString(3, color.toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            waiting = false;
            rowSelected=-2;
            columnSelected=-2;
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
            int[] wyniki = recieveResults();
            continueToPlay = false;
            displayResult("The player with the black stones won!",wyniki);
        }
        else if (status == PLAYER2_WON) {
            int[] wyniki = recieveResults();
            continueToPlay = false;
            displayResult("The player with the white stones won",wyniki);
        }
        else if (status == DRAW) {
            int[] wyniki = recieveResults();
            continueToPlay = false;
            displayResult("Draw",wyniki);

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
    private int[] recieveResults() throws IOException {
        int results[] = new int[2];
        results[0] = inputStream.readInt();
        results[1] = inputStream.readInt();

        return results;
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
    private void displayResult(String resultMessage,int[] wyniki) {
        Platform.runLater(() -> {
            VBox vBox = new VBox(10);
            vBox.setAlignment(Pos.CENTER);
            vBox.setBackground(Background.fill(Color.rgb(26,26,26)));
            vBox.setPadding(new Insets(10));
            vBox.setMinWidth(300);

            Label label = new Label("Goo...");
            label.setFont(Font.font(75));
            label.setTextFill(Color.WHITE);

            Label resultLabel = new Label(resultMessage);
            resultLabel.setFont(Font.font(25));
            resultLabel.setTextFill(Color.WHITE);

            Label result = new Label("Black: "+wyniki[0]+" White: "+wyniki[1]);
            result.setFont(Font.font(25));
            result.setTextFill(Color.WHITE);

            vBox.getChildren().addAll(label,resultLabel,result);
            vBox.setAlignment(Pos.CENTER);

            Scene resultScene = new Scene(vBox);
            stage.setScene(resultScene);
            stage.setMinHeight(350);
            stage.setMinWidth(350);
            stage.setScene(resultScene);
        });
    }
}

