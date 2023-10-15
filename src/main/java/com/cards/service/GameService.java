package com.cards.service;

import com.cards.dto.deck.DeckResponseExternalDTO;
import com.cards.dto.game.GameResponseDTO;
import com.cards.dto.player.PlayerResponseDTO;
import com.cards.enums.LengthLimitPlayersEnum;
import com.cards.exception.BusinessException;
import com.cards.exception.DataNotFoundException;
import com.cards.exception.InvalidInputException;
import com.cards.mapper.GameMapper;
import com.cards.mapper.PlayerMapper;
import com.cards.model.GameModel;
import com.cards.model.PlayerModel;
import com.cards.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    private final ApiDeckService apiDeckService;

    private final GameMapper gameMapper;

    private final PlayerMapper playerMapper;

    public GameResponseDTO save() {
        try {
            DeckResponseExternalDTO deckResponseExternalDTO = apiDeckService.getNewDeckShuffle();
            GameModel gameModel = gameRepository.save(gameMapper.deckExternalResponseToGameModel(deckResponseExternalDTO));
            return gameMapper.toResponse(gameModel);
        } catch (Exception error) {
            String messageError = "Generic error when try save new game";
            throw new BusinessException(messageError, error);
        }
    }

    public GameResponseDTO getGameResponseById(String gameId) {
        try {
            GameModel gameModel = getGameById(gameId);
            return gameMapper.toResponse(gameModel);
        } catch (DataNotFoundException error) {
            throw error;
        } catch (Exception error) {
            String messageError = String.format("Generic error when try select gameResponse, for gameId: %s", gameId);
            throw new BusinessException(messageError, error);
        }
    }

    public GameModel getGameById(String gameId) {
        try {
            return gameRepository.findById(gameId).orElseThrow(DataNotFoundException::new);
        } catch (DataNotFoundException error) {
            String messageError = String.format("Could not find a game for the requested gameId, gameId: %s", gameId) ;
            throw new DataNotFoundException(messageError);
        } catch (Exception error) {
            String messageError = String.format("Generic error when try select game, for gameId: %s", gameId);
            throw new BusinessException(messageError, error);
        }
    }

    public GameResponseDTO generateWinner(String gameId) {
        try {
            GameModel gameModel = getGameById(gameId);
            validateFields(gameModel.getPlayers());

            List<PlayerResponseDTO> playerWinners = playerMapper.toResponseList(gameModel.getPlayers());
            playerWinners = playerWinners.stream().collect(
                    Collectors.groupingBy(PlayerResponseDTO::calculatedTotalValue))
                    .entrySet()
                    .stream()
                    .max(Comparator.comparingInt(Map.Entry::getKey))
                    .map(Map.Entry::getValue)
                    .orElse(null);

            GameResponseDTO gameResponse = gameMapper.toResponse(gameModel);
            gameResponse.setWinners(playerWinners);
            return gameResponse;

        } catch (DataNotFoundException | InvalidInputException error) {
            throw error;

        } catch (Exception error) {
            String messageError = String.format("Generic error when try generate game wiiner, for gameId: %s", gameId);
            throw new BusinessException(messageError, error);
        }
    }

    private void validateFields(List<PlayerModel> players) {

        if (players.size() != LengthLimitPlayersEnum.MAX_PLAYER_PER_GAME.getValue()) {
            String messageError = "The game is required four players registered to generate winner";
            throw new InvalidInputException(messageError);
        }

        players.forEach(playerModel -> {
            if (playerModel.getCards().size() != LengthLimitPlayersEnum.MAX_CARDS_PER_PLAYER.getValue()) {
                String messageError = String.format("Not possible generate winner of game, cause player %s, " +
                        "not enough cards, quantity needed: %s",
                        playerModel.getPlayerId(), LengthLimitPlayersEnum.MAX_CARDS_PER_PLAYER.getValue());
                throw new InvalidInputException(messageError);
            }
        });
    }
}
