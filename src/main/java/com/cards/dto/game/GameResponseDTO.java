package com.cards.dto.game;

import com.cards.dto.player.PlayerResponseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GameResponseDTO implements Serializable {

    private String gameId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<PlayerResponseDTO> winners;

    private boolean shuffled;

    private int remaining;

    private List<PlayerResponseDTO> players;

}
