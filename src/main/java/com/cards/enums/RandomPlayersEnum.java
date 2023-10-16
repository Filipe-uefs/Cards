package com.cards.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Getter
public enum RandomPlayersEnum {

    ACE("Ace", 0),
    LUFFY("Luffy", 1),
    ZORO("Zoro", 2),
    NAMI("Nami", 3),
    ROBIN("Robin", 4),
    SANJI("Sanji", 5),
    BROOK("Brook", 6),
    FRANKY("Franky", 7),
    JIMBE("Jimbe", 8),
    SHANKS("Shanks", 9),
    GARP("Garp", 10),
    COBY("Coby", 11),
    AKAINU("Akainu", 12),
    IM_SAMA("Im-Sama", 13);


    private final String name;
    private final int value;

    RandomPlayersEnum(String name, int value) {
        this.name = name;
        this.value = value;
    }

    private static String getNameByValue(int value) {
        for(RandomPlayersEnum randomPlayersEnum: RandomPlayersEnum.values()) {
            if (randomPlayersEnum.getValue() == value) {
                return randomPlayersEnum.getName();
            }
        }
        return null;
    }

    public static List<String> generateRandomPlayers(int count) {
        List<String> names = new ArrayList<>();
        List<Integer> randList = new Random().ints(count, 0, 13)
                .boxed().collect(Collectors.toList());

        randList.forEach(number -> {
            names.add(getNameByValue(number));
        });

        return names;
    }

}
