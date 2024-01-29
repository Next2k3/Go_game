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
//    private TextArea logArea = new TextArea();
    private ServerSocket serverSocket;
    private List<Socket> clients;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        clients = new ArrayList<>();
    }


//    @Override
    public void start() throws IOException {
//        primaryStage.setTitle("Tic Tac Toe Server");
//
//        logArea.setEditable(false);
//        Scene scene = new Scene(logArea, 400, 300);
//        primaryStage.setScene(scene);
//
//        primaryStage.show();

        while (true) {
            Socket clientSocket = serverSocket.accept();
            clients.add(clientSocket);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            // Przypisuje Czarne pierwszemu, Białe drugiemu
            out.println(clients.size() == 1 ? "CZ" : "B");
            new ClientHandler(clientSocket, this).start();

        }

        //startServer();
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
//        launch(args);
    }

//    private void startServer() {
//        new Thread(() -> {
//            try {
//                ServerSocket serverSocket = new ServerSocket(12345);
//                log("Server started.");
//
//                while (true) {
//                    Socket socket = serverSocket.accept();
//                    log("Client connected: " + socket.getInetAddress());
//
//                    new Thread(() -> handleClient(socket)).start();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }).start();
//    }

//    private void handleClient(Socket socket) {
//        try (
//                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
//                ObjectInputStream input = new ObjectInputStream(socket.getInputStream())
//        ) {
//            while (true) {
//                // Oczekiwanie na dane od klienta
//                Object data = input.readObject();
//
//                if (data instanceof int[]) {
//                    int[] coordinates = (int[]) data;
//
//                    // Przetwarzanie otrzymanych danych (wysyłanie do wszystkich klientów, itp.)
//                    // Tutaj możesz użyć coordinates[0] i coordinates[1] jako współrzędnych
//
//                    // Przykład:
//                    log("Received from client: " + coordinates[0] + ", " + coordinates[1]);
//                }
//            }
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

//    private void log(String message) {
//        // Logowanie do TextArea na wątku UI
//        logArea.appendText(message + "\n");
//    }

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

