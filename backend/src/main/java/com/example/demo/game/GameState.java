package com.example.demo.game;

import com.example.demo.card.CardDeck;
import com.example.demo.card.CardSuit;
import com.example.demo.player.Player;

import java.util.*;

public class GameState {

    enum Stage {
        DEALING,
        BIDDING,
        Playing
    }

    private List<Player> players;
    private Player dealer;
    private int currentRound;
    private Stage currentStage;
    private CardSuit currentTrumpSuit;
    private ScorePad scorePad;
    private CardDeck deck;

    public GameState(List<Player> players,
                     Player dealer,
                     int currentRound,
                     Stage currentStage,
                     CardSuit currentTrumpSuit,
                     ScorePad scorePad,
                     CardDeck deck) {
        this.players = players;
        this.dealer = dealer;
        this.currentRound = currentRound;
        this.currentStage = currentStage;
        this.currentTrumpSuit = currentTrumpSuit;
        this.scorePad = scorePad;
        this.deck = deck;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public void dealingStage() {
        deck.resetDeck();

        // check whether game is finished
        if(deck.getDeckSize() < players.size() * currentRound) {
            finalizeGame();
            return;
        }

        // distribute cards
        for(int i = 0; i < currentRound; i++) {
            for(Player player : players) {
                player.addToHand(deck.popDeck());
            }
        }

        // set trump suit
        if(!deck.isDeckEmpty()) {
            this.currentTrumpSuit = deck.popDeck().getSuit();
        } else {
            this.currentTrumpSuit = null;
        }

    }

    public void finalizeGame() {

    }

}
