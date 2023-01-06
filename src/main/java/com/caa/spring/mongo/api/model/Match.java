package com.caa.spring.mongo.api.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.annotation.Id;
//import jakarta.annotation.
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Document(collection = "Match")
public class Match {	
	@Id
    private long id;	
	private int player1ID;
	private int player2ID;	
	private int player1Score;
	private int player2Score;
	private String division;
	private String matchType;
	private String matchDate;
	private String homeTeam;
	private String awayTeam;
	private String player1Name;
	private String player2Name;
	public Match(long id, int player1ID, int player2ID, int player1Score, int player2Score, String division,
			String matchType, String matchDate, String homeTeam, String awayTeam) {
		super();
		this.id = id;
		this.player1ID = player1ID;
		this.player2ID = player2ID;
		this.player1Score = player1Score;
		this.player2Score = player2Score;
		this.division = division;
		this.matchType = matchType;
		this.matchDate = matchDate;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getPlayer2ID() {
		return player2ID;
	}
	public void setPlayer2ID(int player2id) {
		player2ID = player2id;
	}
	public int getPlayer1Score() {
		return player1Score;
	}
	public void setPlayer1Score(int player1Score) {
		this.player1Score = player1Score;
	}
	public int getPlayer2Score() {
		return player2Score;
	}
	public void setPlayer2Score(int player2Score) {
		this.player2Score = player2Score;
	}
	public int getPlayer1ID() {
		return player1ID;
	}
	public void setPlayer1ID(int player1id) {
		player1ID = player1id;
	}
	
	public Date getDate() {
		try {
			return new SimpleDateFormat("MM/dd/yyyy").parse(matchDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	@Override
	public String toString() {
		return "Match [id=" + id + ", player1ID=" + player1ID + ", player2ID=" + player2ID + ", player1Score="
				+ player1Score + ", player2Score=" + player2Score + ", division=" + division + ", matchType="
				+ matchType + ", matchDate=" + matchDate + ", homeTeam=" + homeTeam + ", awayTeam=" + awayTeam + "]";
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getMatchType() {
		return matchType;
	}
	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}
	public String getMatchDate() {
		return matchDate;
	}
	public void setMatchDate(String matchDate) {
		this.matchDate = matchDate;
	}
	public String getHomeTeam() {
		return homeTeam;
	}
	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}
	public String getAwayTeam() {
		return awayTeam;
	}
	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}
	public String getPlayer1Name() {
		return player1Name;
	}
	public String getPlayer2Name() {
		return player2Name;
	}
	public void setPlayer1Name(String player1Name) {
		this.player1Name = player1Name;
		
	}
	public void setPlayer2Name(String player2Name) {
		this.player2Name = player2Name;
		
	}
	
	
}
