package com.test.mancala.model;

import java.util.Objects;

/**
 * This class represent the players of the game.
 */

public record Player(Integer playerIndex, String name) {

    public Player {
        Objects.requireNonNull(playerIndex);
        Objects.requireNonNull(name);
    }

}
