package net.lunalabs.central.service.mysql;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import net.lunalabs.central.domain.patient.Patient;
import net.lunalabs.central.mapper.mysql.PatientMapper;

@RequiredArgsConstructor
@Service("MysqlPatientService")
public class PatientService {

	
	
	private static final Logger log = LoggerFactory.getLogger(PatientService.class);

	
	
	@Qualifier("MysqlPatientMapper")
	private final PatientMapper patientMapper;
	
	
	@Qualifier("OraclePatientMapper")
	private final net.lunalabs.central.mapper.oracle.PatientMapper oraclePatientMapper;
	
	
	@Transactional(readOnly = true)
	public List<Patient> findAll(){	
		List<Patient> patients = patientMapper.findAll();	
		return patients;
	}
	
//	@Transactional(readOnly = true)
//	public List<Patient> findByName(String name){	
//		List<Patient> patients = patientMapper.findByContainName(name);	
//		return patients;
//	}
//	
//	
//	@Transactional(readOnly = true)
//	public List<Patient> findByPatinetUserId(String patientUserId){	
//		List<Patient> patients = patientMapper.findByContainPatientUserId(patientUserId);	
//		return patients;
//	}
	
	
	@Transactional
	public void insertOnDuplicateKeyUpdate(List<Patient> patients) {
		
		patientMapper.insertOnDuplicateKeyUpdate(patients);
		
	}
	
	@Cacheable(value = "kang")
	@Transactional
	public void updateLastSession(String sid, Integer pid) {
		
		log.info("캐싱 테스트 " + sid + "  " + pid);
		
		patientMapper.updateLastSession(sid, pid);
		
	}
	
	
	@Transactional(readOnly = true)
	public List<Patient> searchByIdOrName(String searchKeyword, String searchWord){	
		
		List<Patient> patients = new ArrayList<>();
		
		if(searchKeyword.equals("patientUserId")) {
			
			patients = patientMapper.findByContainPatientUserId(searchWord);	

		}else {
			patients = patientMapper.findByContainName(searchWord);	

		}	
		return patients;
	}
	
	
	@Transactional(readOnly = true)
	public List<Patient> findByContainPatientUserId(String patientUserId){	
		

		return patientMapper.findByContainPatientUserId(patientUserId);
	}
	
	@Transactional(readOnly = true)
	public List<Patient> findByContainName(String patietName){	
		

		return patientMapper.findByContainName(patietName);
	}
	
	
	
	@Cacheable(value = "kang")
	@Transactional(readOnly = true)
	public Patient findById(Integer id){	
		
		log.info("캐싱 잘 되나 테스트 " + id);
		
		Patient patient = patientMapper.findById(id);	
		return patient;
	}
	
	
	@Transactional(readOnly = true)
	public List<Patient> findAll2(){	
		List<Patient> patients = oraclePatientMapper.findAll();	
		return patients;
	}
	
	
	
}
