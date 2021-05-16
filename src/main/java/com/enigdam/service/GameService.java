package com.enigdam.service;

import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.enigdam.entity.Game;
import com.enigdam.repository.IGameRepository;

@Transactional
@Service
public class GameService {
	
	@Autowired
	IGameRepository repoGame;

	public List<Map<String, Object>> getAllScores() {
		return repoGame.allListScores();
	}
	
	public void addGame(Game game) {
		repoGame.save(game);
		repoGame.flush();
	}
}
