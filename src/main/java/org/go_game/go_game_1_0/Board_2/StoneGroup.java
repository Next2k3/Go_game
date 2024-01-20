package org.go_game.go_game_1_0.Board_2;

import java.util.ArrayList;
import java.util.List;

public class StoneGroup {
    private List<Stone> stones;

    public StoneGroup(){
        this.stones = new ArrayList<>();
    }
    public void addStone(Stone stone){
        if(!stones.contains(stone)){
            stones.add(stone);
            stone.setStoneGroup(this);
        }
    }
    public void merge(StoneGroup stoneGroup){
        for(Stone stone : stoneGroup.getStones()){
            this.addStone(stone);
        }
    }
    public List<Stone> getStones(){
        return stones;
    }
}
