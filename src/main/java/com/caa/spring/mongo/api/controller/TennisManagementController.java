package com.caa.spring.mongo.api.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.caa.spring.mongo.api.model.Match;
import com.caa.spring.mongo.api.model.MatchDaySummary;
import com.caa.spring.mongo.api.model.User;
import com.caa.spring.mongo.api.model.Player;
import com.caa.spring.mongo.api.model.School;
import com.caa.spring.mongo.api.model.Team;
import com.caa.spring.mongo.api.service.MatchService;
import com.caa.spring.mongo.api.service.PlayerService;
import com.caa.spring.mongo.api.service.SchoolService;
import com.caa.spring.mongo.api.service.UserService;

@CrossOrigin(origins = "https://www.azcaatennis.com")
@RestController
public class TennisManagementController {
	@Autowired
	private PlayerService playerService;
	@Autowired
	private UserService userService;
	@Autowired
	private MatchService matchService;
	@Autowired
	private SchoolService schoolService;
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
	@GetMapping("/findPlayersByDivision/{division}")
	public List<Player> getPlayersByDivision(@PathVariable String division){
		return playerService.getPlayersByDivision(division);
	}
	
	
	@GetMapping("/findAllPlayers/{id}")
	public Optional<Player> getPlayer(@PathVariable int id){
		return playerService.getPlayer(id);
	}
	
	@GetMapping("/findAllMatches")
	public List<Match> getMatches(){
		return matchService.getMatches();
	}
	@GetMapping("/findAllMatches/{division}")
	public List<Match> getMatchesByDivision(@PathVariable String division){
		return matchService.getMatchesByDivision(division);
	}
	
	@GetMapping("/findAllMatchDaySummary")
	public List<MatchDaySummary> getMatchDaySummary(){
		return matchService.getMatchDaySummary();
	}
	@GetMapping("/findAllMatchDaySummary/{division}")
	public List<MatchDaySummary> getMatchDaySummaryByDivision(@PathVariable String division){
		return matchService.getMatchDaySummaryByDivision(division);
	}
	
	@GetMapping("/findTeamStanding")
	public List<Team> getTeamStanding(){
		return matchService.getTeamStanding();
	}
	
	@PostMapping("/addTeam")
	public String saveTeam(@RequestBody Team team) {
		return matchService.saveTeam(team);
	}
	
	@GetMapping("/findTeamStanding/{division}/{name}")
	public List<Team> getTeamStandingByDivisionAndName(@PathVariable String division, @PathVariable String name){
		return matchService.getTeamStandingByDivisionAndName(division,name);
	}
	
	@GetMapping("/findTeamStanding/{division}")
	public List<Team> getTeamStandingByDivision(@PathVariable String division){
		return matchService.getTeamStandingByDivision(division);
	}
	
	@GetMapping("/deletePlayer/{id}")
	public String deletePlayer(@PathVariable int id) {
		return playerService.deletePlayer(id);
	}
	@GetMapping("/deleteMatchSummary/{id}")
	public List<Match> deleteMatchSummary(@PathVariable int id) {
		return matchService.deleteMatchSummary(id);
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
	@GetMapping("/findAllUsers")
	public List<User> getUsers(){
		return userService.getUsers();
	}
	
	@PostMapping("/userlogin")
	public Optional<User> authenticateUser(@RequestBody User user){
		return userService.authenticateUser(user);
	}
	
	@PostMapping("/addUser")
	public String saveUser(@RequestBody User user) {
		return userService.saveUser(user);
	}
	@GetMapping("/deleteUser/{username}")
	public String deleteUser(@PathVariable String username) {
		return userService.deleteUser(username);
	}
	@GetMapping("/findAllSchools")
	public List<School> getSchools(){
		return schoolService.getSchools();
	}

	@GetMapping("/deleteSchool/{id}")
	public String deleteSchool(@PathVariable int id) {
		return schoolService.deleteSchool(id);
	}
	
	@PostMapping("/schools/add")
	public int addSchool(@RequestParam("name") String name,
		 @RequestParam("image") MultipartFile image, Model model
		 ) 
	  throws IOException {
	    int id = schoolService.addSchool(name, image);
	    return id;
	}
	
	@GetMapping("/schools/{id}")
	public School getSchool(@PathVariable int id){ 
		School school = schoolService.getSchool(id);
	    return school;		
	}
	@GetMapping("/resetSeason")
	public String resetSeason() {
		playerService.clear();
		matchService.clear();
		//userService.clear();
		schoolService.clear();
		return "Season reset";
	}
	
	
}
