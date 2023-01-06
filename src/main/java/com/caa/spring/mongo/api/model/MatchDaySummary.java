package com.caa.spring.mongo.api.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.annotation.Id;
//import jakarta.annotation.
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Document(collection = "MatchDaySummary")
public class MatchDaySummary {	
	@Id
    private long id;	
	private String homeTeam;
	private int homeTeamPoints;
	private String awayTeam;
	private int awayTeamPoints;
	private String division;
	private String matchDate;
	
	public MatchDaySummary(long id, String homeTeam, int homeTeamPoints, String awayTeam, int awayTeamPoints,
			String division, String matchDate) {
		super();
		this.id = id;
		this.homeTeam = homeTeam;
		this.homeTeamPoints = homeTeamPoints;
		this.awayTeam = awayTeam;
		this.awayTeamPoints = awayTeamPoints;
		this.division = division;
		this.matchDate = matchDate;
	}


	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getHomeTeamPoints() {
		return homeTeamPoints;
	}
	public void setHomeTeamPoints(int homeTeamPoints) {
		this.homeTeamPoints = homeTeamPoints;
	}
	public int getAwayTeamPoints() {
		return awayTeamPoints;
	}
	public void setAwayTeamPoints(int awayTeamPoints) {
		this.awayTeamPoints = awayTeamPoints;
	}

	
	@Override
	public String toString() {
		return "MatchDaySummary [id=" + id + ", homeTeamPoints=" + homeTeamPoints + ", awayTeamPoints=" + awayTeamPoints
				+ ", division=" + division +  ", matchDate=" + matchDate + ", homeTeam="
				+ homeTeam + ", awayTeam=" + awayTeam + "]";
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
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
	public Date getDate() {
		try {
			return new SimpleDateFormat("MM/dd/yyyy").parse(matchDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
}
