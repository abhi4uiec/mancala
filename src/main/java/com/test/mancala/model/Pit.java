package com.test.mancala.model;

import com.test.mancala.constants.MancalaConstants;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * This class represent the pit of the board.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pit {

    @NotNull
    private Integer pitIndex;

    @NotNull
    private Integer stoneCount;

    @NotNull
    private Integer playerIndex;

    /**
     * Determines difference between pit and the player house.
     *
     * @return false if player tries to place stone in another players house, otherwise true
     */
    public Boolean isDistributable(final Game game) {
        Map<Integer, Integer> houses = game.getBoard().getHouses();
        int playerNumber = game.getGameStatus().getNumber();
        return (pitIndex % MancalaConstants.NUM_PITS_PER_PLAYER != 0) || (pitIndex.equals(houses.get(playerNumber)));
    }

    /**
     * Determines the ownership of current player.
     *
     * @return true if current player is the owner of this pit otherwise false.
     */
    public Boolean isPlayerPit(final GameStatus gameStatus) {
        int playerNumber = gameStatus.getNumber();
        return  playerIndex.equals(playerNumber);
    }

    /**
     *  Determines if the pit is used as a Pit or as a House.
     *
     * @return true if pit is used as a house otherwise false.
     */
    public Boolean isHouse() {
        return pitIndex % MancalaConstants.NUM_PITS_PER_PLAYER == 0;
    }

    /**
     * Find the next pit index
     *
     * @return pitIndex of the next Pit
     */
    public int nextPitIndex(final int lastIndex) {
        int index = pitIndex + 1;
        if (index > lastIndex) {
            index = 1;
        }
        return index;
    }

    /**
     * Returns the opposite pit index.
     *
     * @return pitIndex of the opposite pit.
     */
    public int getOppositePitIndex(final int lastIndex) {
        return (MancalaConstants.PIT_START_INDEX + lastIndex - 1) - pitIndex;
    }

}
