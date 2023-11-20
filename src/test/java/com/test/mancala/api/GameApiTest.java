package com.test.mancala.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.mancala.model.Game;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GameApiTest {

    private static final Integer INITIAL_STONE_ON_PIT = 6;
    private static final Integer INITIAL_STONE_ON_HOUSE = 0;
    private static final Integer PLAYER1_INDEX = 1;
    private static final Integer PLAYER2_INDEX = 2;

    private final static String BASE_URI = "http://localhost";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @LocalServerPort
    private int port;

    @BeforeEach
    public void configureRestAssured() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
    }

    @Test
    void shouldNotStartWithIndexGreaterThanAvailable() throws Exception {
        String response = given().post("/api/mancala/games").asPrettyString();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        Game game = objectMapper.readValue(response, Game.class);

        given().pathParams("pitIndex", 34,
                        "gameId", game.getId())
                .put("/api/mancala/games/{gameId}/pits/{pitIndex}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", Matchers.is("Incorrect pit index"));
    }

    @Test
    void shouldInitGame() {

        given().post("/api/mancala/games")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", Matchers.notNullValue())
                //check game state
                .body("gameStatus", Matchers.is("INIT"))
                //check total pit size
                .body("board.pits.size()", Matchers.is(14))
                //check pit index
                .body("board.pits.1.pitIndex", Matchers.is(1))
                .body("board.pits.8.pitIndex", Matchers.is(8))
                .body("board.pits.14.pitIndex", Matchers.is(14))
                //check pit owner
                .body("board.pits.6.playerIndex", Matchers.is(PLAYER1_INDEX))
                .body("board.pits.13.playerIndex", Matchers.is(PLAYER2_INDEX))
                .body("board.pits.14.playerIndex", Matchers.is(PLAYER2_INDEX))
                //check initial pit stone count
                .body("board.pits.5.stoneCount", Matchers.is(INITIAL_STONE_ON_PIT))
                .body("board.pits.12.stoneCount", Matchers.is(INITIAL_STONE_ON_PIT))
                .body("board.pits.14.stoneCount", Matchers.is(INITIAL_STONE_ON_HOUSE));
    }

    @Test
    void shouldPlayGame() throws Exception {

        String response = given().post("/api/mancala/games").asPrettyString();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        Game game = objectMapper.readValue(response, Game.class);

        given().pathParams("pitIndex", 1,
                        "gameId", game.getId())
                .put("/api/mancala/games/{gameId}/pits/{pitIndex}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("id", Matchers.is(game.getId()))
                //check total pit size
                .body("board.pits.size()", Matchers.is(14))
                //starting pit should be zero
                .body("board.pits.1.stoneCount", Matchers.is(0))
                //pit should increment by 1
                .body("board.pits.2.stoneCount", Matchers.is(7))
                .body("board.pits.3.stoneCount", Matchers.is(7))
                .body("board.pits.4.stoneCount", Matchers.is(7))
                .body("board.pits.5.stoneCount", Matchers.is(7))
                .body("board.pits.6.stoneCount", Matchers.is(7))
                //player 1 house should increment by 1
                .body("board.pits.7.stoneCount", Matchers.is(1))
                //check game state as end with player 1 house
                .body("gameStatus", Matchers.is("PLAYER1_TURN"));
    }

}