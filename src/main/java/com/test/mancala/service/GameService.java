package com.test.mancala.service;

import com.test.mancala.model.Game;

public interface GameService {

    Game initGame(Integer pitNumber);

    Game play(String gameId, Integer pitId);
}
