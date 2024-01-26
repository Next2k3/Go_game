package org.go_game.go_game_1_0.Board;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int size;
    private Stone[][] grid;
    private List<Stone> blockedStonesWhite;
    private List<Stone> blockedStonesBlack;
    private StoneColor move;

    public Board(int size){
        this.size = size;
        this.grid = new Stone[size][size];
        blockedStonesBlack = new ArrayList<>();
        blockedStonesWhite = new ArrayList<>();
        move = StoneColor.BLACK;
    }
    public boolean placeStone(int row, int col, Stone stone) {
        if (isValidMoveorKillMove(row, col, stone) && grid[row][col] == null) {
            List<Stone> blockedStones = (stone.getStoneColor() == StoneColor.WHITE) ? blockedStonesWhite : blockedStonesBlack;
            for(Stone stone1 :blockedStonesBlack){
                System.out.println(stone1.getRow()+ " "+ stone1.getCol());
            }
            if (!isContain(row,col,stone.getStoneColor())) {
                System.out.println("XD");
                blockedStones.clear();
                blockedStones.add(stone);
                grid[row][col] = stone;
                return true;
            }
            else{
                System.out.println("Nieprawidłowy ruch na zajeta pozycji (" + row + ", " + col + ")");
                blockedStones.clear();
            }
        } else {
            System.out.println("Nieprawidłowy ruch na pozycji (" + row + ", " + col + ")");
        }
        if(move == StoneColor.BLACK){
            move = StoneColor.WHITE;
        }else{
            move = StoneColor.BLACK;
        }
        return false;
    }
    private boolean isContain(int row,int col, StoneColor stoneColor){
        List<Stone> blockedStones = (stoneColor == StoneColor.WHITE) ? blockedStonesWhite : blockedStonesBlack;
        if(blockedStones.isEmpty()){
            return false;
        }
        for(Stone stone : blockedStones){
            if(stone.getRow()==row && stone.getCol()==col){
                return true;
            }
        }
        return false;
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
    public boolean isValidMoveorKillMove(int row, int col, Stone stone) {
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
    public boolean isKillMove(int row, int col, StoneColor stoneColor) {
        return grid[row][col] != null && grid[row][col].getStoneColor() != stoneColor
                && grid[row][col].getStoneGroup().getBreaths() == 1;
    }
    public void placeStoneAndUpdateGroups(int row,int col, Stone stone){
        if(placeStone(row,col,stone)) {

            StoneGroup newStoneGroup = new StoneGroup(size);

            addStoneToGroup(row, col, newStoneGroup);
            updateGroups(row, col, newStoneGroup);
            updateAllGroupsBreaths();
        }
    }
    private void updateGroups(int row, int col, StoneGroup newStoneGroup){
        List<StoneGroup> neighboringGroups = getNeighbringGroups(row,col);

        for(StoneGroup stoneGroup : neighboringGroups){
            if(newStoneGroup.getStones() != null ) {
                if (newStoneGroup.getStones().getFirst().getStoneColor() == stoneGroup.getStones().getFirst().getStoneColor()) {
                    newStoneGroup.merge(stoneGroup);
                }
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
            if(stoneGroup.getStones().getFirst().getStoneColor()!=move) {
                updateBreathsForGroup(stoneGroup);
            }
        }
        for(StoneGroup stoneGroup : stoneGroups){
            if(stoneGroup.getStones().getFirst().getStoneColor()==move) {
                updateBreathsForGroup(stoneGroup);
            }
        }

    }
    private void updateBreathsForGroup(StoneGroup stoneGroup){
        stoneGroup.setBreaths(0);

        for(Stone stone : stoneGroup.getStones()) {
            updateBreaths(stoneGroup,stone.getRow(), stone.getCol());
        }
        if(stoneGroup.getBreaths()==0){
            for(Stone stone : stoneGroup.getStones()){
                if(stone.getStoneGroup().getStones().size()==1) {
                    if (stone.getStoneColor() == StoneColor.WHITE) {
                        blockedStonesWhite = stoneGroup.getStones();
                    } else {
                        blockedStonesBlack = stoneGroup.getStones();
                    }
                }
                removeStone(stone.getRow(), stone.getCol());
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
