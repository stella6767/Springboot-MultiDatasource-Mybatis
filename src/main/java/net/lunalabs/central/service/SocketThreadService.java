package net.lunalabs.central.service;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lunalabs.central.config.MeasureDataSse;
import net.lunalabs.central.domain.Patient;
import net.lunalabs.central.domain.mysql.MeasureDataJoinPatientBean;
import net.lunalabs.central.domain.mysql.measuredata.MeasureData;
import net.lunalabs.central.domain.mysql.sessiondata.SessionData;
import net.lunalabs.central.mapper.mysql.MeasureDataMapper;
import net.lunalabs.central.mapper.mysql.PatientMapper;
import net.lunalabs.central.mapper.mysql.SessionDataMapper;
import net.lunalabs.central.utills.MParsing;

@Slf4j
@RequiredArgsConstructor
@EnableAsync
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

	@Async
	public void serverSocketThread(ServerSocketChannel serverSocketChannel, SocketChannel schn) throws IOException {

		// SocketChannel schn = null;

		logger.info("[ESMLC Listen[" + "] Socket Accept EsmlcIfWorkThread Start");

		log.info("read socket data");

		String result = "";

		byte[] readByteArr;

		// Client로부터 글자 받기
		ByteBuffer readBuf = ByteBuffer.allocate(10240);//read into buffer. 일단은 버퍼 초과 신경쓰지 않고
		schn.read(readBuf); // 클라이언트로부터 데이터 읽기
		readBuf.flip();

		readByteArr = new byte[readBuf.remaining()];
		readBuf.get(readByteArr); // 데이터 읽기
		result = result + new String(readByteArr, Charset.forName("UTF-8")); // 어차피 여기서 계속 더하니까.

		
//		int bytesRead = schn.read(readBuf); // 
//		while (bytesRead != -1) {// 만약 소켓채널을 통해 buffer에 데이터를 받아왔으면
//			readBuf.flip(); // make buffer ready for read
//			while (readBuf.hasRemaining()) {
////				System.out.print((char) readBuf.get()); // read 1 byte at a time
//				result = result + String.valueOf(((char) readBuf.get())); //why dont work!!!!
//			}
//
//			readBuf.clear(); //make buffer ready for writing
//			bytesRead = schn.read(readBuf);
//		}
		
		
		log.info("------------------------------처음 파싱되서 도착한 데이터---------------------------------------");
		log.info(result);
		
		HL7DataFirstParse(result, schn);
//         log.info("Received Data : " + charset.decode(readBuf).toString());
		log.info("Received Data : " + result);

		schn.close();

	}

	public void HL7DataFirstParse(String HL7Data, SocketChannel schn) {

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

	public void measureDataParsing(String[] array, SocketChannel schn) { // 5개의 parame이 오면 5번 insert

		List<MeasureDataJoinPatientBean> sses = new ArrayList<>();
		
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

		log.info("session id: " + sid);
		log.info("startTime: " + startTime);
		log.info("endTime: " + endTime);
		log.info("patient Id: " + patientUserId);
		log.info("device Id: " + deviceId);

		for (int i = 3; i < array.length; i++) { // 3부터 OBX param 시작
			log.info("개행문자 기준으로 1차 파싱: " + array[i]);

			String[] splitSecondArray = array[i].split("[|]");

			MeasureData measureData = null;

			for (int j = 0; j < splitSecondArray.length; j++) {
				log.info("| 기준으로 2차 파싱: " + splitSecondArray[j]);

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
			
			
			sses.add(dataJoinPatientBean);

//			try {
//				// seeMeasureData = objectMapper.writeValueAsString(measureData);
//
//				seeMeasurePatientData = objectMapper.writeValueAsString(dataJoinPatientBean);
//
//				// EmitResult result = measureDataSink.sink.tryEmitNext(seeMeasurePatientData);
//				// //sent from server
//				// measureDataSse.sseEmitter.send(SseEmitter.event().reconnectTime(500).data(seeMeasurePatientData));
//				
//				serversentService.sendSseEventsToUI(seeMeasurePatientData, deviceId);
//
//				// logger.info("sse 로 보낼 결과 " + result.toString());
//			} catch (JsonProcessingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			// measureDataSink

		}

		
		try {
			// seeMeasureData = objectMapper.writeValueAsString(measureData);

			String seeMeasurePatientData = objectMapper.writeValueAsString(sses);

			// EmitResult result = measureDataSink.sink.tryEmitNext(seeMeasurePatientData);
			// //sent from server
			// measureDataSse.sseEmitter.send(SseEmitter.event().reconnectTime(500).data(seeMeasurePatientData));
			
			serversentService.sendSseEventsToUI(seeMeasurePatientData, deviceId);

			// logger.info("sse 로 보낼 결과 " + result.toString());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.info("응답파싱결과: " + sb.toString());

		writeBuffer = charset.encode(sb.toString());
		try {
			schn.write(writeBuffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

		log.info("trid: " + trId);

		ByteBuffer writeBuffer = ByteBuffer.allocate(10240);

		if (StringUtils.isNotBlank(patientUserId)) { // patietName.equals("")
			log.info("1.patientUserId: " + patientUserId + ",  patientName: " + patietName);
			// 여기서 다시 HL7 파싱을 해서, 전달

			sb.delete(0, sb.length()); // 초기화
			sb.append("MSH|^~\\&|BILABCENTRAL|NULL|RECEIVER|RECEIVER_FACILITY|" + MParsing.parseLocalDateTime()
					+ "||RPI^I03|" + trId + "\r\n" + "");

			List<Patient> patients = mysqlPatientMapper.findByContainPatientUserId(patientUserId);

			//만약 patientUserId가 없다면, oracle DB에서 찾는 로직 추가	
			if(patients.isEmpty()) {
				
				log.info("여길 타는지");
				
				List<Patient> bilabPatients = oraclePatientMapper.findByContainPatientUserId(patientUserId);
				
				log.info("확인... " + bilabPatients);
				
			}
			
			
			addPatientsList(patients);

			log.info("응답파싱결과: " + sb.toString());

			writeBuffer = charset.encode(sb.toString());
			schn.write(writeBuffer);

			logger.info("응답 완료");

		} else if (StringUtils.isNotBlank(patietName)) {
			log.info("2. patientUserId: " + patientUserId + ",  patientName: " + patietName);

			sb.delete(0, sb.length()); // 초기화

			sb.append("MSH|^~\\&|BILABCENTRAL|NULL|RECEIVER|RECEIVER_FACILITY|" + MParsing.parseLocalDateTime()
					+ "||RPI^I03|" + trId + "\r\n" + "");

			// 요렇게 받으면 안 되고 배열로 받아야 됨.
			List<Patient> patients = mysqlPatientMapper.findByContainName(patietName);

			addPatientsList(patients);

			log.info("응답파싱결과: " + sb.toString());

			writeBuffer = charset.encode(sb.toString());
			schn.write(writeBuffer);

			logger.info("응답 완료");

		} else { // keyword가 없으면 모든 환자데이터 응답해라.

			sb.delete(0, sb.length()); // 초기화

			sb.append("MSH|^~\\&|BILABCENTRAL|NULL|RECEIVER|RECEIVER_FACILITY|" + MParsing.parseLocalDateTime()
					+ "||RPI^I03|" + trId + "\r\n" + "");

			List<Patient> patients = mysqlPatientMapper.findAll();

			addPatientsList(patients);

			log.info("응답파싱결과: " + sb.toString());

			writeBuffer = charset.encode(sb.toString());
			schn.write(writeBuffer);

			logger.info("응답 완료");

		}

	}

	public void addPatientsList(List<Patient> patients) {

		for (int i = 0; i < patients.size(); i++) {

			sb.append("PID||" + patients.get(i).getPatientUserId() + "|" + patients.get(i).getAge() + "|"
					+ patients.get(i).getHeight() + "|" + patients.get(i).getFirstname() + "|"
					+ patients.get(i).getLastname() + "|" + patients.get(i).getWeight() + "|"
					+ patients.get(i).getGender() + "|" + patients.get(i).getComment() + "|"
					+ patients.get(i).getLastSession() + "|"+ patients.get(i).getPid() +"||||||||\r\n" + "");
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
