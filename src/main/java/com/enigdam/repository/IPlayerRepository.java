package com.enigdam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.enigdam.entity.Player;

public interface IPlayerRepository extends JpaRepository<Player, Long>{

	@Query("SELECT p FROM Player p WHERE p.email= ?1")
	public Player findByEmail(String email);
	
	@Query("SELECT p FROM Player p WHERE p.verifyCode= ?1")
	public Player findByVerificationCode(String code);
}
