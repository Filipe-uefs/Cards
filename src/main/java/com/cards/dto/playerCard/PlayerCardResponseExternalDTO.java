package com.cards.dto.playerCard;

import com.cards.dto.card.CardResponseExternalDTO;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

@Data
public class PlayerCardResponseExternalDTO {

    private boolean success;

    @JsonAlias("deck_id")
    private String deckId;

    private int remaining;

    List<CardResponseExternalDTO> cards;

}
