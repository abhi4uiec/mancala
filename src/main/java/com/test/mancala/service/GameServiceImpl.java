package com.test.mancala.service;

import com.test.mancala.constants.MancalaConstants;
import com.test.mancala.exception.MancalaException;
import com.test.mancala.exception.MancalaIllegalMoveException;
import com.test.mancala.model.Board;
import com.test.mancala.model.Game;
import com.test.mancala.model.Pit;
import com.test.mancala.repository.GameMemoryRepository;
import com.test.mancala.service.rule.RulesApplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * This is a service class implementation
 */

@Slf4j
@Service
public class GameServiceImpl implements GameService {

    private final GameMemoryRepository gameMemoryRepository;

    private final List<RulesApplier> rulesAppliers;

    public GameServiceImpl(final GameMemoryRepository gameMemoryRepository, final List<RulesApplier> rulesAppliers) {
        this.gameMemoryRepository = gameMemoryRepository;
        this.rulesAppliers = rulesAppliers;
    }

    /**
     * Responsible for initializing a new game.
     *
     * @param initialPitStoneCount are the initial number of stones in each pit.
     * @param numberOfPlayers Number of players in the game
     * @return Game
     */
    @Override
    public Game initGame(final Integer initialPitStoneCount, final Integer numberOfPlayers) {
        return gameMemoryRepository.create(initialPitStoneCount, numberOfPlayers);
    }


    /**
     * Responsible for every new move of the stones from a pit.
     *
     * @param gameId game id
     * @param pitIndex index of the pit
     * @return Game
     */
    @Override
    public Game play(final String gameId, final Integer pitIndex) {
        log.debug("gameId {}, pitIndex {}", gameId, pitIndex);

        Game game = gameMemoryRepository.findById(gameId);

        Board board = game.getBoard();
        int pitEndIndex = MancalaConstants.NUM_PITS_PER_PLAYER * board.getHouses().size();
        if (pitIndex > pitEndIndex || pitIndex < MancalaConstants.PIT_START_INDEX) {
            throw new MancalaException("Incorrect pit index");
        }

        if (pitIndex % MancalaConstants.NUM_PITS_PER_PLAYER == 0) {
            throw new MancalaIllegalMoveException("House stones cannot be distributed");
        }

        AtomicReference<Pit> pit = new AtomicReference<>(board.getPitByPitIndex(pitIndex));

        rulesAppliers.forEach(applier ->
            pit.set(applier.applyRule(game, pit.get())));
        return game;
    }

}
