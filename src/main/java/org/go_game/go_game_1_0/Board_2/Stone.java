package org.go_game.go_game_1_0.Board_2;

import javafx.scene.Group;

public class Stone {
    private StoneColor stoneColor;
    private StoneGroup stoneGroup;

    public Stone(StoneColor stoneColor){
        this.stoneColor = stoneColor;
    }
    public void setStoneGroup(StoneGroup stoneGroup){
        this.stoneGroup = stoneGroup;
    }
    public StoneColor getStoneColor(){
        return stoneColor;
    }
    public StoneGroup getStoneGroup(){
        return stoneGroup;
    }
}
