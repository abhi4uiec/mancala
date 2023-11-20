package com.test.mancala.model;

import com.test.mancala.constants.MancalaConstants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PitTest {

    @Test
    void shouldDistributable(){
        Game game = new Game(MancalaConstants.INITIAL_STONE_ON_PIT);

        //given
        Pit pit1 = new Pit(1, 6, 1);
        Pit pit7 = new Pit(7, 6, 1);

        //then
        assertEquals(true, pit1.isDistributable(game));
        assertEquals(false, pit7.isDistributable(game));
    }

    @Test
    void shouldPlayerPit(){
        //given
        Pit pit1 = new Pit(1, 6, 1);

        //then
        assertEquals(true, pit1.isPlayerPit(GameStatus.PLAYER1_TURN));
        assertEquals(false, pit1.isPlayerPit(GameStatus.PLAYER2_TURN));
    }

    @Test
    void shouldHouse(){
        //given
        Pit pit1 = new Pit(1, 6, 1);
        Pit pit7 = new Pit(7, 6, 1);
        Pit pit13 = new Pit(13, 6, 1);
        Pit pit14 = new Pit(14, 6, 1);
        Pit pit27 = new Pit(27, 6, 1);
        Pit pit28 = new Pit(28, 6, 1);

        //then
        assertEquals(false, pit1.isHouse());
        assertEquals(true, pit7.isHouse());
        assertEquals(false, pit13.isHouse());
        assertEquals(true, pit14.isHouse());
        assertEquals(false, pit27.isHouse());
        assertEquals(true, pit28.isHouse());
    }

    @Test
    void shouldNextPitIndex(){

        //given
        int lastPitIndex = MancalaConstants.NUM_PITS_PER_PLAYER * MancalaConstants.NUM_OF_PLAYERS;
        Pit pit1 = new Pit(1, 6, 1);
        Pit pit7 = new Pit(7, 6, 1);
        Pit lastPit = new Pit(lastPitIndex, 6, 1);

        //then
        assertEquals(2, pit1.nextPitIndex());
        assertEquals(8, pit7.nextPitIndex());
        assertEquals(1, lastPit.nextPitIndex());
    }

    @Test
    void shouldGetOppositePitIndex(){
        //given
        Pit pit1 = new Pit(1, 6, 1);
        Pit pit8 = new Pit(8, 6, 1);

        //then
        assertEquals(13, pit1.getOppositePitIndex());
        assertEquals(6, pit8.getOppositePitIndex());
    }

}
