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

    public boolean isJester() {
        return type == CardType.JESTER;
    }

    public boolean isWizard() {
        return type == CardType.WIZARD;
    }

    public boolean isNumberCard() {
        return type != CardType.JESTER && type != CardType.WIZARD;
    }

    public int getNumber() {
        return type.getNumber();
    }

}
