package org.go_game.go_game_1_0.Board;

import java.util.ArrayList;
import java.util.List;

public class StoneGroup {
    private final List<Stone> stones;
    private int breaths;
    private final int size;
    public StoneGroup(int size){
        this.stones = new ArrayList<>();
        this.size = size;
        this.breaths = 0;
    }
    public void addStone(Stone stone){
        if(!stones.contains(stone)){
            stones.add(stone);
            stone.setStoneGroup(this);
            updateBreaths();
        }
    }
    public void merge(StoneGroup stoneGroup){
        for(Stone stone : stoneGroup.getStones()){
            this.addStone(stone);
        }
        updateBreaths();
    }
    public List<Stone> getStones(){
        return stones;
    }
    private void updateBreaths(){
        breaths = 0;

        for(Stone stone : stones){
            updateBreaths(stone);
        }
    }
    private void updateBreaths(Stone stone){
        int row = stone.getRow();
        int col = stone.getCol();

        checkAndAddBreath(row - 1, col);
        checkAndAddBreath(row + 1, col);
        checkAndAddBreath(row, col - 1);
        checkAndAddBreath(row, col + 1);
    }
    private void checkAndAddBreath(int row, int col){
        if(isValidMove(row,col)){
            Stone neighborStone = getStone(row,col);
            if(neighborStone == null){
                addBreath(row,col);
            }
        }
    }
    public void addBreath(int row, int col){
        this.breaths++;
    }
    public int getBreaths(){
        return breaths;
    }
    public void setBreaths(int breaths){
        this.breaths = breaths;
    }
    private Stone getStone(int row,int col){
        for(Stone stone : stones){
            if(stone.getRow() == row && stone.getCol() == col){
                return stone;
            }
        }
        return null;
    }
    private boolean isValidMove(int row, int col){
        return row >= 0 && row < size && col >= 0 && col <= size;
    }
}

