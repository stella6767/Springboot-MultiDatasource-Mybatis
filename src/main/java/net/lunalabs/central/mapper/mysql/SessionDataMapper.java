package net.lunalabs.central.mapper.mysql;

import java.util.List;

import net.lunalabs.central.config.db.MysqlConnMapper;
import net.lunalabs.central.domain.mysql.sessiondata.SessionData;

@MysqlConnMapper
public interface SessionDataMapper {
	
	public void save(SessionData sessionData); 
	public void deleteById(String id);
	public void updateStartTime(SessionData sessionData);
	public void updateEndTime(SessionData sessionData);
	public List<SessionData> findAll();
	public SessionData findById(String id);	
	

}
