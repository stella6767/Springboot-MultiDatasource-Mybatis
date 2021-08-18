package net.lunalabs.central.service.mysql;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.lunalabs.central.mapper.mysql.MeasureDataMapper;
import net.lunalabs.central.mapper.mysql.ProductMapper;

@Primary 
@RequiredArgsConstructor
@Service("MysqlMeasureDataService")
public class MeasureDataService {
	

	@Qualifier("MysqlMeasureDataMapper")
	private final MeasureDataMapper mapper;
	
	
	@Qualifier("OracleMeasureDataMapper")
	private final net.lunalabs.central.mapper.oracle.MeasureDataMapper mapper2;
	
	
	public void check() {
		
		mapper.check();
	
	}

	
}
