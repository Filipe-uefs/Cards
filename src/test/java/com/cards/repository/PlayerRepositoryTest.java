package com.cards.repository;

import com.cards.model.CardModel;
import com.cards.model.GameModel;
import com.cards.model.PlayerModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlayerRepositoryTest {

    @Mock
    PlayerRepository playerRepository;

    @Test
    public void findByNameIgnoreCaseAndGameSuccess() {

        List<PlayerModel> playerModelList = getPlayers();
        String name = "Filipe";
        GameModel gameModel = new GameModel();
        when(playerRepository.findByNameIgnoreCaseAndGame(name, gameModel)).thenReturn(playerModelList);

        List<PlayerModel> playerModelsReturn = playerRepository.findByNameIgnoreCaseAndGame(name, gameModel);
        verify(playerRepository, times(1)).findByNameIgnoreCaseAndGame(name, gameModel);
        Assertions.assertEquals(playerModelsReturn, playerModelList);

    }

    public List<PlayerModel> getPlayers() {
        List<PlayerModel> playerModelList = new ArrayList<>();

        PlayerModel playerModel = new PlayerModel();
        playerModel.setName("Filipe");
        playerModel.setCards(getCardsModel(Arrays.asList(1, 2, 3, 4, 4)));

        PlayerModel playerModel1 = new PlayerModel();
        playerModel1.setName("Luffy");
        playerModel1.setCards(getCardsModel(Arrays.asList(1, 4, 4, 4, 4)));

        PlayerModel playerModel2 = new PlayerModel();
        playerModel2.setName("Zoro");
        playerModel2.setCards(getCardsModel(Arrays.asList(1, 1, 1, 1, 1)));

        PlayerModel playerModel3 = new PlayerModel();
        playerModel3.setName("Ace");
        playerModel3.setCards(getCardsModel(Arrays.asList(1, 1, 1, 2, 1)));

        playerModelList.add(playerModel);
        playerModelList.add(playerModel1);
        playerModelList.add(playerModel2);
        playerModelList.add(playerModel3);
        return playerModelList;
    }

    public List<CardModel> getCardsModel(List<Integer> values) {
        List<CardModel> cardModels = new ArrayList<>();
        values.forEach(value -> {
            CardModel cardModel = new CardModel();
            cardModel.setValue(value);
            cardModels.add(cardModel);
        });
        return cardModels;
    }

}
