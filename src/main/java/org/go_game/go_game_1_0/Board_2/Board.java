package org.go_game.go_game_1_0.Board_2;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int size;
    private Stone[][] grid;

    public Board(int size){
        this.size = size;
        this.grid = new Stone[size][size];
    }
    public void placeStone(int row, int col, Stone stone){
        if(isValidMove(row,col) && grid[row][col] == null){
            grid[row][col] = stone;
        }  else {
            System.out.println("Nieprawidłowy ruch na pozycji (" + row + ", " + col + ")");
        }
    }
    public Stone getStone(int row, int col){
        if(isValidMove(row,col)){
             return grid[row][col];
        } else {
            return null;
        }
    }

    public void removeStone(int row, int col){
        if(isValidMove(row,col)){
            grid[row][col] = null;
        } else {
            System.out.println("Nieprawidłowa próba usunięcia kamienia z pozycji (" + row + ", " + col + ")");
        }
    }
    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }
    public void placeStoneAndUpdateGroups(int row,int col, Stone stone){
        placeStone(row,col,stone);

        StoneGroup newStoneGroup = new StoneGroup();
        addStoneToGroup(row,col,newStoneGroup);
        updateGroups(row,col,newStoneGroup);
    }
    private void updateGroups(int row, int col, StoneGroup newStoneGroup){
        List<StoneGroup> neighboringGroups = getNeighbringGroups(row,col);

        for(StoneGroup stoneGroup : neighboringGroups){
            newStoneGroup.merge(stoneGroup);
        }
    }
    private List<StoneGroup> getNeighbringGroups(int row,int col){
        List<StoneGroup> neighboringGroups = new ArrayList<>();

        checkAndAddNeighbor(neighboringGroups, row - 1, col);
        checkAndAddNeighbor(neighboringGroups, row + 1, col);
        checkAndAddNeighbor(neighboringGroups, row, col - 1);
        checkAndAddNeighbor(neighboringGroups, row, col + 1);

        return neighboringGroups;
    }
    private void checkAndAddNeighbor(List<StoneGroup> neighboringGroups, int row, int col){
        if (isValidMove(row, col)) {
            Stone neighborStone = getStone(row, col);
            if (neighborStone != null) {
                StoneGroup neighborGroup = neighborStone.getStoneGroup();
                if (!neighboringGroups.contains(neighborGroup)) {
                    neighboringGroups.add(neighborGroup);
                }
            }
        }
    }
    private void addStoneToGroup(int row, int col, StoneGroup stoneGroup){
        Stone stone = getStone(row,col);
        if(stone != null){
            stoneGroup.addStone(stone);
        }
    }
}
