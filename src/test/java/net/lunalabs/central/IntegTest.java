package net.lunalabs.central;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import net.lunalabs.central.domain.measuredata.MeasureData;
import net.lunalabs.central.domain.patient.Patient;
import net.lunalabs.central.mapper.mysql.MeasureDataMapper;
import net.lunalabs.central.mapper.mysql.PatientMapper;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class IntegTest {

	@Qualifier("MysqlPatientMapper")
	@Autowired
	private PatientMapper patientMapper;
	
	@Qualifier("MysqlMeasureDataMapper")
	@Autowired
	private MeasureDataMapper mapper;

	@Autowired
	private net.lunalabs.central.mapper.oracle.PatientMapper oraclePatientMapper;
	
	
	
	
	@Transactional //실제 DB에 값이 들어가지 않도록.
	@Test
	public void insertOnDuplicateKeyUpdateTest() {
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		map.put("patientUserId", "110");				//공백문제..일단은 컨셉 프로젝트에서 제외		
		oraclePatientMapper.findByContainPatientUserId(map);		//오라클 프로시저 호출
		//log.info(map.toString());
		List<Patient> bilabPatients = (List<Patient>)map.get("key"); //key가 out
		
		log.info("확인... " + bilabPatients);
		
		patientMapper.insertOnDuplicateKeyUpdate(bilabPatients);
		
		//log.info("정상적으로 업데이트 되었는지.. " + updatePatients);
		
	     List<Patient> list = patientMapper.findAll();
	     System.out.println("확인：" + list.size() + "  :" + list);

	}
	
	
	
	
	@Test
    public void contextLoads() {
        List<Patient> list = patientMapper.findAll();
	     System.out.println("확인：" + list.size() + "  :" + list);
    }	
	
	
	@Test
	public void findByContainIdTest() {
		List<Patient> list = patientMapper.findByContainId(7);
		
		log.info("findbyContainedIdList check: " + list);
				
		for (int i = 0; i < list.size(); i++) { 
				log.info(list.get(i).toString());
															
		}		
	}
	

	
	
	
	@Test
	public void findParamTest() {
		List<MeasureData> latestParames = mapper.findLatestParame();
		
		log.info("lastestParames: " + latestParames);
		
		List<MeasureData> rvsParames = mapper.findRvsAll();
		
		log.info("rvsParames: " + rvsParames);
	
	}
	
}
