package net.lunalabs.central.mapper.mysql;

import java.util.List;

import net.lunalabs.central.config.db.MysqlConnMapper;
import net.lunalabs.central.domain.mysql.MeasureData;

@MysqlConnMapper("MysqlMeasureDataMapper")
public interface MeasureDataMapper {
	
	
	//public void save(MeasureData measureData); 
	public void deleteById(int id);
	//public void update(MeasureData measureData);
	public List<MeasureData> findAll();
	public MeasureData findById(int id);	
	
	
	public void check();
	

}
