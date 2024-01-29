package org.go_game.go_game_1_0;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class clienthandlerTEST extends Thread {
    private Socket clientSocket;
    private serverTEST server;

    public clienthandlerTEST(Socket socket, serverTEST server) {
        this.clientSocket = socket;
        this.server = server;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if ("BUTTON_PRESSED".equals(inputLine)) {
                    server.broadcastMessage("YOUR_TURN", clientSocket);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}