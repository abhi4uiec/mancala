# Mancala Game

A java web application that enables 2 players to play the mancala game with 6-stone in each pit.

For rules of playing a game, please watch this video:
https://www.youtube.com/watch?v=OX7rj93m6o8&t=36s

## Board Setup
* Each of the two players has his six pits in front of him. 
* To the right of the six pits, each player has a larger pit. 
* At the start of the game, there are six stones in each of the six round pits.

## Rules
* The player who begins with the first move picks up all the stones in any of his own six pits, and sows the stones on to the right, one in each of the following pits, including his own big pit. 
* No stones are put in the opponents' big pit. 
* If the player's last stone lands in his own big pit, he gets another turn. 
* This can be repeated several times before it's the other player's turn.

## Capturing Stones
* During the game the pits are emptied on both sides. 
* Always when the last stone lands in an own empty pit, the player captures his own stone and all stones in the opposite pit (the other player’s pit) and puts them in his own (big or little?) pit.

## The Game Ends
* The game is over as soon as one of the sides runs out of stones. 
* The player who still has stones in his pits keeps them and puts them in his big pit. 
* The winner of the game is the player who has the most stones in his big pit.

## Installations needed

* Java 17
* Spring 3
* Gradle
* AngularJS (for frontend)

## Running

### Build & run 

* Clone the repo


* Run
```
$ ./gradlew bootRun
```

* Run with spring profile as "dev" (default)
```
$ ./gradlew bootRun --args='--spring.profiles.active=dev'
```

* Run with spring profile "prod"
```
$ ./gradlew bootRun --args='--spring.profiles.active=prod'
```

### Build & run using Docker

* Build docker image
```
$ docker build -t mancala-game .
```

* Run the container to expose app on port 9090
```
$ docker run -it -p 9090:9090 mancala-game
```

### Play the game
Game will be accessible at: http://localhost:9090

### Documentation
Open API documentation accessible at: http://localhost:9090/api-docs

Swagger UI accessible at: http://localhost:9090/swagger-ui/index.html


