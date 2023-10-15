package com.cards.controller;

import com.cards.dto.deck.DeckResponseDTO;
import com.cards.dto.player.PlayerRequestDTO;
import com.cards.dto.player.PlayerResponseDTO;
import com.cards.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("v1/player")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody @Valid PlayerRequestDTO requestDTO) {

        PlayerResponseDTO responseDTO = playerService.save(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PostMapping(value = "/{playerId}/createCards", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCards(@PathVariable UUID playerId) {

        PlayerResponseDTO responseDTO = playerService.createCardsForPlayer(playerId);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PostMapping(value = "/{gameId}/createCardsForAllPlayers", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAllCards(@PathVariable String gameId) {

        List<PlayerResponseDTO> responseDTO = playerService.createAllCardsForPlayers(gameId);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
}
