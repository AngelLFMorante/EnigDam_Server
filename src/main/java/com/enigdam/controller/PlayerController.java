package com.enigdam.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.enigdam.dto.PlayerDto;
import com.enigdam.entity.Player;
import com.enigdam.service.EmailService;
import com.enigdam.service.PlayerService;

@RestController
@RequestMapping(path = "/game")
@Component
public class PlayerController {

	@Autowired
	PlayerService service;
	@Autowired
	EmailService serviceEmail;
	
	@GetMapping("/scores")
	public List<Map<String, Object>> scoreList(){
		return service.getAllScores();
	}
	

	@GetMapping("/players")
	public List<PlayerDto> puntuaciones(){
		List<PlayerDto> playerDto = new ArrayList<>();
			playerDto = service.getAllPlayers();
		return playerDto;
	}
	
	@PostMapping("/add")
	public Map<String, String> addPlayer(@RequestBody  Player player) {
		boolean response = service.addPlayer(player);
		if(response) {
			return  Collections.singletonMap("response", "Registry success");
		}else {
			return Collections.singletonMap("response","Unable to register");
		}
	}
	
	@GetMapping("/verify{code}")
	public String verifyPlayer(@Param("code") String code) {
		
		if(serviceEmail.verify(code)) {
			return serviceEmail.verifySuccess();
		}else {
			return serviceEmail.verifyFail();
		}
	}


	

	
}
