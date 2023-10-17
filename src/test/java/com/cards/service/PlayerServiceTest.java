package com.cards.service;

import com.cards.dto.player.PlayerRequestDTO;
import com.cards.dto.player.PlayerResponseDTO;
import com.cards.exception.BusinessException;
import com.cards.exception.DataNotFoundException;
import com.cards.exception.InvalidInputException;
import com.cards.mapper.PlayerMapper;
import com.cards.model.CardModel;
import com.cards.model.GameModel;
import com.cards.model.PlayerModel;
import com.cards.repository.PlayerRepository;
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
import java.util.UUID;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {

    @Mock
    GameService gameService;

    @Mock
    PlayerRepository playerRepository;

    @Mock
    PlayerMapper playerMapper;

    @InjectMocks
    PlayerService playerService;

    @Test
    public void saveSuccess() {


        String gameId = "1234";
        String name = "Filipe";
        GameModel gameModel = getGameModel();
        PlayerModel playerModel = getPlayerModel(name);

        when(gameService.getGameById(gameId)).thenReturn(gameModel);
        when(playerRepository.findByNameIgnoreCaseAndGame(name, gameModel)).thenReturn(new ArrayList<>());
        when(playerMapper.toModel(getPlayerRequestDTO(gameId, name))).thenReturn(playerModel);
        when(playerRepository.save(playerModel)).thenReturn(playerModel);
        when(playerMapper.toResponse(playerModel)).thenReturn(getPlayerResponse(name));

        PlayerResponseDTO responseReturn = playerService.save(getPlayerRequestDTO(gameId, name));
        verify(playerRepository, times(1)).save(playerModel);
        verify(playerRepository, times(1)).findByNameIgnoreCaseAndGame(name, gameModel);
        verify(gameService, times(1)).getGameById(gameId);
        Assertions.assertEquals(responseReturn, playerMapper.toResponse(playerModel));

    }

    @Test
    public void saveErrorInvalidInputException() {


        String gameId = "1234";
        String name = "Filipe";
        GameModel gameModel = getGameModel();

        when(gameService.getGameById(gameId)).thenReturn(gameModel);
        when(playerRepository.findByNameIgnoreCaseAndGame(name, gameModel)).thenReturn(getPlayers());

        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> playerService.save(getPlayerRequestDTO(gameId, name)));
        verify(playerRepository, times(1)).findByNameIgnoreCaseAndGame(name, gameModel);
        Assertions.assertNotNull(exception.getMessage());
    }

    @Test
    public void saveErrorInvalidInputException2() {


        String gameId = "1234";
        String name = "Filipe";
        GameModel gameModel = getGameModel();
        gameModel.setPlayers(getPlayers());

        when(gameService.getGameById(gameId)).thenReturn(gameModel);
        when(playerRepository.findByNameIgnoreCaseAndGame(name, gameModel)).thenReturn(new ArrayList<>());

        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> playerService.save(getPlayerRequestDTO(gameId, name)));
        verify(playerRepository, times(1)).findByNameIgnoreCaseAndGame(name, gameModel);
        verify(gameService, times(1)).getGameById(gameId);
        Assertions.assertNotNull(exception.getMessage());

    }

    @Test
    public void saveErrorDataNotFoundException() {

        String gameId = "1234";
        String name = "Filipe";
        String messageError = "Error DataNotFound";
        GameModel gameModel = getGameModel();
        PlayerModel playerModel = getPlayerModel(name);

        when(gameService.getGameById(gameId)).thenReturn(gameModel);
        when(playerRepository.findByNameIgnoreCaseAndGame(name, gameModel)).thenReturn(new ArrayList<>());
        when(playerMapper.toModel(getPlayerRequestDTO(gameId, name))).thenReturn(playerModel);
        when(playerRepository.save(playerModel)).thenThrow(new DataNotFoundException(messageError));

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> playerService.save(getPlayerRequestDTO(gameId, name)));
        verify(playerRepository, times(1)).findByNameIgnoreCaseAndGame(name, gameModel);
        verify(gameService, times(1)).getGameById(gameId);
        Assertions.assertNotNull(exception.getMessage());

    }

    @Test
    public void saveErrorGenericException() {

        String gameId = "1234";
        String name = "Filipe";

        when(gameService.getGameById(gameId)).thenThrow(new BusinessException());

        BusinessException exception = assertThrows(BusinessException.class, () -> playerService.save(getPlayerRequestDTO(gameId, name)));
        verify(gameService, times(1)).getGameById(gameId);
        Assertions.assertNotNull(exception.getMessage());

    }

    @Test
    public void getPlayerByNameSuccess() {
        String name = "Filipe";
        GameModel gameModel = getGameModel();


        when(playerRepository.findByNameIgnoreCaseAndGame(name, gameModel)).thenReturn(new ArrayList<>());
        List<PlayerModel> playerModelList = playerService.getPlayersByName(name, gameModel);

        verify(playerRepository, times(1)).findByNameIgnoreCaseAndGame(name, gameModel);
        Assertions.assertEquals(playerModelList, new ArrayList<>());
    }

    @Test
    public void getPlayerByNameSuccess2() {
        String name = "Filipe";
        GameModel gameModel = getGameModel();


        when(playerRepository.findByNameIgnoreCaseAndGame(name, gameModel)).thenReturn(getPlayers());
        List<PlayerModel> playerModelList = playerService.getPlayersByName(name, gameModel);

        verify(playerRepository, times(1)).findByNameIgnoreCaseAndGame(name, gameModel);
        Assertions.assertEquals(playerModelList, getPlayers());
    }

    @Test
    public void getPlayerByNameErrorGenericException() {

        String name = "Filipe";
        GameModel gameModel = getGameModel();


        when(playerRepository.findByNameIgnoreCaseAndGame(name, gameModel)).thenThrow(new BusinessException());
        BusinessException exception = assertThrows(BusinessException.class, () -> playerService.getPlayersByName(name, gameModel));

        verify(playerRepository, times(1)).findByNameIgnoreCaseAndGame(name, gameModel);
        Assertions.assertNotNull(exception.getMessage());

    }

    @Test
    public void getPlayerByIdSuccess() {

        UUID id = UUID.randomUUID();
        String name = "Filipe";
        PlayerModel playerModel = getPlayerModel(name);

        when(playerRepository.findById(id)).thenReturn(Optional.ofNullable(playerModel));
        PlayerModel playerModelReturn = playerService.getPlayerById(id);

        verify(playerRepository, times(1)).findById(id);
        Assertions.assertEquals(playerModelReturn, playerModel);

    }

    @Test
    public void getPlayerByIdErrorDataNotFoundException() {

        UUID id = UUID.randomUUID();

        when(playerRepository.findById(id)).thenThrow(new DataNotFoundException());
        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> playerService.getPlayerById(id));

        verify(playerRepository, times(1)).findById(id);
        Assertions.assertNotNull(exception.getMessage());
    }

    @Test
    public void getPlayerByIdErrorGenericException() {

        UUID id = UUID.randomUUID();

        when(playerRepository.findById(id)).thenThrow(new BusinessException());
        BusinessException exception = assertThrows(BusinessException.class, () -> playerService.getPlayerById(id));

        verify(playerRepository, times(1)).findById(id);
        Assertions.assertNotNull(exception.getMessage());
    }

    public PlayerResponseDTO getPlayerResponse(String name) {
        PlayerResponseDTO playerResponseDTO = new PlayerResponseDTO();
        playerResponseDTO.setName(name);
        return playerResponseDTO;
    }
    public PlayerModel getPlayerModel(String name) {
        PlayerModel playerModel = new PlayerModel();
        playerModel.setName(name);
        return playerModel;
    }

    public PlayerRequestDTO getPlayerRequestDTO(String gameId, String name) {
        PlayerRequestDTO playerRequestDTO = new PlayerRequestDTO();
        playerRequestDTO.setGameId(gameId);
        playerRequestDTO.setName(name);
        return playerRequestDTO;
    }

    public GameModel getGameModel() {
        GameModel gameModel = new GameModel();
        gameModel.setPlayers(new ArrayList<>());
        gameModel.setShuffled(true);
        gameModel.setRemaining(52);
        gameModel.setGameId("1234");
        return gameModel;
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
