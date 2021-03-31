package com.enigdam.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enigdam.service.GameService;

@RestController
@RequestMapping(path = "/game")
public class GameController {

	@Autowired
	GameService service;
	
	@GetMapping("/scores")
	public List<Map<String, Object>> list(){
		return service.getAll();
	}
	
}
