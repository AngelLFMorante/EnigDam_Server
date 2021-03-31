package com.enigdam.interfaces;

import java.util.List;
import java.util.Map;

import com.enigdam.model.Game;

public interface IGameDao {

	public List<Map<String, Object>>getAll();
	public List<Map<String, Object>>getOne(int id);
	public Game add(Game game);
	public Game edit(Game game);
	public void delete(int id);
	
}
