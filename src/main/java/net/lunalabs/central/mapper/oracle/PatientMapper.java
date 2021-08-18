package net.lunalabs.central.mapper.oracle;

import java.util.List;

import net.lunalabs.central.config.db.OracleConnMapper;
import net.lunalabs.central.domain.oracle.Patient2;

@OracleConnMapper("OraclePatientMapper")
public interface PatientMapper {
	
	
	public void save(Patient2 patient); 
	public void deleteById(int id);
	public void update(Patient2 patient);
	public List<Patient2> findAll();
	public Patient2 findById(int id);	
	

}
