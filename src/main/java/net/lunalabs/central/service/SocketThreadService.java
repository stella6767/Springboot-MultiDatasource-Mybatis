package net.lunalabs.central.service;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lunalabs.central.config.MeasureDataSse;
import net.lunalabs.central.domain.measuredata.MeasureData;
import net.lunalabs.central.domain.mysql.MeasureDataJoinPatientBean;
import net.lunalabs.central.domain.mysql.sessiondata.SessionData;
import net.lunalabs.central.domain.patient.Patient;
import net.lunalabs.central.mapper.mysql.MeasureDataMapper;
import net.lunalabs.central.mapper.mysql.PatientMapper;
import net.lunalabs.central.mapper.mysql.SessionDataMapper;
import net.lunalabs.central.utills.MParsing;

@Slf4j
@RequiredArgsConstructor
@Service
public class SocketThreadService {

	private static final Logger logger = LoggerFactory.getLogger(SocketThreadService.class);

	@Qualifier("MysqlMeasureDataMapper")
	private final MeasureDataMapper measureDataMapper;
	
	@Qualifier("OracleMeasureDataMapper")
	private final net.lunalabs.central.mapper.oracle.MeasureDataMapper oracleMeasureDataMapper; //oracle package 안에 MAPPER, 이름이 같으니 주의
	
	//@Qualifier("MysqlPatientMapper")
	private final PatientMapper mysqlPatientMapper;
	
	private final net.lunalabs.central.mapper.oracle.PatientMapper oraclePatientMapper;


	private final SessionDataMapper sessionDataMapper;

	private final MeasureDataSse measureDataSse;

	private final ServersentService serversentService;

	StringBuffer sb = new StringBuffer();
	Charset charset = Charset.forName("UTF-8");
	// private final ExecutorService executor = Executors.newSingleThreadExecutor();

