package com.enigdam.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enigdam.dao.IPlayerDao;
import com.enigdam.daoimpl.PlayerDaoImpl;

@Service
public class PlayerService implements IPlayerDao {

	@Autowired
	PlayerDaoImpl daoImpl;

	public List<Map<String, Object>> getAllScores() {
		return daoImpl.getAllScores();
	}
	
	

}
