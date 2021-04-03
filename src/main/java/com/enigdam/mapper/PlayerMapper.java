package com.enigdam.mapper;

import java.util.ArrayList;
import java.util.List;

import com.enigdam.dto.PlayerDto;
import com.enigdam.entity.Player;

public class PlayerMapper {

	GameMapper gameMapper = new GameMapper();

	public List<PlayerDto> toDtos(List<Player> players) {
		List<PlayerDto> playersDto = new ArrayList<>();
		for (Player player : players) {
			PlayerDto playerDto = toDto(player);
			playersDto.add(playerDto);
		}

		return playersDto;
	}

	private PlayerDto toDto(Player player) {
		PlayerDto playerDto = new PlayerDto();
		playerDto.setId(player.getId());
		playerDto.setUsername(player.getUsername());
		playerDto.setEmail(player.getEmail());
		playerDto.setHint(player.getHint());
		playerDto.setGames(gameMapper.toDtos(player.getGames()));
		return playerDto;
	}
	
	@SuppressWarnings("unused")
	private List<Player> toEntities(List<PlayerDto> players){
		List<Player> playersToEntity = new ArrayList<>();
		for (PlayerDto playerDto : players) {
			playersToEntity.add(toEntity(playerDto));
		}
		return playersToEntity;
	}

	private Player toEntity(PlayerDto playerDto) {
		Player player = new Player();
		player.setName(playerDto.getName());
		player.setEmail(playerDto.getEmail());
		player.setUsername(playerDto.getUsername());
		player.setPassword(playerDto.getPassword());
		player.setHint(playerDto.getHint());
		player.setVerify(playerDto.isVerify());
		return player;
	}
	

}
