package com.cards.service;

import com.cards.dto.card.CardResponseExternalDTO;
import com.cards.dto.deck.DeckResponseExternalDTO;
import com.cards.dto.playerCard.PlayerCardResponseExternalDTO;
import com.cards.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApiDeckServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ApiDeckService apiDeckService;

    @Test
    public void testGetNewDeckShuffle() {
        DeckResponseExternalDTO expectedDeck = new DeckResponseExternalDTO();
        when(restTemplate.getForObject("https://www.deckofcardsapi.com/api/deck/new/shuffle/", DeckResponseExternalDTO.class))
                .thenReturn(expectedDeck);

        DeckResponseExternalDTO actualDeck = apiDeckService.getNewDeckShuffle();

        Assertions.assertEquals(expectedDeck, actualDeck);
    }

    @Test
    public void testGenerateCardsForPlayer() {
        String deckId = "testDeckId";
        int qtdCards = 5;
        String url = "https://www.deckofcardsapi.com/api/deck/testDeckId/draw/?count=5";

        PlayerCardResponseExternalDTO playerCardResponseDTO = new PlayerCardResponseExternalDTO();
        List<CardResponseExternalDTO> expectedCards = new ArrayList<>();
        expectedCards.add(new CardResponseExternalDTO());
        playerCardResponseDTO.setCards(expectedCards);

        when(restTemplate.getForObject(url, PlayerCardResponseExternalDTO.class)).thenReturn(playerCardResponseDTO);

        List<CardResponseExternalDTO> actualCards = apiDeckService.generateCardsForPlayer(deckId, qtdCards);

        Assertions.assertEquals(expectedCards, actualCards);
    }

    @Test
    public void testGetNewDeckShuffle_Error() {

        String url = "https://www.deckofcardsapi.com/api/deck/new/shuffle/";
        when(restTemplate.getForObject(url, DeckResponseExternalDTO.class))
                .thenThrow(new RuntimeException("Simulate error"));

       BusinessException exception = assertThrows(BusinessException.class, () -> apiDeckService.getNewDeckShuffle());
       verify(restTemplate, times(1)).getForObject(url, DeckResponseExternalDTO.class);
       Assertions.assertNotNull(exception.getMessage());
    }

    @Test
    public void testGenerateCardsForPlayer_Error() {
        String deckId = "testDeckId";
        int qtdCards = 5;
        String url = "https://www.deckofcardsapi.com/api/deck/testDeckId/draw/?count=5";

        when(restTemplate.getForObject(url, PlayerCardResponseExternalDTO.class))
                .thenThrow(new RuntimeException("Simulate error"));

        BusinessException exception = assertThrows(BusinessException.class, () -> apiDeckService.generateCardsForPlayer(deckId, qtdCards));
        verify(restTemplate, times(1)).getForObject(url, PlayerCardResponseExternalDTO.class);
        Assertions.assertNotNull(exception.getMessage());
    }
}