package org.go_game.go_game_1_0;

import org.go_game.go_game_1_0.Board.Board;
import org.go_game.go_game_1_0.Board.Stone;
import org.go_game.go_game_1_0.Board.StoneColor;
import org.go_game.go_game_1_0.Board.StoneGroup;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    public void testPlaceStone() {
        Board board = new Board(9);
        Stone stone = new Stone(StoneColor.BLACK, 3, 3);

        assertTrue(board.placeStone(3, 3, stone));
        assertEquals(stone, board.getStone(3, 3));
        assertEquals(StoneColor.WHITE, board.getMove());
    }

    @Test
    public void testRemoveStone() {
        Board board = new Board(9);
        Stone stone = new Stone(StoneColor.BLACK, 3, 3);
        board.placeStone(3, 3, stone);

        board.removeStone(3, 3);
        assertNull(board.getStone(3, 3));
        assertEquals(1, board.getDeadBlackStones());
    }

    @Test
    public void testStoneGroupBreaths() {
        StoneGroup stoneGroup = new StoneGroup(9);
        Stone stone1 = new Stone(StoneColor.BLACK, 1, 1);
        Stone stone2 = new Stone(StoneColor.BLACK, 1, 2);
        Stone stone3 = new Stone(StoneColor.BLACK, 2, 1);

        stoneGroup.addStone(stone1);
        stoneGroup.addStone(stone2);
        stoneGroup.addStone(stone3);

        assertEquals(8, stoneGroup.getBreaths());
    }

    @Test
    public void testGameScoring() {
        Board board = new Board(9);
        Stone blackStone = new Stone(StoneColor.BLACK, 1, 1);
        Stone whiteStone = new Stone(StoneColor.WHITE, 2, 2);
        board.placeStoneAndUpdateGroups(1, 1, blackStone);
        board.placeStoneAndUpdateGroups(2, 2, whiteStone);

        int[] scores = board.zliczPunkty();
        assertEquals(4, scores[0]); // Czarne punkty
        assertEquals(4, scores[1]); // Białe punkty
    }
}

