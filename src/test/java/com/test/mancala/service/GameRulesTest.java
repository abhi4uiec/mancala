package com.test.mancala.service;

import com.test.mancala.constants.MancalaConstants;
import com.test.mancala.exception.MancalaIllegalMoveException;
import com.test.mancala.model.Board;
import com.test.mancala.model.Game;
import com.test.mancala.model.GameStatus;
import com.test.mancala.model.Pit;
import com.test.mancala.repository.GameMemoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class GameRulesTest {

    @MockBean
    private GameMemoryRepository gameMemoryRepository;

    @Autowired
    private GameService gameService;

    private Game game;

    private int PLAYER1_HOUSE = 7;

    private int PLAYER2_HOUSE = 14;

    @BeforeEach
    void setup(){
        game = new Game(MancalaConstants.INITIAL_STONE_ON_PIT);
        game.setId(UUID.randomUUID().toString());
    }

    @Test
    void shouldStartWithOwnPit(){

        //given
        Board board = game.getBoard();

        //when
        when(gameMemoryRepository.findById(any())).thenReturn(game);
        gameService.play(game.getId(), 1);

        //then
        assertEquals(0, board.getStoneCountByPitIndex(1));
        assertEquals(7, board.getStoneCountByPitIndex(2) );
        assertEquals(7, board.getStoneCountByPitIndex(3));
        assertEquals(7, board.getStoneCountByPitIndex(4));
        assertEquals(7, board.getStoneCountByPitIndex(5));
        assertEquals(7, board.getStoneCountByPitIndex(6));
        assertEquals(1, board.getStoneCountByPitIndex(7));
        assertEquals(GameStatus.PLAYER1_TURN, game.getGameStatus());
        assertEquals(1, board.getPits().get(PLAYER1_HOUSE).getStoneCount());
        assertEquals(0, board.getPits().get(PLAYER2_HOUSE).getStoneCount());
    }

    @Test
    void shouldNotStartWithEmptyPit(){
        //given
        int pitIndex = 2;
        Pit pit = game.getBoard().getPits().get(pitIndex);
        pit.setStoneCount(0);

        //when
        when(gameMemoryRepository.findById(any())).thenReturn(game);

        //then
        assertThrowsExactly(MancalaIllegalMoveException.class, () -> gameService.play(game.getId(), pitIndex));
    }

    @Test
    void shouldNotStartWithOpponentPit(){
        //given
        game.setGameStatus(GameStatus.PLAYER2_TURN);

        //when
        when(gameMemoryRepository.findById(any())).thenReturn(game);

        //then
        assertThrowsExactly(MancalaIllegalMoveException.class, () -> gameService.play(game.getId(), 2));
    }

    @Test
    void shouldDistributeStoneFromPlayer2PitToPlayer1Pit() {
        //given
        Board board = game.getBoard();

        //when
        when(gameMemoryRepository.findById(any())).thenReturn(game);
        gameService.play(game.getId(), 12);

        //then
        assertEquals(0, board.getStoneCountByPitIndex(12));
        assertEquals(7, board.getStoneCountByPitIndex(13));
        assertEquals(1, board.getStoneCountByPitIndex(14));
        assertEquals(7, board.getStoneCountByPitIndex(1));
        assertEquals(7, board.getStoneCountByPitIndex(2));
        assertEquals(7, board.getStoneCountByPitIndex(3));
        assertEquals(7, board.getStoneCountByPitIndex(4));
        assertEquals(GameStatus.PLAYER1_TURN, game.getGameStatus());
        assertEquals(0, board.getPits().get(PLAYER1_HOUSE).getStoneCount());
        assertEquals(1, board.getPits().get(PLAYER2_HOUSE).getStoneCount());
    }

    @Test
    void shouldDistributeStoneFromPlayer1PitToPlayer2Pit(){
        //given
        Board board = game.getBoard();

        //when
        when(gameMemoryRepository.findById(any())).thenReturn(game);
        gameService.play(game.getId(), 4);

        //then
        assertEquals(0, board.getStoneCountByPitIndex(4));
        assertEquals(7, board.getStoneCountByPitIndex(5));
        assertEquals(7, board.getStoneCountByPitIndex(6));
        assertEquals(1, board.getStoneCountByPitIndex(7));
        assertEquals(7, board.getStoneCountByPitIndex(8));
        assertEquals(7, board.getStoneCountByPitIndex(9));
        assertEquals(7, board.getStoneCountByPitIndex(10));
        assertEquals(GameStatus.PLAYER2_TURN, game.getGameStatus());
        assertEquals(1, board.getPits().get(PLAYER1_HOUSE).getStoneCount());
        assertEquals(0, board.getPits().get(PLAYER2_HOUSE).getStoneCount());
    }

    @Test
    void shouldDistribute13Stone(){
        //given
        Board board = game.getBoard();
        board.getPits().get(4).setStoneCount(13);
        board.getPits().get(10).setStoneCount(10);

        //when
        when(gameMemoryRepository.findById(any())).thenReturn(game);
        gameService.play(game.getId(), 4);

        //then
        assertEquals(0, board.getStoneCountByPitIndex(4));
        assertEquals(7, board.getStoneCountByPitIndex(5));
        assertEquals(7, board.getStoneCountByPitIndex(6));
        assertEquals(13, board.getStoneCountByPitIndex(7));
        assertEquals(7, board.getStoneCountByPitIndex(8));
        assertEquals(7, board.getStoneCountByPitIndex(9));
        assertEquals(0, board.getStoneCountByPitIndex(10));
        assertEquals(GameStatus.PLAYER2_TURN, game.getGameStatus());
        assertEquals(13, board.getPits().get(PLAYER1_HOUSE).getStoneCount());
        assertEquals(0, board.getPits().get(PLAYER2_HOUSE).getStoneCount());
    }

    @Test
    void shouldIncreaseHouseStoneOnOwnEmptyPit() {
        //given
        Board board = game.getBoard();
        Pit pit1 = board.getPitByPitIndex(1);
        pit1.setStoneCount(2);

        Pit pit2 = board.getPitByPitIndex(3);
        pit2.setStoneCount(0);

        //when
        when(gameMemoryRepository.findById(any())).thenReturn(game);
        gameService.play(game.getId(), 1);

        //then
        assertEquals(0, board.getStoneCountByPitIndex(1));
        assertEquals(0, board.getStoneCountByPitIndex(3) );
        assertEquals(0, board.getStoneCountByPitIndex(11));
        assertEquals(GameStatus.PLAYER2_TURN, game.getGameStatus());
        assertEquals(7, board.getPits().get(PLAYER1_HOUSE).getStoneCount());
        assertEquals(0, board.getPits().get(PLAYER2_HOUSE).getStoneCount());
    }


    @Test
    void shouldChangeGameToPlayerTurn1() {

        //when
        when(gameMemoryRepository.findById(any())).thenReturn(game);
        gameService.play(game.getId(), 1);

        //then
        assertEquals(GameStatus.PLAYER1_TURN, game.getGameStatus());
    }


    @Test
    void shouldChangeGameToPlayerTurn2() {
        //when
        when(gameMemoryRepository.findById(any())).thenReturn(game);
        gameService.play(game.getId(), 2);

        //then
        assertEquals(GameStatus.PLAYER2_TURN, game.getGameStatus());
    }


    @Test
    void shouldChangeGameToPlayerTurn2Again() {
        //when
        Pit pit = game.getBoard().getPits().get(8);
        pit.setStoneCount(6);

        when(gameMemoryRepository.findById(any())).thenReturn(game);
        gameService.play(game.getId(), 8);

        //then
        assertEquals(GameStatus.PLAYER2_TURN, game.getGameStatus());
    }


    @Test
    void shouldGameOver() {

        //given
        Board board = game.getBoard();
        for(Integer key : board.getPits().keySet()){
            Pit pit = board.getPits().get(key);
            if(!pit.isHouse()) {
                pit.setStoneCount(0);
            }
        }
        board.getPits().get(6).setStoneCount(1);

        //when
        when(gameMemoryRepository.findById(any())).thenReturn(game);
        gameService.play(game.getId(), 6);

        //then
        assertEquals(GameStatus.FINISHED, game.getGameStatus());
        assertEquals(game.getWinner().name(), "Player1");
    }

    @Test
    void shouldPlayer1Win() {

        //given
        Board board = game.getBoard();
        for(Integer key : board.getPits().keySet()){
            Pit pit = board.getPits().get(key);
            if(!pit.isHouse()) {
                pit.setStoneCount(0);
            }
        }
        Pit lastPit = board.getPits().get(6);
        lastPit.setStoneCount(1);

        //when
        when(gameMemoryRepository.findById(any())).thenReturn(game);
        gameService.play(game.getId(), 6);

        //then
        assertEquals(GameStatus.FINISHED, game.getGameStatus());
        assertEquals(game.getWinner().name(), "Player1");
    }

    @Test
    void shouldPlayer2Win(){

        //given
        Board board = game.getBoard();
        for(Integer key : board.getPits().keySet()){
            Pit pit = board.getPits().get(key);
            if(!pit.isHouse()) {
                pit.setStoneCount(0);
            }
        }
        board.getPits().get(13).setStoneCount(1);

        //when
        when(gameMemoryRepository.findById(any())).thenReturn(game);
        gameService.play(game.getId(), 13);

        //then
        assertEquals(GameStatus.FINISHED, game.getGameStatus());
        assertEquals(game.getWinner().name(), "Player2");
    }

    @Test
    void shouldDraw(){

        //given
        Board board = game.getBoard();

        for(Integer key : board.getPits().keySet()){
            Pit pit = board.getPits().get(key);
            if(!pit.isHouse()) {
                pit.setStoneCount(0);
            }
        }
        board.getPits().get(6).setStoneCount(1);
        board.getPits().get(14).setStoneCount(1);

        //when
        when(gameMemoryRepository.findById(any())).thenReturn(game);
        gameService.play(game.getId(), 6);

        //then
        assertEquals(GameStatus.FINISHED, game.getGameStatus());
        assertNull(game.getWinner());
    }

}


