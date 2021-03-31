package com.enigdam.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Game {
	
	@Id
	private int id;
	private int id_user;
	private String time;
	private String score;
	
	public Game() {
	}
	
	public Game(int id, int id_user, String time, String score) {
		super();
		this.id = id;
		this.id_user = id_user;
		this.time = time;
		this.score = score;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_user() {
		return id_user;
	}

	public void setId_user(int id_user) {
		this.id_user = id_user;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
	
	
	
}
