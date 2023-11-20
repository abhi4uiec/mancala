package com.test.mancala.model;

import com.test.mancala.constants.GameConstants;
import com.test.mancala.constants.MancalaConstants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void createGame(){

        //give
        Game game = new Game(GameConstants.INITIAL_STONE_ON_PIT,
                GameConstants.NUM_OF_PLAYERS);

        //then
        Board board = game.getBoard();
        assertNotNull(board);
        assertEquals(MancalaConstants.NUM_PITS_PER_PLAYER * GameConstants.NUM_OF_PLAYERS, board.getPits().size());
        assertEquals(GameConstants.NUM_OF_PLAYERS, board.getHouses().size());
        assertNull(game.getWinner());
        assertEquals(GameStatus.INIT, game.getGameStatus());
    }

}
