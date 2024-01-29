package org.go_game.go_game_1_0.ClientApp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Application {
    private TextArea logArea = new TextArea();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tic Tac Toe Server");

        logArea.setEditable(false);
        Scene scene = new Scene(logArea, 400, 300);
        primaryStage.setScene(scene);

        primaryStage.show();

        startServer();
    }

    private void startServer() {
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(12345);
                log("Server started.");

                while (true) {
                    Socket socket = serverSocket.accept();
                    log("Client connected: " + socket.getInetAddress());

                    new Thread(() -> handleClient(socket)).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void handleClient(Socket socket) {
        try (
                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream())
        ) {
            while (true) {
                // Oczekiwanie na dane od klienta
                Object data = input.readObject();

                if (data instanceof int[]) {
                    int[] coordinates = (int[]) data;

                    // Przetwarzanie otrzymanych danych (wysyłanie do wszystkich klientów, itp.)
                    // Tutaj możesz użyć coordinates[0] i coordinates[1] jako współrzędnych

                    // Przykład:
                    log("Received from client: " + coordinates[0] + ", " + coordinates[1]);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void log(String message) {
        // Logowanie do TextArea na wątku UI
        logArea.appendText(message + "\n");
    }
}

