package com.caa.spring.mongo.api.service;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.caa.spring.mongo.api.model.Match;
import com.caa.spring.mongo.api.model.MatchDaySummary;
import com.caa.spring.mongo.api.model.Player;
import com.caa.spring.mongo.api.model.Team;
import com.caa.spring.mongo.api.repository.MatchRepository;
import com.caa.spring.mongo.api.repository.PlayerRepository;
import com.caa.spring.mongo.api.repository.TeamRepository;
import com.caa.spring.mongo.api.repository.MatchDaySummaryRepository;

@Service
public class MatchService {
	@Autowired
	private MatchRepository repository;
	@Autowired
	private MatchDaySummaryRepository matchDaySummaryRepository;
	@Autowired
	private TeamRepository teamRepository;
	@Autowired
	private PlayerRepository playerRepository;
	@Autowired
	private PlayerService playerService;
	
	//gets matches with player name populated
	public List<Match> getMatches(){
		List<Match> matches = repository.findAll(Sort.by(Sort.Direction.ASC, "matchDate"));
		return getMatchesDetails(matches);
	}
	private List<Match> getMatchesDetails(List<Match> matches){
		if(matches.size() == 0) {
			return matches;
		}
		else {
			List<Player> players= playerService.getPlayers();
			for (Match match : matches) 
			{
				Player p1 = playerService.findPlayer(match.getPlayer1ID(),players);
				Player p2 = playerService.findPlayer(match.getPlayer2ID(),players);	
				if(p1 == null) 
					match.setPlayer1Name("No Show");
				else
					match.setPlayer1Name(p1.getName());
				
				if(p2 == null) 
					match.setPlayer2Name("No Show");
				else
					match.setPlayer2Name(p2.getName());				
			}
		}
		matches.sort(Comparator.comparing(Match::getDate));
		return matches;
	}
	public List<Match> getMatchesByDivision(String division){
		if(division.equals("All Divisions")) {
			return getMatches();
		}
		else {
			List<Match> matchesByDivision = repository.findMatchByDivision(division);
			return getMatchesDetails(matchesByDivision);
		}
	}
	
