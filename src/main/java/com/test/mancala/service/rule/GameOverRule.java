package com.test.mancala.service.rule;

import com.test.mancala.constants.MancalaConstants;
import com.test.mancala.model.Board;
import com.test.mancala.model.Game;
import com.test.mancala.model.GameStatus;
import com.test.mancala.model.Pit;
import com.test.mancala.model.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class is responsible to check the game end rules.
 */
@Service
@Slf4j
public final class GameOverRule implements RulesApplier {

    @Override
    public Pit applyRule(final Game game, final Pit currentPit) {
        log.debug("checking game end rule");

        Map<Integer, Integer> stoneCount = new HashMap<>();
        boolean isGameFinished = isGameOver(game, stoneCount);

        if (isGameFinished) {
            game.setGameStatus(GameStatus.FINISHED);

            Board board = game.getBoard();
            for (int i = 1; i <= MancalaConstants.NUM_OF_PLAYERS; i++) {
                Pit house = board.getPits().get(board.getHouses().get(i));
                house.setStoneCount(house.getStoneCount() + stoneCount.get(i));
            }

            findWinner(game);

            resetBoard(game);
        }

        return currentPit;
    }

    /**
     * Determines whether game is over or not
     *
     * @param game
     * @param stoneCount
     * @return true if either of the player has no more stones left in his pits
     */
    private static boolean isGameOver(final Game game, final Map<Integer, Integer> stoneCount) {
        boolean isGameFinished = false;
        int startIndex = MancalaConstants.PIT_START_INDEX;
        int endIndex = MancalaConstants.NUM_PITS_PER_PLAYER;
        for (int i = 1; i <= MancalaConstants.NUM_OF_PLAYERS; i++) {
            int playerStoneCount = game.getBoard().getPlayerPitStoneCount(startIndex, endIndex);
            stoneCount.put(i, playerStoneCount);
            if (playerStoneCount == 0) {
                isGameFinished = true;
            }
            startIndex = endIndex + 1;
            endIndex = (i + 1) * MancalaConstants.NUM_PITS_PER_PLAYER;
        }
        return isGameFinished;
    }

    private static void findWinner(final Game game) {
        int max = 0;
        Board board = game.getBoard();
        for (int i = 1; i <= MancalaConstants.NUM_OF_PLAYERS; i++) {
            Pit house = board.getPits().get(board.getHouses().get(i));
            int count = house.getStoneCount();
            if (count > max) {
                max = count;
                game.setWinner(new Player(i, "Player" + i));
            } else if (count == max) {
                log.debug("Game with id {} ends in a draw", game.getId());
                game.setWinner(null);
            }
        }
    }

    private void resetBoard(final Game game) {
        Map<Integer, Pit> pits = game.getBoard().getPits();
        Set<Integer> pitSet = pits.keySet();
        for (Integer key : pitSet) {
            if (key % MancalaConstants.NUM_PITS_PER_PLAYER == 0) {
                continue;
            }
            Pit pit = pits.get(key);
            pit.setStoneCount(0);
        }
    }

    @Override
    public int getOrder() {
        return 3;
    }
}
