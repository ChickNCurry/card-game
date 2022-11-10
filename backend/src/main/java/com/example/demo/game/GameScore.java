package com.example.demo.game;

import com.example.demo.player.Player;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class GameScore {

    private HashMap<String, Integer> trickPredictionMap;
    private HashMap<String, Integer> trickCountMap;
    private HashMap<String, Integer> scoreMap;

    public GameScore(List<Player> players) {
        trickPredictionMap = new HashMap<>();
        trickCountMap = new HashMap<>();
        scoreMap = new HashMap<>();
        for(Player player: players) {
            trickPredictionMap.put(player.getId(), 0);
            trickCountMap.put(player.getId(), 0);
            scoreMap.put(player.getId(), 0);
        }
    }

    public void resetTricks() {
        trickPredictionMap.clear();
        trickCountMap.clear();
    }

    public void recordTrickPrediction(Player player, int trickPrediction) {
        trickPredictionMap.put(player.getId(), trickPrediction);
    }

    public void addTrick(Player player) {
        int currentTrickCount = trickCountMap.get(player.getId());
        trickCountMap.replace(player.getId(), currentTrickCount + 1);
    }

    public void recordScores(LinkedList<Player> players) {
        for(Player player: players) {
            int trickResult = trickCountMap.get(player.getId());
            int trickPrediction = trickPredictionMap.get(player.getId());
            int oldScore = scoreMap.get(player.getId());
            int newScore;
            if (trickPrediction == trickResult) {
                newScore = oldScore + 20 + (10 * trickResult);
            } else {
                newScore = oldScore - (10 * Math.abs(trickPrediction - trickResult));
            }
            scoreMap.replace(player.getId(), newScore);
        }
    }

    public Player determineWinner(LinkedList<Player> players) {
        Player winner = players.getFirst();
        for(Player player: players) {
            if(scoreMap.get(player.getId()) > scoreMap.get(winner.getId())) {
                winner = player;
            }
        }
        return winner;
    }

}
