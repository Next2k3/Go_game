package org.go_game.go_game_1_0.Board;

import java.util.ArrayList;
import java.util.List;

public class StoneGroup {
    private List<Stone> stones = new ArrayList<Stone>();
    private StoneType groupType;
    private int breaths;

    StoneGroup(Stone stone){
        stones.add(stone);
        groupType = stone.getType();
        breaths = stone.getBreaths();
    }
    void addStone(Stone stone){
        stones.add(stone);
        breaths+=stone.getBreaths();
        breaths-=2;
    }
    void removeStone(int x, int y){
        for(int i=0;i<stones.size();i++){
            if(x == stones.get(i).getX() && y == stones.get(i).getY()){
                stones.remove(stones.get(i));
            }
        }
    }
    public Stone getStone(int i){
        return stones.get(i);
    }
    List<Stone> getStones(){
        return stones;
    }
    boolean isDead(){
        if(breaths>=0){
            return true;
        }
        return false;
    }
    public int getSize(){
        return stones.size();
    }
}
