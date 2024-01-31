package org.go_game.go_game_1_0.Client_Server;

import org.go_game.go_game_1_0.Board.Board;
import org.go_game.go_game_1_0.Board.Stone;
import org.go_game.go_game_1_0.Board.StoneColor;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
        int skips = 0;
        try {
            DataInputStream fromPlayer1 = new DataInputStream(player1Socket.getInputStream());
            DataOutputStream toPlayer1 = new DataOutputStream(player1Socket.getOutputStream());
            DataInputStream fromPlayer2 = new DataInputStream(player2Socket.getInputStream());
            DataOutputStream toPlayer2 = new DataOutputStream(player2Socket.getOutputStream());



            while(true){
                if(skips==2){
                    if(board.zliczPunkty()[0]>board.zliczPunkty()[1]){
                        toPlayer1.writeInt(PLAYER1_WON);
                        toPlayer2.writeInt(PLAYER1_WON);
                    }else if(board.zliczPunkty()[0]<board.zliczPunkty()[1]){
                        toPlayer1.writeInt(PLAYER2_WON);
                        toPlayer2.writeInt(PLAYER2_WON);
                    }else{
                        toPlayer1.writeInt(DRAW);
                        toPlayer2.writeInt(DRAW);
                    }
                }else {
                    skips = 0;
                    int row = fromPlayer1.readInt();
                    int column = fromPlayer1.readInt();

                    if (row != -1 && column != -1) {
                        while (!board.placeStoneAndUpdateGroups(row, column, new Stone(StoneColor.BLACK, row, column))) {
                            toPlayer1.writeInt(UNCORRECTMOVE);
                            row = fromPlayer1.readInt();
                            column = fromPlayer1.readInt();

                        }
                        board.placeStoneAndUpdateGroups(row, column, new Stone(StoneColor.BLACK, row, column));
                        saveMoveToDatabase(row, column, "Black");
                    } else {
                        board.changeMoveColor();
                        skips++;
                    }
                    toPlayer1.writeInt(CORRECTMOVE);
                    sendMove(toPlayer1);

                    toPlayer2.writeInt(CONTINUE);
                    sendMove(toPlayer2);

                    row = fromPlayer2.readInt();
                    column = fromPlayer2.readInt();

                    if (row != -1 && column != -1) {
                        while (!board.placeStoneAndUpdateGroups(row, column, new Stone(StoneColor.WHITE, row, column))) {
                            toPlayer2.writeInt(UNCORRECTMOVE);
                            row = fromPlayer2.readInt();
                            column = fromPlayer2.readInt();
                        }
                        board.placeStoneAndUpdateGroups(row, column, new Stone(StoneColor.WHITE, row, column));
                        saveMoveToDatabase(row, column, "White");
                    } else {
                        board.changeMoveColor();
                        skips++;
                    }

                    toPlayer2.writeInt(CORRECTMOVE);
                    sendMove(toPlayer2);

                    toPlayer1.writeInt(CONTINUE);
                    sendMove(toPlayer1);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void saveMoveToDatabase(int row, int column, String color) {
        String sql = "INSERT INTO moves (wiersz, kolumna, kolor) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, row);
            pstmt.setInt(2, column);
            pstmt.setString(3, color);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    private void sendMove(DataOutputStream out) throws IOException {
        String tablica =board.getBoardToString();
        out.writeUTF(tablica);
    }
}


