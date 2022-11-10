package com.example.demo.game;

import com.example.demo.card.Card;
import com.example.demo.card.CardDeck;
import com.example.demo.card.CardSuit;
import com.example.demo.card.CardType;
import com.example.demo.player.Player;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {

    private final int minPlayerCount = 3;
    private final int maxPlayerCount = 6;

    class PlayerCardPair {
        private Player player;
        private Card card;

        public PlayerCardPair(Player player, Card card) {
            this.player = player;
            this.card = card;
        }

        public Player getPlayer() {
            return player;
        }

        public Card getCard() {
            return card;
        }
    }

    // last player is dealer
    private LinkedList<Player> players;
    private Player turnPlayer;
    private Player winner;
    private int currentRound;
    private CardSuit currentTrumpSuit;
    private GameScore score;
    private CardDeck deck;
    private Stack<PlayerCardPair> playedCards;
    private boolean isStartable;

    public GameService() {
        this.deck = new CardDeck();
        this.players = new LinkedList<>();
        this.score = new GameScore(players);
        this.currentRound = 0;
        this.playedCards = new Stack<>();
        this.currentTrumpSuit = null;
        this.winner = null;
        this.isStartable = false;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        players.add(player);
        isStartable = minPlayerCount <= players.size() && players.size() <= maxPlayerCount;
    }

    public void removePlayer(String playerId) {
        players.removeIf(player -> player.getId().equals(playerId));
        isStartable = minPlayerCount <= players.size() && players.size() <= maxPlayerCount;
    }

    public Player getTurnPlayer() {
        return turnPlayer;
    }

    public void runGame() {
        if(!isStartable) {
            System.out.println("invalid player count: " + players.size());
            return;
        }

        int rounds = deck.getDeckSize() / players.size();
        for(int i = 0; i < rounds; i++) {
            currentRound = i;
            rotateDealer();
            runDealingStage();
            runBiddingStage();
            runPlayingStage();
        }
        winner = score.determineWinner(players);
    }

    private void rotateDealer() {
        Player dealer = players.removeLast();
        players.addFirst(dealer);
    }

    private void runDealingStage() {
        deck.resetDeck();

        // distribute cards
        for(int i = 0; i < currentRound; i++) {
            for(Player player : players) {
                player.addToHand(deck.popDeck());
            }
        }

        // set trump suit
        if(!deck.isDeckEmpty()) {
            Card card = deck.popDeck();
            if(card.getType() == CardType.JESTER) {
                this.currentTrumpSuit = null;
            } else if (card.getType() == CardType.WIZARD) {
                this.currentTrumpSuit = players.getLast().pickTrumpSuit();
            }
        } else {
            this.currentTrumpSuit = null;
        }
    }

    private void runBiddingStage() {
        score.resetTricks();

        for(Player player: players) {
            turnPlayer = player;
            int trickCount = player.stateTrickCount();
            score.recordTrickPrediction(player, trickCount);
        }
    }

    private void runPlayingStage() {
        playedCards.clear();

        for (int i = 0; i < currentRound; i++) {
            for(Player player: players) {
                turnPlayer = player;
                Card playedCard = player.playCard();
                playedCards.push(new PlayerCardPair(player, playedCard));
            }
            Player winner = evaluateTrickWinner();
            score.addTrick(winner);
        }

        score.recordScores(players);
    }

    private Player evaluateTrickWinner() {
        // TODO
        return playedCards.peek().getPlayer();
    }

    public void pauseGame() {
        // TODO
    }

    public Player getWinner() {
        return winner;
    }

}
