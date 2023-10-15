package com.cards.dto.deck;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class DeckResponseExternalDTO {

    private String success;

    @JsonAlias("deck_id")
    private String deckId;

    private int remaining;

    private boolean shuffled;

}
