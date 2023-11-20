package com.test.mancala.service.rule;

import com.test.mancala.constants.MancalaConstants;
import com.test.mancala.exception.MancalaIllegalMoveException;
import com.test.mancala.model.Game;
import com.test.mancala.model.GameStatus;
import com.test.mancala.model.Pit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * This class checks starting rules for distributing stones.
 */
@Service
@Slf4j
public final class StartPitRule implements RulesApplier {

    @Override
    public Pit applyRule(final Game game, final Pit startPit) {
        log.debug("check rules for the start pit {}", startPit);

        checkPlayerTurnRule(game, startPit);
        checkEmptyStartRule(startPit);
        return startPit;
    }

    private void checkPlayerTurnRule(final Game game, final Pit startPit) {

        if (game.getGameStatus().equals(GameStatus.INIT)) {
            GameStatus gameStatus =  GameStatus.valueOf("PLAYER" + startPit.getPlayerIndex() + "_TURN");
            game.setGameStatus(gameStatus);
        }

        int playerNumber = game.getGameStatus().getNumber();
        // make sure player only picks stone from his own pit, otherwise throw exception
        if ((startPit.getPitIndex() > playerNumber * MancalaConstants.NUM_PITS_PER_PLAYER)
                || (startPit.getPitIndex() < playerNumber * MancalaConstants.NUM_PITS_PER_PLAYER - MancalaConstants.NUM_PITS_PER_PLAYER)) {
            throw new MancalaIllegalMoveException("Incorrect pit to play");
        }
    }

    private void checkEmptyStartRule(final Pit startPit) {
        if (startPit.getStoneCount() == 0) {
            throw new MancalaIllegalMoveException("Cannot start from empty pit");
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
