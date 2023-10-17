package com.cards.mapper;

import com.cards.dto.deck.DeckResponseExternalDTO;
import com.cards.dto.game.GameResponseDTO;
import com.cards.model.GameModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GameMapper {

    @Mapping(source = "deckId", target = "gameId")
    GameModel toDeckExternalResponseToGameModel(DeckResponseExternalDTO deckResponseExternalDTO);

    GameResponseDTO toResponse(GameModel gameModel);
}
