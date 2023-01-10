package com.caa.spring.mongo.api.service;

import java.util.HashMap;
import org.springframework.data.domain.Sort;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.caa.spring.mongo.api.model.Match;
import com.caa.spring.mongo.api.model.Player;
import com.caa.spring.mongo.api.repository.PlayerRepository;

@Service
public class PlayerService {
	@Autowired
	private PlayerRepository repository;

	public String savePlayer(Player player) {
		int id = generateID(player);
		player.setPlayerID(id);
		repository.save(player);
		return "Added " + player.getName()+" With ID " + player.getPlayerID();
	}
	
	private int generateID(Player player) {
		
		int id = 1;
		Map<String, Integer> schoolMap = new HashMap<>();
		schoolMap.put("BASIS Scottsdale", 1000);
		schoolMap.put("BASIS Mesa", 2000);
		schoolMap.put("BASIS Ahwatukee", 3000);
		schoolMap.put("Heritage Academy Maricopa", 4000);
		schoolMap.put("Heritage Academy Mesa", 5000);
		schoolMap.put("Benjamin Franklin Charter", 6000);
		schoolMap.put("ALA Gilbert North", 7000);
		schoolMap.put("BASIS Prescott", 8000);
		schoolMap.put("Tri-City Christian", 9000);
		
		Map<String, Integer> divisionMap = new HashMap<>();
		divisionMap.put("JH Boys", 0);
		divisionMap.put("JH Girls", 250);
		divisionMap.put("HS Boys", 500);
		divisionMap.put("HS Girls", 750);
		
		Map<String, Integer> playerTypeMap = new HashMap<>();
		playerTypeMap.put("Singles", 0);
		playerTypeMap.put("Doubles", 125);
		
		
		String playerType = player.getPlayerType();
		String division = player.getDivision();
		String school = player.getSchool();
		
		List <Player> players = repository.findBySchoolAndDivisionAndPlayerType(school, division, playerType);
		int count = players.size();
		System.out.println("count:" + count);
		id+= count;
		System.out.println("school"+school + "D"+ division+"PT"+playerType);
		
		id+= schoolMap.get(school);
		id+= divisionMap.get(division);
		id+= playerTypeMap.get(playerType);
		System.out.println("ID Generated:"+id);
		
		return id;
	}
	

	public List<Player> getPlayers(){
		return repository.findAll(Sort.by(Sort.Direction.DESC, "wins"));
	}
			
	public List<Player> getPlayersBySchoolAndDivisionAndPlayerType(String school, String division, String playerType){
		return repository.findBySchoolAndDivisionAndPlayerType(school, division, playerType);
	}
	

	public Optional<Player> getPlayer(int id){
		return repository.findById(id);
	}
	
	public String deletePlayer(int id) {
		repository.deleteById(id);
		return "Player deleted with  " + id;
	}

	public String updateScores(List<Match> matches) {
		List<Player> players= repository.findAll();	
		for (Match match : matches) 
		{
			Player p1 = findPlayer(match.getPlayer1ID(),players);
			Player p2 = findPlayer(match.getPlayer2ID(),players);
			if(p1 == null && p2 == null) {
				System.out.println( "Match not valid");
			}
			else if(p1 == null && p2 != null) {
				p2.setWins(p2.getWins() + 1);
			}		
			else if(p1 != null && p2 == null) {
				System.out.println( "Match not valid");
				p1.setWins(p1.getWins() + 1);
			}		
			else if(match.getPlayer1Score()> match.getPlayer2Score())
			{
				p1.setWins(p1.getWins() + 1);
				p2.setLosses(p2.getLosses() + 1);
				
			}
			else {
				p2.setWins(p2.getWins() + 1);
				p1.setLosses(p1.getLosses() + 1);
			}			
		}
		repository.saveAll(players);
		return "Player scores updated";
	}
	
	public Player findPlayer(int playerID, List<Player> players)
	{
		Player player;
		for(int i = 0; i < players.size(); i++)
		{
			player = players.get(i);
			if(player.getPlayerID() == playerID) {
				return player;
			}
		}
		return null;
	}
	public List<Player> getPlayersBySchool(String school, Sort sort){
		return repository.findBySchool(school,sort);
	}
	
		
}
