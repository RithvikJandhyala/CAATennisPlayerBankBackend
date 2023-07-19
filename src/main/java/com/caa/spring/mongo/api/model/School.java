package com.caa.spring.mongo.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "School")
public class School {
	public School(int id, String  name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	@Id
	private int id;
	private String name;
	private byte[] image;
	
	
	public int getID() {
		return id;
	}
	public void setID(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setImage(byte[] image) {
		this.image = image;
		
	}
	public byte[] getImage() {
		return image;
	}
	
}
