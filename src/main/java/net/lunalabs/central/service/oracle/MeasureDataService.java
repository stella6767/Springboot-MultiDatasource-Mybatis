package net.lunalabs.central.service.oracle;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;
import net.lunalabs.central.mapper.oracle.MeasureDataMapper;

@RequiredArgsConstructor
@Service("OracleMeasureDataService")
public class MeasureDataService {

	
	
	private static final Logger log = LoggerFactory.getLogger(MeasureDataService.class);

	
	@Qualifier("OracleMeasureDataMapper")
	private final MeasureDataMapper measureDataMapper; //oracle package 안에 MAPPER, 이름이 같으니 주의
	
	
	
	@Scheduled(fixedDelay = 1000 * 60 * 30)
	public void deleteMeasureDataByOneHour() { 
		
		log.info("30분마다 주기적으로 오라클 측정 DB 칼럼 삭제");
		
		measureDataMapper.deleteAll();
		
	}
	
	
	
	
}
