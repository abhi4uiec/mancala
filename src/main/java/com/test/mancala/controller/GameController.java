package com.test.mancala.controller;

import com.test.mancala.model.Game;
import com.test.mancala.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/mancala")
public class GameController {

    private final GameService gameService;

    public GameController(final GameService gameService) {
        this.gameService = gameService;
    }

    @Operation(summary = "Sets up the board")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Board setup successful",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Game.class)) }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Board setup failed",
                    content = @Content) })
    @PostMapping("/games")
    @ResponseStatus(HttpStatus.CREATED)
    public Game initBoard(
            @Parameter(description = "number of stones in each pit to start the game")
            @RequestParam(name = "stones", defaultValue = "6", required = false)
            final Integer numberOfStones,
            @Parameter(description = "number of players in the game")
            @RequestParam(name = "players", defaultValue = "2", required = false)
            final Integer numberOfPlayers) {
        log.debug("Initializing mancala game");

        return gameService.initGame(numberOfStones, numberOfPlayers);
    }

    @Operation(summary = "Moves stones from pit based on rules")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Move was successful",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Game.class)) }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Move was unsuccessful",
                    content = @Content) })
    @PutMapping("/games/{gameId}/pits/{pitIndex}")
    @ResponseStatus(HttpStatus.OK)
    public Game play(
            @Parameter(description = "unique id assigned to each game")
            @PathVariable final String gameId,
            @Parameter(description = "pit number")
            @PathVariable final Integer pitIndex) {
        log.debug("From game {}, player is moving stone from pit {}", gameId, pitIndex);

        return gameService.play(gameId, pitIndex);
    }

}
