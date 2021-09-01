package net.lunalabs.central.config;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import net.lunalabs.central.domain.mysql.measuredata.MeasureData;
import reactor.core.publisher.Sinks;

@Configuration
public class MeasureDataSse {

	public ConcurrentHashMap<String, MeasureData> concurrentHashMap = new ConcurrentHashMap<>();
	
	public Sinks.Many<String> sink = Sinks.many().multicast().onBackpressureBuffer(); //webflux
	
	//public SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);  //webmvc
	public final Collection<SseEmitter> emitters = Collections.synchronizedCollection(new HashSet<SseEmitter>());  //multi-Thread-Safe한 Collection 객체를 생성
	
	public String sseData;
	
}
