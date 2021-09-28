package net.lunalabs.central.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import net.lunalabs.central.config.MeasureDataSse;
import net.lunalabs.central.domain.mysql.MeasureDataJoinPatientBean;

@RequiredArgsConstructor
@Service
public class ServersentService {

	
	private static final Logger logger = LoggerFactory.getLogger(ServersentService.class);
	
	ObjectMapper objectMapper = new ObjectMapper();
	Random random = new Random();
	
	
	private final MeasureDataSse measureDataSse;
	
	public void register(SseEmitter emitter) {
		
		logger.info("브라우저마다 eventsource 연결할때마다 emitter 객체 생성하여 등록(새로고침 시 혹은 페이지 이동, 새로 브라우저 띄울때마다");
		
	    emitter.onTimeout(() -> timeout(emitter));
	    emitter.onCompletion(() -> complete(emitter));

	    measureDataSse.emitters.add(emitter);
	}

	private void complete(SseEmitter emitter) {
	    System.out.println("emitter completed");
	    measureDataSse.emitters.remove(emitter);
	}

	private void timeout(SseEmitter emitter) {
	    System.out.println("emitter timeout");
	    measureDataSse.emitters.remove(emitter);
	}


	public void sendSseEventsToUI(String seeMeasurePatientData, String eventName) { 
		
		logger.info("서버에서 단방향으로 브라우저에 보낼 데이터: "+seeMeasurePatientData);
		
		Iterator<SseEmitter> iter = measureDataSse.emitters.iterator();

		while (iter.hasNext()) {
		    SseEmitter emitter = iter.next();
		   
	        try {
	        	logger.info("data 보내는 객체 주소: " + emitter.toString());
//	            emitter.send(SseEmitter.event().reconnectTime(500).data(seeMeasurePatientData), MediaType.APPLICATION_JSON);
	            emitter.send(SseEmitter.event().name(eventName).reconnectTime(500).data(seeMeasurePatientData));
	        } catch (Throwable e) {
	            emitter.complete();
	        }
		}
		
	}
	
	

	//@Scheduled(fixedDelay = 100) //3초마다 실행, 테스트용도
	public void sendSseEventsToUITest2() throws JsonProcessingException { 
	

		HashMap<String, MeasureDataJoinPatientBean> hashMap = new HashMap<>();
		

			MeasureDataJoinPatientBean dataJoinPatientBean = MeasureDataJoinPatientBean.builder()
					// .deviceId() 굳이?
					.age(28)
//					.endTime("2021-07-16 03:09:06.868000000")
//					.startTime("2021-07-16 03:09:06.766000000")
					.parame("rvs")
					.value(((random.nextInt(100)+1)+"")+"^"+((random.nextInt(100)+1)))
             		//.value(random.ints()^)
					.patientUserId("patient 10")
					.sid("patient10_20210826_114616")
					.valueUnit("LM")
					.build();
			

			hashMap.put(dataJoinPatientBean.getSid(), dataJoinPatientBean);
			
			String seeMeasurePatientData = objectMapper.writeValueAsString(dataJoinPatientBean);
			//String seeMeasurePatientData = objectMapper.writeValueAsString(hashMap);
			
		
			logger.info("서버에서 단방향으로 브라우저에 보낼 데이터: "+seeMeasurePatientData );						
			
			Iterator<SseEmitter> iter = measureDataSse.emitters.iterator();

			while (iter.hasNext()) {
			    SseEmitter emitter = iter.next();
			   
		        try {
		        	logger.info("data 보내는 객체 주소: " + emitter.toString());
//		            emitter.send(SseEmitter.event().reconnectTime(500).data(seeMeasurePatientData));
		            emitter.send(SseEmitter.event().name("CPM0000").reconnectTime(500).data(seeMeasurePatientData));
		         
		            Thread.sleep(1000);
		        } catch (Throwable e) {
		            emitter.complete();
		        }
		
			
			
		}
	}
	
	
	
	
	  //@Scheduled(fixedDelay = 100) //3초마다 실행, 테스트용도
	  public void sendSseEventsToUITest() throws JsonProcessingException {

	    String[] parames ={"mv","rr","rvs","spo2","tv"};

	    String[] sessionId = {"session1","session2","session3","session4","session5"};


	    HashMap<String, MeasureDataJoinPatientBean> hashMap = new HashMap<>();

	    for(int i=0; i<parames.length; i++) {
	      MeasureDataJoinPatientBean dataJoinPatientBean = MeasureDataJoinPatientBean.builder()
	          // .deviceId() 굳이?
	          .age(28)
	          //.parame(parames[i])
	          .parame("rvs")
	          .value(((random.nextInt(100)+1)+"")+"^"+((random.nextInt(100)+1)+"")+"^"+((random.nextInt(100)+1)+"")+"^"+((random.nextInt(500)+1)+"")+"^"+((random.nextInt(300)+1)+""))
	          .patientUserId("patient 10")
	          .sid("patient10_20210826_114616")
	          .valueUnit("LM")
	          .build();

	      hashMap.put(dataJoinPatientBean.getSid(), dataJoinPatientBean);

	      String seeMeasurePatientData = objectMapper.writeValueAsString(dataJoinPatientBean);
	      //String seeMeasurePatientData = objectMapper.writeValueAsString(hashMap);


	      logger.info("서버에서 단방향으로 브라우저에 보낼 데이터: "+seeMeasurePatientData);

	      Iterator<SseEmitter> iter = measureDataSse.emitters.iterator();

	      while (iter.hasNext()) {
	          SseEmitter emitter = iter.next();

	            try {
	              logger.info("data 보내는 객체 주소: " + emitter.toString());
	                //emitter.send(SseEmitter.event().reconnectTime(500).data(seeMeasurePatientData));
	          emitter.send(SseEmitter.event().name("CPM0000").reconnectTime(500).data(seeMeasurePatientData));
	         // emitter.send(SseEmitter.event().name("CPM0005").reconnectTime(500).data(seeMeasurePatientData));

	                Thread.sleep(1000);
	            } catch (Throwable e) {
	                emitter.complete();
	            }
	      }


	    }
	  }
	
}


