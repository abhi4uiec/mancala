package com.test.mancala.service;

import com.test.mancala.model.Game;

public interface GameService {

    Game initGame(Integer numberOfStones, Integer numberOfPlayers);

    Game play(String gameId, Integer pitId);
}
