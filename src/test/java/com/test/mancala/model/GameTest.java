package com.test.mancala.model;

import com.test.mancala.constants.MancalaConstants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void createGame(){

        //give
        Game game = new Game(MancalaConstants.INITIAL_STONE_ON_PIT);

        //then
        Board board = game.getBoard();
        assertNotNull(board);
        assertEquals(MancalaConstants.NUM_PITS_PER_PLAYER * MancalaConstants.NUM_OF_PLAYERS, board.getPits().size());
        assertEquals(MancalaConstants.NUM_OF_PLAYERS, board.getHouses().size());
        assertNull(game.getWinner());
        assertEquals(GameStatus.INIT, game.getGameStatus());
    }

}
