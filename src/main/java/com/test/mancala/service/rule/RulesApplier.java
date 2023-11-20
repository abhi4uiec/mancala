package com.test.mancala.service.rule;

import com.test.mancala.model.Game;
import com.test.mancala.model.Pit;
import org.springframework.core.Ordered;

/**
 * Main abstraction for all the rules applier.
 * Chain of Responsibility pattern in Spring ecosystem using {@link Ordered} interface.
 * The less order number {@link RulesApplier} instance has, the sooner it is called to process the current turn
 */
public interface RulesApplier extends Ordered {

    /**
     * main entry point for each element of the chain.
     * By applying the rule, it means that each chain element changes {@param game} based on rules,
     * which it's responsible for
     *
     * @param game
     * @return
     */
    Pit applyRule(Game game, Pit currentPit);

    /**
     * The less order number {@link RulesApplier} instance has, the sooner it is called to process the current turn
     *
     * @return order number
     * @see Ordered
     */
    int getOrder();
}
