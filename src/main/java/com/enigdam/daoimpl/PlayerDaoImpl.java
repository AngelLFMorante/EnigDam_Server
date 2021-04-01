package com.enigdam.daoimpl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.enigdam.dao.IPlayerDao;



@Repository
public class PlayerDaoImpl implements IPlayerDao {
	
	@Autowired
	JdbcTemplate template;

	
	public List<Map<String, Object>> getAllScores() {
		List<Map<String, Object>>allListScores =template.queryForList("Select player.username, time,score from game, player where game.id_user=player.id order by time desc;");
		return allListScores;
	}
	
	
	
	

}
