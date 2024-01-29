package org.go_game.go_game_1_0;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.go_game.go_game_1_0.Board.Stone;
import org.go_game.go_game_1_0.Board.StoneColor;

import java.io.*;
import java.net.*;

public class Client extends Application implements Runnable {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static final int PLAYER1 = 1;
    public static final int PLAYER2 = 2;
    public static final int PLAYER1_WON = 1;
    public static final int PLAYER2_WON = 2;
    public static int DRAW = 3;

    private boolean myTurn = false;
    private boolean waiting = true;
    private boolean continueToPlay = true;
    private Stone [][] stone = new Stone[9][9];
    private int rowSelected;
    private int columnSelected;

    private DataInputStream fromServer;
    private DataOutputStream toServer;
    Socket socket;

    StoneColor stoneColor;

    private StoneColor myColor = null;
    private StoneColor otherColor = null;


    private BoardPane boardPane;
    public static void main(String[] args) throws Exception {
//        Client display = new Client();
//        // ewwntualne ustawienie wielkosci planszy itd.
//        display.init();

        // Uruchamianie JavaFX w głównym wątku
         launch(args);
    }

    @Override
    public void start(Stage stage) {
        StoneColor[][] stoneColors = new StoneColor[9][9];
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                stoneColors[i][j]=null;
            }
        }
        stoneColors[1][1]= StoneColor.WHITE;

        BoardPane boardPane = new BoardPane(9,stoneColors);
        boardPane.addXYChangeListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            rowSelected = boardPane.getX();
            columnSelected = boardPane.getY();
            boardPane.setStoneColor(rowSelected,columnSelected,StoneColor.BLACK);
            stage.setScene(new Scene(boardPane.getBoardPane()));
            System.out.println("Updated x and y in TestXD: " + rowSelected + " " + columnSelected);
        });
        Scene scene = new Scene(boardPane.getBoardPane()) ;
        stage.setScene(scene);
        stage.show();

        connectToServer();
    }

    private void connectToServer() {
        try {
            socket = new Socket("localhost", 12345);
            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            System.err.println(ex);
        }

        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try {
            int player = fromServer.readInt();

            if (player == PLAYER1) {
                myColor = StoneColor.WHITE;
                otherColor = StoneColor.BLACK;
                // logika w grze

                fromServer.readInt();

                myTurn = true;
            } else if (player == PLAYER2) {
                myColor = StoneColor.BLACK;
                otherColor = StoneColor.WHITE;


            }

            while (continueToPlay) {
                if (player == PLAYER1) {
                    waitForPlayerAction();
                    sendMove();
                    receiveInfoFromServer();
                } else if (player == PLAYER2) {
                    receiveInfoFromServer();
                    waitForPlayerAction();
                    sendMove();
                }
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }  catch (InterruptedException ex) {}

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
    }

    private void receiveInfoFromServer() throws IOException {
        int status = fromServer.readInt();
        if (status == PLAYER1_WON) {
            continueToPlay = false;
            if (stoneColor == StoneColor.WHITE) {
                // wyswietlenie na planszy wygranego i jego koloru
            } else if (stoneColor == StoneColor.BLACK) {
                // wyswietlenie ze przeciwnik wygral
                receiveMove();
            }
        } else if (status == PLAYER2_WON) {
            continueToPlay = false;
            if (stoneColor == StoneColor.WHITE) {
                // wyswietlenie wygranej i koloru gracza
            } else if (stoneColor == StoneColor.BLACK) {
                // wyswietlenie wygranej przeciwnika i jego koloru
                receiveMove();
            }
        } else if (status == DRAW ) {
            continueToPlay = false;
            // wyswietlenie informacji o remisie

            if (stoneColor == StoneColor.WHITE) {
                receiveMove();
            } else {
                receiveMove();
                // wyswietlenie: Moja tura
                myTurn = true;
            }
        }
    }

    private void receiveMove() throws IOException {
        int row = fromServer.readInt();
        int column = fromServer.readInt();
        if(myColor==StoneColor.BLACK) {
            boardPane.setStoneColor(row, column,StoneColor.WHITE );
        }else{
            boardPane.setStoneColor(row,column,StoneColor.BLACK);
        }
    }

//    @Override
//    public void start(Stage stage) throws Exception {
//        MenuScene menuScene = new MenuScene(stage);
//        stage.setScene(menuScene.getScene());
//        stage.show();
//    }
}
