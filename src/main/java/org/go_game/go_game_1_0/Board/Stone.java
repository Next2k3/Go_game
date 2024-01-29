package org.go_game.go_game_1_0.Board;


import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static org.go_game.go_game_1_0.Board.StoneColor.WHITE;

public class Stone {
    private StoneColor stoneColor;
    private StoneGroup stoneGroup;
    private int row;
    private int col;
  
    public Stone(StoneColor stoneColor,int row,int col){
        this.stoneColor = stoneColor;
        this.row = row;
        this.col = col;
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
    public int getRow(){
        return row;
    }
    public int getCol(){
        return col;
    }

    public void setColor(StoneColor stoneColor) {
        stoneColor = WHITE; // aLBO BLACK??
        // repaint() ?
    }

    // COS W TYM STYLU?
//    private class ClickListener extends MouseAdapter {
//        @Override
//        public void mouseClicked(MouseEvent e) {
//            if ((token == ' ') && myTurn) {
//                setToken(myToken);
//                myTurn = false;
//                rowSelected = row;
//                columnSelected = column;
//                statusLabel.setText("Waiting for the other player to move");
//                waiting = false;
//            }
//        }
//    }
}