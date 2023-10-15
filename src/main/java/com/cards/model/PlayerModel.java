package com.cards.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "PLAYER")
public class PlayerModel {

    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID playerId;

    private String name;

    @ManyToOne
    @JoinColumn(name="game_id", nullable=false)
    private GameModel game;

    @OneToMany(mappedBy = "player")
    private List<CardModel> cards;

}
