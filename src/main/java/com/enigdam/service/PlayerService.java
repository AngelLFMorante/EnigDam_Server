package com.enigdam.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.enigdam.daoimpl.PlayerDaoImpl;
import com.enigdam.dto.PlayerDto;
import com.enigdam.mapper.PlayerMapper;

@Service
public class PlayerService {

	@Autowired
	PlayerDaoImpl daoImpl;
	
	PlayerMapper playerMapper = new PlayerMapper();

	public List<Map<String, Object>> getAllScores() {
		return daoImpl.getAllScores();
	}


	public List<PlayerDto> obtenerPuntuacion() {
		List<PlayerDto> players = null;
		players = playerMapper.toDtos(daoImpl.allPlayers());
		return players;
	}

	
	

}
