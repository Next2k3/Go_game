package org.go_game.go_game_1_0.Board_2;

public class BoardAppTestXD {
    public static void main(String[] args){
        Board board = new Board(9);
        board.placeStoneAndUpdateGroups(0,0,new Stone(StoneColor.BLACK));
        board.placeStoneAndUpdateGroups(1,0,new Stone(StoneColor.BLACK));
        board.placeStoneAndUpdateGroups(2,2,new Stone(StoneColor.WHITE));
        /*
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(board.getStone(i,j)!=null) {
                    if (board.getStone(i, j).getStoneColor() == StoneColor.WHITE) {
                        System.out.print("WHITE ");
                    } else{
                        System.out.print("BLACK ");
                    }
                }else{
                    System.out.print("NULL  ");
                }
            }
            System.out.println();
        }

         */
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(board.getStone(i,j)!= null && board.getStone(i,j).getStoneGroup()!=null) {
                    if (board.getStone(i,j).getStoneGroup().getStones().get(0).getStoneColor() == null) {
                        System.out.print("NULL  ");
                    } else if (board.getStone(i,j).getStoneGroup().getStones().get(0).getStoneColor() == StoneColor.WHITE) {
                        System.out.print("WHITE ");
                    } else {
                        System.out.print("BLACK ");
                    }
                }else{
                    System.out.print("NULL  ");
                }
            }
            System.out.println();
        }
    }
}
