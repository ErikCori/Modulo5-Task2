package com.codeoftheweb.salvo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;
import java.util.*;

@Entity
public class GamePlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private Date joinDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private Game game;

    @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER)
    private Set<Ship> ships;

    @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER)
    private Set<Salvo> salvoes;

    //Constructor
    public GamePlayer(){}
    public GamePlayer(Game game, Player player){
        this.joinDate = new Date();
        this.player = player;
        this.game = game;
    }

    //Getters
    public Date getJoinDate() {
        return joinDate;
    }
    public long getId(){
        return id;
    }
    @JsonIgnore
    public Player getPlayer(){
        return player;
    }
    @JsonIgnore
    public Game getGame(){
        return game;
    }
    public Set<Ship> getShips() {
        return ships;
    }
    public Set<Salvo> getSalvoes() {
        return salvoes;
    }

    //Controller

    public Map<String, Object> makeGamePlayerDto(){
        Map<String, Object> dto= new LinkedHashMap<>();
        dto.put("id", this.getId());
        dto.put("player", this.player.makePlayerDto());
        return dto;
    }
}
