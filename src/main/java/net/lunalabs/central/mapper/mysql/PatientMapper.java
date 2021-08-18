package net.lunalabs.central.mapper.mysql;

import java.util.List;

import net.lunalabs.central.config.db.MysqlConnMapper;
import net.lunalabs.central.domain.mysql.Patient;

@MysqlConnMapper("MysqlPatientMapper")
public interface PatientMapper {
	
	
	//public void save(Patient patient); 
	public void deleteById(int id);
	//public void update(Patient patient);
	public List<Patient> findAll();
	public Patient findById(int id);	
	

}
