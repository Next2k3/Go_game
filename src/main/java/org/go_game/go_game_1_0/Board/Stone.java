package org.go_game.go_game_1_0.Board;


public class Stone {
    private StoneType stoneType;
    private int x;
    private int y;
    private int breaths=4;
    private Stone[] neighbours = new Stone[4];
    Stone(StoneType stoneType){
        this.stoneType = stoneType;
    }
    Stone(int x,int y, StoneType stoneType){
        this.stoneType = stoneType;
        this.x = x;
        this.y = y;
    }
    public StoneType getType(){
        return stoneType;
    }
    public void addBreath(){
        breaths++;
    }
    public void removeBreath(){
        breaths--;
    }
    public void setStoneType(StoneType stoneType){
        this.stoneType = stoneType;
    }
    public void setNeighbours(int x, Stone stone){
        if(x>=0 && x<4){
            neighbours[x]=stone;
        }
    }
    public Stone getNeighbours(int x){
        if(x>=0 && x<4){
            return neighbours[x];
        }
        return new Stone(StoneType.NULL);
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getBreaths(){
        return breaths;
    }
}
