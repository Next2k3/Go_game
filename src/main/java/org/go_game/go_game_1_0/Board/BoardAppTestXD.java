package org.go_game.go_game_1_0.Board;

import java.util.Scanner;

public class BoardAppTestXD {
     public static void printBoard(Board board){
        for(int i=0;i<9;i++){
            for (int j=0;j<9;j++){
                if(board.getStone(i,j)!=null) {
                    if (board.getStone(i, j).getStoneColor() == StoneColor.WHITE) {
                        System.out.print("W  ");
                    } else {
                        System.out.print("B  ");
                    }
                }else{
                    System.out.print("_  ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
    public static void main(String[] args){
        Board board = new Board(9);
        Scanner scanner = new Scanner(System.in);
        int x;
        int y;
        while(true) {
            x = scanner.nextInt();
            y = scanner.nextInt();
            board.placeStoneAndUpdateGroups(x, y, new Stone(StoneColor.BLACK, x, y));
            printBoard(board);

            x = scanner.nextInt();
            y = scanner.nextInt();
            board.placeStoneAndUpdateGroups(x, y, new Stone(StoneColor.WHITE, x, y));
            printBoard(board);
        }
    }
}
