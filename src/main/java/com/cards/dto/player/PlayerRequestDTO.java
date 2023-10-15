package com.cards.dto.player;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PlayerRequestDTO {

    @NotBlank(message = "Field 'gameId' is required")
    private String gameId;

    @NotBlank(message = "Field 'name' is required")
    private String name;

}
