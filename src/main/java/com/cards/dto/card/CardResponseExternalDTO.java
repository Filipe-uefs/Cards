package com.cards.dto.card;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

@Data
@JsonDeserialize()
public class CardResponseExternalDTO {

    private String code;

    private String value;

    private String suit;

}
