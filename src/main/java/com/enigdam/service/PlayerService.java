package com.enigdam.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.enigdam.entity.Player;
import com.enigdam.repository.IGameRepository;
import com.enigdam.repository.IPlayerRepository;

@Transactional
@Service
public class PlayerService {

	@Autowired
	EmailService serviceEmail;
	@Autowired
	IGameRepository repoGame;
	@Autowired
	IPlayerRepository repoPlayer;

	public List<Map<String, Object>> getAllScores() {
		return repoGame.allListScores();
	}

	public List<Player> getAllPlayers() {
		return repoPlayer.findAll();
	}

	public boolean addPlayer(Player player) {
		boolean insert;
		try {
			insert = serviceEmail.register(player);
		} catch (Exception e) {
			e.printStackTrace();
			insert = false;
		}
		return insert;
	}

	public Player editPlayer(String username, int id) {

		repoPlayer.editUserNamePlayer(username, id);
		Optional<Player> player = repoPlayer.findById(id);

		return player.get();
	}

	public void deletePlayer(int id) {
		repoPlayer.deleteById(id);
	}

}
