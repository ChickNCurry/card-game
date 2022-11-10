package com.example.demo.card;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {

    public List<Card> getCards() {
        return List.of(new Card(CardType.WIZARD, CardSuit.GREEN));
    }

}
