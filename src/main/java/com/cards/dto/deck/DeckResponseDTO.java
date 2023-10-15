package com.cards.dto.deck;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class DeckResponseDTO {

    private String gameId;

    private int remaining;

    private boolean shuffled;

}
