package org.go_game.go_game_1_0.Client_Server;

import org.go_game.go_game_1_0.Board.Board;
import org.go_game.go_game_1_0.Board.Stone;
import org.go_game.go_game_1_0.Board.StoneColor;

import java.io.*;
import java.net.Socket;

public class GameSession extends Thread {
    public static int PLAYER1_WON = 1;
    public static int PLAYER2_WON = 2;
    public static int DRAW = 3;
    public static final int CORRECTMOVE = 4;
    private static final int UNCORRECTMOVE = 5;
    public static int CONTINUE = 6;
    private Socket player1Socket;
    private Socket player2Socket;
    private int boardSize;
    private Board board;

    public GameSession(Socket player1Socket, Socket player2Socket,int boardSize) {
        this.player1Socket = player1Socket;
        this.player2Socket = player2Socket;
        this.boardSize = boardSize;
        board = new Board(boardSize);
    }

    @Override
    public void run() {
        try {
            DataInputStream fromPlayer1 = new DataInputStream(player1Socket.getInputStream());
            DataOutputStream toPlayer1 = new DataOutputStream(player1Socket.getOutputStream());
            DataInputStream fromPlayer2 = new DataInputStream(player2Socket.getInputStream());
            DataOutputStream toPlayer2 = new DataOutputStream(player2Socket.getOutputStream());



            while(true){
                int row = fromPlayer1.readInt();
                int column = fromPlayer1.readInt();

                while(!board.isPosibleMove(row,column,StoneColor.BLACK)){
                    toPlayer1.writeInt(UNCORRECTMOVE);
                    row = fromPlayer1.readInt();
                    column = fromPlayer1.readInt();

                }
                board.placeStoneAndUpdateGroups(row,column,new Stone(StoneColor.BLACK,row,column));

                toPlayer1.writeInt(CORRECTMOVE);
                sendMove(toPlayer1);

                toPlayer2.writeInt(CONTINUE);
                sendMove(toPlayer2);


                row = fromPlayer2.readInt();
                column = fromPlayer2.readInt();

                while(!board.isPosibleMove(row,column,StoneColor.WHITE)){
                    toPlayer1.writeInt(UNCORRECTMOVE);
                    row = fromPlayer2.readInt();
                    column = fromPlayer2.readInt();
                }
                board.placeStoneAndUpdateGroups(row,column,new Stone(StoneColor.WHITE,row,column));

                toPlayer2.writeInt(CORRECTMOVE);
                sendMove(toPlayer2);

                toPlayer1.writeInt(CONTINUE);
                sendMove(toPlayer1);


            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void sendMove(DataOutputStream out) throws IOException {
        String tablica =board.getBoardToString();
        out.writeUTF(tablica);
    }
}


