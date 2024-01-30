package org.go_game.go_game_1_0.ClientNew2;

import org.go_game.go_game_1_0.Board.Board;

import java.io.*;
import java.net.Socket;

public class Session implements Runnable {
    private Socket firstPlayer;
    private Socket secondPlayer;
    private Board board;
    public Session(Socket firstPlayer, Socket secondPlayer, int size){
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    @Override
    public void run() {
        try{
            DataInputStream fromPlayer1 = new DataInputStream(firstPlayer.getInputStream());
            DataOutputStream toPlayer1 = new DataOutputStream(firstPlayer.getOutputStream());
            DataInputStream fromPlayer2 = new DataInputStream(secondPlayer.getInputStream());
            DataOutputStream toPlayer2 = new DataOutputStream(secondPlayer.getOutputStream());





        }catch (IOException ex) {
            System.err.println("ex");
        }
    }
}
