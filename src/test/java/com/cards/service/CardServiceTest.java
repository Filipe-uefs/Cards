package com.cards.service;


import com.cards.exception.BusinessException;
import com.cards.model.CardModel;
import com.cards.repository.CardRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CardServiceTest {

    @InjectMocks
    private CardService cardService;
    @Mock
    private CardRepository cardRepository;


    @Test
    public void saveSuccess() {

        List<CardModel> cardModels = generateListCardModels();
        when(cardRepository.saveAll(anyList())).thenReturn(cardModels);

        List<CardModel> cardsSave = cardService.saveAll(cardModels);
        verify(cardRepository, times(1)).saveAll(cardModels);
        Assertions.assertEquals(cardModels, cardsSave);

    }

    @Test
    public void saveErrorBusinessException() {

        String messageError = "Error business";
        List<CardModel> cardModels = generateListCardModels();

        when(cardRepository.saveAll(anyList())).thenThrow(new BusinessException(messageError, new Exception()));

        BusinessException exception = assertThrows(BusinessException.class, () -> cardService.saveAll(cardModels));
        Assertions.assertEquals(messageError, exception.getCause().getMessage());
        verify(cardRepository, times(1)).saveAll(cardModels);

    }

    private List<CardModel> generateListCardModels() {
        List<CardModel> cardModelList = new ArrayList<>();
        CardModel cardModel1 = new CardModel();
        cardModel1.setValue(8);
        cardModel1.setCode("123");
        CardModel cardModel2 = new CardModel();
        cardModel2.setValue(8);
        cardModel2.setCode("123");

        cardModelList.add(cardModel1);
        cardModelList.add(cardModel2);

        return cardModelList;
    }
}
