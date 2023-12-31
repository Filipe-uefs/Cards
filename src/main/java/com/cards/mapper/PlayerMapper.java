package com.cards.mapper;

import com.cards.dto.player.PlayerRequestDTO;
import com.cards.dto.player.PlayerResponseDTO;
import com.cards.model.PlayerModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = CardMapper.class)
public interface PlayerMapper {

    PlayerModel toModel(PlayerRequestDTO playerRequestDTO);

    PlayerModel toModel(PlayerResponseDTO playerResponseDTO);

    PlayerResponseDTO toResponse(PlayerModel playerModel);

    List<PlayerResponseDTO> toResponseList(List<PlayerModel> playerModels);

}
