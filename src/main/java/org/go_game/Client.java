package org.go_game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;



public class Client extends Application implements Runnable {
    private DataInputStream fromServer;
    private DataOutputStream toServer;

    Socket socket;

    public static void main(String[] args){
        launch(args);
    }
    private void connectToServer(){
        try {
            socket = new Socket("localhost", 8000);
            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());

        }
        catch (IOException ex) {
            System.err.println(ex);
        }

        Thread thread = new Thread(this);
        thread.start();
    }


    @Override
    public void run() {

    }

    @Override
    public void start(Stage stage) {
        Client client = new Client();
        client.connectToServer();
    }

}
