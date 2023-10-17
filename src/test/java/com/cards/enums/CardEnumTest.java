package com.cards.enums;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CardEnumTest {

    @Test
    public void getCarEnumByNameSuccess() {
        CardEnum cardEnum = CardEnum.ACE;
        String name = "ace";

        Assertions.assertEquals(CardEnum.getCarEnumByName(name), cardEnum);

    }

    @Test
    public void getCarEnumByNameSuccessReturnNull() {

        String name = "casasBahia";
        Assertions.assertNull(CardEnum.getCarEnumByName(name));

    }
}
