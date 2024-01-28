package org.go_game.go_game_1_0;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import org.go_game.go_game_1_0.Board.Stone;
import org.go_game.go_game_1_0.Board.StoneColor;

public class BoardPane {
    private int x;
    private int y;
    private boolean changed = false;
    private final int size;
    private final Color BACKGROUD_COLOR=Color.web("#262626");
    private final Color BOARD_COLOR= Color.BURLYWOOD;
    StoneColor[][] stoneColors;
    public BoardPane(int size, StoneColor[][] stoneColors){
        this.size = size;
        this.stoneColors = stoneColors;
    }
    public void setStoneColors(StoneColor[][] stoneColors){
        this.stoneColors = stoneColors;
    }
    public void setStoneColors(String stoneColors){


    }
    public void setStoneColor(int row,int col,StoneColor stoneColor){
        stoneColors[row][col]= stoneColor;
    }
    public GridPane getBoardPane(){
        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: #DEB887");

        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++) {
                Circle circle = new Circle(17.5);

                circle.setStroke(BACKGROUD_COLOR);
                circle.setStrokeWidth(3);
                if(stoneColors[i][j]==null) {
                    circle.setFill(BOARD_COLOR);
                }else if(stoneColors[i][j]==StoneColor.WHITE){
                    circle.setFill(Color.WHITE);
                }else{
                    circle.setFill(Color.BLACK);
                }

                circle.setOnMouseClicked(e->{
                    for(int k=0;k< gridPane.getChildren().size();k++){
                        if(gridPane.getChildren().get(k)==circle){
                            x = (int) gridPane.getChildren().get(k).getLayoutX();
                            y = (int) gridPane.getChildren().get(k).getLayoutY();
                            changed = true;
                        }
                    }
                }
                );
                Line lineV = new Line();
                lineV.setStartX(25);
                lineV.setStartY(0);
                lineV.setEndX(25);
                lineV.setEndY(50);

                Line lineH = new Line();
                lineH.setStartX(0);
                lineH.setStartY(25);
                lineH.setEndX(50);
                lineH.setEndY(25);

                lineV.setStrokeWidth(5);
                lineH.setStrokeWidth(5);

                StackPane stackPane = new StackPane(lineH,lineV,circle);
                gridPane.add(stackPane, j, i);

                GridPane.setHalignment(stackPane, HPos.CENTER);
                GridPane.setValignment(stackPane, VPos.CENTER);

            }
        }
        return gridPane;
    }
}
