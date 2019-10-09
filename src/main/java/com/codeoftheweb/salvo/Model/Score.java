package com.codeoftheweb.salvo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private float score;

    private Date finishDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private Game game;

    //Constructor
    public Score(){}

    public Score(Game game, Player player, float score) {
        this.score = score;
        this.finishDate = new Date();
        this.player = player;
        this.game = game;
    }
    //Getters

    public long getId() { return id; }
    @JsonIgnore
    public Player getPlayer() { return player; }
    @JsonIgnore
    public Game getGame() { return game; }
    public float getScore() { return score; }
    public Date getFinishDate() { return finishDate; }


}
