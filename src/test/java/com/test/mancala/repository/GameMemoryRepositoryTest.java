package com.test.mancala.repository;

import com.test.mancala.constants.GameConstants;
import com.test.mancala.model.Game;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class GameMemoryRepositoryTest {

    @Autowired
    private GameMemoryRepository  gameMemoryRepository;

    @Test
    void createGame(){

        //given
        Game newGame = gameMemoryRepository.create(GameConstants.INITIAL_STONE_ON_PIT,
                GameConstants.NUM_OF_PLAYERS);

        //when
        Game game = gameMemoryRepository.findById(newGame.getId());

        //assert
        assertNotNull(game);
        assertEquals(game, newGame);
    }
}
