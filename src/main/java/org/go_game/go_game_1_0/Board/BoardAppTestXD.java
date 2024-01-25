package org.go_game.go_game_1_0.Board;

public class BoardAppTestXD {
    public static void printBoard(Board board){
        for(int i=0;i<9;i++){
            for (int j=0;j<9;j++){
                if(board.getStone(i,j)!=null) {
                    if (board.getStone(i, j).getStoneColor() == StoneColor.WHITE) {
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
        System.out.println();
    }
    public static void main(String[] args){
        Board board = new Board(9);

        board.placeStoneAndUpdateGroups(0,0,new Stone(StoneColor.BLACK,0,0, 15));
        printBoard(board);
        if(board.getStone(0,0)!=null) {
            System.out.println(board.getStone(0, 0).getStoneGroup().getBreaths());
        }
        board.placeStoneAndUpdateGroups(1,0,new Stone(StoneColor.WHITE,1,0, 15));
        printBoard(board);
        if(board.getStone(0,0)!=null) {
            System.out.println(board.getStone(0, 0).getStoneGroup().getBreaths());
        }

        board.placeStoneAndUpdateGroups(0,1,new Stone(StoneColor.WHITE,1,0, 15));
        printBoard(board);
        if(board.getStone(0,0)!=null) {
            System.out.println(board.getStone(0, 0).getStoneGroup().getBreaths());
        }
        System.out.println(board.getBoardToString());
    }
}