package org.go_game.go_game_1_0.Board;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private int size;
    private Stone board[][];
    private List<StoneGroup> stoneGroups = new ArrayList<StoneGroup>();

    public Board(int size){
        this.size =size;
        this.board = new Stone[size][size];
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                board[i][j]= new Stone(i,j,StoneType.NULL);
            }
        }
    }
    public int getsize(){
        return this.size;
    }
    public boolean addStone(int x, int y, StoneType stoneType){
        if(board[y][x].getType() == StoneType.NULL){
            board[y][x] = new Stone(x,y,stoneType);
            stoneGroups.add(new StoneGroup(board[y][x]));
            if(x>0){
                board[y][x].setNeighbours(0,board[y][x-1]);
                board[y][x-1].setNeighbours(2,board[y][x]);
            }else{
                board[y][x].setNeighbours(0,new Stone(StoneType.NULL));
            }
            if(x<size-1){
                board[y][x].setNeighbours(2,board[y][x+1]);
                board[y][x+1].setNeighbours(0,board[y][x]);
            }else{
                board[y][x].setNeighbours(2,new Stone(StoneType.NULL));
            }
            if(y>0){
                board[y][x].setNeighbours(3,board[y-1][x]);
                board[y-1][x].setNeighbours(1,board[y][x]);
            }else{
                board[y][x].setNeighbours(3,new Stone(StoneType.NULL));
            }
            if(y<size-1){
                board[y][x].setNeighbours(1,board[y+1][x]);
                board[y+1][x].setNeighbours(3,board[y][x]);
            }else{
                board[y][x].setNeighbours(1,new Stone(StoneType.NULL));
            }
            stoneGroups= new ArrayList<StoneGroup>();
            calcGroups(0,0);
            return true;
        }
        return false;
    }
    public void calcGroups(int x,int y){

    }
    public boolean isStoneInGroups(Stone stone){
        for(int i=0;i<stoneGroups.size();i++){
            for(int j=0;j<stoneGroups.get(i).getSize();j++){
                if(stoneGroups.get(i).getStone(j)==stone){
                    return true;
                }
            }
        }
        return false;
    }
    public void setStone(int x,int y, StoneType stoneType){
        board[y][x]= new Stone(x,y,stoneType);
    }
    public Stone getStone(int x, int y){
        if(x<0 || y<0 || x>=size || y>=size){
            return null;
        }
        return board[y][x];
    }
}