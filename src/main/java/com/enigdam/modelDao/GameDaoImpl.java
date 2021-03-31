package com.enigdam.modelDao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.enigdam.interfaces.IGameDao;
import com.enigdam.model.Game;

@Repository
public class GameDaoImpl implements IGameDao {
	
	@Autowired
	JdbcTemplate template;
	
	public List<Map<String, Object>> getAll() {
		List<Map<String, Object>>allList =template.queryForList("Select * From game");
		return allList;
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
