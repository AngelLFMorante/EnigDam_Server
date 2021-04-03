package com.enigdam.service;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.enigdam.daoimpl.PlayerDaoImpl;
import com.enigdam.dto.PlayerDto;
import com.enigdam.entity.Player;
import com.enigdam.mapper.PlayerMapper;

@Service
public class PlayerService {

	@Autowired
	PlayerDaoImpl daoImpl;
	@Autowired
	EntityManager em;
	
	PlayerMapper playerMapper = new PlayerMapper();

	public List<Map<String, Object>> getAllScores() {
		return daoImpl.getAllScores();
	}


	public List<PlayerDto> obtenerPuntuacion() {
		List<PlayerDto> players = null;
		players = playerMapper.toDtos(daoImpl.allPlayers());
		return players;
	}


	public boolean addPlayer(Player player) {
		boolean insert = false;
		try {
			daoImpl.addPlayer(player);
			em.getTransaction().commit();
			insert = true;
		}catch(Exception e) {
			e.printStackTrace();
			insert = false;
			em.getTransaction().rollback();
		}
		return insert;
	}

	
	

}
