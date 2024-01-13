package org.go_game.go_game_1_0;

public enum StoneType {
    WHITE,
    BLACK;
    public static StoneType valueOf(int ind){
        return StoneType.values()[ind];
    }
}
