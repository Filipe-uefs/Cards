package com.cards.service;

import com.cards.dto.card.CardResponseExternalDTO;
import com.cards.dto.deck.DeckResponseExternalDTO;
import com.cards.dto.playerCard.PlayerCardResponseExternalDTO;
import com.cards.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiDeckService {

    private final RestTemplate restTemplate;

    public DeckResponseExternalDTO getNewDeckShuffle() {

        String url = null;
        try {
            url = "https://www.deckofcardsapi.com/api/deck/new/shuffle/";
            return restTemplate.getForObject(url, DeckResponseExternalDTO.class);

        } catch (Exception error) {
            String messageError = String.format("Generic error when try call endpoint for create new deck, for url: %s", url);
            throw new BusinessException(messageError, error);
        }
    }

    public List<CardResponseExternalDTO> generateCardsForPlayer(String deckId, int qtdCards) {

        String url = null;
        try {
            url = "https://www.deckofcardsapi.com/api/deck/{deckId}/draw/?count=" + qtdCards;
            url = url.replace("{deckId}", deckId);

            PlayerCardResponseExternalDTO playerCardResponseDTO= restTemplate.getForObject(url, PlayerCardResponseExternalDTO.class);
            if (playerCardResponseDTO == null) {
                return new ArrayList<>();
            }
            return playerCardResponseDTO.getCards();

        } catch (Exception error) {
            String messageError = String.format("Generic error when try call endpoint for generate new cards for player, for url: %s", url);
            throw new BusinessException(messageError, error);
        }

    }
}
