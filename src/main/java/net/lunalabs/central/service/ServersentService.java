package net.lunalabs.central.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;
import net.lunalabs.central.config.MeasureDataSse;

@RequiredArgsConstructor
@Service
public class ServersentService {

	
	private static final Logger logger = LoggerFactory.getLogger(ServersentService.class);

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

	//@Scheduled(fixedDelay = 3000)
	public void sendSseEventsToUI(String seeMeasurePatientData) { 
		
		logger.info("서버에서 단방향으로 브라우저에 보낼 데이터: "+seeMeasurePatientData);
		
		Iterator<SseEmitter> iter = measureDataSse.emitters.iterator();

		while (iter.hasNext()) {
		    SseEmitter emitter = iter.next();
		   
	        try {
	        	logger.info("data 보내는 객체 주소: " + emitter.toString());
//	            emitter.send(SseEmitter.event().reconnectTime(500).data(seeMeasurePatientData), MediaType.APPLICATION_JSON);
	            emitter.send(SseEmitter.event().reconnectTime(500).data(seeMeasurePatientData));
	        } catch (Throwable e) {
	            emitter.complete();
	        }
		}
		 		
		
//	    for(SseEmitter emitter : measureDataSse.emitters) {
//	    	
//	    	logger.info(emitter.toString());
//	        try {
//	        	
//	        	logger.info("보냈는데?");
//	            emitter.send(SseEmitter.event().reconnectTime(500).data(seeMeasurePatientData), MediaType.APPLICATION_JSON);
//	        } catch (Throwable e) {
//	            emitter.complete();
//	        }
//	    };
	}
	
}


