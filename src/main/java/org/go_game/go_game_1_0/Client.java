package org.go_game.go_game_1_0;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client extends Application {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) throws Exception {
        // Inicjalizacja połączenia sieciowego
        new Thread(() -> {
            try {
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                Scanner scanner = new Scanner(System.in);
                System.out.println("Connected to server. Type your messages:");

                // Wątek do odbierania wiadomości od serwera
                new Thread(() -> {
                    try {
                        String line;
                        while ((line = in.readLine()) != null) {
                            System.out.println(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();

                // Wątek do wysyłania wiadomości do serwera
                while (scanner.hasNextLine()) {
                    out.println(scanner.nextLine());
                }

                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // Uruchamianie JavaFX w głównym wątku
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        MenuScene menuScene = new MenuScene(stage);
        stage.setScene(menuScene.getScene());
        stage.show();
    }
}
