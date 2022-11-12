package com.example.demo.game;

import com.example.demo.card.Card;
import com.example.demo.card.CardSuit;
import com.example.demo.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping(path = "/players")
    public List<Player> getPlayers() {
        return gameService.getPlayers();
    }

    @PostMapping(path = "/players")
    public void addPlayer(@RequestBody Player player) {
        gameService.addPlayer(player);
    }

    @DeleteMapping(path = "/players/{playerId}")
    public void removePlayer(@PathVariable("playerId") String id) {
        gameService.removePlayer(id);
    }

    @GetMapping(path = "/winners")
    public List<Player> getWinners() {
        return gameService.getWinners();
    }

    @GetMapping(path = "/turnPlayer")
    public Player getTurnPlayer() {
        return gameService.getTurnPlayer();
    }

    @PostMapping(path = "/hostGame")
    public void hostGame(@RequestBody Player player) {
        gameService.hostGame(player);
    }

    @PostMapping(path = "/startGame/{playerId}")
    public void startGame(@PathVariable("playerId") String id) {
        new Thread(() -> {
            try {
                gameService.runGame(id);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    @PostMapping(path = "/chooseTrumpSuit/{playerId}")
    public void chooseTrumpSuit(@PathVariable("playerId") String id, @RequestBody CardSuit suit) {
        new Thread(() -> gameService.chooseTrumpSuit(id, suit)).start();
    }

    @PostMapping(path = "/predictTricks/{playerId}")
    public void chooseTrickCount(@PathVariable("playerId") String id, @RequestBody int tricks) {
        new Thread(() -> gameService.predictTricks(id, tricks)).start();
    }

    @PostMapping(path = "/playCard/{playerId}")
    public void playCard(@PathVariable("playerId") String id, @RequestBody Card card) {
        new Thread(() -> gameService.playCard(id, card)).start();
    }

}
