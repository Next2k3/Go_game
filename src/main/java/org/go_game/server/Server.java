package org.go_game.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int PLAYER1 = 1;
    public static final int PLAYER2 = 2;
    public static void main(String[] args){
        try(ServerSocket serverSocket = new ServerSocket(8000)){
            System.out.println("Server is listening on port 8000");

            while(true){
                Socket firstPlayer = serverSocket.accept();
                System.out.println("Player 1 joined session");
                new DataOutputStream(firstPlayer.getOutputStream()).writeInt(PLAYER1);

                Socket secondPlayer = serverSocket.accept();
                System.out.println("Player 2 joined session");
                new DataOutputStream(secondPlayer.getOutputStream()).writeInt(PLAYER2);

                Session task = new Session(firstPlayer,secondPlayer);
                Thread t1 = new Thread(task);
                t1.start();
            }
        }catch(IOException ex){
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
