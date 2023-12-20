package org.go_game.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Session implements Runnable{
    private Socket firstPlayer;
    private Socket secondPlayer;

    public Session(Socket firstPlayer, Socket secondPlayer){
        this.firstPlayer=firstPlayer;
        this.secondPlayer=secondPlayer;
        System.out.println("XD");
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