	public String saveMatch(Match match) {
		repository.save(match);
		return "match saved";
	}
	private boolean isValidMatch(Match match) {
		// Make sure that the right division is set for the matches
		int divisionID = match.getPlayer1ID() % 1000;
		
		if(divisionID > 0 && divisionID <= 250) {
			match.setDivision("JH Boys");
		}
		else if(divisionID > 250 && divisionID <= 500) {
			match.setDivision("JH Girls");
		}
		else if(divisionID > 500 && divisionID <= 750) {
			match.setDivision("HS Boys");
		}
		else if(divisionID > 750 && divisionID <= 999) {
			match.setDivision("HS Girls");
		}
					
		if(match.getPlayer1ID()== 0 && match.getPlayer2ID() == 0) {
			System.out.println("No Show vs No Show");
			return false;
		}
		if(match.getPlayer1ID()== 0 && match.getPlayer2ID()!=0) {
			return true;
		}
		if(match.getPlayer1ID()!= 0 && match.getPlayer2ID()==0) {
			return true;
		}
		return true;
		
	}
	private long getMaxMatchID(){
		long id;
		if(repository.count() == 0) {
			id=0;
		}
		else {
			List<Match> matches = repository.findAll();
			Match maxIDMatch = matches.stream()
	                .max(Comparator.comparingLong(Match::getId))
	                .get();
			long maxID = maxIDMatch.getId();
			id = maxID;
		}
		return id;
		
	}
	private String setMatchPair(long matchID) {
		if(matchID % 3 == 1) {
			return "Pair 1";
		}
		else if(matchID % 3 == 2) {
			return "Pair 2";
		}
		else  {
			return "Pair 3";
		}
	}
	public String saveMatches(List<Match> matches) {
		int homeTeamSummaryPoints = 0;
		int awayTeamSummaryPoints = 0;
		
		//Team Standings variables
		int homeTeamStandingWins = 0;
		int homeTeamStandingLosses = 0;
		double  homeTeamStandingPct = 0;
		int homeTeamStandingTies = 0;
		int homeTeamStandingPointsFor = 0;
		int homeTeamStandingPointsAgainst = 0;
		
		int awayTeamStandingWins = 0;
		int awayTeamStandingLosses = 0;
		double awayTeamStandingPct = 0;
		int awayTeamStandingTies = 0;
		int awayTeamStandingPointsFor = 0;
		int awayTeamStandingPointsAgainst = 0;
		HashMap<Integer, Match> validMatches = new HashMap<Integer, Match>();
		
		long currentCount = getMaxMatchID();
		for (int i =0; i < matches.size();i++) {
			if(isValidMatch(matches.get(i))) {
				validMatches.put(i,matches.get(i));
			}
		}
		if(validMatches.size() == 0) {
			return "All Invalid Matches";
		}
	
		
		
		for (HashMap.Entry<Integer, Match> validMatch : validMatches.entrySet()) {
		    int seq = validMatch.getKey();
		    Match match = validMatch.getValue();
		    currentCount++;
		    match.setId(currentCount);
		    match.setPair(setMatchPair(match.getId()));
		    if(seq == 0)
			{
					if(match.getPlayer1Score() > match.getPlayer2Score()) {
						homeTeamSummaryPoints +=2;
					}
					else {
						awayTeamSummaryPoints +=2;
					} 
				
			}
			else {
				if(match.getPlayer1Score() > match.getPlayer2Score()) {
					homeTeamSummaryPoints++;
				}
				else {
					awayTeamSummaryPoints++;
				} 
			}
		}
		
		Match firstMatch = validMatches.get(validMatches.keySet().toArray()[0]);
		String homeTeam = firstMatch.getHomeTeam();
		String awayTeam =  firstMatch.getAwayTeam();
		String division = firstMatch.getDivision();
		String matchDate = firstMatch.getMatchDate();
		long matchID = firstMatch.getId();
		for (HashMap.Entry<Integer, Match> validMatch : validMatches.entrySet()) {
		    Match match = validMatch.getValue();
		    match.setMatchSummaryID(matchID);
		}
		repository.saveAll(validMatches.values());		
		MatchDaySummary matchDaySummary= new MatchDaySummary( matchID,  homeTeam, homeTeamSummaryPoints, awayTeam ,  awayTeamSummaryPoints,
				division, matchDate );
		matchDaySummaryRepository.save(matchDaySummary);
		
		//Team Standings
		if(homeTeamSummaryPoints > awayTeamSummaryPoints) {
			homeTeamStandingWins = 1;
			awayTeamStandingLosses = 1;		
			homeTeamStandingPointsFor = 2;
			awayTeamStandingPointsAgainst = 2;	
			
		}
		else if(homeTeamSummaryPoints == awayTeamSummaryPoints) {
			homeTeamStandingTies = 1;
			awayTeamStandingTies = 1;
			homeTeamStandingPointsFor = 1;
			awayTeamStandingPointsFor = 1;
			homeTeamStandingPointsAgainst = 1;
			awayTeamStandingPointsAgainst = 1;
		}
		else {
			homeTeamStandingLosses = 1;
			awayTeamStandingWins = 1;
			awayTeamStandingPointsFor = 2;
			homeTeamStandingPointsAgainst = 2;	
		}
		
		//Save Team Standing
		Optional<Team> myHomeTeam = teamRepository.findById(homeTeam+"_"+division);
		Optional<Team> myAwayTeam = teamRepository.findById(awayTeam+"_"+division);
		if(myHomeTeam.isPresent() ){	
			Team homeTeamStanding = myHomeTeam.get();
			homeTeamStanding.setWins(homeTeamStanding.getWins()+homeTeamStandingWins );
			homeTeamStanding.setLosses(homeTeamStanding.getLosses()+homeTeamStandingLosses);
			homeTeamStanding.setTies(homeTeamStanding.getTies()+homeTeamStandingTies);
			if((homeTeamStanding.getWins() + homeTeamStanding.getLosses() + homeTeamStanding.getTies())!=0)
			{
				homeTeamStanding.setPct((double) homeTeamStanding.getWins()/(homeTeamStanding.getWins() + homeTeamStanding.getLosses() + homeTeamStanding.getTies()) * 100);
				System.out.println("Pct" + (double) homeTeamStanding.getWins()/(homeTeamStanding.getWins() + homeTeamStanding.getLosses() + homeTeamStanding.getTies()) );
				System.out.println("W:"+ homeTeamStanding.getWins());
				System.out.println("Total:" + (homeTeamStanding.getWins() + homeTeamStanding.getLosses() + homeTeamStanding.getTies()));

				
			}
			homeTeamStanding.setWinPoints(homeTeamStanding.getWinPoints() + homeTeamSummaryPoints);
			homeTeamStanding.setLossPoints(homeTeamStanding.getLossPoints() + awayTeamSummaryPoints);
			homeTeamStanding.setPointsFor(homeTeamStanding.getPointsFor() + homeTeamSummaryPoints);
			homeTeamStanding.setPointsAgainst(homeTeamStanding.getPointsAgainst() + homeTeamStandingPointsAgainst) ;
			teamRepository.save(homeTeamStanding);
		}
		else{
			if((homeTeamStandingWins+ homeTeamStandingLosses + homeTeamStandingTies) !=0) {
				homeTeamStandingPct = homeTeamStandingWins/(homeTeamStandingWins+ homeTeamStandingLosses + homeTeamStandingTies) * 100;
			}			
			Team homeTeamStanding = new Team(homeTeam+"_"+division,homeTeam,homeTeamStandingWins,homeTeamStandingLosses,homeTeamStandingTies,homeTeamStandingPct,homeTeamSummaryPoints,awayTeamSummaryPoints,homeTeamStandingPointsFor,homeTeamStandingPointsAgainst,division);
			teamRepository.save(homeTeamStanding);
		}
		
		if(myAwayTeam.isPresent() ){		
			Team awayTeamStanding = myAwayTeam.get();
			awayTeamStanding.setWins(awayTeamStanding.getWins()+awayTeamStandingWins);
			awayTeamStanding.setLosses(awayTeamStanding.getLosses()+awayTeamStandingLosses);
			awayTeamStanding.setTies(awayTeamStanding.getTies()+awayTeamStandingTies);
			if((awayTeamStanding.getWins() + awayTeamStanding.getLosses() + awayTeamStanding.getTies())!=0)
			{
				awayTeamStanding.setPct((double)awayTeamStanding.getWins()/(awayTeamStanding.getWins() + awayTeamStanding.getLosses() + awayTeamStanding.getTies()) * 100);
				System.out.println((awayTeamStanding.getWins()/(awayTeamStanding.getWins() + awayTeamStanding.getLosses() + awayTeamStanding.getTies()) * 100));
			}
			awayTeamStanding.setWinPoints(awayTeamStanding.getWinPoints() + awayTeamSummaryPoints);
			awayTeamStanding.setLossPoints(awayTeamStanding.getLossPoints() + homeTeamSummaryPoints);
			awayTeamStanding.setPointsFor(awayTeamStanding.getPointsFor() + awayTeamStandingPointsFor);
			awayTeamStanding.setPointsAgainst(awayTeamStanding.getPointsAgainst()+awayTeamStandingPointsAgainst);
			teamRepository.save(awayTeamStanding);
		}
		else {
			if((awayTeamStandingWins+ awayTeamStandingLosses + awayTeamStandingTies) !=0) {
				awayTeamStandingPct = awayTeamStandingWins/(awayTeamStandingWins+ awayTeamStandingLosses + awayTeamStandingTies) * 100;
			}
			Team awayTeamStanding = new Team(awayTeam+"_"+division,awayTeam,awayTeamStandingWins,awayTeamStandingLosses,awayTeamStandingTies,awayTeamStandingPct,awayTeamSummaryPoints,homeTeamSummaryPoints,awayTeamStandingPointsFor,awayTeamStandingPointsAgainst,division);
			teamRepository.save(awayTeamStanding);
		}
		return "Matches, Mathes Summary and Team Standings Saved";
	}
	
