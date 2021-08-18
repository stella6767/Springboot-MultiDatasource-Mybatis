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
	public void serverSocketThread() {

		SocketChannel schn = null;
		
		boolean bLoop = true; //1번째 조건문

		boolean isRunning = true; //2번쨰 while문 조건

		logger.info("CsSocketStart!!!!!");
		try {

			ServerSocketChannel serverSocketChannel = null;
			// SocketChannel socketChannel = null;

			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.socket().bind(new InetSocketAddress(5051));


			logger.info("CsSocketStart2!!!!!");

			while (bLoop) {
				logger.info("CsSocketStart3!!!!!");

				try {
					logger.info("CsSocketStart4!!!!!");

					schn = serverSocketChannel.accept();
					schn.configureBlocking(true);

					// System.out.println("[ESMLC Listen[" + "] Socket Accept EsmlcIfWorkThread
					// Start");
					logger.info("[ESMLC Listen[" + "] Socket Accept EsmlcIfWorkThread Start");

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
		
		
		

		while (isRunning) {

			try {

				int byteCount = 0;
				byte[] readByteArr;

				long lThId = Thread.currentThread().getId();
				ByteBuffer readBuf = ByteBuffer.allocate(10240);

				log.info("첫번째  while문");

				String result = ""; // 요기서 초기화, 요까지는 다 처리하고 가야된다.

				Charset charset = Charset.forName("UTF-8");

				while (byteCount >= 0) {
					long time = System.currentTimeMillis();
					String strDT;

					log.info("두번째  while문");

					try {

						Thread.sleep(1000);

						byteCount = schn.read(readBuf); // 소켓채널에서 한번에 초과되는 버퍼사이즈의 데이터가 들어오면..
						readBuf.flip();

						log.info("[gwEmulThread #100] TID[" + "] byteCount :  " + byteCount);

					} catch (Exception e) {

						e.printStackTrace();
						// AsynchronousCloseException

						schn.close();
						isRunning = false;
						break;
					}

					int i = 0;

					// 버퍼에 값이 있다면 계속 버퍼에서 값을 읽어 result 를 완성한다.
					// while (byteCount > 0 || isRunning) {
					while (byteCount > 0) {

						// 입력된 데이터를 읽기 위해 read-mode로 바꿈, positon이 데이터의 시작인 0으로 이동

						readByteArr = new byte[readBuf.remaining()]; // 현재 위치에서 limit까지 읽어드릴 수 있는 데이터의 개수를 리턴
						readBuf.get(readByteArr); // 데이터 읽기

						result = result + new String(readByteArr, Charset.forName("UTF-8")); // 어차피 여기서 계속 더하니까.

						log.info("[gwEmulThread #200] TID[ " + lThId + "] socketRead Start[" + result + "], byteCount["
								+ byteCount + "], i[" + i + "]");
						i++;

						try {
							byteCount = schn.read(readBuf);
							log.info("[gwEmulThread #210] TID[" + result + "] byteCount :  " + byteCount);
						} catch (Exception e) {
							e.printStackTrace();
							// break;
						}

						boolean bEtxEnd = true; // 아래 while문을 실행할지 안할지

					} // byteCount > 0

					Thread.sleep(100);
					// Thread.sleep(1000); //일단 1초로
				} // 무한루프

				schn.close(); // 소켓 닫기

			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}

	}
	
	
	
	
	//
	
	
	
	
	
	
	
	
	
	
	
	

}
