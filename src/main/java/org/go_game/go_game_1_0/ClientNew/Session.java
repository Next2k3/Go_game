package org.go_game.go_game_1_0.ClientNew;

import org.go_game.go_game_1_0.Board.Board;
import org.go_game.go_game_1_0.Board.StoneColor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Session implements Runnable{
    public static int PLAYER1_WON = 1;
    public static int PLAYER2_WON = 2;
    public static int DRAW = 3;
    public static int CONTINUE = 4;
    private Socket firstPlayer;
    private Socket secondPlayer;
    private StoneColor[][] stoneColors;
    private Board board;
    private int SIZE;

    public Session(Socket firstPlayer,Socket secondPlayer,int size){
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.SIZE = size;
        stoneColors = new StoneColor[size][size];
        board = new Board(size);
    }
    @Override
    public void run() {
        try {
            DataInputStream fromPlayer1 = new DataInputStream(firstPlayer.getInputStream());
            DataOutputStream toPlayer1 = new DataOutputStream(firstPlayer.getOutputStream());
            DataInputStream fromPlayer2 = new DataInputStream(secondPlayer.getInputStream());
            DataOutputStream toPlayer2 = new DataOutputStream(secondPlayer.getOutputStream());

            toPlayer1.writeInt(1);
            toPlayer2.writeInt(2);

            while(true){
                System.out.println("XD0");
                int row = fromPlayer1.readInt();
                int column = fromPlayer1.readInt();

                stoneColors[row][column] = StoneColor.BLACK;
                toPlayer2.writeInt(CONTINUE);
                sendMove(toPlayer2, row, column);
               // toPlayer1.writeInt(1);
                System.out.println("XD");


                row = fromPlayer2.readInt();
                column = fromPlayer2.readInt();

                stoneColors[row][column] = StoneColor.WHITE;
                toPlayer1.writeInt(CONTINUE);
                sendMove(toPlayer1,row,column);
               // toPlayer2.writeInt(1);
                System.out.println("XD2");
            }
        }catch (IOException ex) {
            System.err.println("ex");
        }
    }
    private void sendMove(DataOutputStream out, int row, int column) throws IOException {
        out.writeInt(row);
        out.writeInt(column);
    }
}
