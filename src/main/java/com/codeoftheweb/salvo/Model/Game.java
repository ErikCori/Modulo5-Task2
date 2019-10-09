package com.codeoftheweb.salvo.Model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;


@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
    private Set<GamePlayer> gamePlayers;

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
    private Set<Score> scores;

    private Date creationDate;

    //Constructor
    public Game(){
        Date date = new Date();
    }
    public Game(int plusHours){
        int seconds = plusHours * 3600;
        Date date = new Date();
        this.creationDate = Date.from(date.toInstant().plusSeconds(seconds));
    }

    //Getters
    public long getId(){
        return id;
    }
    public Date getCreationDate(){
        return creationDate;
    }
    public Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }
    public Set<Score> getScores() { return scores; }
    //Controller

    public Map<String, Object> makeGameDto(){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", this.getId());
        dto.put("created", this.getCreationDate());
        dto.put("gamePlayers", this.getGamePlayers().stream().map(gp -> gp.makeGamePlayerDto()));
        dto.put("scores", this.getGamePlayers().stream().map(gp -> gp.getPlayer().makeScoredto()));
        return dto;
    }

}
