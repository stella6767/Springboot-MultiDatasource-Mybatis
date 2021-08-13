package net.lunalabs.central.mapper.oracle;

import java.util.List;

import net.lunalabs.central.config.db.OracleConnMapper;
import net.lunalabs.central.domain.oracle.Panama;

@OracleConnMapper
public interface PanamaMapper {

	public void save(Panama panama); 
	public void deleteById(int id);
	public void update(Panama panama);
	public List<Panama> findAll();
	public Panama findById(int id);	
	
	
}
