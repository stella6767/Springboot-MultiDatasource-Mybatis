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
import net.lunalabs.central.service.AsyncService;


@RequiredArgsConstructor
@Configuration 
public class CSSocket {


	private static final Logger logger = LoggerFactory.getLogger(CSSocket.class);

	private final AsyncService asyncService;

	
	@PostConstruct //객체가 생성된 후 바로 실행, @PreDestory 마지막 소멸 단계 때 실행
	public void start(){
		
		asyncService.csSocketStart();				
		
	}
	
	
	
}
