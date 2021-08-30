package net.lunalabs.central.config;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Configuration;

import net.lunalabs.central.domain.mysql.measuredata.MeasureData;
import reactor.core.publisher.Sinks;

@Configuration
public class MeasureDataSink {

	public ConcurrentHashMap<String, MeasureData> concurrentHashMap = new ConcurrentHashMap<>();
	
	public Sinks.Many<String> sink = Sinks.many().multicast().onBackpressureBuffer();
	
}
