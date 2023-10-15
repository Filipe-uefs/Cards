package com.cards.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;


@Entity
@Data
@Table(name = "GAME")
public class GameModel {

    @Id
    @Column(name = "game_id")
    private String gameId;

    private boolean shuffled;

    private int remaining;

    @OneToMany(mappedBy="game")
    private List<PlayerModel> players;

}
