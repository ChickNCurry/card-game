package com.example.demo.card;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record Card(CardType type, CardSuit suit) {

    @JsonCreator
    public Card(@JsonProperty("cardType") CardType type,
                @JsonProperty("cardSuit") CardSuit suit) {
        this.type = type;
        this.suit = suit;
    }

}
