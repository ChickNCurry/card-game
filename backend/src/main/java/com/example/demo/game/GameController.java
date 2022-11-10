package com.example.demo.game;

import com.example.demo.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
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



}
