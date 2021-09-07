package net.lunalabs.central.mapper.oracle;

import java.util.List;

import net.lunalabs.central.config.db.OracleConnMapper;
import net.lunalabs.central.domain.oracle.measuredata.MeasureData;



@OracleConnMapper("OracleMeasureDataMapper")
public interface MeasureDataMapper {
	
	
	public void save(net.lunalabs.central.domain.mysql.measuredata.MeasureData measureData); 
	public void deleteById(int id);
	public void update(MeasureData measureData);
	public List<MeasureData> findAll();
	public MeasureData findById(int id);	
	

}
