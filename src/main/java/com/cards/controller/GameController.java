package com.cards.controller;

import com.cards.dto.game.GameResponseDTO;
import com.cards.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createDeck() {
        GameResponseDTO responseDTO = gameService.save();
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{gameId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getGame(@PathVariable String gameId) {
        GameResponseDTO responseDTO = gameService.getGameResponseById(gameId);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/winner/{gameId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getWinner(@PathVariable(value = "gameId") String gameId) {
        GameResponseDTO responseDTO = gameService.generateWinner(gameId);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
