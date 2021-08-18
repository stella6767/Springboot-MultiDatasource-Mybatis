package net.lunalabs.central.service.oracle;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.lunalabs.central.mapper.oracle.MeasureDataMapper;

@RequiredArgsConstructor
@Service("OracleMeasureDataService")
public class MeasureDataService {

	
	@Qualifier("OracleMeasureDataMapper")
	private final MeasureDataMapper measureDataMapper; //oracle package 안에 MAPPER, 이름이 같으니 주의
	
	
	
	
	
}
