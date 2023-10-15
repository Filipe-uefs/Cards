package com.cards.service;

import com.cards.dto.card.CardResponseExternalDTO;
import com.cards.dto.player.PlayerRequestDTO;
import com.cards.dto.player.PlayerResponseDTO;
import com.cards.enums.CardEnum;
import com.cards.enums.LengthLimitPlayersEnum;
import com.cards.exception.BusinessException;
import com.cards.exception.DataNotFoundException;
import com.cards.exception.InvalidInputException;
import com.cards.mapper.CardMapper;
import com.cards.mapper.PlayerMapper;
import com.cards.model.CardModel;
import com.cards.model.GameModel;
import com.cards.model.PlayerModel;
import com.cards.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    private final PlayerMapper playerMapper;

    private final GameService gameService;

    private final CardService cardService;

    private final ApiDeckService apiDeckService;

    private final CardMapper cardMapper;

    public PlayerResponseDTO save(PlayerRequestDTO requestDTO) {

        try {
            GameModel gameModel = gameService.getGameById(requestDTO.getGameId());

            if (gameModel.getPlayers().size() == LengthLimitPlayersEnum.MAX_PLAYER_PER_GAME.getValue()) {
                String messageError = String.format("Error when try insert new player, limit of players exceeded. Limit: %s",
                        LengthLimitPlayersEnum.MAX_PLAYER_PER_GAME.getValue());
                throw new InvalidInputException(messageError);
            }

            PlayerModel playerModelToInsert = playerMapper.toModel(requestDTO);
            playerModelToInsert.setGame(gameModel);
            return playerMapper.toResponse(playerRepository.save(playerModelToInsert));

        } catch (InvalidInputException | DataNotFoundException error) {
            throw error;

        } catch (Exception error) {
            String messageError = String.format("Generic error when try save new player, for playerResquestDTO: %s", requestDTO);
            throw new BusinessException(messageError, error);
        }
    }

    public PlayerModel getPlayerById(UUID playerId) {

        try {
            return playerRepository.findById(playerId).orElseThrow(DataNotFoundException::new);

        } catch (DataNotFoundException error) {
            String messageError = String.format("Could not find a player for this playerId, playerId: %s", playerId);
            throw new DataNotFoundException(messageError);

        } catch (Exception error) {
            String messageError = String.format("Generic error when try find player, for playerId: %s", playerId);
            throw new BusinessException(messageError, error);
        }
    }

    public PlayerResponseDTO createCardsForPlayer(UUID playerId) {
        //@TODO FAZER SE CARTAS JÀ FORAM CRIADAS PARA PLAYER ID
        try{
            PlayerModel playerModel = getPlayerById(playerId);

            List<CardResponseExternalDTO> cardResponseExternalDTOList = apiDeckService.generateCardsForPlayer(playerModel.getGame().getGameId());
            cardResponseExternalDTOList.forEach(cardResponseExternalDTO -> {
                CardEnum cardEnum = CardEnum.getCarEnumByName(cardResponseExternalDTO.getValue());
                if (cardEnum != null) {
                    cardResponseExternalDTO.setValue(String.valueOf(cardEnum.getValue()));
                }
            });
            playerModel.setCards(cardMapper.toModelList(cardResponseExternalDTOList));
            List<CardModel> cardModels = playerModel.getCards().stream()
                    .peek(cardModel -> cardModel.setPlayer(playerModel)).collect(Collectors.toList());
            cardService.saveAll(cardModels);
            return playerMapper.toResponse(playerModel);

        } catch (DataNotFoundException error) {
            throw error;

        } catch (Exception error) {
            String messageError = String.format("Generic error when try create cards to player, for playerId: %s", playerId);
            throw new BusinessException(messageError, error);
        }
    }

    public List<PlayerResponseDTO> createAllCardsForPlayers(String gameId) {

        try{

            GameModel gameModel = gameService.getGameById(gameId);

            if (gameModel.getPlayers().size() < LengthLimitPlayersEnum.MAX_PLAYER_PER_GAME.getValue()) {
                String messageError = String.format("Error for get players, players numbers insufficient, required %s players",
                        LengthLimitPlayersEnum.MAX_PLAYER_PER_GAME.getValue());
                throw new InvalidInputException(messageError);
            }
            gameModel.getPlayers().forEach(playerModel -> {
                createCardsForPlayer(playerModel.getPlayerId());
            });

            return gameService.getGameResponseById(gameId).getPlayers();

        } catch (DataNotFoundException |InvalidInputException error) {
            throw error;

        } catch (Exception error) {
            String messageError = String.format("Generic error when try create all cards to all player, for gameId: %s", gameId);
            throw new BusinessException(messageError, error);
        }
    }
}
