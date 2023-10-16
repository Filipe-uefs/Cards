package com.cards.service;

import com.cards.exception.BusinessException;
import com.cards.model.CardModel;
import com.cards.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {
    
    private final CardRepository cardRepository;

    public List<CardModel> saveAll(List<CardModel> cardModels) {

        try {
            return cardRepository.saveAll(cardModels);
        } catch (Exception error) {
            String messageError = String.format("Generic error when try save cards, cards: %s", cardModels);
            throw new BusinessException(messageError, error);
        }
    }
}
