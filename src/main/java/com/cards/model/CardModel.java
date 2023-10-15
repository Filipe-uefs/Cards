package com.cards.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "CARD")
public class CardModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code", nullable = false, updatable = false)
    private String code;

    @Column(name = "value", nullable = false, updatable = false)
    private int value;

    @Column(name = "suit", nullable = false, updatable = false)
    private String suit;

    @ManyToOne
    @JoinColumn(name = "player_id",nullable = false)
    private PlayerModel player;

}
