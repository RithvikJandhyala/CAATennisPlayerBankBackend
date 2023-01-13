package com.caa.spring.mongo.api.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

@Document(collection = "Player")
public class Player {
	@Id
	private int playerID;
	private String name;
	private String school;
	private String division;
	private String playerType;
	private int rank;
	private int wins = 0;
	private int losses = 0;
	
	
	public Player(int playerID, String name, String school, String division, String playerType,  int wins, int losses, int rank) {
		this.playerID = playerID;
		this.name = name;
		this.school = school;
		this.division = division;
		this.playerType = playerType;
		this.wins = wins;
		this.losses = losses;
		this.rank = rank;
	
	}
	public int getPlayerID() {
		return playerID;
	}
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getPlayerType() {
		return playerType;
	}
	public void setPlayerType(String playerType) {
		this.playerType = playerType;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public int getWins() {
		return wins;
	}
	public void setWins(int wins) {
		this.wins = wins;
	}
	public int getLosses() {
		return losses;
	}
	public void setLosses(int losses) {
		this.losses = losses;
	}
	@Override
	public String toString() {
		return "Player [playerID=" + playerID + ", name=" + name + ", school=" + school + ", division=" + division
				+ ", playerType=" + playerType + ", rank=" + rank + ", wins=" + wins + ", losses=" + losses + "]";
	}
}
