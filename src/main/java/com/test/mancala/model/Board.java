package com.test.mancala.model;

import com.test.mancala.constants.MancalaConstants;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

/**
 * This class represent the board of the game.
 * Board contain all the pits and the houses.
 */

@Data
@NoArgsConstructor
public class Board {

    // Holds the houses pitIndex for each player, key = player number and value = pitIndex
    private Map<Integer, Integer> houses;

    private Map<Integer, Pit> pits;

    public Board(final int initialStoneOnPit, final List<Player> players) {
        pits = initPit(initialStoneOnPit, players);
        houses = new HashMap<>();
        IntStream.rangeClosed(1, MancalaConstants.NUM_OF_PLAYERS)
                .forEach(i -> houses.put(i, i * MancalaConstants.NUM_PITS_PER_PLAYER));
    }

    /**
     *
     * @param initialStoneOnPit
     * @param players
     * @return builds up the pit for all the players
     */
    private Map<Integer, Pit> initPit(
            final int initialStoneOnPit,
            final List<Player> players) {

        Map<Integer, Pit> pits = new ConcurrentHashMap<>();

        int startIndex = MancalaConstants.PIT_START_INDEX;
        int endIndex = MancalaConstants.NUM_PITS_PER_PLAYER;

        for (Player player : players) {
            IntStream.range(startIndex, endIndex).forEach(i -> {
                Pit pit = new Pit(i, initialStoneOnPit, player.playerIndex());
                pits.put(i, pit);
            });
            Pit house = new Pit(endIndex, MancalaConstants.INITIAL_STONE_ON_HOUSE, player.playerIndex());
            pits.put(endIndex, house);
            startIndex = endIndex + 1;
            endIndex = endIndex + MancalaConstants.NUM_PITS_PER_PLAYER;
        }

        return pits;
    }

    /**
     *
     * @param playerIndex
     * @return pit of the players house
     */
    public Pit getPlayerHouse(final int playerIndex) {
        return pits.get(houses.get(playerIndex));
    }

    /**
     *
     * @param pitIndex
     * @return pit by pitIndex
     */
    public Pit getPitByPitIndex(final int pitIndex) {
        return pits.get(pitIndex);
    }

    /**
     *
     * @param pit
     * @return the next pit
     */
    public Pit getNextPit(final Pit pit) {
        return pits.get(pit.nextPitIndex());
    }

    /**
     *
     * @param pit
     * @return the opposite pit
     */
    public Pit getOppositePit(final Pit pit) {
        return pits.get(pit.getOppositePitIndex());
    }

    /**
     *
     * @param startIndex
     * @param endIndex
     * @return the total number of stones each player holds
     */
    public int getPlayerPitStoneCount(final int startIndex, final int endIndex) {
        return IntStream.range(startIndex, endIndex).map(i -> pits.get(i).getStoneCount()).sum();
    }

    /**
     *
     * @param pitIndex Pit index.
     *
     * @return number of total stone in a particlar pit
     */
    public int getStoneCountByPitIndex(final int pitIndex) {
        return getPitByPitIndex(pitIndex).getStoneCount();
    }

}
