package com.cards.enums;

import lombok.Getter;

@Getter
public enum LengthLimitPlayersEnum {

    MAX_PLAYER_PER_GAME(4),

    MAX_CARDS_PER_PLAYER(5);

    private final int value;

    LengthLimitPlayersEnum(final int newValue) {
        value = newValue;
    }

}