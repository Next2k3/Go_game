package org.go_game.go_game_1_0.Board;


public class Stone {
    private StoneColor stoneColor;
    private StoneGroup stoneGroup;
    private int row;
    private int col;

    public Stone(StoneColor stoneColor,int row,int col){
        this.stoneColor = stoneColor;
        this.row = row;
        this.col = col;
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
