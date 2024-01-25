package org.go_game.go_game_1_0.Board;


import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Stone {
    private StoneColor stoneColor;
    private StoneGroup stoneGroup;
    private int row;
    private int col;
    private Circle circle;

    public Stone(StoneColor stoneColor,int row,int col, double radius){
        this.stoneColor = stoneColor;
        this.row = row;
        this.col = col;
        this.circle = new Circle(radius);
        if (stoneColor == StoneColor.BLACK) {
            this.circle.setFill(Color.BLACK);
        } else {
            this.circle.setFill(Color.WHITE);
        }
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }
    public void setStoneGroup(StoneGroup stoneGroup){
        this.stoneGroup = stoneGroup;
    }
    public StoneColor getStoneColor(){
        return stoneColor;
    }
    public StoneGroup getStoneGroup(){
        return stoneGroup;
    }
    public int getRow(){
        return row;
    }
    public int getCol(){
        return col;
    }
}