package net.lunalabs.central.service.oracle;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;
import net.lunalabs.central.domain.measuredata.MeasureData;
import net.lunalabs.central.mapper.oracle.MeasureDataMapper;

@RequiredArgsConstructor
@Service("OracleMeasureDataService")
public class MeasureDataService {

	
	
	private static final Logger log = LoggerFactory.getLogger(MeasureDataService.class);

	
	@Qualifier("OracleMeasureDataMapper")
	private final MeasureDataMapper measureDataMapper; //oracle package 안에 MAPPER, 이름이 같으니 주의
	
	@Qualifier("MysqlMeasureDataMapper")
	private final net.lunalabs.central.mapper.mysql.MeasureDataMapper mysqlMeasureDataMapper;
	
	
	
	
	@Scheduled(initialDelayString = "${initialDelay}", fixedDelay = 1000 * 60 * 10)
	@Transactional
	public void deleteMeasureDataByOneHour() { 
		
		log.info("10분마다 주기적으로 오라클 측정 DB 칼럼 삭제");
		
		measureDataMapper.deleteAll();
		
	}
	
	
	//@Scheduled(initialDelay = 1000 * 60 * 30, fixedRate = 1000 * 60 * 30)
	//@Async
	@Transactional
	public void insertEMRBatch() {
		
//		log.info("30분 간격으로 EMR 측정데이터 bulk insert");
//
//		
//		List<MeasureData> mysqlMeasureDatas = mysqlMeasureDataMapper.findAll();
//		
//		
//		if(!mysqlMeasureDatas.isEmpty()) {
//			int result = mysqlMeasureDataMapper.insertBatch(globalVar.batchMeasureDatas);
//			
//			log.info("bulkInsert result: " + result);
//			
//			globalVar.batchMeasureDatas.clear();
//
//		}
	}
	
	
	
}
