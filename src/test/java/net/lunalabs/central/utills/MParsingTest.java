package net.lunalabs.central.utills;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import net.lunalabs.central.domain.mysql.MeasureData;



@Slf4j
public class MParsingTest {

	
	@Test
	public void parseBaseOnEnter() {//일단 5개가 무조건 온다는 가정하에 짜놓자...
		
		String rawData = "MSH|^~\\&|BILABGW|NULL|RECEIVER|RECEIVER_FACILITY |2021-08-24 09:09:42||ORU^R01|47c65ee7-4d08-462e-bce0-2efeae4d96ba|P|2.8\r\n"
				+ "PID||1|Patient_NHS_ID|NULL||NULL|NULL||||||||||||\r\n"
				+ "OBR||1_20210716_030903|NULL|NULL|||21.07.16-03:09:06.766|21.07.16-03:09:06.868|||||||||||||||||\r\n"
				+ "OBX|1|NM|mv||8.596203821656049|L/min||||||||2021-08-24 09:09:42|\r\n"
				+ "OBX|2|NM|rr||19|bpm||||||||2021-08-24 09:09:42|\r\n"
				+ "OBX|3|NA|rvs||83766671.93251821^83703921.63870761^83779506.46560705^83684245.16751479^83630464.05741148^83665499.37236984^83568326.53056866^83588762.55610713|mL||||||||2021-08-24 09:09:42|";
				
		
		String rawData2 = "";		
		
		String[] splitEnterArray = rawData.split("[\\r\\n]+"); //1차 파싱
		
	
		String[] headerArray = splitEnterArray[0].split("[|]");
		String trigger = headerArray[8];
		log.info("trigger: " + trigger);
		
		
		
		switch (trigger) {
		case "ORU^R01":
			
			measureDataParsing(splitEnterArray);
			
			break;
			
		case "RQI^I02":
			
			
			break;	

		default:
			break;
		}
		
		
	}
	
	
	
	public void measureDataParsing(String[] array) {  //5개의 parame이 오면 5번 insert...
		
				
		String[] obrArray = array[2].split("[|]");				
		String sid = obrArray[2];
		
		String startTime = obrArray[7];
		String endTime = obrArray[8];
		
		
		log.info("session id: "  +sid);
		log.info("startTime: " + startTime);
		log.info("endTime: " + endTime);
		
		//이걸 한번에 저장해야 되나
		for (int i = 3; i < array.length; i++) { //3부터 OBX param 시작
			log.info("개행문자 기준으로 1차 파싱: " + array[i]);
			
			String[] splitSecondArray = array[i].split("[|]");
			
			//log.info("여기서는 되겠지? " + splitSecondArray[0]); 
			
			for(int j=0; j<splitSecondArray.length; j++) {
				//log.info("| 기준으로 2차 파싱: " + splitSecondArray[j]);	
					
//				MeasureData.builder()
//					.parame(splitSecondArray[3])
//					.value(splitSecondArray[5])
//					.endTime(StringToDate(startTime))
//					.startTime(StringToDate(endTime))
//					.sid(sid)
//					.build();
//					
				
				
									
				//splitSecondArray[2]
				
			}
															
		}	
		
		//StringToDate(startTime);
		Timestamp timestamp = stringToDate2(startTime);
		
		log.info("timestamp: " + timestamp);
	}
	
	
	
	
	
	public Timestamp StringToDate(String from)  {
	
		
		log.info("timestampe 형식에 맞춰서 파싱: " + from);
		
		//from = StringUtils.join(from,"-");
		
		Timestamp timestamp = Timestamp.valueOf(from);
		
		
		
		return timestamp;
	}
	
	
	
	public Timestamp stringToDate2(String from) {
		
		Timestamp timestamp = null;
		
		log.info("파싱 전 문자열: " + from);
		
		try {
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yy.MM.dd-hh:mm:ss.SSS");
		    
		    Date parsedDate = dateFormat.parse(from);
		    
		     timestamp = new java.sql.Timestamp(parsedDate.getTime());
		} catch(Exception e) { //this generic but you can control another types of exception
		    // look the origin of excption 
			log.info("Convert to Timestamp Error");
		}
		
	
		return timestamp;
	}
	
}
