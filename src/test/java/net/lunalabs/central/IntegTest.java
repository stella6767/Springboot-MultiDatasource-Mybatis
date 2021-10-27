//package net.lunalabs.central;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.transaction.annotation.Transactional;
//
//import lombok.extern.slf4j.Slf4j;
//import net.lunalabs.central.config.GlobalVar;
//import net.lunalabs.central.domain.measuredata.MeasureData;
//import net.lunalabs.central.domain.patient.Patient;
//import net.lunalabs.central.mapper.mysql.MeasureDataMapper;
//import net.lunalabs.central.mapper.mysql.PatientMapper;
//
//@Slf4j
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//public class IntegTest {
//
//	@Qualifier("MysqlPatientMapper")
//	@Autowired
//	private PatientMapper patientMapper;
//	
//	@Qualifier("MysqlMeasureDataMapper")
//	@Autowired
//	private MeasureDataMapper measureDataMapper;
//
//	@Autowired
//	private net.lunalabs.central.mapper.oracle.PatientMapper oraclePatientMapper;
//	
//	@Autowired
//	private GlobalVar globalVar;
//	
//	
//	
//	@Transactional //실제 DB에 값이 들어가지 않도록.
//	@Test
//	public void insertOnDuplicateKeyUpdateTest() {
//		
//		HashMap<String,Object> map = new HashMap<String,Object>();
//		
//		map.put("patientUserId", "110");				//공백문제..일단은 컨셉 프로젝트에서 제외		
//		oraclePatientMapper.findByContainPatientUserId(map);		//오라클 프로시저 호출
//		//log.info(map.toString());
//		List<Patient> bilabPatients = (List<Patient>)map.get("key"); //key가 out
//		
//		log.info("확인... " + bilabPatients);
//		
//		patientMapper.insertOnDuplicateKeyUpdate(bilabPatients);
//		
//		//log.info("정상적으로 업데이트 되었는지.. " + updatePatients);
//		
//	     List<Patient> list = patientMapper.findAll();
//	     System.out.println("확인：" + list.size() + "  :" + list);
//
//	}
//	
//	
//	
//	
//	@Test
//    public void contextLoads() {
//        List<Patient> list = patientMapper.findAll();
//	     System.out.println("확인：" + list.size() + "  :" + list);
//    }	
//	
//	
//	@Test
//	public void findByContainIdTest() {
//		List<Patient> list = patientMapper.findByContainId(7);
//		
//		log.info("findbyContainedIdList check: " + list);
//				
//		for (int i = 0; i < list.size(); i++) { 
//				log.info(list.get(i).toString());
//															
//		}		
//	}
//	
//
//	
//	
//	
//	@Test
//	public void findParamTest() {
//		List<MeasureData> latestParames = measureDataMapper.findLatestParame();
//		
//		log.info("lastestParames: " + latestParames);
//		
//		List<MeasureData> rvsParames = measureDataMapper.findRvsAll();
//		
//		log.info("rvsParames: " + rvsParames);
//	
//	}
//	
//	
//	
//	@Transactional
//	@Test
//	public void bulkInsertTest() {
//		
//
//		List<MeasureData> measureDatas = new ArrayList<>();
//				
//		//뿌리기 받을사람 정보 추가
//		for(int i=0; i<100; i++) {
//
//			MeasureData measureData = MeasureData.builder()
//					.sid("patient100_20211021_171700")
//					.pid(100)
//					.patientUserId("patient100")
//					.parame("rvs")
//					.valueUnit("NA")
//					.value("-5.30485E+6^-2.40058E+7")
//					.startTime(getNowTime(1))
//					.endTime(getNowTime(2))
//					.build();
//			
//			measureDatas.add(measureData);
//		}
//		
//		//measureDatas.clear();
//		
//		if(!measureDatas.isEmpty()) {
//			int result = measureDataMapper.insertBatch(measureDatas);
//			
//			log.info("result: " + result);
//		}
//		
//
//		
//	}
//	
//	
//	public String getNowTime(Integer i){ //1이면 startTime, 아니면 endTime
//	    long now = System.currentTimeMillis();
//	    Date mDate = new Date(now);
//	    
//	    Calendar cal = Calendar.getInstance();
//        cal.setTime(mDate);
//        
//        cal.add(Calendar.MILLISECOND, 20);
//
//	    SimpleDateFormat sdfNow = new SimpleDateFormat("yy.MM.dd-HH:mm:ss.SSS");
//	    
//	    String Time;
//	    
//	    if(i==1) {
//	    	
//		    Time = sdfNow.format(mDate);
//
//	    }else {
//	    	 Time = sdfNow.format(cal.getTime());
//	    }
//	    
//	    String nowTime =Time;
//	    return nowTime;
//
//	    }
//	
//	
//}
