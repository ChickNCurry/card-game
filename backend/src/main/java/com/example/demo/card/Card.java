package com.example.demo.card;

public class Card {

    private Long id;
    private CardType type;
    private CardSuit suit;

    public Card(Long id, CardType type, CardSuit color) {
        this.id = id;
        this.type = type;
        this.suit = color;
    }

    public Card(CardType type, CardSuit color) {
        this.type = type;
        this.suit = color;
    }

    public CardType getType() {
        return type;
    }

    public CardSuit getSuit() {
        return suit;
    }

}
