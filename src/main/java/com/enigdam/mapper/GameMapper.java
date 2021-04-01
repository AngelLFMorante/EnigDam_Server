package com.enigdam.mapper;

import java.util.ArrayList;
import java.util.List;

import com.enigdam.dto.GameDto;
import com.enigdam.entity.Game;

public class GameMapper {

	public List<GameDto> toDtos(List<Game> games) {
		List<GameDto> gamesDto = new ArrayList<>();
		for (Game game : games) {
			GameDto gameDto = toDto(game);
			gamesDto.add(gameDto);
		}
		return gamesDto;
	}

	private GameDto toDto(Game game) {
		GameDto gameDto = new GameDto();
		gameDto.setId(game.getId());
		gameDto.setTime(game.getTime());
		gameDto.setScore(game.getScore());
		return gameDto;
	}

}
