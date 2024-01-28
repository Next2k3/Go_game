package org.go_game.go_game_1_0;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server extends Application {
    private static final int PORT = 12345;
    private static Set<PrintWriter> writers = new HashSet<>();
    private TextArea logTextArea;

    public static final int PLAYER1 = 1;
    public static final int PLAYER2 = 2;

    public static void main(String[] args) throws Exception {
        System.out.println("Server is running...");
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Tworzenie TextArea do wyświetlania logów
        TextArea logTextArea = new TextArea();
        logTextArea.setEditable(false);

        // Tworzenie ScrollPane
        ScrollPane scrollPane = new ScrollPane(logTextArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        // Tworzenie BorderPane i dodanie ScrollPane
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(scrollPane);

        // Ustawienie sceny
        Scene scene = new Scene(borderPane, 500, 300);
        stage.setTitle("Server Logs");
        stage.setScene(scene);
        stage.show();

        // Uruchomienie logiki serwera w nowym wątku
        new Thread(this::runServer).start();
        }

    private void runServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            log("Server started on port: " + PORT);
            int sessionNum = 1;
            while (true) {
                log("Waiting for players...");

                Socket firstPlayer = serverSocket.accept();
                log("First player joined session " + sessionNum);
                new DataOutputStream(firstPlayer.getOutputStream()).writeInt(PLAYER1);

                Socket secondPlayer = serverSocket.accept();
                log("Second player joined session " + sessionNum);
                new DataOutputStream(secondPlayer.getOutputStream()).writeInt(PLAYER2);

                log("Starting a thread for session " + sessionNum++ +"...\n");
                NewSession task = new NewSession(firstPlayer, secondPlayer);
                Thread t1 = new Thread(task);
                t1.start();


            }
        } catch (IOException ex) {
            log("Error: " + ex.getMessage());
        }
    }
    private void log(String message) {
        // Wykonywanie aktualizacji GUI w wątku JavaFX
        javafx.application.Platform.runLater(() -> {
            logTextArea.appendText(message + "\n");
        });
    }

}