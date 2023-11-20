package com.test.mancala.service.rule;

import com.test.mancala.constants.MancalaConstants;
import com.test.mancala.model.Board;
import com.test.mancala.model.Game;
import com.test.mancala.model.GameStatus;
import com.test.mancala.model.Pit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * This class is responsible to check the last stone placing rules.
 */
@Service
@Slf4j
public final class EndPitRule implements RulesApplier {

    @Override
    public Pit applyRule(final Game game, final Pit endPit) {
        log.debug("checking end rule for the last pit {}", endPit);

        lastEmptyPitRule(game, endPit);
        nextPlayerTurnRule(game, endPit);
        return endPit;
    }

    private void lastEmptyPitRule(final Game game, final Pit endPit) {
        Board board = game.getBoard();
        // if the end pit is not a house && end pit is owned by a player && endPit has only 1 stone left,
        // then pick all the stones from opponent pit as well, and place them in players house
        if (Boolean.TRUE.equals(!endPit.isHouse() && endPit.isPlayerPit(game.getGameStatus()))
                && endPit.getStoneCount().equals(1)) {
            Pit oppositePit = board.getOppositePit(endPit);
            if (oppositePit.getStoneCount() > 0) {
                Pit house = board.getPlayerHouse(endPit.getPlayerIndex());
                house.setStoneCount((house.getStoneCount() + oppositePit.getStoneCount()) + endPit.getStoneCount());
                oppositePit.setStoneCount(0);
                endPit.setStoneCount(0);
            }
        }
    }

    private void nextPlayerTurnRule(final Game game, final Pit endPit) {
        GameStatus gameStatus = game.getGameStatus();
        int playerNumber = gameStatus.getNumber();
        Map<Integer, Integer> houses = game.getBoard().getHouses();
        // if endPit is a house and endPit is owned by a player, then continue with the current player
        if (Boolean.TRUE.equals(endPit.getPitIndex() == houses.get(playerNumber)) && playerNumber == endPit.getPlayerIndex()) {
            return;
        } else if (playerNumber == MancalaConstants.NUM_OF_PLAYERS) {
            // if it's the last player, then next turn will be of the first player
            game.setGameStatus(GameStatus.PLAYER1_TURN);
            log.debug("setting game status for pit {} as {}", endPit, gameStatus);
        } else {
            // move on to the next player
            game.setGameStatus(GameStatus.valueOf("PLAYER" + (playerNumber + 1) + "_TURN"));
            log.debug("setting last game status for pit {} as {}", endPit, gameStatus);
        }

    }

    @Override
    public int getOrder() {
        return 2;
    }
}
