package net.lunalabs.central.config;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Configuration;

import net.lunalabs.central.domain.measuredata.MeasureData;
import reactor.core.publisher.Sinks;

@Configuration
public class MeasureDataSse {

	public ConcurrentHashMap<String, MeasureData> concurrentHashMap = new ConcurrentHashMap<>();
	
	public Sinks.Many<String> sink = Sinks.many().multicast().onBackpressureBuffer(); //webflux
	
	//public SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);  //webmvc
	//public final ConcurrentHashSet<SseEmitter> emitters = new ConcurrentHashSet<>();  //multi-Thread-Safe한 Collection 객체를 생성
	
	public String sseData;
	
}
