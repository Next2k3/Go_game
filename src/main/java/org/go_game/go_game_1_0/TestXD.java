package org.go_game.go_game_1_0;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.go_game.go_game_1_0.Board.StoneColor;


public class TestXD extends Application implements Runnable {
    public static int x;
    public static int y;
    public static void main(String[] args) {
        launch(args);
    }
        @Override
    public void start(Stage stage) {
        StoneColor[][] stoneColors = new StoneColor[9][9];
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                stoneColors[i][j]=null;
            }
        }
        stoneColors[1][1]= StoneColor.WHITE;

        BoardPane boardPane = new BoardPane(9,stoneColors);
        boardPane.addXYChangeListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            x = boardPane.getX();
            y = boardPane.getY();
            boardPane.setStoneColor(x,y,StoneColor.BLACK);
            stage.setScene(new Scene(boardPane.getBoardPane()));
            System.out.println("Updated x and y in TestXD: " + x + " " + y);
        });
        Scene scene = new Scene(boardPane.getBoardPane()) ;
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void run() {
    }
}
