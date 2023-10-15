package com.cards.mapper;

import com.cards.dto.card.CardResponseDTO;
import com.cards.dto.card.CardResponseExternalDTO;
import com.cards.model.CardModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CardMapper {

    CardModel toModelFromResponseExternal(CardResponseExternalDTO cardResponseExternalDTO);
    List<CardModel> toModelList(List<CardResponseExternalDTO> cardResponseExternalDTO);

    CardResponseDTO toResponse(CardModel cardModel);

    List<CardResponseDTO> toResponseList(List<CardModel> cardModelList);
}
