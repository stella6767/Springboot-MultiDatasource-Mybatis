package net.lunalabs.central;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.extern.slf4j.Slf4j;
import net.lunalabs.central.domain.mysql.Patient;
import net.lunalabs.central.mapper.mysql.PatientMapper;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class IntegTest {

	@Qualifier("MysqlPatientMapper")
	@Autowired
	private PatientMapper patientMapper;
	
	
	@Test
    public void contextLoads() {
        List<Patient> list = patientMapper.findAll();
        System.out.println("확인：" + list);
    }	
	
	
	@Test
	public void findByContainIdTest() {
		List<Patient> list = patientMapper.findByContainId(7);
		
		log.info("findbyContainedIdList check: " + list);
		
		
		
		for (int i = 0; i < list.size(); i++) { 
				log.info(list.get(i).toString());
															
		}	
	}
	
}
