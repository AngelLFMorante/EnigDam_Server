package com.enigdam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.enigdam.entity.Player;

public interface IPlayerRepository extends JpaRepository<Player, Integer>{

	@Query("SELECT p FROM Player p WHERE p.email= ?1")
	public Player findByEmail(String email);
	
	@Query("SELECT p FROM Player p WHERE p.verifyCode= ?1")
	public Player findByVerificationCode(String code);
	
	@Modifying
	@Query("UPDATE Player p SET p.username= :username WHERE p.id= :id")
	public void editUserNamePlayer(@Param("username")String username, @Param("id") Integer id);
	
}
