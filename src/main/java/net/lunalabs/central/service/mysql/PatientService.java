package net.lunalabs.central.service.mysql;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import net.lunalabs.central.domain.mysql.Patient;
import net.lunalabs.central.mapper.mysql.PatientMapper;

@RequiredArgsConstructor
@Service("MysqlPatientService")
public class PatientService {

	
	@Qualifier("MysqlPatientMapper")
	private final PatientMapper patientMapper;
	
	
	@Qualifier("OraclePatientMapper")
	private final net.lunalabs.central.mapper.oracle.PatientMapper patientMapper2;
	
	
	@Transactional(readOnly = true)
	public List<Patient> findAll(){	
		List<Patient> patients = patientMapper.findAll();	
		return patients;
	}
	
	
	@Transactional(readOnly = true)
	public List<net.lunalabs.central.domain.oracle.Patient> findAll2(){	
		List<net.lunalabs.central.domain.oracle.Patient> patients = patientMapper2.findAll();	
		return patients;
	}
	
	
	
}
