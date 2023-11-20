package com.test.mancala.service.rule;

import com.test.mancala.model.Game;
import com.test.mancala.model.Pit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * This class is responsible to distribute stones to the next pits except for the opponent house.
 */
@Service
@Slf4j
public final class DistributePitStoneRule implements RulesApplier {

    @Override
    public Pit applyRule(final Game game, Pit currentPit) {
        log.debug("Check the rules for distributing stone to the next pit(s)");

        int stoneToDistribute = currentPit.getStoneCount();
        currentPit.setStoneCount(0);

        for (int i = 0; i < stoneToDistribute; i++) {
            currentPit = game.getBoard().getNextPit(currentPit);
            log.debug("next pit {}", currentPit);
            if (Boolean.TRUE.equals(currentPit.isDistributable(game))) {
                currentPit.setStoneCount(currentPit.getStoneCount() + 1);
            } else {
                i--;
            }
        }
        return currentPit;
    }

    @Override
    public int getOrder() {
        return 1;
    }

}
