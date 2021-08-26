package net.lunalabs.central.utills;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import net.lunalabs.central.service.GWSocketService;


@RequiredArgsConstructor
@Configuration 
public class GWEmulator {


	private static final Logger logger = LoggerFactory.getLogger(GWEmulator.class);

	private final GWSocketService gWSocketService;

	
	@PostConstruct //객체가 생성된 후 바로 실행, @PreDestory 마지막 소멸 단계 때 실행
	public void start(){
		
		gWSocketService.csSocketStart();				
		
	}
	
	
	
}
