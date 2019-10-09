package com.codeoftheweb.salvo;

import com.codeoftheweb.salvo.Model.*;
import com.codeoftheweb.salvo.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;



@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {

		SpringApplication.run(SalvoApplication.class, args);
	}
	@Bean
	public PasswordEncoder passwordEncoder(){
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository,
									  GameRepository gameRepository,
									  GamePlayerRepository gamePlayerRepository,
									  ShipRepository shipRepository,
									  SalvoRepository salvoRepository,
									  ScoreRepository scoreRepository){
		return (args) -> {
			//save  a couple of players
			Player player1 = new Player("j.bauer@ctu.gov", passwordEncoder().encode("24"));
			Player player2 = new Player("c.obrian@ctu.gov", passwordEncoder().encode("42"));
			Player player3 = new Player("kim_bauer@gmail.com", passwordEncoder().encode("kb"));
			Player player4 = new Player("t.almeida@ctu.gov", passwordEncoder().encode("mole"));
			Player player5 = new Player("null", passwordEncoder().encode("null"));

			playerRepository.saveAll(Arrays.asList(player1, player2, player3, player4, player5));
			//save a couple of games

			Game game1 = new Game(1);
			Game game2 = new Game(2);
			Game game3 = new Game(3);
			Game game4 = new Game(4);
			Game game5 = new Game(5);
			Game game6 = new Game(6);
			Game game7 = new Game(7);
			Game game8 = new Game(8);

			gameRepository.saveAll(Arrays.asList(game1, game2, game3, game4, game5, game6, game7, game8));
			//save a couple of PlayerGames

			GamePlayer gamePlayer1 = new GamePlayer(game1, player1);
			GamePlayer gamePlayer2 = new GamePlayer(game1, player2);
			GamePlayer gamePlayer3 = new GamePlayer(game2, player1);
			GamePlayer gamePlayer4 = new GamePlayer(game2, player2);
			GamePlayer gamePlayer5 = new GamePlayer(game3, player4);
			GamePlayer gamePlayer6 = new GamePlayer(game3, player2);
			GamePlayer gamePlayer7 = new GamePlayer(game4, player2);
			GamePlayer gamePlayer8 = new GamePlayer(game4, player1);
			GamePlayer gamePlayer9 = new GamePlayer(game5, player4);
			GamePlayer gamePlayer10 = new GamePlayer(game5, player1);
			GamePlayer gamePlayer11 = new GamePlayer(game6, player3);
			GamePlayer gamePlayer12 = new GamePlayer(game6, player5);
			GamePlayer gamePlayer13 = new GamePlayer(game7, player4);
			GamePlayer gamePlayer14 = new GamePlayer(game7, player5);
			GamePlayer gamePlayer15 = new GamePlayer(game8, player3);
			GamePlayer gamePlayer16 = new GamePlayer(game8, player4);

			gamePlayerRepository.saveAll(Arrays.asList(gamePlayer1, gamePlayer2, gamePlayer3, gamePlayer4, gamePlayer5, gamePlayer6, gamePlayer7, gamePlayer8,
										gamePlayer9, gamePlayer10, gamePlayer11, gamePlayer12, gamePlayer13, gamePlayer14, gamePlayer15, gamePlayer16));

			//Save a couple of ships
			String carrier = "Carrier";
			String battleship = "Battleship";
			String submarine = "Submarine";
			String destroyer = "Destroyer";
			String patrolBoat = "Patrol Boat";

			Ship ship1 = new Ship(gamePlayer1, destroyer, Arrays.asList("H2", "H3", "H4"));
			Ship ship2 = new Ship(gamePlayer1, submarine, Arrays.asList("E1", "F1", "G1"));
			Ship ship3 = new Ship(gamePlayer1, patrolBoat, Arrays.asList("B4", "B5"));
			Ship ship4 = new Ship(gamePlayer2, destroyer, Arrays.asList("B5", "C5", "D5"));
			Ship ship5 = new Ship(gamePlayer2, patrolBoat, Arrays.asList("F1", "F2"));
			Ship ship6 = new Ship(gamePlayer3, destroyer, Arrays.asList("B5", "C5", "D5"));
			Ship ship7 = new Ship(gamePlayer3, patrolBoat, Arrays.asList("C6", "C7"));
			Ship ship8 = new Ship(gamePlayer4, submarine, Arrays.asList("A2", "A3", "A4"));
			Ship ship9 = new Ship(gamePlayer4, patrolBoat, Arrays.asList("G6", "H6"));
			Ship ship10 = new Ship(gamePlayer6, destroyer, Arrays.asList("B%", "C%", "D%"));
			Ship ship11 = new Ship(gamePlayer6, patrolBoat, Arrays.asList("C6", "C7"));
			Ship ship12 = new Ship(gamePlayer5, submarine, Arrays.asList("A2", "A3", "A4"));
			Ship ship13 = new Ship(gamePlayer5, patrolBoat, Arrays.asList("G6", "H6"));
			Ship ship14 = new Ship(gamePlayer7, destroyer, Arrays.asList("B5", "C%", "D5"));
			Ship ship15 = new Ship(gamePlayer7, patrolBoat, Arrays.asList("C6", "C7"));
			Ship ship16 = new Ship(gamePlayer8, submarine, Arrays.asList("A2","A3", "A4"));
			Ship ship17 = new Ship(gamePlayer8, patrolBoat, Arrays.asList("G6", "H6"));
			Ship ship18 = new Ship(gamePlayer9, destroyer, Arrays.asList("B5", "C5", "D5"));
			Ship ship19 = new Ship(gamePlayer9, patrolBoat, Arrays.asList("C6", "C7"));
			Ship ship20 = new Ship(gamePlayer10, submarine, Arrays.asList("A2","A3", "A4"));
			Ship ship21 = new Ship(gamePlayer10, patrolBoat, Arrays.asList("G6", "H6"));
			Ship ship22 = new Ship(gamePlayer11, destroyer, Arrays.asList("B5", "C5", "D5"));
			Ship ship23 = new Ship(gamePlayer11, patrolBoat, Arrays.asList("C6", "C7"));
			Ship ship24 = new Ship(gamePlayer15, destroyer, Arrays.asList("B5", "C5", "D5"));
			Ship ship25 = new Ship(gamePlayer15, patrolBoat, Arrays.asList("C6", "C7"));
			Ship ship26 = new Ship(gamePlayer16, submarine, Arrays.asList("A2", "A3", "A4"));
			Ship ship27 = new Ship(gamePlayer16, patrolBoat, Arrays.asList("G6", "H6"));

			shipRepository.saveAll(Arrays.asList(ship1, ship2, ship3, ship4, ship5, ship6, ship7, ship8, ship9,
					                             ship10, ship11, ship12, ship13, ship14, ship15, ship16, ship17, ship18,
												 ship19, ship20, ship21, ship22, ship23, ship24, ship25, ship26, ship27));

			//save a couple of salvoes
			Salvo salvo1 = new Salvo(gamePlayer1, 1, Arrays.asList("B5", "C5", "F1"));
			Salvo salvo2 = new Salvo(gamePlayer2, 1, Arrays.asList("B4", "B5", "B6"));
			Salvo salvo3 = new Salvo(gamePlayer1, 2,Arrays.asList("F2", "D5"));
			Salvo salvo4 = new Salvo(gamePlayer2, 2, Arrays.asList("E1", "H3", "A2"));
			Salvo salvo5 = new Salvo(gamePlayer3, 1, Arrays.asList("A2", "A4", "G6"));
			Salvo salvo6 = new Salvo(gamePlayer4, 1, Arrays.asList("B5", "D5", "C7"));
			Salvo salvo7 = new Salvo(gamePlayer3, 2, Arrays.asList("A3", "H6"));
			Salvo salvo8 = new Salvo(gamePlayer4, 2, Arrays.asList("C5", "C6"));
			Salvo salvo9 = new Salvo(gamePlayer6, 1,Arrays.asList("G6", "H6", "A4"));
			Salvo salvo10 = new Salvo(gamePlayer5, 1, Arrays.asList("H1", "H2", "H3"));
			Salvo salvo11 = new Salvo(gamePlayer6, 2, Arrays.asList("A2", "A3", "D8"));
			Salvo salvo12 = new Salvo(gamePlayer5, 2, Arrays.asList("E1", "F2", "G3"));
			Salvo salvo13 = new Salvo(gamePlayer7, 1, Arrays.asList("A3", "A4", "F7"));
			Salvo salvo14 = new Salvo(gamePlayer8, 1, Arrays.asList("B5", "C6", "H1"));
			Salvo salvo15 = new Salvo(gamePlayer7, 2, Arrays.asList("A2", "G6", "H6"));
			Salvo salvo16 = new Salvo(gamePlayer8, 2, Arrays.asList("C5", "C7", "D5"));
			Salvo salvo17 = new Salvo(gamePlayer9, 1, Arrays.asList("A1", "A2", "A3"));
			Salvo salvo18 = new Salvo(gamePlayer10, 1, Arrays.asList("B5", "B6", "C7"));
			Salvo salvo19 = new Salvo(gamePlayer9, 2, Arrays.asList("G6", "G7", "G8"));
			Salvo salvo20 = new Salvo(gamePlayer10, 2, Arrays.asList("C6", "D6", "E6"));
			Salvo salvo21 = new Salvo(gamePlayer10, 3, Arrays.asList("H1", "H8"));

			salvoRepository.saveAll(Arrays.asList(salvo1, salvo2, salvo3, salvo4, salvo5, salvo6, salvo7, salvo8, salvo9,
												salvo10, salvo11, salvo12, salvo13, salvo14, salvo15, salvo16, salvo17,
												salvo18, salvo19, salvo20, salvo21));

			//Save a couple of scores
			float win = 1;
			float tie = (float) 0.5;
			float lose = 0;

			Score score1 = new Score(game1, player1, win);
			Score score2 = new Score(game1, player2, lose);
			Score score3 = new Score(game3, player2, win);
			Score score4 = new Score(game3, player4, lose);
			Score score5 = new Score(game2, player1, tie);
			Score score6 = new Score(game2, player2, tie);
			Score score7 = new Score(game4, player1, tie);
			Score score8 = new Score(game4, player2, tie);

			scoreRepository.saveAll(Arrays.asList(score1, score2, score3, score4, score5, score6, score7, score8));
		};
	}
}

@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

	@Autowired
	PlayerRepository playerRepository;

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(inputName -> {
			Player player = playerRepository.findByUsername(inputName);
			if (player != null) {
				return new User(player.getUsername(), player.getPassword(),
						AuthorityUtils.createAuthorityList("USER"));
			} else {
				throw new UsernameNotFoundException("Unknown user: " + inputName);
			}
		});
	}

}
@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/web/games.html", "/api/login", "/api/games").permitAll()
				//.antMatchers("/rest/**").hasAuthority("ADMIN")
				.antMatchers("/web/game.html**").hasAuthority("USER")
				.antMatchers("/api/game_view/*").hasAuthority("USER")
				.anyRequest().permitAll();
		http.formLogin()
				.usernameParameter("username")
				.passwordParameter("password")
				.loginPage("/api/login");
		http.logout().logoutUrl("/api/logout");

		// turn off checking for CSRF tokens
		http.csrf().disable();
		// if user is not authenticated, just send an authentication failure response
		http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
		// if login is successful, just clear the flags asking for authentication
		http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));
		// if login fails, just send an authentication failure response
		http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
		// if logout is successful, just send a success response
		http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
	}
	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
	}
}
