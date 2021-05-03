package com.enigdam.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
	public List<Map<String, Object>> scoreList()
	{
		return service.getAllScores();
	}
	
	@GetMapping("/players")
	public List<Player> players()
	{
		return service.getAllPlayers();
	}
	
	@PostMapping("/add")
	public void addPlayer(@RequestBody  Player player) 
	{
		service.addPlayer(player);
	}
	
	@GetMapping("/verify{code}")
	public String verifyPlayer(@Param("code") String code) 
	{	
		if(serviceEmail.verify(code)) {
			return serviceEmail.verifySuccess();
		}else {
			return serviceEmail.verifyFail();
		}
	}	
	
	@PutMapping("/editPlayer{id}")
	public Player editPlayer(@RequestBody Player player, @Param("id") int id) {
		return service.editPlayer(player.getUsername(), id);
	}
	
	@DeleteMapping("/deletePlayer{id}")
	public void deletePlayer(@Param("id") int id) {
		service.deletePlayer(id);
	}
	
}
