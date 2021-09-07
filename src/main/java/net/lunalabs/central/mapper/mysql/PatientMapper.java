package net.lunalabs.central.mapper.mysql;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.lunalabs.central.config.db.MysqlConnMapper;
import net.lunalabs.central.domain.Patient;

@MysqlConnMapper("MysqlPatientMapper")
public interface PatientMapper {
	
	
	//public void save(Patient patient); 
	public void deleteById(int id);
	//public void update(Patient patient);
	public List<Patient> findAll();
	public Patient findById(int id);	
	public Patient findByPatientUserId(String patientUserId);	
	
	public void updateLastSession(@Param("sid") String sid, @Param("pid") int pid);
	
	public List<Patient> findByContainId(int id);
	public List<Patient> findByContainPatientUserId(String patientUserId);
	public List<Patient> findByContainName(String name);

	

}
