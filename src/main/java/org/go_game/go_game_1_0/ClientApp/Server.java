package org.go_game.go_game_1_0.ClientApp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server  {
    private ServerSocket serverSocket;
    private List<Socket> clients;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        clients = new ArrayList<>();
    }

    public void start() throws IOException {

        while (true) {
            Socket clientSocket = serverSocket.accept();
            clients.add(clientSocket);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            // Przypisuje Czarne pierwszemu, Białe drugiemu
            out.println(clients.size() == 1 ? "CZ" : "B");
            new ClientHandler(clientSocket, this).start();

        }
    }

    public synchronized void makeMove(String move, Socket sender) throws IOException {
        String kolor = clients.indexOf(sender) == 0 ? "CZ" : "B";
        for (Socket client : clients) {
            if (client != sender) {
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                out.println(move + "," + kolor);
            }
        }
    }
    public static void main(String[] args) throws IOException {
        Server server = new Server(6789);
        server.start();
    }

    private class ClientHandler extends Thread {
        private Socket clientSocket;
        private Server server;

        public ClientHandler(Socket socket, Server server) {
            this.clientSocket = socket;
            this.server = server;
        }

        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    server.makeMove(inputLine, clientSocket);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

