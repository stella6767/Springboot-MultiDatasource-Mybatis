package net.lunalabs.central.utills;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MParsing {

	public static Timestamp stringToDate(String from) {

		Timestamp timestamp = null;

		log.info("파싱 전 문자열: " + from);

		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yy.MM.dd-hh:mm:ss.SSS");

			Date parsedDate = dateFormat.parse(from);

			timestamp = new java.sql.Timestamp(parsedDate.getTime());
		} catch (Exception e) { // this generic but you can control another types of exception
			// look the origin of excption
			log.info("Convert to Timestamp Error");
		}

		log.info("timeStamp 초 단위이하 표기 check: " + timestamp);
		return timestamp;
	}

}
