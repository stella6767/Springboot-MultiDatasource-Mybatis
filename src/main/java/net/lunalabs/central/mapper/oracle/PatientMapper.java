package net.lunalabs.central.mapper.oracle;

import java.util.List;
import java.util.Map;

import net.lunalabs.central.config.db.OracleConnMapper;
import net.lunalabs.central.domain.patient.Patient;

@OracleConnMapper("OraclePatientMapper")
public interface PatientMapper {
	
	
	public void save(Patient patient); 
	public void deleteById(int id);
	public void update(Patient patient);
	public List<Patient> findAll();
	public Patient findById(int id);	
	
	public List<Patient> findByContainPatientUserId(Map<String,Object> map); //프로시저로 대체
//	public List<Patient> findByContainName(String name);

}
