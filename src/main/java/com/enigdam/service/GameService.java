package com.enigdam.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enigdam.interfaces.IGameDao;
import com.enigdam.model.Game;
import com.enigdam.modelDao.GameDaoImpl;

@Service
public class GameService implements IGameDao {
	
	@Autowired
	GameDaoImpl daoImpl;
	
	public List<Map<String, Object>> getAll() {
		return daoImpl.getAll();
	}

	public List<Map<String, Object>> getOne(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Game add(Game game) {
		// TODO Auto-generated method stub
		return null;
	}

	public Game edit(Game game) {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

}
