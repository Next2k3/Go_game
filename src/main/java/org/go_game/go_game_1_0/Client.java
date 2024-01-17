package org.go_game.go_game_1_0;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client extends Application {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static final int PLAYER1 = 1;
    public static final int PLAYER2 = 2;
    public static final int PLAYER1_WON = 1;
    public static final int PLAYER2_WON = 2;
    public static final int DRAW = 3;

    private boolean myTurn = false;
    private boolean waiting = true;
    private boolean continueToPlay = true;

    private DataInputStream fromServer;
    private DataOutputStream toServer;



    public static void main(String[] args) throws Exception {                               // DO ZMIANY
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

    private void connectToServer() {

    }

    private void waitForPlayerAction() throws InterruptedException {
        while (waiting) {
            Thread.sleep(100);
        }
        waiting = true;
    }

    private void sendMove() throws IOException {

    }

    private void receiveInfoFromServer() throws IOException {

    }

    private void receiveMove() throws IOException {

    }

    @Override
    public void start(Stage stage) throws Exception {
        MenuScene menuScene = new MenuScene(stage);
        stage.setScene(menuScene.getScene());
        stage.show();
    }
}
