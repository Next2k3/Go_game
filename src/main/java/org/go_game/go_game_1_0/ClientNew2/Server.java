package org.go_game.go_game_1_0.ClientNew2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.go_game.go_game_1_0.ClientNew.Session;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Server extends Application {
    private TextArea logArea = new TextArea();
    private Map<Integer, Socket> waitingClients;
    private ServerSocket serverSocket;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        logArea = new TextArea();
        BorderPane root = new BorderPane();
        root.setCenter(logArea);

        primaryStage.setTitle("Server");
        primaryStage.setScene(new Scene(root, 550, 300));
        primaryStage.setOnCloseRequest(e -> System.exit(0)); // Terminate the application on window close
        primaryStage.show();

        new Thread(this::startServer).start();
    }
    private void startServer() {
        try {
            waitingClients = new HashMap<>();
            ServerSocket serverSocket = new ServerSocket(8000);
            appendMessage(new Date() + ":     Server started at socket 8000\n");
            int sessionNum = 1;
            while (true) {
                Socket clientSocket = serverSocket.accept();
                appendMessage("Nowe połącznie od klienta: "+ clientSocket);
                handleClient(clientSocket);
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }finally {
            try {
                if (serverSocket != null && !serverSocket.isClosed()) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void appendMessage(String message) {
        Platform.runLater(() -> logArea.appendText(message));
    }
}
