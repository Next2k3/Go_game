package org.go_game.go_game_1_0.ClientNew3;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private ServerSocket serverSocket;
    private Map<Integer, Socket> waitingClients;

    public Server(int port) {
        waitingClients = new HashMap<>();
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Serwer uruchomiony. Oczekiwanie na połączenia...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nowe połączenie od klienta: " + clientSocket);
                handleClient(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null && !serverSocket.isClosed()) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleClient(Socket clientSocket) throws IOException {
        DataInputStream in = new DataInputStream(clientSocket.getInputStream());
        DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

        // Odczytaj rozmiar planszy od klienta
        int boardSize = in.readInt();
        System.out.println("Klient wybrał rozmiar planszy: " + boardSize);

        if (waitingClients.containsKey(boardSize)) {
            // Jeśli istnieje inny klient oczekujący na planszę o tym samym rozmiarze, połącz ich
            Socket opponentSocket = waitingClients.get(boardSize);
            waitingClients.remove(boardSize);

            // Przypisz kolory graczom
            DataOutputStream opponentOut = new DataOutputStream(opponentSocket.getOutputStream());
            DataOutputStream clientOut = new DataOutputStream(clientSocket.getOutputStream());

            // Przypisz kolory
            int colorForClient = 1; // BLACK
            int colorForOpponent = 2; // WHITE

            // Wyslij informacje o rozpoczęciu gry oraz przydzielony kolor do obu klientów
            clientOut.writeInt(colorForClient);
            clientOut.flush();

            opponentOut.writeInt(colorForOpponent);
            opponentOut.flush();

            new GameSession(clientSocket, opponentSocket,boardSize).start();
        } else {
            // Dodaj klienta do oczekujących
            waitingClients.put(boardSize, clientSocket);
        }
    }

    public static void main(String[] args) {
        int port = 8000; // Możesz dostosować numer portu według potrzeb
        new Server(port);
    }
}

