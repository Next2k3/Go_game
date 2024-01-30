package org.go_game.go_game_1_0.ClientNew;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.go_game.go_game_1_0.NewSession;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;


public class Server extends Application {
    public static final int PLAYER1 = 1;
    public static final int PLAYER2 = 2;
    private TextArea logArea = new TextArea();
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
            ServerSocket serverSocket = new ServerSocket(8000);
            appendMessage(new Date() + ":     Server started at socket 8000\n");
            int sessionNum = 1;
            while (true) {
                appendMessage(new Date() + ":     Waiting for players to join session " + sessionNum + "\n");

                //connection to player1
                Socket firstPlayer = serverSocket.accept();
                appendMessage(new Date() + ":     Player 1 joined session " + sessionNum + ". Player 1's IP address " + firstPlayer.getInetAddress().getHostAddress() + "\n");
                //notify first player that he is first player
                new DataOutputStream(firstPlayer.getOutputStream()).writeInt(PLAYER1);

                //connection to player2
                Socket secondPlayer = serverSocket.accept();
                appendMessage(new Date() + ":     Player 2 joined session " + sessionNum + ". Player 2's IP address " + secondPlayer.getInetAddress().getHostAddress() + "\n");
                //notify second player that he is the second player
                new DataOutputStream(secondPlayer.getOutputStream()).writeInt(PLAYER2);

                //starting the thread for two players
                appendMessage(new Date() + ":     Starting a thread for session " + sessionNum++ + "...\n");
                Session task = new Session(firstPlayer, secondPlayer,9);
                Thread t1 = new Thread(task);
                t1.start();
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    private void appendMessage(String message) {
        Platform.runLater(() -> logArea.appendText(message));
    }
}
