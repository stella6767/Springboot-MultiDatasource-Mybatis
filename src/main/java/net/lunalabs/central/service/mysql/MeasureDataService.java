package net.lunalabs.central.service.mysql;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import net.lunalabs.central.domain.mysql.measuredata.MeasureData;
import net.lunalabs.central.mapper.mysql.MeasureDataMapper;

@Primary 
@RequiredArgsConstructor
@Service("MysqlMeasureDataService")
public class MeasureDataService {
	

	@Qualifier("MysqlMeasureDataMapper")
	private final MeasureDataMapper mapper;
	

	
	public void check() {
		
		mapper.check();
	
	}
	
	
	@Transactional(readOnly = true)
	public List<MeasureData> findLatestParam() {
		
		List<MeasureData> latestParames = mapper.findLatestParame();
		List<MeasureData> rvsParames = mapper.findRvsAll();
	
		
		return mapper.findAll();		
	}
	
	
	
	@Transactional(readOnly = true)
	public List<MeasureData> findAll() {
		
		return mapper.findAll();
	}

	
	@Transactional
	public void save(MeasureData measureData){
		
		mapper.save(measureData);
		
	}
	
	
	
	
}
