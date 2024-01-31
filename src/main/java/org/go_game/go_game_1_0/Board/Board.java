package org.go_game.go_game_1_0.Board;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Board {
    private int size;
    private Stone[][] grid;
    private List<Stone> blockedStonesWhite;
    private List<Stone> blockedStonesBlack;
    private StoneColor move;
    private int deadBlackStones;
    private int deadWhiteStones;

    public Board(int size){
        this.size = size;
        this.grid = new Stone[size][size];
        blockedStonesBlack = new ArrayList<>();
        blockedStonesWhite = new ArrayList<>();
        move = StoneColor.BLACK;
        deadBlackStones=0;
        deadWhiteStones=0;
    }
    public StoneColor getMove(){
        return move;
    }
    public boolean placeStone(int row, int col, Stone stone) {
        if (isValidMoveorKillMove(row, col, stone) && grid[row][col] == null){
            List<Stone> blockedStones = (stone.getStoneColor() == StoneColor.WHITE) ? blockedStonesWhite : blockedStonesBlack;
            if (!isContain(row, col, stone.getStoneColor())) {
                blockedStones.clear();
                blockedStones.add(stone);
                grid[row][col] = stone;
                if(move == StoneColor.BLACK){
                    move = StoneColor.WHITE;
                }else{
                    move = StoneColor.BLACK;
                }
                return true;
            }
        }
        return false;
    }
    public void changeMoveColor(){
        if(move == StoneColor.BLACK){
            move = StoneColor.WHITE;
        }else{
            move = StoneColor.BLACK;
        }
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
            if(grid[row][col].getStoneColor()==StoneColor.WHITE){
                deadWhiteStones++;
            }else{
                deadBlackStones++;
            }
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
        int i=0,j=0;
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if(stone.getStoneColor()==StoneColor.WHITE ) {
                if (isValidMove(newRow, newCol) && isKillMove(newRow, newCol, StoneColor.BLACK)) {
                    i++;
                }
            }else{
                if (isValidMove(newRow, newCol) && isKillMove(newRow, newCol, StoneColor.WHITE)) {
                    i++;
                }
            }
            if(isValidMove(newRow,newCol)){
                if(grid[newRow][newCol]==null) {
                    j++;
                }
            }
        }
        if(j==0 && i!=0){
            return false;
        }
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            if (isValidMove(newRow, newCol) && isKillMove(newRow, newCol, stone.getStoneColor())) {
                return true;
            }
        }

        return isValidMove(row, col);
    }
    public boolean isPosibleMove(int row,int col, Stone stone){
        if (isValidMoveorKillMove(row, col, stone) && grid[row][col] == null){
            if (!isContain(row, col, stone.getStoneColor())) {
                return false;
            }
        }
        return true;
    }
    public boolean isKillMove(int row, int col, StoneColor stoneColor) {
        if(grid[row][col]==null){
            return false;
        }else if(grid[row][col].getStoneColor() != stoneColor
                && grid[row][col].getStoneGroup().getBreaths() == 1){
            return true;
        }
        return false;
    }
    public boolean placeStoneAndUpdateGroups(int row,int col, Stone stone){
        if(placeStone(row,col,stone)) {
            StoneGroup newStoneGroup = new StoneGroup(size);
            addStoneToGroup(row, col, newStoneGroup);
            updateGroups(row, col, newStoneGroup);
            updateAllGroupsBreaths();
            return true;
        }else{
            return false;
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
            if(stoneGroup.getStones().getFirst().getStoneColor()==move) {
                updateBreathsForGroup(stoneGroup);
            }
        }
        for(StoneGroup stoneGroup : stoneGroups){
            if(stoneGroup.getStones().getFirst().getStoneColor()!=move) {
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
                if(grid[j][i]!=null){
                    if(grid[j][i].getStoneColor()==StoneColor.WHITE){
                        board+="W";
                    }else{
                        board+="B";
                    }
                }else{
                    board+="N";
                }
            }
        }
        return board;
    }
    public int[] zliczPunkty() {
        int[] punkty = new int[2];  // Indeks 0 - punkty dla czarnego, Indeks 1 - punkty dla białego

        // Utwórz zbiory, aby śledzić obszary, które zostały już zliczone
        Set<StoneGroup> zliczoneObszaryCzarne = new HashSet<>();
        Set<StoneGroup> zliczoneObszaryBiałe = new HashSet<>();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] != null) {
                    Stone stone = grid[i][j];
                    StoneGroup stoneGroup = stone.getStoneGroup();

                    // Jeśli obszar nie został jeszcze zliczony, zlicz punkty
                    if (stoneGroup != null && !zliczoneObszaryCzarne.contains(stoneGroup) && !zliczoneObszaryBiałe.contains(stoneGroup)) {
                        int punktyObszaru = stoneGroup.getBreaths();
                        if (stone.getStoneColor() == StoneColor.BLACK) {
                            punkty[0] += punktyObszaru;
                            zliczoneObszaryCzarne.add(stoneGroup);
                        } else if (stone.getStoneColor() == StoneColor.WHITE) {
                            punkty[1] += punktyObszaru;
                            zliczoneObszaryBiałe.add(stoneGroup);
                        }
                    }
                }
            }
        }

        punkty[0]+=deadBlackStones;
        punkty[1]+=deadWhiteStones;
        return punkty;
    }
}

