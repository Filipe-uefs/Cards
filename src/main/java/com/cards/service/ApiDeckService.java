package com.cards.service;

import com.cards.dto.card.CardResponseExternalDTO;
import com.cards.dto.deck.DeckResponseExternalDTO;
import com.cards.dto.playerCard.PlayerCardResponseExternalDTO;
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
        try {
            String url = "https://www.deckofcardsapi.com/api/deck/new/shuffle/";
            return restTemplate.getForObject(url, DeckResponseExternalDTO.class);
        } catch (Exception error) {
            System.out.println(error);
            return null;
        }
    }

    public List<CardResponseExternalDTO> generateCardsForPlayer(String deckId) {
        String url = "https://www.deckofcardsapi.com/api/deck/{deckId}/draw/?count=5";
        url = url.replace("{deckId}", deckId);

        PlayerCardResponseExternalDTO playerCardResponseDTO= restTemplate.getForObject(url, PlayerCardResponseExternalDTO.class);
        if (playerCardResponseDTO == null) {
            return new ArrayList<>();
        }
        return playerCardResponseDTO.getCards();
    }
}
