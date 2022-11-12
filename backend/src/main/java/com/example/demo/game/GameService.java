package com.example.demo.game;

import com.example.demo.card.Card;
import com.example.demo.card.CardSuit;
import com.example.demo.card.CardType;
import com.example.demo.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {

    private final GameState state;
    private final GameScore score;

    @Autowired
    public GameService(GameState state, GameScore score) {
        this.state = state;
        this.score = score;
    }

    public void hostGame(Player player) {
        state.setHost(player);
    }

    public void addPlayer(Player player) {
        state.addPlayer(player);
        score.addPlayer(player);
    }

    public void removePlayer(String playerId) {
        state.removePlayer(playerId);
        score.removePlayer(playerId);
    }

    public List<Player> getPlayers() {
        return state.getPlayers();
    }

    public Player getTurnPlayer() {
        return state.getCurrentTurnPlayer();
    }

    public synchronized void runGame(String playerId) throws InterruptedException {
        if(!playerId.equals(state.getHost().getId())) return;

        if(!state.isReady()) {
            System.out.println("invalid player count: " + state.getPlayerCount());
            return;
        }

        int rounds = state.getDeck().getDeckSize() / state.getPlayerCount();
        for(int i = 0; i < rounds; i++) {
            state.setCurrentRound(i + 1);
            runDealingStage();
            runBiddingStage();
            runPlayingStage();
            state.rotateDealer();
        }

        state.setWinners(score.determineWinners(state.getPlayers()));
    }

    private synchronized void runDealingStage() throws InterruptedException {
        state.setCurrentGameStage(GameStage.DEALING);
        state.resetDeck();

        // distribute cards
        for(int i = 0; i < state.getCurrentRound(); i++) {
            for(Player player : state.getPlayers()) {
                player.addToHand(state.popDeck());
            }
        }

        // set trump suit
        if(!state.isDeckEmpty()) {
            Card card = state.popDeck();
            if(card.type() == CardType.JESTER) {
                state.setCurrentTrumpSuit(null);
            } else if (card.type() == CardType.WIZARD) {
                // wait until trump suit is chosen by dealer
                state.setCurrentTurnPlayer(state.getPlayers().getLast());
                state.setTrumpSuitChosen(false);
                while (!state.isTrumpSuitChosen()) {
                    wait();
                }
                state.setCurrentTurnPlayer(null);
            }
        } else {
            state.setCurrentTrumpSuit(null);
        }
    }

    private synchronized void runBiddingStage() throws InterruptedException {
        state.setCurrentGameStage(GameStage.BIDDING);
        score.resetTricks();

        for(Player player: state.getPlayers()) {
            // wait until trick count is chosen by turn player
            state.setCurrentTurnPlayer(player);
            state.setTricksPredicted(false);
            while(!state.isTricksPredicted()) {
                wait();
            }
            state.setCurrentTurnPlayer(null);
        }
    }

    private synchronized void runPlayingStage() throws InterruptedException {
        state.setCurrentGameStage(GameStage.PLAYING);
        state.resetPlayedCards();

        for (int i = 0; i < state.getCurrentRound(); i++) {
            for(Player player: state.getPlayers()) {
                // wait until card is played by turn player
                state.setCurrentTurnPlayer(player);
                state.setCardPlayed(false);
                while(!state.isCardPlayed()) {
                    wait();
                }
                state.setCurrentTurnPlayer(null);
            }
            Player winner = evaluateTrickWinner();
            score.addToTrickCount(winner);
        }

        score.recordScores(state.getPlayers());
    }

    public synchronized void chooseTrumpSuit(String playerId, CardSuit suit) {
        if(state.getCurrentGameStage() == GameStage.DEALING
                && playerId.equals(state.getCurrentTurnPlayer().getId())) {
            state.setCurrentTrumpSuit(suit);
            state.setTrumpSuitChosen(true);
            notifyAll();
        }
    }

    public synchronized void predictTricks(String playerId, int tricks) {
        if(state.getCurrentGameStage() == GameStage.BIDDING
                && playerId.equals(state.getCurrentTurnPlayer().getId())) {
            score.recordTrickPrediction(state.getCurrentTurnPlayer(), tricks);
            state.setTricksPredicted(true);
            notifyAll();
        }
    }

    public synchronized void playCard(String playerId, Card card) {
        if(state.getCurrentGameStage() == GameStage.PLAYING
                && playerId.equals(state.getCurrentTurnPlayer().getId())) {
            if(state.getCurrentTurnPlayer().hasInHand(card)) {
                state.playCard(card);
                state.setCardPlayed(true);
                notifyAll();
            }
        }
    }

    private Player evaluateTrickWinner() {
        // TODO
        return state.getPlayers().peek();
    }

    public List<Player> getWinners() {
        return state.getWinners();
    }

}
