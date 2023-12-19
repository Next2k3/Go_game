package org.go_game;

import javafx.application.Application;
import javafx.stage.Stage;
import org.go_game.board.Board;
import org.go_game.board.StoneType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Application implements Runnable{
    public static final int PLAYER1 = 1;
    public static final int PLAYER2 = 2;
    public static final int PLAYER1_WON = 1;
    public static final int PLAYER2_WON = 2;
    public static final int DRAW = 3;
    Socket socket;
    private StoneType myStoneType, otherStoneType;
    private boolean myTurn = false;
    private Board board = new Board(9);
    private int rowSelected;
    private int columnSelected;
    private DataInputStream fromServer;
    private DataOutputStream toServer;
    private boolean continueToPlay = true;
    private boolean waiting = true;
    public static void main(String[] args){
        launch(args);
    }
    private void connectToServer(){
        try{
            socket = new Socket("localhost",8000);
            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex){
            System.err.println(ex);
        }
        Thread thread = new Thread(this);
        thread.start();
    }
    @Override
    public void run() {
        try{
            int player = fromServer.readInt();

            if(player == PLAYER1){

            }
            else if(player == PLAYER2){

            }

            while(continueToPlay){
                if(player == PLAYER1){
                    waitForPlayerAction();
                    sendMove();
                    recieveInfoFromServer();
                }
                else if(player == PLAYER2){
                    recieveInfoFromServer();
                    waitForPlayerAction();
                    sendMove();
                }
            }
        }catch(IOException ex){
            System.err.println(ex);
        } catch (InterruptedException ex) {}
    }
    private void waitForPlayerAction() throws InterruptedException {
        while (waiting) {
            Thread.sleep(100);
        }
        waiting = true;
    }
    private void sendMove() throws IOException{
        toServer.writeInt(rowSelected);
        toServer.writeInt(columnSelected);
    }
    private void recieveInfoFromServer() throws IOException{
        int status = fromServer.readInt();
        if(status == PLAYER1_WON){
            continueToPlay = false;
            if(myStoneType ==  StoneType.BLACK){

            }
            else if(myStoneType == StoneType.WHITE){

                recieveMove();
            }
        }
        else if(status == PLAYER2_WON){
            continueToPlay = false;
            if(myStoneType ==  StoneType.WHITE){

            }
            else if(myStoneType == StoneType.BLACK){

                recieveMove();
            }
        }
        else if(status == DRAW){
            continueToPlay = false;

            if (myStoneType == StoneType.WHITE) {
                recieveMove();
            }
        }
        else{
            recieveMove();
            myTurn = true;
        }
    }
    private void recieveMove() throws IOException {
        int row = fromServer.readInt();
        int column = fromServer.readInt();
        board.addStone(row,column,otherStoneType);
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}