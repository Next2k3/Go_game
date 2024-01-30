package org.go_game.go_game_1_0.ClientApp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
        // Przekazanie ruchu do innych klientów
        for (Socket client : clients) {
            if (client != sender) {
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                out.println(move + "," + kolor);
            }
        }
        saveMoveToDatabase(move, kolor);

    }

    private void saveMoveToDatabase(String move, String kolor) {
        String jdbcURL = "jdbc:mysql://localhost:3306/test";
        String username = "root";
        String password = "cimvyD-4warje-siqcux";

        System.out.println("Próba zapisu ruchu do bazy danych...");

        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
            System.out.println("Połączenie z bazą danych udane.");

            String sql = "INSERT INTO ruchy (ruch, kolor) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, move);
                preparedStatement.setString(2, kolor);
                preparedStatement.executeUpdate();
                System.out.println("Ruch zapisany do bazy danych: Ruch = " + move + ", Kolor = " + kolor);
            }
        } catch (SQLException e) {
            System.err.println("Błąd podczas zapisywania ruchu do bazy danych: " + e.getMessage());
            e.printStackTrace();
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