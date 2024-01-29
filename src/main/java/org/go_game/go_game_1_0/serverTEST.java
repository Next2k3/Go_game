package org.go_game.go_game_1_0;

import java.io.*;
import java.net.*;
import java.util.*;
import java.io.*;
import java.net.*;
import java.util.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class serverTEST {
    private ServerSocket serverSocket;
    private List<Socket> clients = new ArrayList<>();

    public serverTEST(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Serwer uruchomiony na porcie " + port);
    }

    public void start() throws IOException {
        while (true) {
            Socket clientSocket = serverSocket.accept();
            clients.add(clientSocket);
            new clienthandlerTEST(clientSocket, this).start();

            if (clients.size() == 2) {
                startGame();
            }
        }
    }

    private void startGame() throws IOException {
        PrintWriter outFirst = new PrintWriter(clients.get(0).getOutputStream(), true);
        outFirst.println("YOUR_TURN");

        PrintWriter outSecond = new PrintWriter(clients.get(1).getOutputStream(), true);
        outSecond.println("WAIT");
    }

    public void broadcastMessage(String message, Socket sender) throws IOException {
        for (Socket clientSocket : clients) {
            if (clientSocket != sender) {
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println(message);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        int port = 22565; // Przykładowy port
        serverTEST server = new serverTEST(port);
        server.start();
    }
}
