package com.test.mancala.model;

/**
 * Identifies the current game status
 * All players have corresponding number for them
 */
public enum GameStatus {

    /**
     * Game was initiated but not started
     */
    INIT(0),

    /**
     * Player 1 turn
     */
    PLAYER1_TURN(1),

    /**
     * Player 2 turn
     */
    PLAYER2_TURN(2),

    /**
     * Player 3 turn
     */
    PLAYER3_TURN(3),

    /**
     * Player 4 turn
     */
    PLAYER4_TURN(4),

    /**
     * Game finished
     */
    FINISHED(0);

    private int number;

    GameStatus(final int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

}
