package com.caa.spring.mongo.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.caa.spring.mongo.api.model.Match;
import com.caa.spring.mongo.api.model.MatchDaySummary;
import com.caa.spring.mongo.api.model.User;
import com.caa.spring.mongo.api.model.Player;
import com.caa.spring.mongo.api.model.Team;
import com.caa.spring.mongo.api.service.MatchService;
import com.caa.spring.mongo.api.service.PlayerService;
import com.caa.spring.mongo.api.service.UserService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class TennisManagementController {
	@Autowired
	private PlayerService playerService;
	@Autowired
	private UserService userService;
	@Autowired
	private MatchService matchService;
	@PostMapping("/addPlayer")
	public String savePlayer(@RequestBody Player player) {
		return playerService.savePlayer(player);
	}
	
	@GetMapping("/findAllPlayers")
	public List<Player> getPlayers(){
		return playerService.getPlayers();
	}
	
	@GetMapping("/findPlayersBySchool/{school}")
	public List<Player> getPlayersBySchool(@PathVariable String school){
		return playerService.getPlayersBySchool(school, Sort.by(Sort.Direction.ASC, "playerID"));
	}
	@GetMapping("/findPlayersBySchoolAndDivisionAndPlayerType/{school}/{division}/{playerType}")
	public List<Player> getPlayersBySchoolAndDivisionAndPlayerType(@PathVariable String school, @PathVariable String division, @PathVariable String playerType){
		return playerService.getPlayersBySchoolAndDivisionAndPlayerType(school,division,playerType);
	}
	
	@GetMapping("/findAllPlayers/{id}")
	public Optional<Player> getPlayer(@PathVariable int id){
		return playerService.getPlayer(id);
	}
	
	@GetMapping("/findAllMatches")
	public List<Match> getMatches(){
		return matchService.getMatches();
	}
	
	@GetMapping("/findAllMatchDaySummary")
	public List<MatchDaySummary> getMatchDaySummary(){
		return matchService.getMatchDaySummary();
	}
	
	@GetMapping("/findTeamStanding")
	public List<Team> getTeamStanding(){
		return matchService.getTeamStanding();
	}
	
	@PostMapping("/addTeam")
	public String saveTeam(@RequestBody Team team) {
		return matchService.saveTeam(team);
	}
	
	@DeleteMapping("/delete/{id}")
	public String deletePlayer(@PathVariable int id) {
		return playerService.deletePlayer(id);
	}
	
	@PostMapping("/addMatch")
	public String saveMatch(@RequestBody Match match) {
		//return playerService.saveMatch(match);
		return matchService.saveMatch(match);

	}
	
	@PostMapping("/addMatches")
	public String saveMatches(@RequestBody List<Match> matches) {
		matchService.saveMatches(matches);
		playerService.updateScores(matches);
		return "";

	}
	
	@PostMapping("/userlogin")
	public Optional<User> authenticateUser(@RequestBody User user){
		return userService.authenticateUser(user);
	}
	
	@PostMapping("/addUser")
	public String saveUser(@RequestBody User user) {
		return userService.saveUser(user);
	}
	

	
	
	
	
}
