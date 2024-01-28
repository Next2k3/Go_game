package org.go_game.go_game_1_0;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import org.go_game.go_game_1_0.Board.StoneColor;


public class BoardPane{
    public static int x;
    public int y;
    public boolean changed = false;
    private final int size;
    private final Color BACKGROUD_COLOR=Color.web("#262626");
    private final Color BOARD_COLOR= Color.BURLYWOOD;
    StoneColor[][] stoneColors;
    private final IntegerProperty xProperty = new SimpleIntegerProperty(0);
    private final IntegerProperty yProperty = new SimpleIntegerProperty(0);

    public BoardPane(int size, StoneColor[][] stoneColors){
        this.size = size;
        this.stoneColors = stoneColors;
    }
    public void setStoneColors(StoneColor[][] stoneColors){
        this.stoneColors = stoneColors;
    }
    public void setStoneColors(String stoneColors){


    }
    public void addXYChangeListener(ChangeListener<? super Number> listener) {
        xProperty.addListener(listener);
        yProperty.addListener(listener);
    }

    private void notifyXYChange(int newX, int newY) {
        xProperty.set(newX);
        yProperty.set(newY);
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void setStoneColor(int row,int col,StoneColor stoneColor){
        stoneColors[col][row]= stoneColor;
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
                    x= (int) Math.round(e.getSceneX()/50.0)-1;
                    y= (int) Math.round(e.getSceneY()/50.0)-1;
                    x = Math.max(0, x);
                    y = Math.max(0, y);
                    System.out.println(x+" "+y);
                    TestXD.x=x;
                    TestXD.y=y;
                    //circle.setFill(Color.BLACK);
                    changed = true;
                    notifyXYChange(x, y);
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
