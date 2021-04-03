package com.enigdam.dao;

import java.util.List;
import java.util.Map;

import com.enigdam.entity.Player;


public interface IPlayerDao {

	public List<Map<String, Object>>getAllScores();
	public List<Player> allPlayers();
//	public List<Map<String, Object>>getOne(int id);
	public void addPlayer(Player player);
//	public int edit(Game game);
//	public int delete(int id);
	
}
