package net.lunalabs.central.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.mina.util.ConcurrentHashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import net.lunalabs.central.config.MeasureDataSse;
import net.lunalabs.central.domain.mysql.MeasureDataJoinPatientBean;
import net.lunalabs.central.utills.MParsing;

@RequiredArgsConstructor
@Service
public class ServersentService {

	
	private static final Logger logger = LoggerFactory.getLogger(ServersentService.class);
	
	ObjectMapper objectMapper = new ObjectMapper();
	Random random = new Random();
    //private Set<SseEmitter> sseEmitters = new HashSet<SseEmitter>();
    private final ConcurrentHashSet<SseEmitter> sseEmitters = new ConcurrentHashSet<>();
	
	
	public void register(SseEmitter emitter) {
		
		logger.info("브라우저마다 eventsource 연결할때마다 emitter 객체 생성하여 등록(새로고침 시 혹은 페이지 이동, 새로 브라우저 띄울때마다");
		
		emitter.onCompletion(() -> {
            synchronized (this.sseEmitters) {
                this.sseEmitters.remove(emitter);
            }
        });

		emitter.onTimeout(()-> {
			emitter.complete();
        });

        // Put context in a map
        sseEmitters.add(emitter);
	}


	//@Cacheable(value="kang")
	//@Async
	public void sendSseEventsToUI(String seeMeasurePatientData, String eventName) { // ConcurrentModificationException
		
		//logger.info(eventName + ":  서버에서 단방향으로 브라우저에 보낼 데이터: "+seeMeasurePatientData);
		
		
		if(seeMeasurePatientData.contains("rvs")) {
			logger.info(eventName + ":  서버에서 단방향으로 브라우저에 보낼 데이터: "+seeMeasurePatientData);
		}
		
		
		Iterator<SseEmitter> iter = sseEmitters.iterator();

		while (iter.hasNext()) {
		    SseEmitter emitter = iter.next();
		   
	        try {
	        	logger.info("data 보내는 객체 주소: " + emitter.toString());
//	            emitter.send(SseEmitter.event().reconnectTime(500).data(seeMeasurePatientData), MediaType.APPLICATION_JSON);
	            emitter.send(SseEmitter.event().name(eventName).reconnectTime(500).data(seeMeasurePatientData));
	        } catch (Throwable e) {
	            //emitter.complete();
	        	iter.remove();
	        }
		}
		
	}
	
	
	//@Scheduled(fixedRate = 100) //3초마다 실행, 테스트용도
	@Async
	public void sendSseEventsToUITest2() throws JsonProcessingException { 
	
		
		//Thread.sleep(3000);

		HashMap<String, MeasureDataJoinPatientBean> hashMap = new HashMap<>();
		

			MeasureDataJoinPatientBean dataJoinPatientBean = MeasureDataJoinPatientBean.builder()
					// .deviceId() 굳이?
					.age(28)
					.endTime(MParsing.getNowTime(2))
					.startTime(MParsing.getNowTime(1))
					.parame("rvs")
					.value(random.nextInt(100)+1 + "^" +(random.nextInt(100)+1))
             		//.value(random.ints()^)
					.patientUserId("patient 10")
					.sid("patient10_20210826_114616")
					.valueUnit("LM")
					.build();
			

			hashMap.put(dataJoinPatientBean.getSid(), dataJoinPatientBean);
			
			String seeMeasurePatientData = objectMapper.writeValueAsString(dataJoinPatientBean);
			//String seeMeasurePatientData = objectMapper.writeValueAsString(hashMap);
			
		
			logger.info("서버에서 단방향으로 브라우저에 보낼 데이터: "+seeMeasurePatientData );						
			
			Iterator<SseEmitter> iter = sseEmitters.iterator();

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
	
	//@Scheduled(fixedRate = 100) //3초마다 실행, 테스트용도
	@Async
	public void sendSseEventsToUITest3() throws JsonProcessingException { 
	
		
		//Thread.sleep(3000);

		HashMap<String, MeasureDataJoinPatientBean> hashMap = new HashMap<>();
		

			MeasureDataJoinPatientBean dataJoinPatientBean = MeasureDataJoinPatientBean.builder()
					// .deviceId() 굳이?
					.age(28)
					.endTime(MParsing.getNowTime(2))
					.startTime(MParsing.getNowTime(1))
					.parame("rvs")
					.value((random.nextInt(10)+1) + "^" +(random.nextInt(10)+1))
             		//.value(random.ints()^)
					.patientUserId("patient 10")
					.sid("patient10_20210826_114616")
					.valueUnit("LM")
					.build();
			

			hashMap.put(dataJoinPatientBean.getSid(), dataJoinPatientBean);
			
			String seeMeasurePatientData = objectMapper.writeValueAsString(dataJoinPatientBean);
			//String seeMeasurePatientData = objectMapper.writeValueAsString(hashMap);
			
		
			logger.info("서버에서 단방향으로 브라우저에 보낼 데이터: "+seeMeasurePatientData );						
			
			Iterator<SseEmitter> iter = sseEmitters.iterator();

			while (iter.hasNext()) {
			    SseEmitter emitter = iter.next();
			   
		        try {
		        	logger.info("data 보내는 객체 주소: " + emitter.toString());
//		            emitter.send(SseEmitter.event().reconnectTime(500).data(seeMeasurePatientData));
		            emitter.send(SseEmitter.event().name("CPM0001").reconnectTime(500).data(seeMeasurePatientData));
		         
		            Thread.sleep(1000);
		        } catch (Throwable e) {
		            emitter.complete();
		        }
		
			
			
		}
	}
	
	//@Scheduled(fixedRate = 100) //3초마다 실행, 테스트용도
	@Async
	public void sendSseEventsToUITest4() throws JsonProcessingException { 
	
		
		//Thread.sleep(3000);

		HashMap<String, MeasureDataJoinPatientBean> hashMap = new HashMap<>();
		

			MeasureDataJoinPatientBean dataJoinPatientBean = MeasureDataJoinPatientBean.builder()
					// .deviceId() 굳이?
					.age(28)
					.endTime(MParsing.getNowTime(2))
					.startTime(MParsing.getNowTime(1))
					.parame("rvs")
					.value(random.nextInt(100)+1 + "^" +(random.nextInt(100)+1))
             		//.value(random.ints()^)
					.patientUserId("patient 10")
					.sid("patient10_20210826_114616")
					.valueUnit("LM")
					.build();
			

			hashMap.put(dataJoinPatientBean.getSid(), dataJoinPatientBean);
			
			String seeMeasurePatientData = objectMapper.writeValueAsString(dataJoinPatientBean);
			//String seeMeasurePatientData = objectMapper.writeValueAsString(hashMap);
			
		
			logger.info("서버에서 단방향으로 브라우저에 보낼 데이터: "+seeMeasurePatientData );						
			
			Iterator<SseEmitter> iter = sseEmitters.iterator();

			while (iter.hasNext()) {
			    SseEmitter emitter = iter.next();
			   
		        try {
		        	logger.info("data 보내는 객체 주소: " + emitter.toString());
//		            emitter.send(SseEmitter.event().reconnectTime(500).data(seeMeasurePatientData));
		            emitter.send(SseEmitter.event().name("CPM0002").reconnectTime(500).data(seeMeasurePatientData));
		         
		            Thread.sleep(1000);
		        } catch (Throwable e) {
		            emitter.complete();
		        }
		
			
			
		}
	}
	
	//@Scheduled(fixedRate = 100) //3초마다 실행, 테스트용도
	@Async
	public void sendSseEventsToUITest5() throws JsonProcessingException { 
	
		
		//Thread.sleep(3000);

		HashMap<String, MeasureDataJoinPatientBean> hashMap = new HashMap<>();
		

			MeasureDataJoinPatientBean dataJoinPatientBean = MeasureDataJoinPatientBean.builder()
					// .deviceId() 굳이?
					.age(28)
					.endTime(MParsing.getNowTime(2))
					.startTime(MParsing.getNowTime(1))
					.parame("rvs")
					.value(random.nextInt(100)+1 + "^" +(random.nextInt(100)+1))
             		//.value(random.ints()^)
					.patientUserId("patient 10")
					.sid("patient10_20210826_114616")
					.valueUnit("LM")
					.build();
			

			hashMap.put(dataJoinPatientBean.getSid(), dataJoinPatientBean);
			
			String seeMeasurePatientData = objectMapper.writeValueAsString(dataJoinPatientBean);
			//String seeMeasurePatientData = objectMapper.writeValueAsString(hashMap);
			
		
			logger.info("서버에서 단방향으로 브라우저에 보낼 데이터: "+seeMeasurePatientData );						
			
			Iterator<SseEmitter> iter = sseEmitters.iterator();

			while (iter.hasNext()) {
			    SseEmitter emitter = iter.next();
			   
		        try {
		        	logger.info("data 보내는 객체 주소: " + emitter.toString());
//		            emitter.send(SseEmitter.event().reconnectTime(500).data(seeMeasurePatientData));
		            emitter.send(SseEmitter.event().name("CPM0003").reconnectTime(500).data(seeMeasurePatientData));
		         
		            Thread.sleep(1000);
		        } catch (Throwable e) {
		            emitter.complete();
		        }
		
			
			
		}
	}

	
	
	//@Scheduled(fixedRate = 100) //3초마다 실행, 테스트용도
	@Async
	public void sendSseEventsToUITest44() throws JsonProcessingException { 
	
		
		//Thread.sleep(3000);

		HashMap<String, MeasureDataJoinPatientBean> hashMap = new HashMap<>();
		

			MeasureDataJoinPatientBean dataJoinPatientBean = MeasureDataJoinPatientBean.builder()
					// .deviceId() 굳이?
					.age(28)
					.endTime(MParsing.getNowTime(2))
					.startTime(MParsing.getNowTime(1))
					.parame("rvs")
					.value(random.nextInt(100)+1 + "^" +(random.nextInt(100)+1))
             		//.value(random.ints()^)
					.patientUserId("patient 10")
					.sid("patient10_20210826_114616")
					.valueUnit("LM")
					.build();
			

			hashMap.put(dataJoinPatientBean.getSid(), dataJoinPatientBean);
			
			String seeMeasurePatientData = objectMapper.writeValueAsString(dataJoinPatientBean);
			//String seeMeasurePatientData = objectMapper.writeValueAsString(hashMap);
			
		
			logger.info("서버에서 단방향으로 브라우저에 보낼 데이터: "+seeMeasurePatientData );						
			
			Iterator<SseEmitter> iter = sseEmitters.iterator();

			while (iter.hasNext()) {
			    SseEmitter emitter = iter.next();
			   
		        try {
		        	logger.info("data 보내는 객체 주소: " + emitter.toString());
//		            emitter.send(SseEmitter.event().reconnectTime(500).data(seeMeasurePatientData));
		            emitter.send(SseEmitter.event().name("CPM0004").reconnectTime(500).data(seeMeasurePatientData));
		         
		            Thread.sleep(1000);
		        } catch (Throwable e) {
		            emitter.complete();
		        }
		
			
			
		}
	}
	
	
	
	//@Scheduled(fixedRate = 100) //3초마다 실행, 테스트용도
	@Async
	public void sendSseEventsToUITest55() throws JsonProcessingException { 
	
		
		//Thread.sleep(3000);

		HashMap<String, MeasureDataJoinPatientBean> hashMap = new HashMap<>();
		

			MeasureDataJoinPatientBean dataJoinPatientBean = MeasureDataJoinPatientBean.builder()
					// .deviceId() 굳이?
					.age(28)
					.endTime(MParsing.getNowTime(2))
					.startTime(MParsing.getNowTime(1))
					.parame("rvs")
					.value(random.nextInt(100)+1 + "^" +(random.nextInt(100)+1))
             		//.value(random.ints()^)
					.patientUserId("patient 10")
					.sid("patient10_20210826_114616")
					.valueUnit("LM")
					.build();
			

			hashMap.put(dataJoinPatientBean.getSid(), dataJoinPatientBean);
			
			String seeMeasurePatientData = objectMapper.writeValueAsString(dataJoinPatientBean);
			//String seeMeasurePatientData = objectMapper.writeValueAsString(hashMap);
			
		
			logger.info("서버에서 단방향으로 브라우저에 보낼 데이터: "+seeMeasurePatientData );						
			
			Iterator<SseEmitter> iter = sseEmitters.iterator();

			while (iter.hasNext()) {
			    SseEmitter emitter = iter.next();
			   
		        try {
		        	logger.info("data 보내는 객체 주소: " + emitter.toString());
//		            emitter.send(SseEmitter.event().reconnectTime(500).data(seeMeasurePatientData));
		            emitter.send(SseEmitter.event().name("CPM0005").reconnectTime(500).data(seeMeasurePatientData));
		         
		            Thread.sleep(1000);
		        } catch (Throwable e) {
		            emitter.complete();
		        }
		
			
			
		}
	}
	
	
	
	//@Scheduled(fixedRate = 100) //3초마다 실행, 테스트용도
	@Async
	public void sendSseEventsToUITest6() throws JsonProcessingException { 
	
		
		//Thread.sleep(3000);

		HashMap<String, MeasureDataJoinPatientBean> hashMap = new HashMap<>();
		

			MeasureDataJoinPatientBean dataJoinPatientBean = MeasureDataJoinPatientBean.builder()
					// .deviceId() 굳이?
					.age(28)
					.endTime(MParsing.getNowTime(2))
					.startTime(MParsing.getNowTime(1))
					.parame("rvs")
					.value(random.nextInt(100)+1 + "^" +(random.nextInt(100)+1))
             		//.value(random.ints()^)
					.patientUserId("patient 10")
					.sid("patient10_20210826_114616")
					.valueUnit("LM")
					.build();
			

			hashMap.put(dataJoinPatientBean.getSid(), dataJoinPatientBean);
			
			String seeMeasurePatientData = objectMapper.writeValueAsString(dataJoinPatientBean);
			//String seeMeasurePatientData = objectMapper.writeValueAsString(hashMap);
			
		
			logger.info("서버에서 단방향으로 브라우저에 보낼 데이터: "+seeMeasurePatientData );						
			
			Iterator<SseEmitter> iter = sseEmitters.iterator();

			while (iter.hasNext()) {
			    SseEmitter emitter = iter.next();
			   
		        try {
		        	logger.info("data 보내는 객체 주소: " + emitter.toString());
//		            emitter.send(SseEmitter.event().reconnectTime(500).data(seeMeasurePatientData));
		            emitter.send(SseEmitter.event().name("CPM0006").reconnectTime(500).data(seeMeasurePatientData));
		         
		            Thread.sleep(1000);
		        } catch (Throwable e) {
		            emitter.complete();
		        }
		
			
			
		}
	}
	
	
	
	//@Scheduled(fixedRate = 100) //3초마다 실행, 테스트용도
	@Async
	public void sendSseEventsToUITest7() throws JsonProcessingException { 
	
		
		//Thread.sleep(3000);

		HashMap<String, MeasureDataJoinPatientBean> hashMap = new HashMap<>();
		

			MeasureDataJoinPatientBean dataJoinPatientBean = MeasureDataJoinPatientBean.builder()
					// .deviceId() 굳이?
					.age(28)
					.endTime(MParsing.getNowTime(2))
					.startTime(MParsing.getNowTime(1))
					.parame("rvs")
					.value(random.nextInt(100)+1 + "^" +(random.nextInt(100)+1))
             		//.value(random.ints()^)
					.patientUserId("patient 10")
					.sid("patient10_20210826_114616")
					.valueUnit("LM")
					.build();
			

			hashMap.put(dataJoinPatientBean.getSid(), dataJoinPatientBean);
			
			String seeMeasurePatientData = objectMapper.writeValueAsString(dataJoinPatientBean);
			//String seeMeasurePatientData = objectMapper.writeValueAsString(hashMap);
			
		
			logger.info("서버에서 단방향으로 브라우저에 보낼 데이터: "+seeMeasurePatientData );						
			
			Iterator<SseEmitter> iter = sseEmitters.iterator();

			while (iter.hasNext()) {
			    SseEmitter emitter = iter.next();
			   
		        try {
		        	logger.info("data 보내는 객체 주소: " + emitter.toString());
//		            emitter.send(SseEmitter.event().reconnectTime(500).data(seeMeasurePatientData));
		            emitter.send(SseEmitter.event().name("CPM0007").reconnectTime(500).data(seeMeasurePatientData));
		         
		            Thread.sleep(1000);
		        } catch (Throwable e) {
		            emitter.complete();
		        }
		
			
			
		}
	}
	
}