	public List<MatchDaySummary> getMatchDaySummary() {	
		List<MatchDaySummary> matchesSummary = matchDaySummaryRepository.findAll();
		matchesSummary.sort(Comparator.comparing(MatchDaySummary	::getDate));
		return matchesSummary;
	}
	public List<MatchDaySummary> getMatchDaySummaryByDivision(String division){
		if(division.equals("All Divisions")) {
			return getMatchDaySummary();
		}
		else {
			List<MatchDaySummary> matchesSummaryByDivision = matchDaySummaryRepository.findMatchDaySummaryByDivision(division);
			matchesSummaryByDivision.sort(Comparator.comparing(MatchDaySummary	::getDate));
			return matchesSummaryByDivision;
		}
	}

	public List<Team> getTeamStanding() {
		return teamRepository.findAll();
	}
	
	public List<Team> getTeamStandingByDivisionAndName(String division,String name){
		return teamRepository.findTeamStandingByDivisionAndName(division,name);
	}
	
	public List<Team> getTeamStandingByDivision(String division){
		if(division.equals("All Divisions")) {
			return getTeamStanding();
		}
		else {
			return teamRepository.findTeamStandingByDivision(division);
		}
	}

	public String saveTeam(Team team) {
		teamRepository.save(team);
		return "Team Saved";
	}
	public List<Match> deleteMatchSummary(int id) {
		//Find Match Details
		Optional<MatchDaySummary> matchSum= matchDaySummaryRepository.findById(id);
		if(matchSum.isPresent() ){	
			MatchDaySummary matchSummary = matchSum.get();
			List<Match> matches = repository.findMatchByMatchSummaryID(matchSummary.getId());
			//reset player scores
			resetPlayerScores(matches);
			//reset team standings
			resetTeamStandings(matchSummary);
			//delete matches and match summary
			repository.deleteAll(matches);
			matchDaySummaryRepository.delete(matchSummary);
			return matches;
			
		}
		return null;
		
	}
	public String clear() {
		repository.deleteAll();
		matchDaySummaryRepository.deleteAll();
		return "ALL MATCHES CLEARED";
	}
	private void resetPlayerScores(List<Match> matches){
		if(matches.size() == 0) {
			return;
		}
		else {
			for (Match match : matches) 
			{
				Optional<Player> p1 = playerService.getPlayer(match.getPlayer1ID());
				Optional<Player> p2 = playerService.getPlayer(match.getPlayer2ID());
				Player player1 = null, player2 = null;
				int p1OGWins = 0 ,p1OGLosses = 0 ,p2OGWins  = 0 ,p2OGLosses  = 0;
				if(p1.isPresent())
				{
					player1 = p1.get();
					p1OGWins = player1.getWins();
					p1OGLosses = player1.getLosses();
				}
				
				if(p2.isPresent())
				{
					player2 = p2.get();
					p2OGWins = player2.getWins();
					p2OGLosses = player2.getLosses();
				}
				
				System.out.println("Before");
				System.out.println(player1);
				System.out.println(player2);
				if(match.getPlayer1Score() > match.getPlayer2Score()){
					if(player1 != null)
					    player1.setWins(p1OGWins-1);
					if(player2 != null)
          				player2.setLosses(p2OGLosses-1);
				}
				if(match.getPlayer1Score() < match.getPlayer2Score()){
					if(player2 != null)
						player2.setWins(p2OGWins-1);
					if(player1 != null)
						player1.setLosses(p1OGLosses-1);
				}
				System.out.println("After");
				System.out.println(player1);
				System.out.println(player2);
				if(player1 !=null) {
					playerRepository.save(player1);
				}
				if(player2!=null) {
					playerRepository.save(player2);
				}
				
				
			}
		}
	}
	private void resetTeamStandings(MatchDaySummary matchSummary) {
		String ht = matchSummary.getHomeTeam();
		String at = matchSummary.getAwayTeam();
		String division = matchSummary.getDivision();
		List<Team> home = teamRepository.findTeamStandingByDivisionAndName(division,ht);
		List<Team> away = teamRepository.findTeamStandingByDivisionAndName(division,at);
		if(home.size() != 0 && away.size() != 0) {
			Team homeTeam = home.get(0);
			Team awayTeam = away.get(0);
			System.out.println("Before");
			System.out.println(homeTeam);
			System.out.println(awayTeam);
			int homeWins = homeTeam.getWins();
			int homeLosses = homeTeam.getLosses();
			int homeTies = homeTeam.getTies();
			int homeOGWinPoints = homeTeam.getWinPoints();
			int homeOGLossPoints = homeTeam.getLossPoints();
		
			int awayWins = awayTeam.getWins();
			int awayLosses = awayTeam.getLosses();
			int awayTies = awayTeam.getTies();
			int awayOGWinPoints = awayTeam.getWinPoints();
			int awayOGLossPoints = awayTeam.getLossPoints();
			
			int homePoints = matchSummary.getHomeTeamPoints();	
			int awayPoints = matchSummary.getAwayTeamPoints();
			
			if(homePoints > awayPoints) {
				homeTeam.setWins(homeWins-1);
				awayTeam.setLosses(awayLosses-1);
			}
			else if(homePoints < awayPoints) {
				awayTeam.setWins(awayWins-1);
				homeTeam.setLosses(homeLosses-1);
			}
			else{
				awayTeam.setTies(awayTies-1);
				homeTeam.setTies(homeTies-1);
			}
			if((awayTeam.getWins() + awayTeam.getLosses() + awayTeam.getTies()) !=0) {
				awayTeam.setPct((double)awayTeam.getWins()/(awayTeam.getWins() + awayTeam.getLosses() + awayTeam.getTies()) * 100);
			}
			else {
				awayTeam.setPct(0);
			}
			if((homeTeam.getWins() + homeTeam.getLosses() + homeTeam.getTies()) !=0) {
				homeTeam.setPct((double)homeTeam.getWins()/(homeTeam.getWins() + homeTeam.getLosses() + homeTeam.getTies()) * 100);
			}
			else {
				homeTeam.setPct(0);
			}
			
			homeTeam.setWinPoints(homeOGWinPoints - homePoints);
			homeTeam.setLossPoints(homeOGLossPoints - awayPoints);
			awayTeam.setWinPoints(awayOGWinPoints - awayPoints);
			awayTeam.setLossPoints(awayOGLossPoints - homePoints);
			System.out.println("After");
			System.out.println(homeTeam);
			System.out.println(awayTeam);
			teamRepository.save(homeTeam);
			teamRepository.save(awayTeam);
			
		}
		
		
	}
}
