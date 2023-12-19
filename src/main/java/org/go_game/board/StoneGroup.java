package org.go_game.board;

import java.util.ArrayList;
import java.util.List;

public class StoneGroup {
    private List<Stone> stones= new ArrayList<Stone>();
    private StoneType stoneType;
    private int breaths;
    StoneGroup(Stone stone){
        stones.add(stone);
        stoneType=stone.getType();
        breaths=stone.getBreaths();
    }
    void addStone(Stone stone){
        stones.add(stone);
        breaths+=stone.getBreaths();
    }
}
