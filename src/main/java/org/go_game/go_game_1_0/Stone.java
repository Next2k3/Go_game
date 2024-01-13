package org.go_game.go_game_1_0;


public class Stone {
    private StoneType stoneType;
    private int x;
    private int y;
    private int breaths=0;
    private Stone[] neighbours = new Stone[4];
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
