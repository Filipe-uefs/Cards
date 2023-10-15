package com.cards.dto.player;

import com.cards.dto.card.CardResponseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class PlayerResponseDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer totalValue;

    private String gameId;

    private UUID playerId;

    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CardResponseDTO> cards;

    public Integer calculatedTotalValue() {
        this.totalValue = cards.stream().mapToInt(CardResponseDTO::getValue).sum();
        return this.totalValue;
    }
}
