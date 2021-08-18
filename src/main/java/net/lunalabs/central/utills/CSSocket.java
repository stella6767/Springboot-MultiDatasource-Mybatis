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

	
	@PostConstruct
	public void start(){
		
		//System.out.println("로그가 안 찍히네?");
		
		
		logger.info("CsSocketStart!!!!!");
		
		try {
			
			ServerSocketChannel serverSocketChannel = null;
			//SocketChannel socketChannel = null;
			
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.socket().bind(new InetSocketAddress (5051));
						
			boolean bLoop = true;
			
			logger.info("CsSocketStart2!!!!!");

			
			while (bLoop) {
				logger.info("CsSocketStart3!!!!!");

				
				try {
					logger.info("CsSocketStart4!!!!!");

					SocketChannel socketChannel = serverSocketChannel.accept();
					socketChannel.configureBlocking(true);
				

					//System.out.println("[ESMLC Listen[" + "] Socket Accept EsmlcIfWorkThread Start");
					logger.info("[ESMLC Listen[" + "] Socket Accept EsmlcIfWorkThread Start");					
					asyncService.serverSocketThread(socketChannel);				
					
					
				} catch (Exception e) {
					e.printStackTrace();
					try{
						Thread.sleep(5000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}

				try{
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e1) {
			e1.printStackTrace();
		}
		
	}
	
	
	
}
