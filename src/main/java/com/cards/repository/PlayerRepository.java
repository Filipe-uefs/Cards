package com.cards.repository;

import com.cards.model.GameModel;
import com.cards.model.PlayerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerModel, UUID> {
    List<PlayerModel> findByNameIgnoreCaseAndGame(String name, GameModel gameModel);

}
