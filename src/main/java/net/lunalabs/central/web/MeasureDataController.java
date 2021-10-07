package net.lunalabs.central.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;
import net.lunalabs.central.config.MeasureDataSse;
import net.lunalabs.central.service.ServersentService;
import net.lunalabs.central.web.dto.CMRespDto;

@CrossOrigin
@RequiredArgsConstructor
@RestController
public class MeasureDataController {

	// 일단은 mysql 기준

	private static final Logger log = LoggerFactory.getLogger(MeasureDataController.class);

	private final ServersentService serversentService;


	@GetMapping("/measureData")
	public CMRespDto<?> findAllParmeAndPatient() {

		log.info("측정정보 뿌리기");

		return new CMRespDto<>(1, "한꺼번에 안 뿌리고 프론트에서 따로 받도록 설계할게요", null);
	}

	// data:실제값\n\n webflux
//	@GetMapping(value="/sse")//, produces = MediaType.TEXT_EVENT_STREAM_VALUE (default) // 발행
//	public Flux<ServerSentEvent<String>> sse() { //ServerSentEvent의 ContentType은 text event stream
//		return measureDataSink.sink.asFlux().map(e->ServerSentEvent.builder(e).build()).doOnCancel(()->{
//			log.info("SSE 종료됨");
//			measureDataSink.sink.asFlux().blockLast();
//		}); //구독
//	}
//	

	@GetMapping("/sse") //발행
	public SseEmitter streamDateTime() { // webmvc
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);  
        
        log.info("처음 등록된 emitter 주소"+ sseEmitter.toString());
        serversentService.register(sseEmitter);

		return sseEmitter;
	}

}
