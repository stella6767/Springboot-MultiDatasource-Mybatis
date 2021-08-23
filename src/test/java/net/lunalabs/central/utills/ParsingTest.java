package net.lunalabs.central.utills;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;



@Slf4j
public class ParsingTest {

	
	@Test
	public void parseBaseOnEnter() {
		
		String rawData = "MSH|^~\\&|BILABGW|NULL|RECEIVER|RECEIVER_FACILITY |2021-08-23 16:41:53||ORU^R01|47c65ee7-4d08-462e-bce0-2efeae4d96ba|P|2.8\r\n"
				+ "PID||Test|Patient_NHS_ID|NULL||NULL|NULL||||||||||||\r\n"
				+ "OBR||NULL|NULL|NULL|||2021-08-23 16:41:53||||||||||||||||||\r\n"
				+ "OBX|1|NM|CUBESCAN^SERIALNUMBER||8.596203821656049|L/min||||||||2021-08-23 16:41:53|\r\n"
				+ "OBX|2|NM|CUBESCAN^SERIALNUMBER||19|bpm||||||||2021-08-23 16:41:53|\r\n"
				+ "OBX|3|NA|CUBESCAN^SERIALNUMBER||83766671.93251821^83703921.63870761^83779506.46560705^83684245.16751479^83630464.05741148^83665499.37236984^83568326.53056866^83588762.55610713|mL||||||||2021-08-23 16:41:53|";
		
		
		String[] splitEnterArray = rawData.split("[\\r\\n]+");
		
	
		
		for (int i = 0; i < splitEnterArray.length; i++) {
			log.info("개행문자 기준으로 1차 파싱: " + splitEnterArray[i]);
			
			String[] splitSecondArray = splitEnterArray[i].split("[|]");
			
			if(splitEnterArray[i].contains("ORU^R01")) {
						
				for(int j=0; j<splitSecondArray.length; j++) {
					log.info("| 기준으로 2차 파싱: " + splitSecondArray[j]);
					
						
					
					
				}
				
			}
			

							
			//StringUtils		
		}
		

		
	}
	
	
}
