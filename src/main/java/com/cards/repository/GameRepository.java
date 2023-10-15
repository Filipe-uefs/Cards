package com.cards.repository;

import com.cards.model.GameModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<GameModel, String> {

}
