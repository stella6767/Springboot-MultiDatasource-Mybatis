package net.lunalabs.central.mapper.mysql;

import java.util.List;

import net.lunalabs.central.config.db.MysqlConnMapper;
import net.lunalabs.central.domain.mysql.SessionData;

@MysqlConnMapper
public interface SessionDataMapper {
	
	//public void save(SessionData sessionData); 
	public void deleteById(int id);
	//public void update(SessionData sessionData);
	public List<SessionData> findAll();
	public SessionData findById(int id);	
	

}
