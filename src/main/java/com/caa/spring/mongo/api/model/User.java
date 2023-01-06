package com.caa.spring.mongo.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "User")
public class User {

	public User(String firstName, String lastName, String homeTeam, String username, String password, String role) {
		super();
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.homeTeam = homeTeam;
		this.username = username;
		this.password = password;
		this.role = role;
	}
	@Id
	private String username;
	private String firstName;
	private String lastName;
	private String homeTeam;	
	private String password;
	private String role;
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getHomeTeam() {
		return homeTeam;
	}
	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String toString() {
		return "User [firstName=" + firstName + ", lastName=" + lastName + ", homeTeam=" + homeTeam + ", username="
				+ username + ", password=" + password + ", role=" + role + "]";
	}
	
	
	
	
}
