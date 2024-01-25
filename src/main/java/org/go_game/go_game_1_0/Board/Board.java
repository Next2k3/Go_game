package org.go_game.go_game_1_0.Board;

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
        if(isValidMoveorKillMove(row,col,stone) && grid[row][col] == null){
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
    public boolean isValidMove(int row, int col) {
        return row >= 0 && row <= size - 1 && col >= 0 && col <= size - 1 ;
    }
    private boolean isValidMoveorKillMove(int row, int col, Stone stone) {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            if (isValidMove(newRow, newCol) && isKillMove(newRow, newCol, stone.getStoneColor())) {
                if (grid[newRow][newCol].getStoneColor() != stone.getStoneColor()) {
                    return true;
                }
            }
        }

        return isValidMove(row, col);
    }
    private boolean isKillMove(int row, int col, StoneColor stoneColor) {
        return grid[row][col] != null && grid[row][col].getStoneColor() != stoneColor
                && grid[row][col].getStoneGroup().getBreaths() == 1;
    }
    public void placeStoneAndUpdateGroups(int row,int col, Stone stone){
        placeStone(row,col,stone);

        StoneGroup newStoneGroup = new StoneGroup(size);

        addStoneToGroup(row,col,newStoneGroup);
        updateGroups(row,col,newStoneGroup);
        updateAllGroupsBreaths();
    }
    private void updateGroups(int row, int col, StoneGroup newStoneGroup){
        List<StoneGroup> neighboringGroups = getNeighbringGroups(row,col);

        for(StoneGroup stoneGroup : neighboringGroups){
            if(newStoneGroup.getStones().get(0).getStoneColor()==stoneGroup.getStones().get(0).getStoneColor()) {
                newStoneGroup.merge(stoneGroup);
            }
        }
    }
    private List<StoneGroup> getNeighbringGroups(int row, int col){
        List<StoneGroup> neighboringGroups = new ArrayList<>();

        checkAndAddNeighbor(neighboringGroups, row - 1, col);
        checkAndAddNeighbor(neighboringGroups, row + 1, col);
        checkAndAddNeighbor(neighboringGroups, row, col - 1);
        checkAndAddNeighbor(neighboringGroups, row, col + 1);

        return neighboringGroups;
    }
    public void checkAndAddNeighbor(List<StoneGroup> neighboringGroups, int row, int col){
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
    private void addStoneToGroup(int row,int col,StoneGroup stoneGroup){
        Stone stone = getStone(row,col);
        if(stone != null){
            stoneGroup.addStone(stone);
        }
    }
    private void addStoneToGroupAndUpdateBreaths(int row, int col, StoneGroup stoneGroup){
        Stone stone = getStone(row,col);
        if(stone != null){
            stoneGroup.addStone(stone);
            updateAllGroupsBreaths();
        }
    }
    private void updateAllGroupsBreaths(){
        List<StoneGroup> stoneGroups = new ArrayList<>();
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                if(grid[i][j]!= null && grid[i][j].getStoneGroup()!=null){
                    stoneGroups.add(grid[i][j].getStoneGroup());
                }
            }
        }
        for(StoneGroup stoneGroup : stoneGroups){
            updateBreathsForGroup(stoneGroup);
        }
    }
    private void updateBreathsForGroup(StoneGroup stoneGroup){
        stoneGroup.setBreaths(0);

        for(Stone stone : stoneGroup.getStones()) {
            updateBreaths(stoneGroup,stone.getRow(), stone.getCol());
        }

        if(stoneGroup.getBreaths()==0){
            for(Stone stone : stoneGroup.getStones()){
                removeStone(stone.getRow(),stone.getCol());
            }
        }
    }
    private void updateBreaths(StoneGroup stoneGroup, int row,int col){
        checkAndAddBreath(stoneGroup, row - 1, col);
        checkAndAddBreath(stoneGroup, row + 1, col);
        checkAndAddBreath(stoneGroup, row, col - 1);
        checkAndAddBreath(stoneGroup, row, col + 1);
    }
    private void checkAndAddBreath(StoneGroup stoneGroup,int row, int col){
        if(isValidMove(row,col)){
            Stone neighborStone = getStone(row,col);
            if(neighborStone == null){
                stoneGroup.addBreath(row,col);
            }
        }
    }
    public String getBoardToString(){
        String board = "";
        for(int i=0;i<size;i++){
            for (int j=0;j<size;j++){
                if(grid[i][j]!=null){
                    if(grid[i][j].getStoneColor()==StoneColor.WHITE){
                        board+=";W";
                    }else{
                        board+=";B";
                    }
                }else{
                    board+=";N";
                }
            }
        }
        return board;
    }
}
