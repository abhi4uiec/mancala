package com.test.mancala.repository;

import com.test.mancala.exception.MancalaException;
import com.test.mancala.model.Game;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class stores all the games being played in a map.
 */

@Slf4j
@Component
public class GameMemoryRepository {

    private static final Map<String, Game> gameMap = new ConcurrentHashMap<>();

    /**
     * Creates a new Game and saves the object in a Map.
     *
     * @param initialPitStoneCount are the number of the stones in a pit.
     * @param numberOfPlayers
     * @return Game object.
     */
    public Game create(final Integer initialPitStoneCount, final Integer numberOfPlayers) {
        Game game = new Game(initialPitStoneCount, numberOfPlayers);
        String id = UUID.randomUUID().toString();
        game.setId(id);
        gameMap.put(id, game);
        return gameMap.get(id);
    }

    /**
     * Returns the game object by id.
     *
     * @param id is the game id.
     * @return Game
     */
    public Game findById(final String id) {
        Game game = gameMap.get(id);
        if (game == null) {
            throw new MancalaException("Game doesn't exist for id: " + id);
        }
        return game;
    }

}
