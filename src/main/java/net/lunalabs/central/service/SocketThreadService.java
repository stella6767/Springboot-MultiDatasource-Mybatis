package net.lunalabs.central.service;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lunalabs.central.domain.mysql.measuredata.MeasureData;
import net.lunalabs.central.domain.mysql.patient.Patient;
import net.lunalabs.central.mapper.mysql.MeasureDataMapper;
import net.lunalabs.central.mapper.mysql.PatientMapper;
import net.lunalabs.central.utills.MParsing;

@Slf4j
@RequiredArgsConstructor
@EnableAsync
@Service
public class SocketThreadService {

	
	private static final Logger logger = LoggerFactory.getLogger(SocketThreadService.class);

	
	@Qualifier("MysqlMeasureDataMapper")
	private final MeasureDataMapper measureDataMapper;

	@Qualifier("MysqlPatientMapper")
	private final PatientMapper patientMapper;

	StringBuffer sb = new StringBuffer();
	Charset charset = Charset.forName("UTF-8");

	
	@Async
	public void serverSocketThread(ServerSocketChannel serverSocketChannel, SocketChannel schn) throws IOException {

		//SocketChannel schn = null;


		logger.info("[ESMLC Listen[" + "] Socket Accept EsmlcIfWorkThread Start");
		
		log.info("read socket data");

		String result = "";

		byte[] readByteArr;

		// Client로부터 글자 받기
		ByteBuffer readBuf = ByteBuffer.allocate(10240);
		schn.read(readBuf); // 클라이언트로부터 데이터 읽기
		readBuf.flip();

		readByteArr = new byte[readBuf.remaining()];
		readBuf.get(readByteArr); // 데이터 읽기
		result = result + new String(readByteArr, Charset.forName("UTF-8")); // 어차피 여기서 계속 더하니까.

		HL7DataFirstParse(result, schn);
//         log.info("Received Data : " + charset.decode(readBuf).toString());
		log.info("Received Data : " + result);

		schn.close();

	}
	
	


	public void HL7DataFirstParse(String HL7Data, SocketChannel schn) {

		String[] splitEnterArray = HL7Data.split("[\\r\\n]+"); // 개행문자 기준으로 1차 파싱

		String[] headerArray = splitEnterArray[0].split("[|]");
		String trigger = headerArray[8];
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

		default:
			break;
		}

	}

	// 환자정보 요청 처리
	public void patientSearchProceed(String[] array, SocketChannel schn) throws IOException {

		log.info("환자정보 요청 HL7");

		String[] pidArray = array[1].split("[|]");
		String[] mshArray = array[0].split("[|]");

		String patiendId = pidArray[2];
		String patietName = pidArray[5];

		String trId = mshArray[9];
		
		log.info("trid: " + trId);

		ByteBuffer writeBuffer = ByteBuffer.allocate(10240);

		if (patietName.equals("")) {
			log.info("1.patiendId: " + patiendId + ",  patientName: " + patietName);
			// 여기서 다시 HL7 파싱을 해서, 전달

			sb.delete(0, sb.length()); // 초기화
			sb.append("MSH|^~\\&|BILABCENTRAL|NULL|RECEIVER|RECEIVER_FACILITY|" + MParsing.parseLocalDateTime()
			+ "||RPI^I03|" + trId + "\r\n" + "");
		
			// 요렇게 받으면 안 되고 배열로 받아야 됨.
			List<Patient> patients = patientMapper.findByContainId(Integer.parseInt(patiendId));

			addPatientsList(patients);

			log.info("응답파싱결과: " + sb.toString());

			writeBuffer = charset.encode(sb.toString());
		    schn.write(writeBuffer);
	
			logger.info("응답 완료");

		} else {
			log.info("2. patiendId: " + patiendId + ",  patientName: " + patietName);
			
			sb.delete(0, sb.length()); // 초기화
			Charset charset = Charset.forName("UTF-8");
			
			sb.append("MSH|^~\\&|BILABCENTRAL|NULL|RECEIVER|RECEIVER_FACILITY|" + MParsing.parseLocalDateTime()
			+ "||RPI^I03|" + trId + "\r\n" + "");

			// 요렇게 받으면 안 되고 배열로 받아야 됨.
			List<Patient> patients = patientMapper.findByContainName(patietName);
	
			addPatientsList(patients);

			log.info("응답파싱결과: " + sb.toString());

			writeBuffer = charset.encode(sb.toString());
		    schn.write(writeBuffer);
	
			logger.info("응답 완료");
			

		}

	}
	
	
	
	
	public void addPatientsList(List<Patient> patients) {
		
		
		for (int i = 0; i < patients.size(); i++) {

			sb.append("PID||" + patients.get(i).getPid() + "|" + patients.get(i).getAge() + "|"
					+ patients.get(i).getHeight() + "|" + patients.get(i).getFirstname() + "|"
					+ patients.get(i).getLastname() + "|" + patients.get(i).getWeight() + "|"
					+ patients.get(i).getGender() + "|"+ patients.get(i).getComment() +"||||||||||\r\n" + "");
		}
		
		
	}
	
	
	

	public void measureDataParsing(String[] array, SocketChannel schn) { // 5개의 parame이 오면 5번 insert

		ByteBuffer writeBuffer = ByteBuffer.allocate(10240);
		sb.delete(0, sb.length()); // 초기화

		String[] mshArray = array[0].split("[|]");
		String trId = mshArray[9];
		
		
		String[] obrArray = array[2].split("[|]");
		String sid = obrArray[2];

		String startTime = obrArray[7];
		String endTime = obrArray[8];

		log.info("session id: " + sid);
		log.info("startTime: " + startTime);
		log.info("endTime: " + endTime);

		for (int i = 3; i < array.length; i++) { // 3부터 OBX param 시작
			log.info("개행문자 기준으로 1차 파싱: " + array[i]);

			String[] splitSecondArray = array[i].split("[|]");

			MeasureData measureData = null;

			for (int j = 0; j < splitSecondArray.length; j++) {
				log.info("| 기준으로 2차 파싱: " + splitSecondArray[j]);

				measureData = MeasureData.builder().parame(splitSecondArray[3]).value(splitSecondArray[5])
//					.endTime(MParsing.stringToDate(startTime))
//					.startTime(MParsing.stringToDate(endTime))
						.endTime(endTime).startTime(startTime).sid(sid).build();

			}
			measureDataMapper.save(measureData);

		}
		

		
		sb.append("MSH|^~\\&|BILABCENTRAL|NULL|RECEIVER|RECEIVER_FACILITY|" + MParsing.parseLocalDateTime()
		+ "||RPI^I03|" + trId + "\r\n" + "");
		
		log.info("응답파싱결과: " + sb.toString());

		writeBuffer = charset.encode(sb.toString());
	    try {
			schn.write(writeBuffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
