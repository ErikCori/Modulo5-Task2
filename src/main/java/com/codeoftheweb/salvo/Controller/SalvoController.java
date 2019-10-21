package com.codeoftheweb.salvo.Controller;


import com.codeoftheweb.salvo.Model.Game;
import com.codeoftheweb.salvo.Model.GamePlayer;
import com.codeoftheweb.salvo.Model.Player;
import com.codeoftheweb.salvo.Model.Ship;
import com.codeoftheweb.salvo.Repository.GamePlayerRepository;
import com.codeoftheweb.salvo.Repository.GameRepository;
import com.codeoftheweb.salvo.Repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SalvoController {
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GamePlayerRepository gamePlayerRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/games")
    public Map<String, Object> getAll(Authentication authentication){
        Map<String, Object> dto = new LinkedHashMap<>();
        if(!isGuest(authentication)){
            dto.put("player", playerRepository.findByUsername(authentication.getName()).makePlayerDto());
        }else{
            dto.put("player", null);
        }
        dto.put("games", gameRepository.findAll().stream().map(game -> game.makeGameDto()).collect(Collectors.toList()));
        return dto;
    }
    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }

    @RequestMapping("/game_view/{gamePlayerId}")
    public ResponseEntity gameView(@PathVariable long gamePlayerId, Authentication authentication){
        GamePlayer gamePlayer = gamePlayerRepository.getOne(gamePlayerId);

        if(gamePlayer.getPlayer().getId() == playerRepository.findByUsername(authentication.getName()).getId()){
            return ResponseEntity.ok(gameViewDto(gamePlayer));
        }else{
            ResponseEntity responseEntity = new ResponseEntity(HttpStatus.FORBIDDEN);
            return responseEntity;
        }
    }
    private Map<String, Object> gameViewDto(GamePlayer gamePlayer){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", gamePlayer.getGame().getId());
        dto.put("created", gamePlayer.getGame().getCreationDate());
        dto.put("gamePlayers", gamePlayer.getGame().getGamePlayers().stream().map(g ->g.makeGamePlayerDto()));
        dto.put("ships", getShipList(gamePlayer.getShips()));
        dto.put("salvoes", gamePlayer.getGame().getGamePlayers().stream()
                                                                .flatMap(gp->gp.getSalvoes()
                                                                                .stream()
                                                                                .map(salvo -> salvo.makeSalvoDto())
                                                                        )
                                                                .collect(Collectors.toList())
        );
        return dto;
    }
    private List<Map<String, Object>> getShipList(Set<Ship> ships){
        return ships.stream().map(ship-> ship.makeShipDto()).collect(Collectors.toList());
    }

    @RequestMapping("/leaderboard")
    public List<Object> leaderboardView(){
        return playerRepository.findAll().stream().map(player -> player.makeTableLeaderboard()).collect(Collectors.toList());
    }
    //********************************************  Create a player  *************************************************
    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createUser(@RequestParam String username, @RequestParam String password, Authentication authentication){
        if(!isGuest(authentication)){
            return new ResponseEntity<>(makeMap("error", "user logged in"), HttpStatus.CONFLICT);
        }
        else if(username.isEmpty()){
            return new ResponseEntity<>(makeMap("Error", "no name given"), HttpStatus.FORBIDDEN);
        }
        else if(password.isEmpty()){
            return new ResponseEntity<>(makeMap("Error", "no password given"), HttpStatus.FORBIDDEN);
        }
        else if(playerRepository.findByUsername(username) != null){
            return new ResponseEntity<>(makeMap("Error","Username already exists"), HttpStatus.CONFLICT);
        }
        else {
            Player newPlayer = playerRepository.save(new Player(username, passwordEncoder.encode(password)));
            return new ResponseEntity<>(makeMap("username:", newPlayer.getUsername()), HttpStatus.CREATED);
        }
    }
    //***************************************  Create a game  ***************************************************
    @RequestMapping(path = "/games", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createGame(Authentication authentication){
        if(isGuest(authentication)){
            return new ResponseEntity<>(makeMap("Error:", "No player logged in"), HttpStatus.UNAUTHORIZED);
        }
        Player player = playerRepository.findByUsername(authentication.getName());
        Game newGame = gameRepository.save(new Game(0));
        GamePlayer newGamePlayer = gamePlayerRepository.save(new GamePlayer(newGame, player));
        return new ResponseEntity<>(makeMap("gpid", newGamePlayer.getId()),HttpStatus.CREATED);
    }
    //**************************************  Join Game  *******************************************************
    @RequestMapping(path = "/games/{id}/players", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> joinGame(Authentication authentication, @PathVariable long id){
        if(isGuest(authentication)){
            return new ResponseEntity<>(makeMap("Error", "No player logged in"), HttpStatus.UNAUTHORIZED);
        }
        Game game = gameRepository.getOne(id);
        if(game == null){
            return new ResponseEntity<>(makeMap("Error", "No such Game"), HttpStatus.FORBIDDEN);
        }
        if(game.getGamePlayers().size() >= 2){
            return new ResponseEntity<>(makeMap("Error", "Game is full"), HttpStatus.FORBIDDEN);
        }
        Player player = playerRepository.findByUsername(authentication.getName());
        GamePlayer newGamePlayer = gamePlayerRepository.save(new GamePlayer(game, player));
        return new ResponseEntity<>(makeMap("gpid", newGamePlayer.getId()), HttpStatus.CREATED);
    }



    private Map<String, Object> makeMap (String key, Object value){
        Map<String, Object>  map = new HashMap<>();
        map.put(key, value);
        return map;
    }
}
