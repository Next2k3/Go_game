package org.go_game.go_game_1_0;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 12345;
    private static Set<PrintWriter> writers = new HashSet<>();

    public static final int PLAYER1 = 1;
    public static final int PLAYER2 = 2;

    public static void main(String[] args) throws Exception {
        System.out.println("Server is running...");
        Server display = new Server();
    }

    public Server() {
        // Wyglad servera w JavaFX

        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            // wyswietlenie "rozpoczecie serwera na porcie: 12345"
            int sessionNum = 1;
            while (true) {
                // wyswietlenie "oczekiwanie na graczy"

                Socket firstPlayer = serverSocket.accept();
                // wyswietlenie "pierwszy gracz dolaczyl do sesji"
                new DataOutputStream(firstPlayer.getOutputStream()).writeInt(PLAYER1);

                Socket secondPlayer = serverSocket.accept();
                // wysiwetlenie "drugi gracz dolaczyl"
                new DataOutputStream(secondPlayer.getOutputStream()).writeInt(PLAYER2);

                // Nowy wątek dla dwóch graczy
                // wyswietlenie: "Tworzenie nowej sesji dla graczy"
                NewSession task = new NewSession(firstPlayer, secondPlayer);
                Thread t1 = new Thread(task);
                t1.start();

            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

}