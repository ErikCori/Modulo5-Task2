package com.codeoftheweb.salvo.Model;


import org.hibernate.annotations.GenericGenerator;
import sun.applet.resources.MsgAppletViewer;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
    private Set<GamePlayer> gamePlayers;

    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
    private Set<Score> scores;

    private String username;

    private String password;

    //Constructor
    public Player(){}

    public Player(String username, String password){

        this.username = username;
        this.password = password;
    }

    //Getters
    public long getId(){
        return id;
    }
    public String getUsername() {
        return username;
    }
    public Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }
    public Set<Score> getScores() { return scores; }
    public Set<Float> getScorePoints(){
        return this.getScores().stream().map(score -> score.getScore()).collect(Collectors.toSet());
    }
    public String getPassword() { return password; }
    //Controller

    public Map<String, Object> makePlayerDto(){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", this.getId());
        dto.put("email", this.getUsername());
        return dto;
    }
    public Map<String, Object> makeTableLeaderboard(){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", this.getId());
        dto.put("email", this.getUsername());
        dto.put("score", this.makeScore());
        return dto;
    }
    public Map<String, Object> makeScore(){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("total", this.totalScore());
        dto.put("won", this.getWon());
        dto.put("lost", this.getLoses());
        dto.put("tied", this.getTied());
        return dto;
    }
    public Map<String, Object> makeScoredto(){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("player", this.makePlayerDto());
        dto.put("scores", this.getScorePoints());
        return dto;
    }

    public float getWon(){
        return getScores().stream().filter(score -> score.getScore() == 1).count();
    }
    public float getTied(){
        return getScores().stream().filter(score -> score.getScore() == (float) 0.5).count();
    }
    public float getLoses(){
        return getScores().stream().filter(score -> score.getScore()== 0).count();
    }
    public float totalScore(){
        return (float) (getWon() *1 + getTied() * 0.5);
    }
}

