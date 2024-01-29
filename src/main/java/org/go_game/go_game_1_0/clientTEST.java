package org.go_game.go_game_1_0;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class clientTEST extends Application {
    private Button button;
    private PrintWriter out;
    private BufferedReader in;
    private boolean myTurn = false;

    @Override
    public void start(Stage primaryStage) {
        try {
            Socket socket = new Socket("localhost", 22565);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            new Thread(() -> {
                try {
                    String line;
                    while ((line = in.readLine()) != null) {
                        String finalLine = line;
                        Platform.runLater(() -> {
                            if (finalLine.equals("YOUR_TURN")) {
                                myTurn = true;
                                button.setDisable(false);
                            }
                        });
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        button = new Button("Naciśnij mnie");
        button.setDisable(true);
        button.setOnAction(e -> {
            button.setDisable(true);
            myTurn = false;
            out.println("BUTTON_PRESSED");
        });

        StackPane root = new StackPane();
        root.getChildren().add(button);

        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.setTitle("Klient");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
