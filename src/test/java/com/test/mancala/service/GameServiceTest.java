package com.test.mancala.service;

import com.test.mancala.constants.GameConstants;
import com.test.mancala.constants.MancalaConstants;
import com.test.mancala.model.Game;
import com.test.mancala.model.GameStatus;
import com.test.mancala.repository.GameMemoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@Slf4j
class GameServiceTest {

    @MockBean
    private GameMemoryRepository gameRepository;

    @Autowired
    private GameService gameService;

    private Game game;

    @BeforeEach
    void setup(){
        game = new Game(GameConstants.INITIAL_STONE_ON_PIT,
                GameConstants.NUM_OF_PLAYERS);
    }

    @Test
    void shouldInitGame(){
        game.setId(UUID.randomUUID().toString());

        //given
        when(gameRepository.create(any(), any())).thenReturn(game);

        //when
        Game mockGame = gameService.initGame(GameConstants.INITIAL_STONE_ON_PIT,
                GameConstants.NUM_OF_PLAYERS);

        //then
        assertEquals(game, mockGame);
    }


    @Test
    void shouldPlayGame(){
        String id = UUID.randomUUID().toString();
        game.setId(id);
        game.setGameStatus(GameStatus.INIT);

        //given1
        when(gameRepository.findById(id)).thenReturn(game);

        //given2
        game.setGameStatus(GameStatus.PLAYER1_TURN);
        when(gameRepository.create(any(), any())).thenReturn(game);

        //when
        Game mockGame =  gameService.play(game.getId(), game.getBoard().getPits().get(1).getPitIndex());

        //then
        assertEquals(GameStatus.PLAYER1_TURN, mockGame.getGameStatus());
    }

}
