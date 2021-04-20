package com.enigdam.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.enigdam.entity.Game;

public interface IGameRepository extends JpaRepository<Game, Long> {
	@Query(nativeQuery = true, value= "Select player.username, game.time, game.score from game, player where game.id_user=player.id order by game.time desc;")
	public List<Map<String, Object>> allListScores ();
}
