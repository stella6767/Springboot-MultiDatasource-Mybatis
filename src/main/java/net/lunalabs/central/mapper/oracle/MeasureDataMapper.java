package net.lunalabs.central.mapper.oracle;

import java.util.List;

import net.lunalabs.central.config.db.OracleConnMapper;
import net.lunalabs.central.domain.oracle.MeasureData;



@OracleConnMapper("OracleMeasureDataMapper")
public interface MeasureDataMapper {
	
	
	public void save(MeasureData measureData); 
	public void deleteById(int id);
	public void update(MeasureData measureData);
	public List<MeasureData> findAll();
	public MeasureData findById(int id);	
	

}
