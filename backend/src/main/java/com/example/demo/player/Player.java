package com.example.demo.player;

import com.example.demo.card.Card;
import com.example.demo.card.CardSuit;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Player {
    private String name;
    private String id;
    private List<Card> hand;

    @JsonCreator
    public Player(@JsonProperty("name") String name) {
        this.name = name;
        this.id = UUID.randomUUID().toString();
        this.hand = new ArrayList<>();
    }

    public void addToHand(Card card) {
        this.hand.add(card);
    }

    public CardSuit pickTrumpSuit() {
        // TODO: promt player
        return CardSuit.BLUE;
    }

    public int stateTrickCount() {
        // TODO: promt player
        return 1;
    }

    public Card playCard() {
        // TODO: promt player
        int pickedIndex = 0;
        Card pickedCard = this.hand.get(pickedIndex);
        this.hand.remove(pickedIndex);
        return pickedCard;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

}
