package com.test.mancala.model;

import com.test.mancala.constants.MancalaConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BoardTest {

    private Board board;

    @BeforeEach
    void setup(){
        List<Player> players = IntStream.rangeClosed(1, MancalaConstants.NUM_OF_PLAYERS)
                .mapToObj(i -> new Player(i, "player" + i)).toList();

        board = new Board(MancalaConstants.INITIAL_STONE_ON_PIT, players);
    }

    @Test
    void createBoard(){
        //then
        assertNotNull(board.getPits());
        assertEquals(MancalaConstants.NUM_PITS_PER_PLAYER * MancalaConstants.NUM_OF_PLAYERS, board.getPits().size());
        assertEquals(MancalaConstants.NUM_OF_PLAYERS, board.getHouses().size());
    }

    @Test
    void shouldGetStoneCountByPitIndex(){
        //when
        int pit1Stone = board.getPitByPitIndex(1).getStoneCount();
        int house1Stone = board.getPitByPitIndex(7).getStoneCount();
        int pit8Stone = board.getPitByPitIndex(8).getStoneCount();
        int house2Stone = board.getPitByPitIndex(14).getStoneCount();

        //then
        assertEquals(6, pit1Stone);
        assertEquals(0, house1Stone);
        assertEquals(6, pit8Stone);
        assertEquals(0, house2Stone);
    }

    @Test
    void shouldGetPlayerHouse(){
        //when
        Pit house1 = board.getPlayerHouse(1);
        Pit house2 = board.getPlayerHouse(2);

        //then
        assertEquals(7, house1.getPitIndex());
        assertEquals(14, house2.getPitIndex());
    }

    @Test
    void shouldGetPitByPitIndex(){
        //when
        Pit pit = board.getPitByPitIndex(2);

        //then
        assertEquals(2, pit.getPitIndex());
        assertEquals(1, pit.getPlayerIndex());

        pit = board.getPitByPitIndex(10);
        assertEquals(10, pit.getPitIndex());
        assertEquals(2, pit.getPlayerIndex());
    }

    @Test
    void shouldGetNextPit() {
        //when
        int lastPitIndex = MancalaConstants.NUM_PITS_PER_PLAYER * MancalaConstants.NUM_OF_PLAYERS;
        Pit pit1 = board.getPitByPitIndex(1);
        Pit pit2 = board.getNextPit(pit1);
        Pit lastPit = board.getPitByPitIndex(lastPitIndex);
        Pit pit1Again = board.getNextPit(lastPit);

        //then
        assertEquals(2, pit2.getPitIndex());
        assertEquals(pit1, pit1Again);
    }

    @Test
    void shouldGetOppositePit() {
        //when
        Pit pit1 = board.getPitByPitIndex(1);
        Pit oppositePit = board.getOppositePit(pit1);
        Pit pit1Again = board.getOppositePit(oppositePit);

        //then
        assertEquals(13, oppositePit.getPitIndex());
        assertEquals(pit1, pit1Again);
    }

    @Test
    void shouldGetPlayerPitStoneCount(){
        //when
        int player1PitStoneCount = board.getPlayerPitStoneCount(1,7);
        int player2PitStoneCount = board.getPlayerPitStoneCount(8,14);

        //then
        assertEquals(36, player1PitStoneCount);
        assertEquals(36, player2PitStoneCount);
    }

    @Test
    void shouldGetPlayer2PitStoneCount(){
        //when
        board.getPits().get(8).setStoneCount(0);
        Integer player2PitStoneCount = board.getPlayerPitStoneCount(8,14);

        //then
        assertEquals(Integer.valueOf(30), player2PitStoneCount);
    }

}
