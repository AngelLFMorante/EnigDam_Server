package com.enigdam.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.enigdam.dto.PlayerDto;
import com.enigdam.service.PlayerService;

@RestController
@RequestMapping(path = "/game")
@Component
public class PlayerController {

	@Autowired
	PlayerService service;
	
	@GetMapping("/scores")
	public List<Map<String, Object>> scoreList(){
		return service.getAllScores();
	}
	

	@GetMapping("/players")
	public List<PlayerDto> puntuaciones(){
		List<PlayerDto> playerDto = new ArrayList<>();
			playerDto = service.obtenerPuntuacion();
		return playerDto;
	}
	

	
}
