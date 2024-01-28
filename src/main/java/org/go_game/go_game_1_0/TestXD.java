package org.go_game.go_game_1_0;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.go_game.go_game_1_0.Board.Stone;
import org.go_game.go_game_1_0.Board.StoneColor;


public class TestXD extends Application {
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

        Scene scene = new Scene(boardPane.getBoardPane()) ;
        stage.setScene(scene);
        stage.show();
    }
}
