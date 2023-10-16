package com.cards.enums;

import lombok.Getter;


@Getter
public enum CardEnum {

    ACE("ACE", 1),
    KING("KING", 13),
    JACK("JACK", 11),
    QUEEN("QUEEN", 12);

    private final String name;
    private final int value;

    CardEnum(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public static CardEnum getCarEnumByName(String name) {
        for(CardEnum cardEnum: CardEnum.values()) {
            if (cardEnum.getName().equalsIgnoreCase(name)) {
                return cardEnum;
            }
        }
        return null;
    }
}
