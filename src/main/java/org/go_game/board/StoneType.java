package org.go_game.board;

public enum StoneType {
    WHITE,
    BLACK;
    public static StoneType valueOf(int ind){
        return StoneType.values()[ind];
    }
}
