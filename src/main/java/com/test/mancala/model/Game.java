package com.test.mancala.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.IntStream;

/**
 * This class represent the game.
 * A game contain players, board and game status.
 */

@Data
@NoArgsConstructor
public class Game {

    private String id;

    private Board board;

    private Player winner;

    private GameStatus gameStatus;

    public Game(final Integer initialStoneOnPit, final Integer numOfPlayers) {
        List<Player> players = IntStream.rangeClosed(1, numOfPlayers)
                .mapToObj(i -> new Player(i, "player" + i)).toList();
        this.board = new Board(initialStoneOnPit, players);
        this.gameStatus = GameStatus.INIT;
    }

}
