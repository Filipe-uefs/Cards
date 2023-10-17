package com.cards.enums;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class RandomPlayersEnumTest {


    @Test

    public void generateRandomPlayersSuccessLength4() {

        int count = 4;
        List<String> names = RandomPlayersEnum.generateRandomPlayers(count);

        Assertions.assertEquals(count, names.size());
    }

    @Test
    public void generateRandomPlayersSuccessLength0() {

        int count = 0;
        List<String> names = RandomPlayersEnum.generateRandomPlayers(count);

        Assertions.assertTrue(names.isEmpty());
    }
}
