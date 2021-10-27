package net.lunalabs.central.service.mysql;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import net.lunalabs.central.config.GlobalVar;
import net.lunalabs.central.domain.measuredata.MeasureData;
import net.lunalabs.central.mapper.mysql.MeasureDataMapper;

@Primary 
@RequiredArgsConstructor
@Service("MysqlMeasureDataService")
public class MeasureDataService {
	
	private static final Logger log = LoggerFactory.getLogger(MeasureDataService.class);

	//private static final Long insertBatchTime = (long) (1000 * 60);
	
	
	private final GlobalVar globalVar;

	@Qualifier("MysqlMeasureDataMapper")
	private final MeasureDataMapper mysqlMeasureDataMapper;
	
	@Qualifier("OracleMeasureDataMapper")
	private final net.lunalabs.central.mapper.oracle.MeasureDataMapper oracleMeasureDataMapper; //oracle package 안에 MAPPER, 이름이 같으니 주의
	
	
	public void check() {
		
		mysqlMeasureDataMapper.check();
	
	}
	
	
	@Scheduled(initialDelayString = "${initialDelay}", fixedRate = 1000 * 60)
	@Async
	@Transactional
	public void insertBatch() {
		
		log.info("1분마다 측정데이터 bulk insert");

		
		if(!globalVar.batchMeasureDatas.isEmpty()) {
			int result = mysqlMeasureDataMapper.insertBatch(globalVar.batchMeasureDatas);
			
			int result2 = oracleMeasureDataMapper.insertBatch(globalVar.batchMeasureDatas);
			
			log.info("bulkInsert result: " + result +"  " + result2);
			
			globalVar.batchMeasureDatas.clear();

		}
				
		//oracleMeasureDataMapper.		
	}
	
	
	@Transactional(readOnly = true)
	public List<MeasureData> findLatestParam() {
		
		List<MeasureData> latestParames = mysqlMeasureDataMapper.findLatestParame();
		List<MeasureData> rvsParames = mysqlMeasureDataMapper.findRvsAll();
	
		
		return mysqlMeasureDataMapper.findAll();		
	}
	
	
	
	@Transactional(readOnly = true)
	public List<MeasureData> findAll() {
		
		return mysqlMeasureDataMapper.findAll();
	}

	
	@Transactional
	public void save(MeasureData measureData){
		
		mysqlMeasureDataMapper.save(measureData);
		
	}
	
	
	
	
}
