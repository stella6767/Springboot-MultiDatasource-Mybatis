package net.lunalabs.central.service.mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import net.lunalabs.central.domain.mysql.sessiondata.SessionData;
import net.lunalabs.central.mapper.mysql.MeasureDataMapper;
import net.lunalabs.central.mapper.mysql.SessionDataMapper;

@RequiredArgsConstructor
@Service
public class SessionDataService {

	
	private static final Logger log = LoggerFactory.getLogger(SessionDataService.class);

	
	private final SessionDataMapper sessionDataMapper;
	

	@Transactional(readOnly = true)
	public SessionData findById(String sid) {
		
		return sessionDataMapper.findById(sid);
	}
	
	@Transactional
	public void updateStartTime(SessionData sessionData) {
		
		sessionDataMapper.updateStartTime(sessionData);
	}
	
	@Transactional
	public void updateEndTime(SessionData sessionData) {
		
		sessionDataMapper.updateEndTime(sessionData);
	}
	
	@Transactional
	public void save(SessionData sessionData) {
		
		sessionDataMapper.save(sessionData);
	}
	
	
}