	ObjectMapper objectMapper = new ObjectMapper();



	
	
	
	/*

		MSH는 무조건 온다고 가정
	 * 
	 * CASE 1 : MSH 가 1개 있고 버퍼의 끝인 경우 메서드 호출
	 * 
	 * CASE 2 :MSH 가 1개 있고 버퍼의 끝이 아닌 경우 -MSH 단위로 메서드 호출하고 나머지 부분은
	 * 저장하여 버퍼에서 계속읽어서 append 함
	 * 
	 * CASE 3 :MSH 가 2개 이상 있고 버퍼의 끝인 경우 -MSH 만 버리고 메서드 호출 하고 다시MSH
	 * 를 읽어 동일하게 처리
	 * 
	 * CASE 4 :MSH 가 2개 이상 있고 버퍼의 끝이 아닌 경우 -MSH 단위로 메서드 호출하고 나머지 부분은
	 * 저장하여 버퍼에서 계속읽어서 append 함
	 */
	
	
	
	
    @Async //멀티접속을 위해서 비동기처리 해야됨..
    public void readSocketData(SocketChannel schn) throws IOException {

        log.info("read Charger Socket Client ");

        boolean isRunning = true; // 일단 추가, socketWork 중지할지 안 중지할지

        while (isRunning && schn.isConnected()) {

            try {
                long lThId = Thread.currentThread().getId();
                int byteCount = 0;
                byte[] readByteArr;

                // ByteBuffer readBuf = ByteBuffer.allocate(10); //버퍼 메모리 공간확보
                ByteBuffer readBuf = ByteBuffer.allocate(600);

                log.info("첫번째  while문");

                // 무한 루프
                String result = ""; // 요기서 초기화

                while (byteCount >= 0) {

                    try {

                        byteCount = schn.read(readBuf); // 소켓채널에서 한번에 초과되는 버퍼사이즈의 데이터가 들어오면..

                        log.info("[gwEmulThread #100] TID[" + "] byteCount :  " + byteCount);
                        // logger.debug("isRunning why: " + isRunning);
                    } catch (Exception e) {
                        // e.printStackTrace();
                        log.info("갑자기 클라이언트 소켓이 닫혔을 시");
                        schn.close();
                        isRunning = false;
                        break;
                    }

                    int i = 0;

                    // 버퍼에 값이 있다면 계속 버퍼에서 값을 읽어 result 를 완성한다.
                    while (byteCount > 0) {


                        readBuf.flip(); // 입력된 데이터를 읽기 위해 read-mode로 바꿈, positon이 데이터의 시작인 0으로 이동
                        readByteArr = new byte[readBuf.remaining()]; // 현재 위치에서 limit까지 읽어드릴 수 있는 데이터의 개수를 리턴
                        readBuf.get(readByteArr); // 데이터 읽기

                        result = result + new String(readByteArr, Charset.forName("UTF-8"));

                        try {
                            byteCount = schn.read(readBuf);
                            log.info("[gwEmulThread #210] TID[" + result + "] byteCount :  " + byteCount);
                        } catch (Exception e) {
                            e.printStackTrace();
                            // break;
                        }

                        boolean bEtxEnd = true; // 아래 while문을 실행할지 안할지

                        while (!result.equals("") && bEtxEnd) {
                            
                        	logger.info("#ETX#단위로 루프 돌기 전 result: " + result);
                        	
							Integer countMSH = StringUtils.countMatches(result, "MSH");

							int indMSH = (result.lastIndexOf("MSH"));

							logger.info("indEtx: " + indMSH + " result.length:  " + result.length() + "  countMSH: " + countMSH);

							
                        	Thread.sleep(300);
							
							
                        	//string1.split("(?=-)");
                        	
            				if ( (indMSH == 0 || indMSH== result.length()) && countMSH == 1) { 

								HL7DataFirstParse(result, schn);


								result = "";
								bEtxEnd = false;
								readBuf.clear();
							} else if (result.length() != indMSH && countMSH > 1) { // case5

								String[] resultArray = result.split("(?=MSH)");

								logger.info("case5 길이: " + resultArray.length);

									for (int a = 0; a < resultArray.length - 1; a++) {
										
										//logger.info("정규표현식활용: " + resultArray[i]);
										
										HL7DataFirstParse(resultArray[i], schn);
									}

									// 예를 들어 #ETX# #STX#{sdfsfdsdf data가 있을시 #STX#로 이어지는 데이터를 저장
									result = resultArray[resultArray.length - 1];
									// 다시 버퍼를 읽음
									readBuf.clear();
									break;
					
							} 
                        
                        }

                    } // #ETX# 단위로 루프
                } // byteCount > 0

                log.info("소켓 닫기");
                schn.close(); // 소켓 닫기

            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }
    
    
	
	

    
	//@Cacheable(value="kang")
	public void HL7DataFirstParse(String HL7Data, SocketChannel schn) {
		
		
		logger.info("문제원인..." + HL7Data);
		

		String[] splitEnterArray = HL7Data.split("[\\r\\n]+"); // 개행문자 기준으로 1차 파싱

		String[] headerArray = splitEnterArray[0].split("[|]");

		String trigger = HL7Data.contains("SS100") ? "SS100" : headerArray[8];

		// String trigger = headerArray[8];
		log.info("trigger: " + trigger);

		switch (trigger) {
		case "ORU^R01":

			measureDataParsing(splitEnterArray, schn);

			break;

		case "RQI^I02":
			try {
				patientSearchProceed(splitEnterArray, schn);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		case "SS100":

			try {
				sessionDataParsing(HL7Data);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		default:
			break;
		}

	}

	
	//@Cacheable(value="kang")
	public void measureDataParsing(String[] array, SocketChannel schn) { // 5개의 parame이 오면 5번 insert

		//List<MeasureDataJoinPatientBean> sses = new ArrayList<>();
		
		ByteBuffer writeBuffer = ByteBuffer.allocate(10240);
		sb.delete(0, sb.length()); // 초기화

		String[] mshArray = array[0].split("[|]");
		String trId = mshArray[9];
		String deviceId = mshArray[7];

		String[] pidArray = array[1].split("[|]");

		Integer pid = Integer.parseInt(pidArray[2]);
		String patientUserId = pidArray[5];

		String[] obrArray = array[2].split("[|]");
		String sid = obrArray[2];

		String startTime = obrArray[7];
		String endTime = obrArray[8];

		sb.append("MSH|^~\\&|BILABCENTRAL|NULL|RECEIVER|RECEIVER_FACILITY|" + MParsing.parseLocalDateTime()
				+ "||RPI^I03|" + trId + "\r\n" + "");

//		log.info("session id: " + sid);
//		log.info("startTime: " + startTime);
//		log.info("endTime: " + endTime);
//		log.info("patient Id: " + patientUserId);
//		log.info("device Id: " + deviceId);

		for (int i = 3; i < array.length; i++) { // 3부터 OBX param 시작
			//log.info("개행문자 기준으로 1차 파싱: " + array[i]);

			String[] splitSecondArray = array[i].split("[|]");

			MeasureData measureData = null;

			for (int j = 0; j < splitSecondArray.length; j++) {
				//log.info("| 기준으로 2차 파싱: " + splitSecondArray[j]);

				measureData = MeasureData.builder()
						.pid(pid)
						.patientUserId(patientUserId)
						.parame(splitSecondArray[3])
						.valueUnit(splitSecondArray[6])
						.value(splitSecondArray[5])
						.endTime(endTime)
						.startTime(startTime)
						.sid(sid)
						.build();

			}

			//현재단계에서는 측정데이터를 보낼때, oracle과 mariaDB 둘다 저장하도록=>2차에서는 프로시저로 대체
			measureDataMapper.save(measureData);
			oracleMeasureDataMapper.save(measureData); 
			
			Patient patient = mysqlPatientMapper.findById(pid);
			mysqlPatientMapper.updateLastSession(measureData.getSid(), patient.getPid());

			String seeMeasurePatientData = "";
			// this.objectMapper.setSerializationInclusion(Jsoninc);

			MeasureDataJoinPatientBean dataJoinPatientBean = MeasureDataJoinPatientBean.builder()
					// .deviceId() 굳이?
					.age(patient.getAge())
					.endTime(measureData.getEndTime())
					.startTime(measureData.getStartTime())
					.parame(measureData.getParame())
					.value(measureData.getValue())
					.patientUserId(measureData.getPatientUserId())
					.sid(measureData.getSid())
					.valueUnit(measureData.getValueUnit())
					.build();
			
			

			try {

				seeMeasurePatientData = objectMapper.writeValueAsString(dataJoinPatientBean);
				// //sent from server				
				//logger.info("??");
				serversentService.sendSseEventsToUI(seeMeasurePatientData, deviceId);

			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// measureDataSink

		}

			
		log.info("응답파싱결과: " + sb.toString());

//		writeBuffer = charset.encode(sb.toString());
//		try {
//			schn.write(writeBuffer);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
	       if (schn.isConnected()) {
	            log.info("Socket channel이 정상적으로 연결되었고 버퍼를 씁니다.");
	    		writeBuffer = charset.encode(sb.toString());
	            try {
					schn.write(writeBuffer);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


	        } else if (!schn.isConnected()) {
	            log.info("Socket channel이 연결이 끊어졌습니다.");
	        }

	}

	public void sessionDataParsing(String jsonData) throws JsonMappingException, JsonProcessingException {

		log.info("sessionData Parsing!!");

		SessionData sessionData = objectMapper.readValue(jsonData, SessionData.class);

		log.info("sessionData parsing 결과: " + sessionData);

		SessionData sessionEntity = sessionDataMapper.findById(sessionData.getSid());

		String startTime = sessionData.getStartTime();
		String endTime = sessionData.getEndTime();

		if (sessionEntity != null) {

			log.info("session: " + sessionData);

			log.info("sessionEndTime: " + endTime);
			log.info("sessionStartTime: " + startTime);

			if (StringUtils.isNoneBlank(startTime)) {

				sessionDataMapper.updateStartTime(sessionData);

			} else if (StringUtils.isNoneBlank(endTime)) {
				sessionDataMapper.updateEndTime(sessionData);
			}

		} else {
			sessionDataMapper.save(sessionData);
		}

	}

	// 환자정보 요청 처리
	public void patientSearchProceed(String[] array, SocketChannel schn) throws IOException {

		log.info("환자정보 요청 HL7");

		String[] pidArray = array[1].split("[|]");
		String[] mshArray = array[0].split("[|]");

		
//		Integer pid = Integer.parseInt(pidArray[2]);
		String patientUserId = pidArray[2];
		String patietName = pidArray[5];

		String trId = mshArray[9];

		log.info("trid 최신: " + trId);

		ByteBuffer writeBuffer = ByteBuffer.allocate(10240);

		if (StringUtils.isNotBlank(patientUserId)) { // patietName.equals("")
			log.info("1.patientUserId: " + patientUserId + ",  patientName: " + patietName);
			// 여기서 다시 HL7 파싱을 해서, 전달

			List<Patient> patients = mysqlPatientMapper.findByContainPatientUserId(patientUserId);
			List<Patient> respPatients = dbMergeProceed(patients, "patientUserId", patientUserId);	
			addPatientsListAndWriteOut(respPatients, schn, sb,  writeBuffer,trId );	

		} else if (StringUtils.isNotBlank(patietName)) {
			log.info("2. patientUserId: " + patientUserId + ",  patientName: " + patietName);

			// 요렇게 받으면 안 되고 배열로 받아야 됨.
			List<Patient> patients = mysqlPatientMapper.findByContainName(patietName);			
			List<Patient> respPatients = dbMergeProceed(patients, "name", patietName);
			addPatientsListAndWriteOut(respPatients, schn, sb,  writeBuffer, trId);


		} else { // keyword가 없으면 모든 환자데이터 응답해라.
			log.info("2. 전체환자목록");

			List<Patient> patients = mysqlPatientMapper.findAll();
			addPatientsListAndWriteOut(patients, schn, sb,  writeBuffer, trId);


		}
	}

	
	public List<Patient> dbMergeProceed(List<Patient> patients, String searchType, String searchWord) {
		
		
		boolean procedureClasfy  = searchType.equals("patientUserId") ? true : false; //여기서 프로시저 호출을 분류 
		
		if(patients.isEmpty()) {
			
			log.info("사내 DB에 환자 정보가 없다면.");
			
			//약간 애매 포함하는 거라서 먼저 병원 DB에서 찾아야 될 것 같기도 하고.
			
			HashMap<String,Object> map = new HashMap<String,Object>();

			map.put(searchType, searchWord);				//공백문제..일단은 컨셉 프로젝트에서 제외
			
			if(procedureClasfy) {
				oraclePatientMapper.findByContainPatientUserId(map);		//오라클 프로시저 호출	
			}else {
				oraclePatientMapper.findByContainName(map);		//오라클 프로시저 호출
			}
			
			//log.info(map.toString());
			List<Patient> bilabPatients = (List<Patient>)map.get("key"); //key가 out
			
			log.info("확인... " + bilabPatients);
			
			//merge into
			
			if(bilabPatients.size() != 0) {
				mysqlPatientMapper.insertOnDuplicateKeyUpdate(bilabPatients);	
			}		
			return bilabPatients;
			
		}else {
			
			return patients;
		}
		
	}
	
	
	
	
	
	
	
	public void addPatientsListAndWriteOut(List<Patient> patients, SocketChannel schn, StringBuffer sb, ByteBuffer writeBuffer, String trId) throws IOException {

		sb.delete(0, sb.length()); // 초기화

		sb.append("MSH|^~\\&|BILABCENTRAL|NULL|RECEIVER|RECEIVER_FACILITY|" + MParsing.parseLocalDateTime()
				+ "||RPI^I03|" + trId + "\r\n" + "");
		
		for (int i = 0; i < patients.size(); i++) {

			sb.append("PID||" + patients.get(i).getPatientUserId() + "|" + patients.get(i).getAge() + "|"
					+ patients.get(i).getHeight() + "|" + patients.get(i).getFirstname() + "|"
					+ patients.get(i).getLastname() + "|" + patients.get(i).getWeight() + "|"
					+ patients.get(i).getGender() + "|" + patients.get(i).getComment() + "|"
					+ patients.get(i).getLastSession() + "|"+ patients.get(i).getPid() +"||||||||\r\n" + "");
		}
		
		log.info("응답파싱결과: " + sb.toString());

//		writeBuffer = charset.encode(sb.toString());
//		schn.write(writeBuffer);
//
//		logger.info("응답 완료");

		
	       if (schn.isConnected()) {
	            log.info("Socket channel이 정상적으로 연결되었고 버퍼를 씁니다.");
	    		writeBuffer = charset.encode(sb.toString());
	            try {
					schn.write(writeBuffer);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


	        } else if (!schn.isConnected()) {
	            log.info("Socket channel이 연결이 끊어졌습니다.");
	        }
		
	}
	
	
	
	
	
	
	
	
	
	
//	@Async
//	public void FTPServiece() {
//		
//		boolean isFTPRunning = true;
//		
//		while(isFTPRunning) {
//			
//			
//			
//			
//		}
//		
//	}

}
