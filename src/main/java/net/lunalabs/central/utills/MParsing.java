package net.lunalabs.central.utills;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MParsing {

	public static Timestamp stringToDate(String from) {

		Timestamp timestamp = null;

		log.info("파싱 전 문자열: " + from);

		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yy.MM.dd-hh:mm:ss.SSS"); //형식을 맞춰줘야함ㄴ

			Date parsedDate = dateFormat.parse(from);

			timestamp = new java.sql.Timestamp(parsedDate.getTime());
		} catch (Exception e) { // this generic but you can control another types of exception
			// look the origin of excption
			log.info("Convert to Timestamp Error");
		}

		log.info("timeStamp 초 단위이하 표기 check: " + timestamp);
		return timestamp;
	}
	
	
	public static String parseLocalDateTime() {

		String dummydate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

		return dummydate;
	}
	
	
	public static ByteBuffer str_to_bb(String msg) {
		Charset charset = Charset.forName("UTF-8");
		CharsetEncoder encoder = charset.newEncoder();
		CharsetDecoder decoder = charset.newDecoder();
		try {
			return encoder.encode(CharBuffer.wrap(msg));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static String getNowTime(Integer i){ //1이면 startTime, 아니면 endTime
	    long now = System.currentTimeMillis();
	    Date mDate = new Date(now);
	    
	    Calendar cal = Calendar.getInstance();
        cal.setTime(mDate);
        
        cal.add(Calendar.MILLISECOND, 20);

	    SimpleDateFormat sdfNow = new SimpleDateFormat("yy.MM.dd-HH:mm:ss:SS");
	    
	    String Time;
	    
	    if(i==1) {
	    	
		    Time = sdfNow.format(mDate);

	    }else {
	    	 Time = sdfNow.format(cal.getTime());
	    }
	    
	    String nowTime =Time;
	    return nowTime;

	    }
	

}
