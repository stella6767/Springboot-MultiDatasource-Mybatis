package net.lunalabs.central.service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableAsync
@Service
public class AsyncService {

	private static final Logger logger = LoggerFactory.getLogger(AsyncService.class);

	@Async // 비동기로 동작하는 메소드
	public void csSocketStart() {

		logger.info("CsSocketStart!!!!!");

		try {

			ServerSocketChannel serverSocketChannel = null;
			// SocketChannel socketChannel = null;

			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.socket().bind(new InetSocketAddress(5051));

			boolean bLoop = true;

			logger.info("CsSocketStart2!!!!!");

			while (bLoop) {
				logger.info("CsSocketStart3!!!!!");

				try {
					logger.info("CsSocketStart4!!!!!");

					SocketChannel socketChannel = serverSocketChannel.accept(); // 이 부분에서 연결이 될때까지 블로킹
					socketChannel.configureBlocking(true); // 블록킹 방식

					logger.info("[ESMLC Listen[" + "] Socket Accept EsmlcIfWorkThread Start");
					serverSocketThread(socketChannel);

				} catch (Exception e) {
					e.printStackTrace();
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}

				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	
	
	
	//Socket으로 단순히 HL7 데이터 읽기
	public void serverSocketThread(SocketChannel schn) throws IOException {
		
		 Charset charset = Charset.forName("UTF-8");

         // Client로부터 글자 받기
         ByteBuffer readBuf = ByteBuffer.allocate(10240);
         schn.read(readBuf); // 클라이언트로부터 데이터 읽기
         readBuf.flip();
         log.info("Received Data : " + charset.decode(readBuf).toString());

         
         HL7DataToJson(charset.decode(readBuf).toString());
         
	}
	
	
	
	public void HL7DataToJson(String HL7Data) {
		
		String[] splitEnterArray = HL7Data.split("[\\r\\n]+");
		
		for (int i = 0; i < splitEnterArray.length; i++) {
			logger.info("개행문자 기준으로 1차 파싱: " + splitEnterArray[i]);
		}
		
		
		
		
	}
	
	
	
	
	
	
	
	

//	@Async
//	public void FTPServiece() {
//		
//		boolean isFTPRunning = true;
//		
//		while(isFTPRunning) {
//			
//			
//			
//			
//		}
//		
//	}


}
