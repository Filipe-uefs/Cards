package com.cards.service;

import com.cards.dto.deck.DeckResponseExternalDTO;
import com.cards.dto.game.GameResponseDTO;
import com.cards.exception.BusinessException;
import com.cards.exception.DataNotFoundException;
import com.cards.exception.InvalidInputException;
import com.cards.mapper.GameMapper;
import com.cards.model.CardModel;
import com.cards.model.GameModel;
import com.cards.model.PlayerModel;
import com.cards.repository.GameRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameService gameService;

    @Mock
    GameMapper gameMapper;

    @Mock
    ApiDeckService apiDeckService;

    @Test
    public void saveSuccess() {

        GameModel gameModel = getGameModel();

        when(gameMapper.toDeckExternalResponseToGameModel(getDeckResponse())).thenReturn(gameModel);
        when(gameRepository.save(gameModel)).thenReturn(gameModel);
        when(apiDeckService.getNewDeckShuffle()).thenReturn(getDeckResponse());
        when(gameMapper.toResponse(gameModel)).thenReturn(getGameResponse());

        GameResponseDTO gameResponseDTO = gameService.save();
        verify(gameRepository, times(1)).save(gameModel);
        Assertions.assertEquals(gameMapper.toResponse(gameModel), gameResponseDTO);
    }

    @Test
    public void saveErrorGenericException() {

        String messageError = "Error business";
        GameModel gameModel = gameMapper.toDeckExternalResponseToGameModel(getDeckResponse());
        when(gameRepository.save(gameModel)).thenThrow(new BusinessException(messageError, new Exception()));
        when(apiDeckService.getNewDeckShuffle()).thenReturn(getDeckResponse());

        BusinessException exception = assertThrows(BusinessException.class, () -> gameService.save());
        Assertions.assertEquals(messageError, exception.getCause().getMessage());
        verify(gameRepository, times(1)).save(gameModel);

    }

    @Test
    public void getGameResponseByIdSuccess() {

        String gameId = "1234";
        GameResponseDTO gameResponseDTO = getGameResponse();
        when(gameRepository.findById(gameId)).thenReturn(Optional.ofNullable(getGameModel()));
        when(gameMapper.toResponse(getGameModel())).thenReturn(getGameResponse());

        GameResponseDTO gameResponseDTOReturn = gameService.getGameResponseById(gameId);
        verify(gameRepository, times(1)).findById(gameId);
        Assertions.assertEquals(gameResponseDTOReturn, gameResponseDTO);

    }

    @Test
    public void getGameResponseByIdErrorDataNotFoundException() {

        String messageError = "Error business";
        String gameId = "1234";
        when(gameRepository.findById(gameId)).thenThrow(new DataNotFoundException(messageError));

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> gameService.getGameResponseById(gameId));
        Assertions.assertNotNull(exception.getMessage());
        verify(gameRepository, times(1)).findById(gameId);

    }

    @Test
    public void getGameResponseByIdErrorGenericException() {

        String messageError = "Error business";
        String gameId = "1234";
        when(gameRepository.findById(gameId)).thenThrow(new BusinessException(messageError, new Exception()));

        BusinessException exception = assertThrows(BusinessException.class, () -> gameService.getGameResponseById(gameId));
        Assertions.assertNotNull(exception.getMessage());
        verify(gameRepository, times(1)).findById(gameId);

    }

    @Test
    public void getGameByIdSuccess() {

        String gameId = "1234";
        GameModel gameModel = getGameModel();
        when(gameRepository.findById(gameId)).thenReturn(Optional.ofNullable(gameModel));

        GameModel gameModelReturn = gameService.getGameById(gameId);
        verify(gameRepository, times(1)).findById(gameId);
        Assertions.assertEquals(gameModelReturn, gameModel);

    }

    @Test
    public void getGameByIdErrorDataNotFoundException() {

        String messageError = "Error DataNotFound";
        String gameId = "1234";
        when(gameRepository.findById(gameId)).thenThrow(new DataNotFoundException(messageError));

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> gameService.getGameById(gameId));
        Assertions.assertNotNull(exception.getMessage());
        verify(gameRepository, times(1)).findById(gameId);

    }

    @Test
    public void generateWinnerErrorInvalidInputException() {

        String gameId = "1234";
        GameModel gameModel = getGameModel();
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(gameModel));

        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> gameService.generateWinner(gameId));
        verify(gameRepository, times(1)).findById(gameId);
        Assertions.assertNotNull(exception.getMessage());

    }

    @Test
    public void generateWinnerErrorDataNotFoundException() {

        String gameId = "1234";
        GameModel gameModel = getGameModel();
        gameModel.setPlayers(getPlayers());
        when(gameRepository.findById(gameId)).thenThrow(new DataNotFoundException());

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> gameService.generateWinner(gameId));
        verify(gameRepository, times(1)).findById(gameId);
        Assertions.assertNotNull(exception.getMessage());

    }

    public GameResponseDTO getGameResponse() {
        GameResponseDTO gameResponseDTO = new GameResponseDTO();
        gameResponseDTO.setPlayers(new ArrayList<>());
        gameResponseDTO.setGameId("1234");
        gameResponseDTO.setRemaining(52);
        gameResponseDTO.setShuffled(true);
        return gameResponseDTO;
    }

    public GameModel getGameModel() {
        GameModel gameModel = new GameModel();
        gameModel.setPlayers(new ArrayList<>());
        gameModel.setShuffled(true);
        gameModel.setRemaining(52);
        gameModel.setGameId("1234");
        return gameModel;
    }

    public DeckResponseExternalDTO getDeckResponse() {
       DeckResponseExternalDTO deckResponseExternalDTO = new DeckResponseExternalDTO();
       deckResponseExternalDTO.setDeckId("1234");
       deckResponseExternalDTO.setRemaining(52);
       deckResponseExternalDTO.setShuffled(true);
       return deckResponseExternalDTO;
    }

    public List<PlayerModel> getPlayers() {
        List<PlayerModel> playerModelList = new ArrayList<>();

        PlayerModel playerModel = new PlayerModel();
        playerModel.setName("Filipe");
        playerModel.setCards(getCardsModel(Arrays.asList(1, 2, 3, 4, 4)));

        PlayerModel playerModel1 = new PlayerModel();
        playerModel1.setName("Luffy");
        playerModel1.setCards(getCardsModel(Arrays.asList(1, 4, 4, 4, 4)));

        PlayerModel playerModel2 = new PlayerModel();
        playerModel2.setName("Zoro");
        playerModel2.setCards(getCardsModel(Arrays.asList(1, 1, 1, 1, 1)));

        PlayerModel playerModel3 = new PlayerModel();
        playerModel3.setName("Ace");
        playerModel3.setCards(getCardsModel(Arrays.asList(1, 1, 1, 2, 1)));

        playerModelList.add(playerModel);
        playerModelList.add(playerModel1);
        playerModelList.add(playerModel2);
        playerModelList.add(playerModel3);
        return playerModelList;
    }

    public List<CardModel> getCardsModel(List<Integer> values) {
        List<CardModel> cardModels = new ArrayList<>();
        values.forEach(value -> {
            CardModel cardModel = new CardModel();
            cardModel.setValue(value);
            cardModels.add(cardModel);
        });
        return cardModels;
    }

}
